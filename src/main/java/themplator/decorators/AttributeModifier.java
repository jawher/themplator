package themplator.decorators;

import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.XMLEvent;

import themplator.AbstractBrick;
import themplator.AbstractDecorator;
import themplator.Brick;
import themplator.Model;
import themplator.models.SimpleModel;

public class AttributeModifier extends AbstractDecorator {
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

	public void bind(AbstractBrick brick) {

	}

	@SuppressWarnings("restriction")
	@Override
	public List<? extends XMLEvent> postBrickStartElement(
			XMLEventFactory eventFactory) {
		return Arrays.asList(eventFactory.createAttribute(attrName, attrValue.get()));
	}

	public void bind(Brick brick) {
		// TODO Auto-generated method stub
		
	}

}
