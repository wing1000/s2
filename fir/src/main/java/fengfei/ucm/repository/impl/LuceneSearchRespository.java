package fengfei.ucm.repository.impl;

import fengfei.fir.search.lucene.PhotoFields;
import fengfei.fir.search.lucene.Searcher;
import fengfei.ucm.entity.photo.Rank;
import fengfei.ucm.entity.profile.User;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LuceneSearchRespository {
    public Searcher userSearcher = Searcher.userSearcher;
    public Searcher photoSearcher = Searcher.photoSearcher;
    public SqlShowRepository showRepository = new SqlShowRepository();

    private String[] keywords(String qstr) {
        String[] keywords = qstr.split(" |,|，");
        return keywords;
    }

    private List<Long> getIdPhoto(TopDocs topDocs, int page, int size) throws IOException {
        List<Long> idPhotos = new ArrayList<>();
        ScoreDoc[] sds = topDocs.scoreDocs;
        for (ScoreDoc sd : sds) {
            //7.根据searcher和TopDocs对象获取Document对象
            Document d = photoSearcher.getIndexSearcher().doc(sd.doc);//sd.doc:文档内部编号
            //8.根据Document对象获取需要的值
            String id = d.get(PhotoFields.ID);
            Long idPhoto = Long.parseLong(id);
            idPhotos.add(idPhoto);
            System.out.println(String.format("%f  %d  %s    %s", sd.score, sd.shardIndex, d.get("id"), d.get(PhotoFields.Content)));
        }
        return idPhotos;
    }

    public List<Rank> selectPhotosByTag(
            final ScoreDoc after,
            final String qstr,
            final Byte category,
            final int currentPageNum,
            final int limit) throws Exception {
        String[] keywords = keywords(qstr);
        int lucenePageSize = Searcher.PageSize;
        int currPage = currentPageNum <= 0 ? 1 : currentPageNum;
        int total = currPage * limit;
        int differ = lucenePageSize;

        List<Long> idPhotos = null;
        TopDocs tds = photoSearcher.search(after, 1, keywords);
        List<Rank> ranks = showRepository.selectRanks(idPhotos);
        return ranks;
    }


    public List<User> selectUsers(final String qstr, final int offset, final int limit)
            throws Exception {

        List<User> users = null;

        return users;
    }

}
