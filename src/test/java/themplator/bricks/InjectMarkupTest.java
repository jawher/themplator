package themplator.bricks;

import org.junit.Test;

import themplator.models.SimpleModel;

public class InjectMarkupTest extends BaseBrickTest<InjectMarkup<?>> {
	@Test
	public void testSimple() {
		InjectMarkup<?> im = new InjectMarkup<Object>(
				"container",
				InjectMarkupTest.class
						.getResourceAsStream("resources/inject-markup1.fragment.input.xml"));
		test("resources/inject-markup1.input.xml",
				"resources/inject-markup1.output.xml", im);
	}
	
	@Test
	public void testWithLabel() {
		InjectMarkup<?> im = new InjectMarkup<Object>(
				"container",
				InjectMarkupTest.class
						.getResourceAsStream("resources/inject-markup2.fragment.input.xml"));
		im.add(new Label("content", new SimpleModel<String>("magic")));
		test("resources/inject-markup1.input.xml",
				"resources/inject-markup4.output.xml", im);
	}

	@Test
	public void testNoBody() {
		InjectMarkup<?> im = new InjectMarkup<Object>(
				"container",
				InjectMarkupTest.class
						.getResourceAsStream("resources/inject-markup1.fragment.input.xml"));
		im.setRenderBodyOnly(true);
		test("resources/inject-markup1.input.xml",
				"resources/inject-markup2.output.xml", im);
	}

	@Test
	public void testNotVisible() {
		InjectMarkup<?> im = new InjectMarkup<Object>(
				"container",
				InjectMarkupTest.class
						.getResourceAsStream("resources/inject-markup1.fragment.input.xml"));
		im.setVisible(false);
		test("resources/inject-markup1.input.xml",
				"resources/inject-markup3.output.xml", im);
	}

}
