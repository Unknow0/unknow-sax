import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import unknow.sax.SaxContext;
import unknow.sax.SaxHandler;
import unknow.sax.SaxParser;

/**
 * 
 */

/**
 * @author unknow
 */
public class Test {

	@org.junit.Test
	public void test() throws ParserConfigurationException, SAXException, IOException {

		SaxParser.parse(new H("root"), new InputSource(new StringReader("<R><A>bla</A><B attr='5'/><C attr='5'>bla</C></R>")));
	}

	private static class H implements SaxHandler<SaxContext> {
		private final String n;

		public H(String n) {
			this.n = n;
		}

		@Override
		public void attributes(String qname, String name, Attributes atts, SaxContext context) throws SAXException {
			System.out.println(n + " attrs: " + qname + " " + atts);
		}

		@Override
		public void startElement(String qname, String name, SaxContext context) throws SAXException {
			System.out.println(n + " start: " + qname);
			context.next(new H(name));
		}

		@Override
		public void endElement(String qname, String name, SaxContext context) throws SAXException {
			System.out.println(n + " end  : " + qname);
			if (n.equals(name))
				context.previous();
		}
	}
}
