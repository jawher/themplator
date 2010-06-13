package themplators.readers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public interface ThEventReader {

	public abstract boolean hasNext();

	public abstract XMLEvent next() throws XMLStreamException;

}