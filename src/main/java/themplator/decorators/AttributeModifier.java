package themplator.decorators;

import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.XMLEvent;

import themplator.AbstractDecorator;
import themplator.Model;
import themplator.models.SimpleModel;

public class AttributeModifier<T> extends AbstractDecorator<T> {
	private String attrName;
	private Model<String> attrValue;

	public AttributeModifier(String attrName, String attrValue) {
		super();
		this.attrName = attrName;
		this.attrValue = new SimpleModel<String>(attrValue);
	}

	public AttributeModifier(String attrName, Model<String> attrValue) {
		super();
		this.attrName = attrName;
		this.attrValue = attrValue;
	}

	@SuppressWarnings("restriction")
	@Override
	public List<? extends XMLEvent> postBrickStartElement(
			XMLEventFactory eventFactory) {
		return Arrays.asList(eventFactory.createAttribute(attrName, attrValue
				.get()));
	}

}
