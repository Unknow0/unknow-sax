/**
 * 
 */
package unknow.sax;

import java.util.Arrays;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * a parsing context
 * 
 * @author unknow
 */
public class SaxContext extends DefaultHandler {
	private final StringBuilder sb = new StringBuilder();
	@SuppressWarnings("rawtypes")
	private SaxHandler[] handlers = new SaxHandler[10];
	private int i = -1;
	private Object[] objects = new Object[10];
	private int o = 0;

	/**
	 * create new SaxContext
	 * 
	 * @param rootHandler the first handler
	 */
	public SaxContext(SaxHandler<?> rootHandler) {
		next(rootHandler);
	}

	/**
	 * set the next handler to call
	 * 
	 * @param h the next handler
	 */
	public void next(SaxHandler<?> h) {
		if (++i == handlers.length)
			handlers = Arrays.copyOf(handlers, i + 5);
		handlers[i] = h;
	}

	/**
	 * use the previous handler
	 */
	public void previous() {
		handlers[i--] = null;
	}

	/**
	 * push an object on the stack
	 * 
	 * @param obj object to push
	 */
	public void push(Object obj) {
		if (o == objects.length)
			objects = Arrays.copyOf(objects, o + 5);
		objects[o++] = obj;
	}

	/**
	 * peek the object on stack
	 * 
	 * @param <T> result type
	 * @return the first object on stack or null if no object on stack
	 */
	@SuppressWarnings("unchecked")
	public <T> T peek() {
		if (o == 0)
			return null;
		return (T) objects[o - 1];
	}

	/**
	 * pop the objects stack
	 * 
	 * @param <T> result type
	 * @return the removed object
	 */
	@SuppressWarnings("unchecked")
	public <T> T pop() {
		Object obj = objects[--o];
		objects[o] = null;
		return (T) obj;
	}

	/**
	 * the text content on end elements
	 * 
	 * @return closing elements text content
	 */
	public String textContent() {
		return sb.toString().trim();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		uri = uri == null || uri.isEmpty() ? localName : '{' + uri + '}' + localName;
		handlers[i].startElement(uri, localName, this);
		handlers[i].attributes(uri, localName, atts, this);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		uri = uri == null || uri.isEmpty() ? localName : '{' + uri + '}' + localName;
		int l;
		do {
			l = i;
			handlers[l].endElement(uri, localName, this);
		} while (i != l);
		sb.setLength(0);
	}
}
