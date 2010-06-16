package themplator.bricks;

import org.junit.Test;

import themplator.models.SimpleModel;

public class RepeaterTest extends BaseBrickTest<Repeater> {

	@Test
	public void testRepeat() {
		test("resources/repeater1.input.xml", "resources/repeater1.output.xml",
				new Repeater("item", new SimpleModel<Long>(4l)));
	}

	@Test
	public void testTextNoBody() {
		Repeater repeater = new Repeater("item", new SimpleModel<Long>(4l));
		repeater.setRenderBodyOnly(true);
		test("resources/repeater1.input.xml",
				"resources/repeater1.nobody.output.xml", repeater);
	}

	@Test
	public void testNotVisible() {
		Repeater repeater = new Repeater("item", new SimpleModel<Long>(4l));
		repeater.setVisible(false);
		test("resources/repeater1.input.xml",
				"resources/repeater1.not-visible.output.xml", repeater);
	}

}
