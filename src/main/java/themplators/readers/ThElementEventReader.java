package themplators.readers;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ThElementEventReader implements ThEventReader {
	private ThEventReader delegate;
	private final QName element;
	private boolean emit = false;
	private XMLEvent next;

	public ThElementEventReader(QName element, ThEventReader delegate) {
		super();
		this.element = element;
		this.delegate = delegate;
		positionAtElement();
	}

	public ThElementEventReader(QName element, XMLEventReader delegate) {
		super();
		this.element = element;
		this.delegate = new ThStaxEventReader(delegate);
		positionAtElement();
	}

	private final void positionAtElement() {
		while (delegate.hasNext()) {
			XMLEvent ev;
			try {
				ev = delegate.next();
			} catch (XMLStreamException e) {
				throw new RuntimeException(e);
			}

			if (ev.isStartElement()) {
				StartElement se = ev.asStartElement();
				if (se.getName().equals(element)) {
					emit = true;
					return;
				}
			}
		}
	}

	public boolean hasNext() {
		if (!emit || !delegate.hasNext()) {
			return false;
		}
		if (next != null) {
			return true;
		}
		try {
			next = delegate.next();
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
		if (next.isEndElement()
				&& next.asEndElement().getName().equals(element)) {
			emit = false;
			return false;
		} else {
			return true;
		}
	}

	public XMLEvent next() throws XMLStreamException {
		if (emit) {
			XMLEvent res = next;
			next = null;
			return res;
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
