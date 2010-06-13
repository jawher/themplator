package themplator.writers;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

@SuppressWarnings("restriction")
public class ThStaxEventWriter implements ThEventWriter {
	private XMLEventWriter writer;

	public ThStaxEventWriter(XMLEventWriter writer) {
		super();
		this.writer = writer;
	}

	/* (non-Javadoc)
	 * @see themplator.writers.ThEventWriter#add(javax.xml.stream.events.XMLEvent)
	 */
	public void add(XMLEvent ev) throws XMLStreamException {
		writer.add(ev);
	}
}
