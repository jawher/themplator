package themplator.readers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.junit.Test;

import themplator.utils.LogUtils;
import themplator.utils.StaxUtils;
import themplators.readers.ThElementEventReader;
import themplators.readers.ThStaxEventReader;

public class ThElementEventReaderTest {
	@Test
	public void test() {
		XMLInputFactory xmlInputFactory = StaxUtils.createInputFactory();

		XMLEventReader evr;
		try {
			evr = xmlInputFactory
					.createXMLEventReader(ThElementEventReaderTest.class
							.getResourceAsStream("resources/data1.xml"));
		} catch (XMLStreamException e) {
			fail(e.getMessage());
			return;
		}
		ThElementEventReader r = new ThElementEventReader(new QName("magic"),
				new ThStaxEventReader(evr));

		XMLEventFactory factory = XMLEventFactory.newFactory();
		List<XMLEvent> expected = new ArrayList<XMLEvent>(3);
		expected.add(factory.createStartElement("", null, "data"));
		expected.add(factory.createCharacters("text"));
		expected.add(factory.createEndElement("", null, "data"));
		int pos = 0;
		while (r.hasNext()) {
			XMLEvent ev;
			try {
				ev = r.next();
			} catch (XMLStreamException e) {
				fail(e.getMessage());
				return;
			}
			if (!ev.isCharacters()
					|| ev.asCharacters().getData().trim().length() > 0) {

				assertEquals(expected.get(pos), ev);
				pos++;
				if (pos > expected.size()) {
					fail("Reader returned more events than expected");
					return;
				}
			}
		}

		if (pos != expected.size()) {
			fail("Reader returned less events than expected");
			return;
		}
	}
}
