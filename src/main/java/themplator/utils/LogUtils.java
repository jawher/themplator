package themplator.utils;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

@SuppressWarnings("restriction")
public class LogUtils {

	public static void log(XMLEvent ev) {
		String s = str(ev);
		if(s!=null){
			System.out.println(s);
		}

	}
	
	public static String str(XMLEvent ev) {
		if (ev.isStartElement()) {
			StartElement se = ev.asStartElement();
			return("<" + se.getName().getLocalPart() + ">");
		} else if (ev.isEndElement()) {
			EndElement se = ev.asEndElement();
			return("</" + se.getName().getLocalPart() + ">");
		} else if (ev.isStartDocument()) {
			return("begin");
		} else if (ev.isEndDocument()) {
			return("end");
		} else if (ev.isCharacters()) {
			return("text: '"+ev.asCharacters().getData()+"'");
		}
		return null;

	}
}
