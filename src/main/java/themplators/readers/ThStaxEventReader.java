package themplators.readers;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

@SuppressWarnings("restriction")
public class ThStaxEventReader implements ThEventReader {
	private XMLEventReader evr;

	public ThStaxEventReader(XMLEventReader evr) {
		super();
		this.evr = evr;
	}

	public boolean hasNext() {
		return evr.hasNext();
	}

	public XMLEvent next() throws XMLStreamException {
		return evr.nextEvent();
	}

	public void remove() {
		throw new UnsupportedOperationException();
		
	}
	

	@Override
	public String toString() {
		return "Stax";
	}
	
}
