/**
 * 
 */
package unknow.sax;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author unknow
 */
public class SaxParser {
	private static final SAXParserFactory newInstance = SAXParserFactory.newInstance();
	static {
		newInstance.setNamespaceAware(true);
	}

	/**
	 * parse source with default SaxContext
	 * 
	 * @param <T>    the result type
	 * 
	 * @param root   the root handler
	 * @param source the xml source
	 * @return the last object on stack
	 * @throws ParserConfigurationException on sax parser creation error
	 * @throws SAXException                 on sax error
	 * @throws IOException                  on io error
	 */
	public static <T> T parse(SaxHandler<?> root, InputSource source) throws ParserConfigurationException, SAXException, IOException {
		return parse(new SaxContext(root), source);
	}

	/**
	 * parse source with this context
	 * 
	 * @param <T>     the result type
	 * 
	 * @param context the context
	 * @param source  the source
	 * @return the last object on stack
	 * @throws ParserConfigurationException on sax parser creation error
	 * @throws SAXException                 on sax error
	 * @throws IOException                  on io error
	 */
	public static <T> T parse(SaxContext context, InputSource source) throws ParserConfigurationException, SAXException, IOException {
		SAXParser parser = newInstance.newSAXParser();
		XMLReader xmlReader = parser.getXMLReader();

		xmlReader.setContentHandler(context);
		xmlReader.parse(source);
		return context.peek();
	}
}
