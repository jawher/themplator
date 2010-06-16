package themplator.bricks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import themplator.models.SimpleModel;

public class ListRepeaterTest extends BaseBrickTest<ListRepeater<?>> {

	@Test
	public void testRepeat() {
		List<Integer> list = new ArrayList<Integer>(Arrays
				.asList(1, 3, 5, 7, 9));
		ListRepeater<Integer> repeater = new ListRepeater<Integer>("item",
				new SimpleModel<List<Integer>>(list)) {

			@Override
			protected void populate(ListItem<Integer> item) {

			}
		};
		test("resources/list-repeater1.input.xml",
				"resources/list-repeater1.output.xml", repeater);
	}

	@Test
	public void testRepeatWithLabel() {
		List<Integer> list = new ArrayList<Integer>(Arrays
				.asList(1, 3, 5, 7, 9));
		ListRepeater<Integer> repeater = new ListRepeater<Integer>("item",
				new SimpleModel<List<Integer>>(list)) {

			@Override
			protected void populate(ListItem<Integer> item) {
				item.add(new Label("label", new SimpleModel<String>(item
						.getModel().get().toString())));

			}
		};
		test("resources/list-repeater2.input.xml",
				"resources/list-repeater2.output.xml", repeater);
	}

	@Test
	public void testTextNoBody() {
		List<Integer> list = new ArrayList<Integer>(Arrays
				.asList(1, 3, 5, 7, 9));
		ListRepeater<Integer> repeater = new ListRepeater<Integer>("item",
				new SimpleModel<List<Integer>>(list)) {

			@Override
			protected void populate(ListItem<Integer> item) {
				item.setRenderBodyOnly(true);
				item.add(new Label("label", new SimpleModel<String>(item
						.getModel().get().toString())));

			}
		};
		
		test("resources/list-repeater2.input.xml",
				"resources/list-repeater2.nobody.output.xml", repeater);
	}
	/*
	 * @Test public void testNotVisible() { Repeater repeater = new
	 * Repeater("item", new SimpleModel<Long>(4l)); repeater.setVisible(false);
	 * test("resources/repeater1.input.xml",
	 * "resources/repeater1.not-visible.output.xml", repeater); }
	 */

}
