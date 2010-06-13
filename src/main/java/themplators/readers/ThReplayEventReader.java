package themplators.readers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

@SuppressWarnings("restriction")
public class ThReplayEventReader implements ThEventReader {

	private Iterator<? extends XMLEvent> events;

	public ThReplayEventReader(List<? extends XMLEvent> events) {
		super();
		this.events = new ArrayList(events).iterator();
	}

	public boolean hasNext() {
		return events.hasNext();
	}

	public XMLEvent next() throws XMLStreamException {
		return events.next();
	}

	@Override
	public String toString() {
		return "Replayer";
	}
}
