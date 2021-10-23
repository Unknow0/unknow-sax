package unknow.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @param <T> the context implementation
 * @author unknow
 */
public interface SaxHandler<T extends SaxContext> {
	/**
	 * start element
	 * 
	 * @param qname   the qname ('{' uri '}')? localName
	 * @param name    the local name
	 * @param context the context
	 * @throws SAXException on parsing error
	 */
	@SuppressWarnings("unused")
	default void startElement(String qname, String name, T context) throws SAXException {
	}

	/**
	 * called when attributes found
	 * 
	 * @param qname   the qname
	 * @param name    the local name
	 * @param atts    the attributes
	 * @param context the context
	 * @throws SAXException on parsing error
	 */
	@SuppressWarnings("unused")
	default void attributes(String qname, String name, Attributes atts, T context) throws SAXException {
	}

	/**
	 * end element
	 * 
	 * @param qname   the qname ('{' uri '}')? localName
	 * @param name    the local name
	 * @param context the context
	 * @throws SAXException on parsing error
	 */
	@SuppressWarnings("unused")
	default void endElement(String qname, String name, T context) throws SAXException {
	}
}
