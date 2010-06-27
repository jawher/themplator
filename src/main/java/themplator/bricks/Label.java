package themplator.bricks;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import themplator.Brick;
import themplator.Model;
import themplator.readers.ThEventReader;
import themplator.writers.ThEventWriter;
import themplator.writers.ThNopEventWriter;

public class Label extends Brick<String> {

	public Label(String id, Model<String> model) {
		super(id, model);
	}

	@Override
	protected void render(StartElement e, ThEventReader thr, ThEventWriter thw)
			throws XMLStreamException {
		renderHead(e, thr, thw);
		if (isVisible()) {
			XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
			thw.add(xmlEventFactory.createCharacters(getModel().get()));
		}
		EndElement ee = renderBody(thr, new ThNopEventWriter());
		renderTail(ee, thr, thw);
	}

}
