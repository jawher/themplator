package themplators.readers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ThWhitespaceStripperEventReader implements ThEventReader {
	private ThEventReader delegate;
	private XMLEvent next;

	public ThWhitespaceStripperEventReader(ThEventReader delegate) {
		this.delegate = delegate;
	}

	public boolean hasNext() {
		if (!delegate.hasNext()) {
			return false;
		}
		if (next != null) {
			return true;
		}
		XMLEvent ev;
		try {
			ev = delegate.next();
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
		if (ev.isCharacters()
				&& ev.asCharacters().getData().trim().length() == 0) {
			return hasNext();
		} else {
			next=ev;
			return true;
		}
	}

	public XMLEvent next() throws XMLStreamException {
		if (next != null) {
			XMLEvent res = next;
			next = null;
			return res;
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
