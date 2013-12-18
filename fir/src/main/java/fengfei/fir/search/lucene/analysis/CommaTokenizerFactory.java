package fengfei.fir.search.lucene.analysis;


import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeSource.AttributeFactory;

import java.io.Reader;
import java.util.Map;

/**
 * Factory for {@link org.apache.lucene.analysis.core.WhitespaceTokenizer}.
 * <pre class="prettyprint">
 * &lt;fieldType name="text_ws" class="solr.TextField" positionIncrementGap="100"&gt;
 * &lt;analyzer&gt;
 * &lt;tokenizer class="solr.CommaTokenizerFactory"/&gt;
 * &lt;/analyzer&gt;
 * &lt;/fieldType&gt;</pre>
 */
public class CommaTokenizerFactory extends TokenizerFactory {

    /**
     * Creates a new CommaTokenizerFactory
     */
    public CommaTokenizerFactory(Map<String, String> args) {
        super(args);
        assureMatchVersion();
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Unknown parameters: " + args);
        }
    }

    @Override
    public CommaTokenizer create(AttributeFactory factory, Reader input) {
        return new CommaTokenizer(luceneMatchVersion, factory, input);
    }
}
