package com.chips.xmlhandler;

import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser handler code originally by Jesse Weinstein
 */
public abstract class SAXHandler<T extends Enum<T> & TagStateEnum> extends DefaultHandler {

	protected T tagState;
	protected Map<T, StringBuilder> buffers;
	protected List<T> values;
	
	@Override
	public void startDocument() throws SAXException {
		clearTagBuffers();
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
	}

	protected void clearTagBuffers() {
		tagState = null;
		for (T key : values) {
			buffers.put(key, new StringBuilder());
		}
	}
	protected abstract T valueOf(String s);
	
	protected String getString(final T key) {
		return buffers.get(key).toString();
	}

	/**
	 * Gets be called on the following structure: <tag>characters</tag>
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		if (tagState!=null) {
			buffers.get(tagState).append(new String(ch, start, length));
		}
	}

	protected String normalize(String localName) {
		return localName.replace('-', '_').toUpperCase();
	}

	/**
	 * Gets be called on opening tags like: <tag> Can provide attribute(s), when
	 * xml was like: <tag attribute="attributeValue">
	 */
	@Override
	public void startElement(String namespaceURI, String localName, String qName,
			Attributes atts) throws SAXException {
	    try {
	        tagState = valueOf(localName);
		} catch (IllegalArgumentException e) {
		    
		}
	}
}
