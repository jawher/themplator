package themplator;

import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.XMLEvent;

@SuppressWarnings("restriction")
public interface Decorator<T> {
	void bind(Brick<T> brick);

	List<? extends XMLEvent> preBrickStartElement(XMLEventFactory eventFactory);

	List<? extends XMLEvent> postBrickStartElement(XMLEventFactory eventFactory);

	List<? extends XMLEvent> preBrickEndElement(XMLEventFactory eventFactory);

	List<? extends XMLEvent> postBrickEndElement(XMLEventFactory eventFactory);
}
