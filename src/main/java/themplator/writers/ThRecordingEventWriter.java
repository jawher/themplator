package themplator.writers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ThRecordingEventWriter implements ThEventWriter {
	private ThEventWriter delegate;
	private List<XMLEvent> recordedEvents = new ArrayList<XMLEvent>();

	public ThRecordingEventWriter() {

	}

	public ThRecordingEventWriter(ThEventWriter delegate) {
		super();
		this.delegate = delegate;
	}

	public void add(XMLEvent ev) throws XMLStreamException {
		recordedEvents.add(ev);
		if (delegate != null) {
			delegate.add(ev);
		}
	}

	public List<XMLEvent> getRecordedEvents() {
		return recordedEvents;
	}

}
