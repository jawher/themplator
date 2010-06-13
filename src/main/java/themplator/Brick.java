package themplator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import themplator.writers.ThEventWriter;
import themplator.writers.ThNopEventWriter;
import themplators.readers.ThEventReader;

public class Brick<T> extends AbstractBrick {
	protected Model<T> model;
	private boolean visible = true;
	private boolean renderBodyOnly = false;
	private String id;
	private List<Decorator> decorators = new ArrayList<Decorator>();

	public Brick(String id) {
		super();
		this.id = id;
	}

	public Brick(String id, Model<T> model) {
		super();
		this.id = id;
		this.model = model;
	}

	public Model<T> getModel() {
		return model;
	}

	public void setModel(Model<T> model) {
		this.model = model;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isRenderBodyOnly() {
		return renderBodyOnly;
	}

	public void setRenderBodyOnly(boolean renderBodyOnly) {
		this.renderBodyOnly = renderBodyOnly;
	}

	public String getId() {
		return id;
	}

	public void add(Decorator b) {
		decorators.add(b);
	}

	public Collection<Decorator> getDecorators() {
		return Collections.unmodifiableCollection(decorators);
	}

	protected void render(StartElement e, ThEventReader thr, ThEventWriter thw)
			throws XMLStreamException {
		if (isVisible()) {
			renderHead(e, thr, thw);
			EndElement ee = renderBody(thr, thw);
			renderTail(ee, thr, thw);
		} else {
			renderBody(thr, new ThNopEventWriter());
		}
	}

	protected EndElement renderBody(ThEventReader thr, ThEventWriter thw)
			throws XMLStreamException {
		boolean cachedVisible = isVisible();
		int depth = 0;
		while (thr.hasNext()) {
			XMLEvent ev = thr.next();
			if (ev.isStartElement()) {
				depth++;

				if (cachedVisible) {
					StartElement se = ev.asStartElement();
					String thid = thid(se);
					if (thid != null) {
						getChildByThid(thid).render(se, thr, thw);
					} else {
						thw.add(ev);
					}
				}
			} else if (ev.isEndElement()) {
				depth--;
				if (depth <= 0) {
					return ev.asEndElement();
				} else if (cachedVisible) {
					thw.add(ev);
				}

			} else if (cachedVisible) {
				thw.add(ev);
			}
		}
		throw new IllegalStateException(depth + "");
	}

	protected void renderHead(StartElement e, ThEventReader evr,
			ThEventWriter evw) throws XMLStreamException {
		if (!isVisible()) {
			return;
		}

		boolean cachedRenderBodyOnly = isRenderBodyOnly();
		if (!cachedRenderBodyOnly) {
			SortedSet<XMLEvent> preStartEvents = new TreeSet<XMLEvent>(
					AttrThenCharThenElem.INSTANCE);

			for (Decorator d : getDecorators()) {
				preStartEvents.addAll(d.preBrickStartElement(XMLEventFactory
						.newFactory()));
			}

			for (XMLEvent xmlEvent : preStartEvents) {
				evw.add(xmlEvent);
			}

			evw.add(e);
		}

		SortedSet<XMLEvent> postStartEvents = new TreeSet<XMLEvent>(
				AttrThenCharThenElem.INSTANCE);
		for (Decorator d : getDecorators()) {
			postStartEvents.addAll(d.postBrickStartElement(XMLEventFactory
					.newFactory()));
		}
		for (XMLEvent xmlEvent : postStartEvents) {
			if (!cachedRenderBodyOnly || !xmlEvent.isAttribute()) {
				evw.add(xmlEvent);
			}
		}
	}

	protected void renderTail(EndElement e, ThEventReader evr, ThEventWriter evw)
			throws XMLStreamException {
		if (!isVisible()) {
			return;
		}
		SortedSet<XMLEvent> preEndEvents = new TreeSet<XMLEvent>(
				AttrThenCharThenElem.INSTANCE);

		XMLEventFactory xmlEventFactory = XMLEventFactory.newFactory();
		for (Decorator d : getDecorators()) {
			preEndEvents.addAll(d.preBrickEndElement(xmlEventFactory));
		}

		for (XMLEvent xmlEvent : preEndEvents) {
			evw.add(xmlEvent);
		}

		boolean cachedRenderBodyOnly = isRenderBodyOnly();
		if (!cachedRenderBodyOnly) {
			evw.add(e);
		} else {
			return;
		}

		SortedSet<XMLEvent> postEndEvents = new TreeSet<XMLEvent>(
				AttrThenCharThenElem.INSTANCE);
		for (Decorator d : getDecorators()) {
			postEndEvents.addAll(d.postBrickEndElement(xmlEventFactory));
		}
		for (XMLEvent xmlEvent : postEndEvents) {
			evw.add(xmlEvent);
		}
	}

	private String thid(StartElement e) {
		Attribute attr = e.getAttributeByName(new QName("thid"));
		if (attr != null) {
			return attr.getValue();
		} else {
			return null;
		}
	}

	// FIXME: c'est pas aussi simple
	private static final class AttrThenCharThenElem implements
			Comparator<XMLEvent> {
		public static final Comparator<XMLEvent> INSTANCE = new AttrThenCharThenElem();

		public int compare(XMLEvent o1, XMLEvent o2) {
			if (o1.isAttribute()) {
				return -1;
			} else if (o2.isAttribute()) {
				return 1;
			} else if (o1.isCharacters()) {
				return -1;
			} else if (o2.isCharacters()) {
				return -1;
			} else
				return 0;
		}

	}

}
