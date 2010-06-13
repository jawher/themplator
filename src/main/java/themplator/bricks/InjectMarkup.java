package themplator.bricks;

import java.io.InputStream;
import java.util.Arrays;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import themplator.Brick;
import themplator.Model;
import themplator.writers.ThEventWriter;
import themplator.writers.ThNopEventWriter;
import themplators.readers.ThChainingEventReader;
import themplators.readers.ThElementEventReader;
import themplators.readers.ThEventReader;
import themplators.readers.ThReplayEventReader;

public class InjectMarkup<T> extends Brick<T> {
	private static final QName TH_BRICK = new QName("themplator", "brick", "th");
	private final InputStream markup;

	public InjectMarkup(String id) {
		super(id);
		this.markup = getClass().getResourceAsStream(
				getClass().getSimpleName() + ".html");
	}

	public InjectMarkup(String id, InputStream markup) {
		super(id);
		this.markup = markup;
	}

	public InjectMarkup(String id, Model<T> model, InputStream markup) {
		super(id, model);
		this.markup = markup;
	}

	@Override
	protected void render(StartElement e, ThEventReader thr, ThEventWriter thw)
			throws XMLStreamException {
		//renderHead(e, thr, thw);
		XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
		xmlInputFactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
		xmlInputFactory.setProperty(
				XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
		xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE,
				Boolean.TRUE);
		xmlInputFactory
				.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		xmlInputFactory.setXMLResolver(new XMLResolver() {

			public Object resolveEntity(String publicID, String systemID,
					String baseURI, String namespace) throws XMLStreamException {
				System.out.println("resolve " + publicID + ", " + systemID
						+ ", " + baseURI + ", " + namespace);
				return null;
			}
		});

		XMLEventReader evr = xmlInputFactory.createXMLEventReader(markup);
		EndElement ee = renderBody(thr, new ThNopEventWriter());
		ThEventReader othr = new ThChainingEventReader(new ThElementEventReader(TH_BRICK, evr), new ThReplayEventReader(Arrays.asList(ee)));

		super.render(e, othr, thw);
		

		/*boolean emit = false;
		while (othr.hasNext()) {
			XMLEvent ev = othr.next();
			if (emit) {
				if (ev.isEndElement()
						&& ev.asEndElement().getName().equals(TH_BRICK)) {
					emit = false;

				} else {
					thw.add(ev);
				}

			} else {
				if (ev.isStartElement()) {
					StartElement se = ev.asStartElement();
					if (se.getName().equals(TH_BRICK)) {
						emit = true;
					}
				}
			}
		}

		EndElement ee = renderBody(thr, new ThNopEventWriter());
		renderTail(ee, thr, thw);*/
		
	}
}
