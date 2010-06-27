package themplator.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLEventsUtils {
	private static XMLEventFactory eventFactory = XMLEventFactory.newInstance();

	public static XMLEvent a(String name, String val) {
		return eventFactory.createAttribute(name, val);
	}
	
	public static XMLEvent c(String val) {
		return eventFactory.createCharacters(val);
	}

	public static StartElement se(String name) {
		return eventFactory.createStartElement(new QName(name), null, null);
	}

	public static EndElement ee(String name) {
		return eventFactory.createEndElement(new QName(name), null);
	}

	public static List<XMLEvent> evts(XMLEvent... items) {
		return Arrays.asList(items);
	}

	public static List<XMLEvent> close(List<XMLEvent> start) {
		List<XMLEvent> res = new ArrayList<XMLEvent>();
		for (int i = start.size() - 1; i >= 0; i--) {
			if (start.get(i).isStartElement()) {
				res.add(ee(start.get(i).asStartElement().getName()
						.getLocalPart()));
			}
		}
		return res;
	}
}
