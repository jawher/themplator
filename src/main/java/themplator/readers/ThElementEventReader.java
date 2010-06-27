package themplator.readers;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ThElementEventReader implements ThEventReader {
	private static enum State {
		DISABLED, ENABLED, ENABLED_TMP
	}
	private ThEventReader delegate;
	private final QName element;
	private State state = State.DISABLED;
	private XMLEvent next;
	private boolean includeEndEvent;

	public ThElementEventReader(QName element, ThEventReader delegate,
			boolean alreadyPositioned, boolean includeEndEvent) {
		this.element = element;
		this.delegate = delegate;
		this.includeEndEvent=includeEndEvent;
		if (alreadyPositioned) {
			state=State.ENABLED;
		} else
			positionAtElement();
	}

	public ThElementEventReader(QName element, XMLEventReader delegate,
			boolean alreadyPositioned, boolean includeEndEvent) {
		this(element, new ThStaxEventReader(delegate), alreadyPositioned, includeEndEvent);
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
					state=State.ENABLED;
					return;
				}
			}
		}
	}

	public boolean hasNext() {
		if (state==State.DISABLED || !delegate.hasNext()) {
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
			if(includeEndEvent){
				state=State.ENABLED_TMP;
				return true;
			} else {
			state=State.DISABLED;
			return false;
			}
		} else {
			return true;
		}
	}

	public XMLEvent next() throws XMLStreamException {
		if (state!=State.DISABLED) {
			XMLEvent res = next;
			next = null;
			if(state==State.ENABLED_TMP){
				state=State.DISABLED;
			}
			return res;
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
