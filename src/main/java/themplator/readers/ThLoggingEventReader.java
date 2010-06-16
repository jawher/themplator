package themplator.readers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import themplator.utils.LogUtils;

@SuppressWarnings("restriction")
public class ThLoggingEventReader implements ThEventReader {
	private ThEventReader reader;

	public ThLoggingEventReader(ThEventReader reader) {
		super();
		this.reader = reader;
	}

	public boolean hasNext() {
		return reader.hasNext();
	}

	public XMLEvent next() throws XMLStreamException {
		XMLEvent ev = reader.next();
		String s = LogUtils.str(ev);
		if(s!=null){
			System.out.println(reader+".read "+s);
		}
		return ev;
	}

	

}
