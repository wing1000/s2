package fengfei.fir.search.lucene;

import fengfei.sprucy.AppConstants;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;

/**
 */
public class AnalyzerUtils {
    public static String toCommaString(String str) throws IOException {

        String st = null;
        StringBuffer sb = new StringBuffer();

        StringReader reader = new StringReader(str);

        Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_45);

        TokenStream ts = analyzer.tokenStream("", reader);
        CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
        ts.reset();//重置做准备
        while (ts.incrementToken()) {
            String s = term.toString();
            if (s.length() > 1) {
                sb.append(term.toString()).append(AppConstants.CommaSeparator);
            }
        }
        ts.end();//
        ts.close();//关闭流
        return sb.toString();

    }

    public static void main(String[] args) throws Exception {
        System.out.println(toCommaString("Lucene不是一个完整的全文索引应用，而是是一个用Java写的全文索引引擎工具包，它可以方便的嵌入到各种应用中实现针对应用的全文索引/检索功能。"));
    }
}
