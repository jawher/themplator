package themplator.decorators;

import org.junit.Test;

import themplator.Brick;
import themplator.bricks.BaseBrickTest;
import themplator.bricks.Label;
import themplator.models.SimpleModel;

public class AttributeModifierTest extends BaseBrickTest<Brick> {
	@Test
	public void test() {
		Brick b = new Brick("b");
		b.add(new AttributeModifier("class", new SimpleModel<String>("magic")));
		test("resources/attribute-modifier1.input.xml", "resources/attribute-modifier1.output.xml", b);
	}

	@Test
	public void testNoBody() {
		Brick b = new Brick("b");
		b.setRenderBodyOnly(true);
		b.add(new AttributeModifier("class", new SimpleModel<String>("magic")));
		test("resources/attribute-modifier1.input.xml", "resources/attribute-modifier1.nobody.output.xml", b);
	}
	
	

	@Test
	public void testNotVisible() {
		Brick b = new Brick("b");
		b.setVisible(false);
		b.add(new AttributeModifier("class", new SimpleModel<String>("magic")));
		test("resources/attribute-modifier1.input.xml", "resources/attribute-modifier1.not-visible.output.xml", b);
	}

}
