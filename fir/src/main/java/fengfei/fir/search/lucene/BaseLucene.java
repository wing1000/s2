package fengfei.fir.search.lucene;

import fengfei.fir.search.lucene.analysis.CommaAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 */
public abstract class BaseLucene {

    protected Analyzer getAnalyzer() {
        Analyzer analyzer = new CommaAnalyzer(Version.LUCENE_45);
        return analyzer;
    }

    protected File getIndexFile(String dir) {
        final File docDir = new File(dir);
        if (!docDir.exists()) {
            docDir.mkdirs();
        }
        return docDir;

    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[`~!@#$%^&*()+=|{ }':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？·\'\"\\-\t\n\r]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}
