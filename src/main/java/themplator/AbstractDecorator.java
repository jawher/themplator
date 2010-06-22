package themplator;

import java.util.Collections;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.XMLEvent;

@SuppressWarnings("restriction")
public abstract class AbstractDecorator<T> implements Decorator<T> {

	public void bind(Brick<T> brick) {

	}

	public List<? extends XMLEvent> postBrickEndElement(
			XMLEventFactory eventFactory) {
		return Collections.EMPTY_LIST;
	}

	public List<? extends XMLEvent> postBrickStartElement(
			XMLEventFactory eventFactory) {
		return Collections.EMPTY_LIST;
	}

	public List<? extends XMLEvent> preBrickEndElement(
			XMLEventFactory eventFactory) {
		return Collections.EMPTY_LIST;
	}

	public List<? extends XMLEvent> preBrickStartElement(
			XMLEventFactory eventFactory) {
		return Collections.EMPTY_LIST;
	}

}
