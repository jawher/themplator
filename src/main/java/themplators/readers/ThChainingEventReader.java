package themplators.readers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ThChainingEventReader implements ThEventReader {
	private ThEventReader[] readers;
	private int currentReader = 0;

	public ThChainingEventReader(ThEventReader... readers) {
		this.readers = readers;
	}

	public boolean hasNext() {
		if (currentReader < readers.length) {
			if (readers[currentReader].hasNext()) {
				return true;
			} else {
				currentReader++;
				return hasNext();
			}
		} else {
			return false;
		}
	}

	public XMLEvent next() throws XMLStreamException {
		return readers[currentReader].next();
	}

}
