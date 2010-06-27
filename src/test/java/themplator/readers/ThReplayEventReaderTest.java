package themplator.readers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.junit.Test;

import themplator.readers.ThElementEventReader;
import themplator.readers.ThStaxEventReader;
import themplator.utils.LogUtils;
import themplator.utils.StaxUtils;
import static themplator.utils.XMLEventsUtils.*;

public class ThReplayEventReaderTest {
	@Test
	public void test() {
		List<XMLEvent> expected = evts(se("data"), a("a", "v"), c("text"), ee("data"));
		test(new ThReplayEventReader(expected), expected);
	}
	
	
	protected void test(ThEventReader r, List<XMLEvent> expected) {
		System.out.println("ThElementEventReaderTest.test()");
		int pos = 0;
		while (r.hasNext()) {
			XMLEvent ev;
			try {
				ev = r.next();
			} catch (XMLStreamException e) {
				fail(e.getMessage());
				return;
			}
			
			System.out.println(LogUtils.str(ev));
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
