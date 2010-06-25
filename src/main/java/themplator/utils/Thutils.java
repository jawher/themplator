package themplator.utils;

import java.util.HashMap;
import java.util.Map;

public class Thutils {
	public static <U, V> Pair<U, V> pair(U left, V right) {
		return new Pair<U, V>(left, right);
	}

	public static <U, V> Map<U, V> map(Pair<U, V>... pairs) {
		Map<U, V> res = new HashMap<U, V>(pairs.length);
		for (Pair<U, V> pair : pairs) {
			res.put(pair.getLeft(), pair.getRight());
		}
		return res;
	}
}
