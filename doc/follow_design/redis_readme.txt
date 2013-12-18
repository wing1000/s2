redis.h中

1212：void bitcountCommand(redisClient *c);
1213：void replconfCommand(redisClient *c);
添加：
void spmembersComamnd(redisClient *c);

-------------------------

redis.c 中
116：struct redisCommand redisCommandTable[] = {  
...
254：{"bitcount",bitcountCommand,-2,"r",0,NULL,1,1,1,0,0},
添加，注意逗号：   
2.4.x:  {"spmembers",spmembersComamnd,4,REDIS_CMD_DENYOOM,NULL,1,-1,1}
2.6.x:  {"spmembers",spmembersComamnd,4,"rS",REDIS_CMD_DENYOOM,NULL,1,-1,1,0,0}
参考smembers
    //{"smembers",sinterCommand,2,"rS",0,                    NULL,1,1,1,0,0},
    //{"smembers",sinterCommand,2,     0,                    NULL,1,1,1},
      {"spmembers",spmembersComamnd,4,"r",0,NULL,1,-1,1,0,0}
---------------------

Makefile.dep

98：t_set.o: t_set.c redis.h fmacros.h config.h ../deps/lua/src/lua.h \
  ../deps/lua/src/luaconf.h ae.h sds.h dict.h adlist.h zmalloc.h anet.h \
  ziplist.h intset.h version.h util.h rdb.h rio.h

添加：t_set_ext.o: t_set_ext.c redis.h fmacros.h config.h ../deps/lua/src/lua.h \
  ../deps/lua/src/luaconf.h ae.h sds.h dict.h adlist.h zmalloc.h anet.h \
  ziplist.h intset.h version.h util.h rdb.h rio.h

----------------

Makefile

102:REDIS_SERVER_OBJ= adlist.o ae.o anet.o dict.o redis.o sds.o zmalloc.o lzf_c.o lzf_d.o pqsort.o zipmap.o sha1.o ziplist.o release.o networking.o util.o object.o db.o replication.o rdb.o t_string.o t_list.o t_set.o t_zset.o t_hash.o config.o aof.o pubsub.o multi.o debug.o sort.o intset.o syncio.o migrate.o endianconv.o slowlog.o scripting.o bio.o rio.o rand.o memtest.o crc64.o bitops.o sentinel.o
最后面（不换行）添加：t_set_ext.o，如下：

102:REDIS_SERVER_OBJ= adlist.o ae.o anet.o dict.o redis.o sds.o zmalloc.o lzf_c.o lzf_d.o pqsort.o zipmap.o sha1.o ziplist.o release.o networking.o util.o object.o db.o replication.o rdb.o t_string.o t_list.o t_set.o t_zset.o t_hash.o config.o aof.o pubsub.o multi.o debug.o sort.o intset.o syncio.o migrate.o endianconv.o slowlog.o scripting.o bio.o rio.o rand.o memtest.o crc64.o bitops.o sentinel.o t_set_ext.o

help.h

592:    { "SMEMBERS",
593:    "key",
594:    "Get all the members in a set",
595:    3,
596:    "0.07" },

之后添加

    { "SPMEMBERS",
    "key offset limit",
    "Get paged the members in a set ",
    3,
    "0.1" },