package themplator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractBrick {

	protected List<Brick> children = new ArrayList<Brick>();

	public void add(Brick b) {
		children.add(b);
	}

	public Collection<Brick> getChildren() {
		return Collections.unmodifiableCollection(children);
	}
	
	public Brick getChildByThid(String thid){
		for (Brick b : getChildren()) {
			if(thid.equals(b.getId()))
				return b;
		}
		return null;
	}

	

}