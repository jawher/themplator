package themplator.readers;

import static org.junit.Assert.*;
import static themplator.utils.XMLEventsUtils.*;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.junit.Test;

import themplator.utils.StaxUtils;

public class ThElementEventReaderTest {
	@Test
	public void testPositionSeekNoEndEvent() {
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
				new ThStaxEventReader(evr), false, false);

		List<XMLEvent> expected = evts(se("data"), c("text"), ee("data"));
		test(r, expected);
	}
	
	@Test
	public void testAlreadyPositionedNoEndEvent() {
		List<XMLEvent> source= evts(se("data"), c("text"), ee("data"), ee("magic"), c("  "), se("junk"), a("a", "v"));
		ThEventReader r0 = new ThReplayEventReader(source);
		
		
		List<XMLEvent> expected = evts(se("data"), c("text"), ee("data"));
		ThElementEventReader r = new ThElementEventReader(new QName("magic"),
				r0, true, false);
		test(r, expected);
	}
	
	@Test
	public void testPositionSeekIncludeEndEvent() {
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
				new ThStaxEventReader(evr), false, true);

		List<XMLEvent> expected = evts(se("data"), c("text"), ee("data"), ee("magic"));
		test(r, expected);
	}
	
	@Test
	public void testAlreadyPositionedIncludeEndEvent() {
		List<XMLEvent> source= evts(se("data"), c("text"), ee("data"), ee("magic"), c("  "), se("junk"), a("a", "v"));
		ThEventReader r0 = new ThReplayEventReader(source);
		
		
		List<XMLEvent> expected = evts(se("data"), c("text"), ee("data"), ee("magic"));
		ThElementEventReader r = new ThElementEventReader(new QName("magic"),
				r0, true, true);
		test(r, expected);
	}
	
	protected void test(ThEventReader r, List<XMLEvent> expected) {
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
