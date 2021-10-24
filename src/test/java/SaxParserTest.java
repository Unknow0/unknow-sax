import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
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
public class SaxParserTest {

	@Test
	public void empty() throws ParserConfigurationException, SAXException, IOException {
		SaxParserTest.TestHandler h = new TestHandler();
		SaxParser.parse(h, new InputSource(new StringReader("<root/>")));
		assertEquals("+root\n-root\n", h.toString());
	}

	@Test
	public void emptyNs() throws ParserConfigurationException, SAXException, IOException {
		SaxParserTest.TestHandler h = new TestHandler();
		SaxParser.parse(h, new InputSource(new StringReader("<root xmlns='test'/>")));
		assertEquals("+{test}root\n-{test}root\n", h.toString());
	}

	@Test
	public void attrs() throws ParserConfigurationException, SAXException, IOException {
		SaxParserTest.TestHandler h = new TestHandler();
		SaxParser.parse(h, new InputSource(new StringReader("<root value='4'/>")));
		assertEquals("+root\n+root@value=4\n-root\n", h.toString());
	}

	@Test
	public void attrsNs() throws ParserConfigurationException, SAXException, IOException {
		SaxParserTest.TestHandler h = new TestHandler();
		SaxParser.parse(h, new InputSource(new StringReader("<root ns:value='4' xmlns:ns='test'/>")));
		assertEquals("+root\n+root@{test}value=4\n-root\n", h.toString());
	}

	@Test
	public void content() throws ParserConfigurationException, SAXException, IOException {
		SaxParserTest.TestHandler h = new TestHandler();
		SaxParser.parse(h, new InputSource(new StringReader("<root>content</root>")));
		assertEquals("+root\ncontent\n-root\n", h.toString());
	}

	private static final class TestHandler implements SaxHandler<SaxContext> {
		private final StringBuilder sb = new StringBuilder();

		@Override
		public void startElement(String qname, String name, SaxContext context) throws SAXException {
			sb.append('+').append(qname).append('\n');
		}

		@Override
		public void attributes(String qname, String name, Attributes atts, SaxContext context) throws SAXException {
			int l = atts.getLength();
			for (int i = 0; i < l; i++) {
				String uri = atts.getURI(i);
				sb.append('+').append(qname).append('@');
				if (uri != null && !uri.isEmpty())
					sb.append('{').append(uri).append('}');
				sb.append(atts.getLocalName(i)).append('=').append(atts.getValue(i)).append('\n');
			}
		}

		@Override
		public void endElement(String qname, String name, SaxContext context) throws SAXException {
			String text = context.textContent();
			if (!text.isEmpty())
				sb.append(text).append('\n');
			sb.append('-').append(qname).append('\n');
		}

		@Override
		public String toString() {
			return sb.toString();
		}
	}
}
