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

import themplator.readers.ThElementEventReader;
import themplator.readers.ThEventReader;
import themplator.readers.ThStaxEventReader;
import themplator.readers.ThWhitespaceStripperEventReader;
import themplator.utils.LogUtils;
import themplator.utils.StaxUtils;

public class ThWhitespaceStripperEventReaderTest {
	@Test
	public void test() {
		XMLInputFactory xmlInputFactory = StaxUtils.createInputFactory();

		XMLEventReader evr;
		try {
			evr = xmlInputFactory
					.createXMLEventReader(ThWhitespaceStripperEventReaderTest.class
							.getResourceAsStream("resources/data1.xml"));

			ThWhitespaceStripperEventReader r = new ThWhitespaceStripperEventReader(
					new ThStaxEventReader(evr));
			
			ThEventReader expected = new ThStaxEventReader(xmlInputFactory
					.createXMLEventReader(ThWhitespaceStripperEventReaderTest.class
							.getResourceAsStream("resources/data2.xml")));

			
			while (r.hasNext()) {
				if(!expected.hasNext()){
					fail("Reader returned more events than expected");
				}
				XMLEvent ev1=r.next();
				XMLEvent ev2=expected.next();
				
				assertEquals(ev2, ev1);
				
			}

			if (expected.hasNext()) {
				fail("Reader returned less events than expected");
				return;
			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
	}
}
