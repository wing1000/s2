package fengfei.fir.search.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import java.io.IOException;

/**
 */
public class Searcher extends BaseLucene {
    public static Searcher userSearcher;
    public static Searcher photoSearcher;
    public static int PageSize = 200;

    protected LuceneFactory factory;


    public Searcher(LuceneFactory factory) {
        this.factory = factory;
    }

    public TopDocs search(
            ScoreDoc after,
            int currentPage,
            String... keywords) throws ParseException, IOException {
        return search(after,PageSize, currentPage, keywords);
    }

    public TopDocs search(
            ScoreDoc after,
            int pageSize,
            int currentPage,
            String... keywords) throws ParseException, IOException {
        //将关键字中的特殊符号过滤
        String[] fields = new String[keywords.length];
        if (null != keywords && keywords.length > 0) {
            String[] tmp = new String[keywords.length];
            for (int i = 0; i < keywords.length; i++) {
                tmp[i] = stringFilter(keywords[i]);
                fields[i] = Fields.Content;
            }
            keywords = tmp;
        }

        Analyzer analyzer = getAnalyzer();

        Query query = MultiFieldQueryParser.parse(Version.LUCENE_45, keywords, fields, analyzer);
        //5.根据searcher搜索并且返回TopDocs
        if (currentPage <= 1) {
            return this.factory.getSearcher().search(query, pageSize);
        } else {
            return this.factory.getSearcher().searchAfter(after, query, 10);
        }
    }

    public IndexSearcher getIndexSearcher() throws IOException {
        return this.factory.getSearcher();
    }

    public static void main(String[] args) throws IOException, ParseException {
        String dir = "/opt/lucene/index/user";
        LuceneFactory luceneFactory = LuceneFactory.get(dir);
        Searcher searcher = new Searcher(luceneFactory);
        TopDocs tds = searcher.search(null, 100, 1, "user", "nice");
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
