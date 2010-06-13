package themplator.bricks;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.junit.Assert;
import org.junit.Test;

import themplator.Themplate;
import themplator.models.SimpleModel;
import themplator.utils.StaxUtils;
import themplator.utils.TestUtils;
import themplator.writers.ThRecordingEventWriter;
import themplators.readers.ThEventReader;
import themplators.readers.ThReplayEventReader;
import themplators.readers.ThStaxEventReader;
import themplators.readers.ThWhitespaceStripperEventReader;

public class LabelTest {
	@Test
	public void testText() {
		test("resources/label1.input.xml", "resources/label1.output.xml",
				new Label("label", new SimpleModel<String>(
						"[text that should get inserted]")), false);
	}

	@Test
	public void testTextNoBody() {
		Label label = new Label("label", new SimpleModel<String>(
				"[text that should get inserted]"));
		label.setRenderBodyOnly(true);
		test("resources/label1.input.xml",
				"resources/label1.nobody.output.xml", label, true);
	}
	
	@Test
	public void testNotVisible() {
		Label label = new Label("label", new SimpleModel<String>(
				"[text that should get inserted]"));
		label.setVisible(false);
		test("resources/label1.input.xml",
				"resources/label1.not-visible.output.xml", label, true);
	}

	public void test(String input, String output, Label label,
			boolean ignoreWhitespace) {
		XMLInputFactory xmlInputFactory = StaxUtils.createInputFactory();

		XMLEventReader evr;
		try {
			evr = xmlInputFactory.createXMLEventReader(LabelTest.class
					.getResourceAsStream(input));

			ThEventReader inputStream = ignoreWhitespace ? new ThWhitespaceStripperEventReader(
					new ThStaxEventReader(evr))
					: new ThStaxEventReader(evr);
			Themplate th = new Themplate();
			th.add(label);

			ThRecordingEventWriter thw = new ThRecordingEventWriter();
			th.render(inputStream, thw);

			ThEventReader expectedStream = ignoreWhitespace ? new ThWhitespaceStripperEventReader(
					new ThStaxEventReader(xmlInputFactory
							.createXMLEventReader(LabelTest.class
									.getResourceAsStream(output))))
					: new ThStaxEventReader(xmlInputFactory
							.createXMLEventReader(LabelTest.class
									.getResourceAsStream(output)));

			ThEventReader actualStream = new ThReplayEventReader(thw
					.getRecordedEvents());

			Assert.assertTrue(TestUtils.assertEquals(expectedStream,
					actualStream));
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
	}
}
