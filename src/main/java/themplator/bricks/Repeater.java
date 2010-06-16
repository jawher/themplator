package themplator.bricks;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import themplator.Brick;
import themplator.Model;
import themplator.readers.ThEventReader;
import themplator.readers.ThRecordingEventReader;
import themplator.readers.ThReplayEventReader;
import themplator.writers.ThEventWriter;

public class Repeater extends Brick<Long> {

	public Repeater(String id, Model<Long> model) {
		super(id, model);
	}

	@Override
	protected void render(StartElement e, ThEventReader thr, ThEventWriter thw)
			throws XMLStreamException {

		Long count = getModel().get();
		EndElement ee = null;

		List<XMLEvent> contentsEvents = null;
		ThEventReader contentsReader = null;

		for (int i = 0; i < count; i++) {
			renderHead(e, thr, thw);
			if (i == 0) {
				ThRecordingEventReader trer = new ThRecordingEventReader(thr);
				ee = renderBody(trer, thw);
				contentsEvents = trer.getRecordedEvents();
			} else {
				contentsReader = new ThReplayEventReader(contentsEvents);
				ee = renderBody(contentsReader, thw);

			}
			renderTail(ee, thr, thw);
		}

	}

}
