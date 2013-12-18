package fengfei.fir.search.lucene.analysis;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;

import java.io.Reader;

/**
 * An Analyzer that uses {@link org.apache.lucene.analysis.core.WhitespaceTokenizer}.
 * <p>
 * <a name="version">You must specify the required {@link org.apache.lucene.util.Version} compatibility
 * when creating {@link org.apache.lucene.analysis.util.CharTokenizer}:
 * <ul>
 * <li>As of 3.1, {@link org.apache.lucene.analysis.core.WhitespaceTokenizer} uses an int based API to normalize and
 * detect token codepoints. See {@link org.apache.lucene.analysis.util.CharTokenizer#isTokenChar(int)} and
 * {@link org.apache.lucene.analysis.util.CharTokenizer#normalize(int)} for details.</li>
 * </ul>
 * <p>
 **/
public final class CommaAnalyzer extends Analyzer {
  
  private final Version matchVersion;
  
  /**
   * Creates a new {@link CommaAnalyzer}
   * @param matchVersion Lucene version to match See {@link <a href="#version">above</a>}
   */
  public CommaAnalyzer(Version matchVersion) {
    this.matchVersion = matchVersion;
  }
  
  @Override
  protected TokenStreamComponents createComponents(final String fieldName,
      final Reader reader) {
    return new TokenStreamComponents(new CommaTokenizer(matchVersion, reader));
  }
}
