package themplator.models;

import themplator.Model;

public class SimpleModel<T> implements Model<T> {
	private T value;

	public SimpleModel() {
	}

	public SimpleModel(T value) {
		super();
		this.value = value;
	}

	public T get() {
		return value;
	}

	public void set(T value) {
		this.value = value;
	}

}
