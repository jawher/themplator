package themplator.bricks;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

import themplator.Brick;
import themplator.Themplate;

public abstract class BaseBrickTest<T extends Brick<?>> {
	@BeforeClass
	public static void beforeClass() {
		XMLUnit.setIgnoreWhitespace(true);
	}

	protected void test(String input, String output, T brick) {
		try {

			Themplate th = new Themplate();
			th.add(brick);

			ByteArrayOutputStream os = new ByteArrayOutputStream();

			th.render(getClass().getResourceAsStream(input), os);

			assertXMLEqual(new InputSource(getClass().getResourceAsStream(
					output)), new InputSource(new ByteArrayInputStream(os
					.toByteArray())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
