package themplator.utils;

public class Pair<S, T> {
	private S left;
	private T right;

	public Pair(S left, T right) {
		super();
		this.left = left;
		this.right = right;
	}

	public S getLeft() {
		return left;
	}

	public T getRight() {
		return right;
	}
}
