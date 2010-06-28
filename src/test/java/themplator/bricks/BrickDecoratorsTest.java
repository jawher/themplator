package themplator.bricks;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.XMLEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import themplator.AbstractDecorator;
import themplator.Brick;
import themplator.Decorator;
import static themplator.utils.XMLEventsUtils.*;

@RunWith(Parameterized.class)
public class BrickDecoratorsTest extends BaseBrickTest<Brick<?>> {

	private static List<Decorator<?>> decs(Decorator<?>... items) {
		return Arrays.asList(items);
	}

	private static Decorator<?> preDec(final List<XMLEvent> start,
			final List<XMLEvent> end) {
		return new AbstractDecorator<Object>() {
			@Override
			public List<? extends XMLEvent> preBrickStartElement(
					XMLEventFactory eventFactory) {
				return start;
			}

			@Override
			public List<? extends XMLEvent> preBrickEndElement(
					XMLEventFactory eventFactory) {
				return end;
			}
		};
	}

	private static Decorator<?> preDec(final List<XMLEvent> start) {
		return new AbstractDecorator<Object>() {
			@Override
			public List<? extends XMLEvent> preBrickStartElement(
					XMLEventFactory eventFactory) {
				return start;
			}

			@Override
			public List<? extends XMLEvent> preBrickEndElement(
					XMLEventFactory eventFactory) {
				return close(start);
			}
		};
	}

	private static Decorator<?> postDec(final List<XMLEvent> start,
			final List<XMLEvent> end) {
		return new AbstractDecorator<Object>() {
			@Override
			public List<? extends XMLEvent> postBrickStartElement(
					XMLEventFactory eventFactory) {
				return start;
			}

			@Override
			public List<? extends XMLEvent> postBrickEndElement(
					XMLEventFactory eventFactory) {
				return end;
			}
		};
	}

	private static Decorator<?> postDec(final List<XMLEvent> start) {
		return new AbstractDecorator<Object>() {
			@Override
			public List<? extends XMLEvent> postBrickStartElement(
					XMLEventFactory eventFactory) {
				return start;
			}

			@Override
			public List<? extends XMLEvent> postBrickEndElement(
					XMLEventFactory eventFactory) {
				return close(start);
			}
		};
	}

	private static String IN = "resources/decorators/brick.input.xml";
	private static String INV_OUT = "resources/decorators/brick.not-visible.output.xml";

	private static String in(String i) {
		return "resources/decorators/" + i + ".input.xml";
	}
	private static String out(String o) {
		return "resources/decorators/" + o + ".output.xml";
	}

	@Parameters
	public static Collection<Object[]> params() {
		return Arrays.asList(new Object[][] {
				{
						IN,
						out("post.e_a"),
						INV_OUT,
						out("post.e_a.body-only"),
						decs(postDec(evts(se("x"))),
								postDec(evts(a("id", "th")))) },
				{
						IN,
						out("post.a_e_a"),
						INV_OUT,
						out("post.a_e_a.body-only"),
						decs(postDec(evts(a("class", "error"))),
								postDec(evts(se("x")), evts(ee("x"))),
								postDec(evts(a("id", "th")), evts())) },
				{
						IN,
						out("post.aea_a"),
						INV_OUT,
						out("post.aea_a.body-only"),
						decs(postDec(evts(a("class", "error"), se("x"),
								a("xa", "xv"))),
								postDec(evts(a("id", "th")), evts())) },
				{
						IN,
						out("post.a_e_ae.same-attr"),
						null,
						null,
						decs(postDec(evts(a("a", "x"))),
								postDec(evts(se("dummy"))),
								postDec(evts(a("a", "y")))) },

				{
						in("brick2"),
						out("post.a_e_ae.same-attr"),
						null,
						null,
						decs(postDec(evts(a("a", "x"))),
								postDec(evts(se("dummy"))),
								postDec(evts(a("a", "y")))) },

		});
	}

	private String input;
	private String regularOutput;
	private String invisibleOutput;
	private String renderBodyOnlyOutput;
	private List<Decorator<?>> decorators;

	public BrickDecoratorsTest(String input, String regularOutput,
			String invisbleOutput, String renderBodyOnlyOutput,
			List<Decorator<?>> decorators) {
		super();
		this.input = input;
		this.regularOutput = regularOutput;
		this.invisibleOutput = invisbleOutput;
		this.renderBodyOnlyOutput = renderBodyOnlyOutput;
		this.decorators = decorators;
	}

	@Test
	public void testBrickHandlingOfDecoratorsOrder() {
		if (regularOutput != null) {
			Brick<?> brick = new Brick<Object>("b");
			for (Decorator<?> decorator : decorators) {
				brick.add(decorator);
			}
			super.test(input, regularOutput, brick);
		}
	}

	@Test
	public void testInvisibleBrickHandlingOfDecorators() {
		if (invisibleOutput != null) {
			Brick<?> brick = new Brick<Object>("b");
			brick.setVisible(false);
			for (Decorator<?> decorator : decorators) {
				brick.add(decorator);
			}
			super.test(input, invisibleOutput, brick);
		}
	}

	@Test
	public void testRenderBodyOnlyBrickHandlingOfDecorators() {
		if (renderBodyOnlyOutput != null) {
			Brick<?> brick = new Brick<Object>("b");
			brick.setRenderBodyOnly(true);
			for (Decorator<?> decorator : decorators) {
				brick.add(decorator);
			}
			super.test(input, renderBodyOnlyOutput, brick);
		}
	}
}
