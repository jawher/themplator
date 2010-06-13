package themplator.examples;

import themplator.bricks.InjectMarkup;
import themplator.bricks.Label;
import themplator.models.SimpleModel;

public class Menu extends InjectMarkup {

	public Menu(String id) {
		super(id);
		add(new Label("i", new SimpleModel<String>("w00t !")));
	}

}
