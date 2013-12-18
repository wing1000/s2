package fengfei.fir.search.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 */
public abstract class IndexCreator<T> {
    static Logger logger = LoggerFactory.getLogger(IndexCreator.class);
    protected LuceneFactory factory;
    protected IndexWriter writer;


    public IndexCreator(LuceneFactory factory) {
        this.factory = factory;
    }

    public void begin() throws IOException {
        this.writer = this.factory.createIndexWriter();
    }

    public void end() throws IOException {
        this.close();
        this.writer = null;
    }

    public void commit() throws IOException {
        this.writer.commit();
    }

    public void rollback() throws IOException {
        this.writer.rollback();
    }

    /**
     * 添加的方法
     */
    public void add(T obj) throws Exception {
        try {
            Document doc = toDocument(obj);
            this.writer.addDocument(doc);//添加进写入流里
            this.writer.forceMerge(1);//优化压缩段,大规模添加数据的时候建议，少使用本方法，会影响性能
            this.commit();//提交数据
        } catch (IOException e) {
            this.rollback();
            logger.error("adding index error", e);
        }
    }

    protected abstract Document toDocument(T obj) throws IOException;

    public void close() throws IOException {
        this.writer.close();
    }

    /**
     * 删除方法
     *
     * @param id 根据ID删除
     */
    public void delete(String id) throws Exception {
        try {

            Query q = new TermQuery(new Term("id", id));
            this.writer.deleteDocuments(q);//删除指定ID的Document
            // this.writer.forceMerge(DEFAULT_MAX_NUM_SEGMENTS);
            this.commit();//提交

        } catch (IOException e) {
            this.rollback();
            logger.error("deleting error for id=" + id, e);
        }

    }

    /**
     * 根据ID进行更行的方法
     */
    public void updateByID(T obj) throws Exception {

        Document doc = null;
        String id = getEntityId(obj);
        try {
            doc = toDocument(obj);

            this.writer.updateDocument(new Term("id", id), doc);
            this.commit();
        } catch (IOException e) {
            this.rollback();
            logger.error("updating error for id=" + id, e);

        }


    }

    protected abstract String getEntityId(T obj);


}
