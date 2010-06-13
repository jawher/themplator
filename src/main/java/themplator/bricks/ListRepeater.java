package themplator.bricks;

import java.util.Collection;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import themplator.Brick;
import themplator.Decorator;
import themplator.Model;
import themplator.models.SimpleModel;
import themplator.writers.ThEventWriter;
import themplators.readers.ThEventReader;
import themplators.readers.ThLoggingEventReader;
import themplators.readers.ThRecordingEventReader;
import themplators.readers.ThReplayEventReader;

public abstract class ListRepeater<T> extends Brick<List<T>> {

	private ListItem<T> currentItem;

	public ListRepeater(String id, Model<List<T>> model) {
		super(id, model);
	}

	@Override
	public void add(Brick b) {
		throw new UnsupportedOperationException(
				"ListRepeater doesn't support adding children directly. override the populate method instead.");
	}

	@Override
	public void add(Decorator b) {
		throw new UnsupportedOperationException(
				"ListRepeater doesn't support adding children directly. override the populate method instead.");
	}

	@Override
	public void render(StartElement e, ThEventReader thr, ThEventWriter thw)
			throws XMLStreamException {

		List<T> items = getModel().get();
		EndElement ee = null;

		List<XMLEvent> contentsEvents = null;
		ThEventReader contentsReader = null;

		for (int i = 0; i < items.size(); i++) {
			Model<T> itemModel = wrapListItem(items.get(i));
			currentItem = new ListItem<T>(getId(), i, itemModel);
			populate(currentItem);

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

		currentItem = null;

	}

	@Override
	public Collection<Brick> getChildren() {
		return currentItem.getChildren();
	}

	@Override
	public Collection<Decorator> getDecorators() {
		return currentItem.getDecorators();
	}

	protected abstract void populate(ListItem<T> item);

	protected Model<T> wrapListItem(T item) {
		return new SimpleModel<T>(item);
	}

	public static class ListItem<S> extends Brick<S> {
		private final int index;

		private ListItem(String id, int index, Model<S> model) {
			super(id, model);
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

	}

}
