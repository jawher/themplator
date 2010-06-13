package themplator.utils;

import javax.xml.stream.XMLInputFactory;

public class StaxUtils {
	public static XMLInputFactory createInputFactory() {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
		xmlInputFactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
		xmlInputFactory.setProperty(
				XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
		xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE,
				Boolean.TRUE);
		xmlInputFactory
				.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		return xmlInputFactory;
	}
}
