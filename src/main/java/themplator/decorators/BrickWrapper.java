package themplator.decorators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.XMLEvent;

import themplator.AbstractDecorator;

public class BrickWrapper<T> extends AbstractDecorator<T> {
	public static enum WrapMode {
		OUTER, INNER
	}

	private String elementName;
	private Map<String, String> attributes;
	private WrapMode wrapMode = WrapMode.OUTER;

	public BrickWrapper(String elementName, Map<String, String> attributes) {
		super();
		this.elementName = elementName;
		this.attributes = attributes;
	}

	public BrickWrapper(String elementName, Map<String, String> attributes,
			WrapMode wrapMode) {
		super();
		this.elementName = elementName;
		this.attributes = attributes;
		this.wrapMode = wrapMode;
	}

	public String getElementName() {
		return elementName;
	}

	public Map<String, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	public WrapMode getWrapMode() {
		return wrapMode;
	}

	protected List<? extends XMLEvent> genStartEvents(
			XMLEventFactory eventFactory) {
		List<XMLEvent> attrs = new ArrayList<XMLEvent>();
		if (attributes != null) {
			for (Entry<String, String> entry : attributes.entrySet()) {
				attrs.add(eventFactory.createAttribute(entry.getKey(), entry
						.getValue()));
			}
		}
		return Arrays.asList(eventFactory.createStartElement(new QName(
				elementName), attrs.iterator(), null));
	}

	@Override
	public List<? extends XMLEvent> preBrickStartElement(
			XMLEventFactory eventFactory) {
		if (wrapMode == WrapMode.OUTER) {
			return genStartEvents(eventFactory);
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public List<? extends XMLEvent> postBrickStartElement(
			XMLEventFactory eventFactory) {
		if (wrapMode == WrapMode.INNER) {
			return genStartEvents(eventFactory);
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	protected List<? extends XMLEvent> genEndEvents(XMLEventFactory eventFactory) {
		return Arrays.asList(eventFactory.createEndElement(new QName(
				elementName), null));
	}

	@Override
	public List<? extends XMLEvent> preBrickEndElement(
			XMLEventFactory eventFactory) {
		if (wrapMode == WrapMode.INNER) {
			return genEndEvents(eventFactory);
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public List<? extends XMLEvent> postBrickEndElement(
			XMLEventFactory eventFactory) {
		if (wrapMode == WrapMode.OUTER) {
			return genEndEvents(eventFactory);
		} else {
			return Collections.EMPTY_LIST;
		}
	}
}
