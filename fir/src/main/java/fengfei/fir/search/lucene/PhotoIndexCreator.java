package fengfei.fir.search.lucene;

import fengfei.ucm.entity.photo.Photo;
import org.apache.lucene.document.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

/**
 */
public class PhotoIndexCreator extends IndexCreator<Photo> {


    public PhotoIndexCreator(LuceneFactory factory) {
        super(factory);
    }


    protected Document toDocument(Photo photo) throws IOException {
        Document doc = new Document();
        String title = AnalyzerUtils.toCommaString(photo.title);
        String desc = AnalyzerUtils.toCommaString(photo.description);
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(desc).append(photo.tags).append(photo.exifToCSV());
        doc.add(new StringField(PhotoFields.ID, String.valueOf(photo.idPhoto), Field.Store.YES));
        doc.add(new StringField(PhotoFields.UserID, String.valueOf(photo.idUser), Field.Store.YES));
        doc.add(new TextField(PhotoFields.Content, sb.toString(), Field.Store.NO));//存储
        doc.add(new StringField(PhotoFields.UserIdAndNiceName, photo.idUser + "#" + photo.niceName, Field.Store.YES));//存储
        doc.add(new StringField(PhotoFields.Title, photo.title, Field.Store.YES));//存储
        doc.add(new NumericDocValuesField(PhotoFields.At, photo.updatedAt));//存储
        return doc;
    }


    @Override
    protected String getEntityId(Photo obj) {
        return String.valueOf(obj.idPhoto);
    }

    public static void main(String[] args) throws Exception {
        String dir = "/opt/lucene/index/photo";
        LuceneFactory luceneFactory = LuceneFactory.get(dir);
        PhotoIndexCreator creator = new PhotoIndexCreator(luceneFactory);creator.begin();
        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo();
            photo.idPhoto = i;
            if (i % 2 == 0) {
                photo.title = "贡嘎" + i;
                photo.tags = "风景";

            } else {
                photo.tags = "贡嘎,丽江,四川";
                photo.title = "风景";
            }
            photo.description = "描述";
            photo.make = "";
            photo.model = "";
            photo.aperture = "";
            photo.shutter = "";
            photo.iso = "";
            photo.lens = "";
            photo.focus = "";
            photo.ev = "";
            creator.add(photo);
        }
        creator.end();

        Searcher searcher = new Searcher(luceneFactory);
        TopDocs tds = searcher.search(null, 100, 1, "贡嘎", "风景");
        out(tds, luceneFactory.getSearcher());

    }

    private static void out(TopDocs tds, IndexSearcher searcher) throws IOException {
        System.out.println("总共有【" + tds.totalHits + "】条匹配结果");
        //6.根据TopDocs获取ScoreDoc对象
        ScoreDoc[] sds = tds.scoreDocs;
        for (ScoreDoc sd : sds) {
            //7.根据searcher和TopDocs对象获取Document对象
            Document d = searcher.doc(sd.doc);//sd.doc:文档内部编号
            //8.根据Document对象获取需要的值
            System.out.println(String.format("%f  %d  %s    %s", sd.score, sd.shardIndex, d.get("id"), d.get(PhotoFields.Content)));
        }
    }
}
