package themplator;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import themplator.utils.StaxUtils;
import themplator.writers.ThEventWriter;
import themplator.writers.ThStaxEventWriter;
import themplators.readers.ThEventReader;
import themplators.readers.ThStaxEventReader;

public class Themplate extends AbstractBrick {

	public void render(ThEventReader markup, ThEventWriter result)
	throws XMLStreamException {
		while (markup.hasNext()) {
			XMLEvent ev = markup.next();
			if (ev.isStartElement()) {
				StartElement se = ev.asStartElement();
				String thid = thid(se);
				if (thid != null) {
					// FIXME:brick might not exist
					getChildByThid(thid).render(se, markup, result);
				} else {
					result.add(ev);
				}
			} else {
				result.add(ev);
			}
		}
	}
	public void render(InputStream markup, OutputStream result)
			throws XMLStreamException {
		XMLInputFactory xmlInputFactory = StaxUtils.createInputFactory();
		XMLEventReader evr = xmlInputFactory.createXMLEventReader(markup);

		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();

		XMLEventWriter evw = xmlOutputFactory.createXMLEventWriter(result);

		ThEventReader thr = new ThStaxEventReader(evr);

		ThEventWriter thw = new ThStaxEventWriter(evw);

		render(thr, thw);
	}

	private String thid(StartElement e) {
		Attribute attr = e.getAttributeByName(new QName("thid"));
		if (attr != null) {
			return attr.getValue();
		} else {
			return null;
		}
	}

}
