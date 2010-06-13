package themplator.examples;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import themplator.Themplate;
import themplator.bricks.InjectMarkup;
import themplator.bricks.Label;
import themplator.bricks.ListRepeater;
import themplator.models.SimpleModel;

public class Thest2 {
	public static void main(String[] args) throws XMLStreamException {
List<String> data = Arrays.asList("one", "two", "three", "four");

Themplate t = new Themplate();

ListRepeater<String> d = new ListRepeater<String>("d",
		new SimpleModel<List<String>>(data)) {

	@Override
	protected void populate(
			themplator.bricks.ListRepeater.ListItem<String> item) {
		InjectMarkup im = new InjectMarkup("s", Thest2.class
				.getResourceAsStream("brick.html"));
		im.add(new Label("s", item.getModel()));
		item.add(im);
	}

};

d.setRenderBodyOnly(true);
t.add(d);

Label label = new Label("invisible", new SimpleModel<String>("text"));
label.setRenderBodyOnly(true);
t.add(label);

InputStream is = Thest2.class.getResourceAsStream("test0.html");
ByteArrayOutputStream os = new ByteArrayOutputStream();

t.render(is, os);

System.out.println(new String(os.toByteArray()));
	}
}
