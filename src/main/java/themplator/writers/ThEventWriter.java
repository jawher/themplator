package themplator.writers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public interface ThEventWriter {

	public abstract void add(XMLEvent ev) throws XMLStreamException;

}