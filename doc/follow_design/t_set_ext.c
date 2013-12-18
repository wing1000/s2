
#include "redis.h"

static inline int qsortCompareSetsBySize(const void *s1, const void *s2) {
    return setTypeSize(*(robj**)s1)-setTypeSize(*(robj**)s2);
}

//enc size is used sizeof(type), so enc is size
static inline int32_t encSize(int32_t enc) {
	return enc;
}


static inline int getDirectType(sds s, char *d, char *t) {
	struct sdshdr *sh;
	int len;
	sh = (void*) (s - (sizeof(struct sdshdr)));
	len = sh->len;
	if (len <= 2)
		return -1;
	*t = sh->buf[len - 1];
	*d = sh->buf[len - 2];
	return 0;
}



static inline void addReplyBuffer(redisClient *c, char *ptr, size_t size) {
	addReplyBulkCBuffer(c, ptr, size);
}

 


static void intsetPageDump(redisClient *c, intset *s_iset, long long offset, long long limits) {
	long long max = 0;
	void *replylen = NULL;
	int32_t isize = 0;
	if(offset >= s_iset->length || offset < 0) {
		addReply(c, shared.emptymultibulk);
		return;
	}
	max = offset + limits;
	max = max < s_iset->length? max: s_iset->length;
	replylen = addDeferredMultiBulkLength(c);
	isize = encSize(s_iset->encoding);
	addReplyBulkLongLong(c, isize);
	addReplyBuffer(c, (char*)s_iset->contents + offset * isize, (max - offset) * isize);
	setDeferredMultiBulkLength(c, replylen, 2);
}

void spmembersComamnd(redisClient *c) {
	robj *set = NULL;
	long long offset = 0, limits = 0;
	void *ptr = NULL;
	//get offset
	ptr = c->argv[2]->ptr;
	if (string2ll(ptr, sdslen(ptr), &offset) < 1) {
		addReply(c, shared.emptymultibulk);
		return;
	}
	//get limits
	ptr = c->argv[3]->ptr;
	if (string2ll(ptr, sdslen(ptr), &limits) < 1) {
		addReply(c, shared.emptymultibulk);
		return;
	}
	if ((set = lookupKeyReadOrReply(c,c->argv[1],shared.emptymultibulk)) == NULL)
		return;
	if(set->encoding != REDIS_ENCODING_INTSET) {
		addReply(c,shared.wrongtypeerr);
		return;
	}
	intsetPageDump(c, (intset*)set->ptr, offset, limits);
}

static inline char getCacheForwardChar(char f) {
	if(f == 'F')
		return 'M';
	else
		return 'N';
}
