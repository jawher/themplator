package themplator.decorators;

import org.junit.Test;

import themplator.Brick;
import themplator.bricks.BaseBrickTest;
import themplator.decorators.BrickWrapper.WrapMode;
import static themplator.utils.Thutils.*;

public class BrickWrapperTest extends BaseBrickTest<Brick> {
	@Test
	public void testOuter() {
		Brick b = new Brick("b");
		b.add(new BrickWrapper("div", null));
		test("resources/brick-wrapper1.input.xml",
				"resources/brick-wrapper1.output.xml", b);
	}
	
	
	@Test
	public void testOuterWithAttrs() {
		Brick b = new Brick("b");
		b.add(new BrickWrapper("div", map(pair("id", "wrapper"), pair("class", "container"))));
		test("resources/brick-wrapper1.input.xml",
				"resources/brick-wrapper2.output.xml", b);
	}
	
	@Test
	public void test2OutersWithAttrs() {
		Brick b = new Brick("b");
		b.add(new BrickWrapper("div", map(pair("id", "wrapper"), pair("class", "container"))));
		b.add(new BrickWrapper("p", map(pair("attr", "val"))));
		test("resources/brick-wrapper1.input.xml",
				"resources/brick-wrapper5.output.xml", b);
	}
	
	@Test
	public void testInner() {
		Brick b = new Brick("b");
		b.add(new BrickWrapper("div", null, WrapMode.INNER));
		test("resources/brick-wrapper1.input.xml",
				"resources/brick-wrapper3.output.xml", b);
	}
	
	@Test
	public void testInnerWithAttrs() {
		Brick b = new Brick("b");
		b.add(new BrickWrapper("div", map(pair("id", "wrapper"), pair("class", "container")), WrapMode.INNER));
		test("resources/brick-wrapper1.input.xml",
				"resources/brick-wrapper4.output.xml", b);
	}
	
	@Test
	public void test2InnersWithAttrs() {
		Brick b = new Brick("b");
		b.add(new BrickWrapper("div", map(pair("id", "wrapper2")), WrapMode.INNER));
		b.add(new BrickWrapper("div", map(pair("id", "wrapper"), pair("class", "container")), WrapMode.INNER));
		test("resources/brick-wrapper1.input.xml",
				"resources/brick-wrapper6.output.xml", b);
	}
	
	@Test
	public void testInnerAndOuterWithAttrs() {
		Brick b = new Brick("b");
		b.add(new BrickWrapper("div", map(pair("id", "wrapper2"))));
		b.add(new BrickWrapper("div", map(pair("id", "wrapper"), pair("class", "container")), WrapMode.INNER));
		test("resources/brick-wrapper1.input.xml",
				"resources/brick-wrapper7.output.xml", b);
	}

}
