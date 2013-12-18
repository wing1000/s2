package fengfei.fir.search.lucene;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class LuceneFactory extends BaseLucene {
    private static Map<String, LuceneFactory> luceneFactoryMap = new ConcurrentHashMap<>();
    private String dir;
    private Directory directory;
    private IndexReader reader;
    private IndexWriter writer;
    private IndexSearcher searcher;
    private static Lock lock = new ReentrantLock();

    public static LuceneFactory get(String dir) {
        lock.lock();
        try {
            LuceneFactory luceneFactory = luceneFactoryMap.get(dir);
            if (luceneFactory == null) {
                luceneFactory = new LuceneFactory(dir);
                luceneFactoryMap.put(dir, luceneFactory);
            }
            return luceneFactory;
        } finally {
            lock.unlock();
        }

    }

    public LuceneFactory(String dir) {
        try {
            this.dir = dir;
            directory = FSDirectory.open(getIndexFile(dir));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public IndexWriter createIndexWriter()
            throws IOException {
        /*
         */
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_45,
                getAnalyzer());
        /*
         * 创建索引模式：CREATE，覆盖模式； conf.setOpenMode(OpenMode.CREATE);
         *
         * APPEND，追加模式 conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
         */
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        if (IndexWriter.isLocked(FSDirectory.open(getIndexFile(dir)))) {
            IndexWriter.unlock(FSDirectory.open(getIndexFile(dir)));
        }
        IndexWriter writer = new IndexWriter(directory, conf);
        return writer;
    }

    public IndexSearcher getSearcher() throws IOException {

        if (reader == null) {
            reader = createIndexReader();
        } else {
            IndexReader tr = DirectoryReader.openIfChanged((DirectoryReader) reader);
            if (tr != null) {
                reader.close();
                reader = tr;
            } else {
                return searcher;
            }
        }
        searcher = new IndexSearcher(reader);
        return searcher;
    }


    private IndexReader createIndexReader()
            throws IOException {
        reader = DirectoryReader.open(directory);
        return reader;
    }
}
