package themplator.utils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import themplators.readers.ThEventReader;

public class TestUtils {
	public static boolean assertEquals(ThEventReader expected,
			ThEventReader actual) throws XMLStreamException {
		while (expected.hasNext()) {
			if (!actual.hasNext()) {
				return false;
			}

			XMLEvent ev1 = expected.next();
			XMLEvent ev2 = actual.next();

			if (ev1.isCharacters()) {
				String s1 = ev1.asCharacters().getData().trim();
				if (ev2.isCharacters()) {
					String s2 = ev2.asCharacters().getData().trim();
					if (!s1.equals(s2)) {
						System.out.println("'" + s1 + "' != '" + s2 + "'");
						return false;
					}
				} else {
					return false;
				}
			} else {
				if (!ev1.equals(ev2)) {
					return false;
				}
			}
		}

		return true;
	}
}
