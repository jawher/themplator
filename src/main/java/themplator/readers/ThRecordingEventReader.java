package themplator.readers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ThRecordingEventReader implements ThEventReader {
	private ThEventReader reader;
	private List<XMLEvent> recordedEvents = new ArrayList<XMLEvent>();

	public ThRecordingEventReader(ThEventReader reader) {
		super();
		this.reader = reader;
	}

	public boolean hasNext() {
		return reader.hasNext();
	}

	public XMLEvent next() throws XMLStreamException {
		XMLEvent ev = reader.next();
		recordedEvents.add(ev);
		return ev;
	}

	public List<XMLEvent> getRecordedEvents() {
		return recordedEvents;
	}

	@Override
	public String toString() {
		return "Recorder";
	}
}
