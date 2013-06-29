/**
 * 
 */
package org.gololang.gldt.core.parser.antlr.internal;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.gololang.gldt.core.GoloInjectorProvider;
import org.gololang.gldt.core.parser.antlr.GoloParser;
import org.gololang.gldt.core.validation.GoloValidator;
import org.junit.Assert;

import com.google.inject.Injector;

/**
 * @author Jeff MAURY
 *
 */
public class ParserTestHelper {

  private static GoloInjectorProvider provider = new GoloInjectorProvider();

  /**
   * Parse the data as a Golo source file.
   * 
   * @param data the content of the Golo source file
   * @return the result of the parsing
   */
  static IParseResult getParser(String data) {
    CharStream stream = new ANTLRStringStream(data);
    Injector injector = provider.getInjector();;
    GoloParser parser = (GoloParser)injector.getInstance(IParser.class);
    return parser.doParse(stream.substring(0, stream.size()-1));
  }

  /**
   * Load a resource from the classpath
   * @param path the name of the resource
   * @return the string content of the file
   * @throws IOException if an I/O error occurs
   */
  static String loadResource(String path) throws IOException {
    InputStream is = ParserTestHelper.class.getResourceAsStream(path);
    byte[] data = new byte[is.available()];
    is.read(data);
    return new String(data, "UTF-8"); //$NON-NLS-1$
  }

  /**
   * Dump the syntax errors as a String
   * 
   * @param iterable the list of syntax errors
   * @return the string containing the errors
   */
  static String toString(Iterable<INode> iterable) {
    StringBuffer buf = new StringBuffer();
    for(INode node : iterable) {
      buf.append(node.getSyntaxErrorMessage().toString());
      buf.append(' ');
    }
    return buf.toString();
  }

  /**
   * Load and parse a Golo source file given its resource name. Checks that no syntax errors are
   * returned.
   * 
   * @param name the name of the resource
   */
  static void testFile(String name) {
    testFile(name, false);
  }

  /**
   * Load and parse a Golo source file given its resource name. Checks that no syntax errors are
   * returned or expected issue code is generated.
   * 
   * @param name the name of the resource
   * @param issueCode the expected issue code
   */
  static void testFile(String name, boolean errorExpected) {
    try {
      String content = loadResource(name);
      IParseResult presult = getParser(content);
      if (errorExpected) {
        Assert.assertTrue(presult.hasSyntaxErrors());
      } else {
        Assert.assertFalse(toString(presult.getSyntaxErrors()), presult.hasSyntaxErrors());
        GoloValidator validator = provider.getInjector().getInstance(GoloValidator.class);
        ValidatorTester<GoloValidator> tester = new ValidatorTester<GoloValidator>(validator, provider.getInjector());
        AssertableDiagnostics diagnostic = tester.validate(presult.getRootASTElement());
        diagnostic.assertOK();
      }
    }
    catch (IOException e) {
      Assert.fail("Can't load resource '" + name + "'"); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }

}
