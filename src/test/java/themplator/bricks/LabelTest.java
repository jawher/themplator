package themplator.bricks;

import org.junit.Test;

import themplator.models.SimpleModel;

public class LabelTest extends BaseBrickTest<Label> {
	@Test
	public void testText() {
		test("resources/label1.input.xml", "resources/label1.output.xml",
				new Label("label", new SimpleModel<String>(
						"[text that should get inserted]")));
	}

	@Test
	public void testTextNoBody() {
		Label label = new Label("label", new SimpleModel<String>(
				"[text that should get inserted]"));
		label.setRenderBodyOnly(true);
		test("resources/label1.input.xml",
				"resources/label1.nobody.output.xml", label);
	}

	@Test
	public void testNotVisible() {
		Label label = new Label("label", new SimpleModel<String>(
				"[text that should get inserted]"));
		label.setVisible(false);
		test("resources/label1.input.xml",
				"resources/label1.not-visible.output.xml", label);
	}

}
