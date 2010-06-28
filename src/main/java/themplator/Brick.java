package themplator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import themplator.readers.ThElementEventReader;
import themplator.readers.ThEventReader;
import themplator.utils.Pair;
import themplator.writers.ThEventWriter;
import themplator.writers.ThNopEventWriter;

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
		while (thr.hasNext()) {
			XMLEvent ev = thr.next();
			if (ev.isStartElement()) {

				if (cachedVisible) {
					StartElement se = ev.asStartElement();
					String thid = thid(se);
					if (thid != null) {
						getChildByThid(thid).render(
								se,
								new ThElementEventReader(se.getName(), thr,
										true, true), thw);
					} else {
						thw.add(ev);
					}
				}
			} else if (ev.isEndElement()) {

				if (!thr.hasNext()) {
					return ev.asEndElement();
				} else if (cachedVisible) {
					thw.add(ev);
				}

			} else if (cachedVisible) {
				thw.add(ev);
			}
		}
		throw new IllegalStateException();
	}

	protected void renderHead(StartElement startElementEvent,
			ThEventReader evr, ThEventWriter evw) throws XMLStreamException {
		if (!isVisible()) {
			return;
		}

		boolean cachedRenderBodyOnly = isRenderBodyOnly();
		Collection<Decorator> decorators = getDecorators();

		XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
		if (!cachedRenderBodyOnly) {
			List<XMLEvent> preStartEvents = new ArrayList<XMLEvent>();

			for (Decorator d : decorators) {
				preStartEvents.addAll(d.preBrickStartElement(xmlEventFactory));
			}

			for (XMLEvent xmlEvent : preStartEvents) {
				evw.add(xmlEvent);
			}

			evw.add(xmlEventFactory.createStartElement(
					startElementEvent.getName(), null,
					startElementEvent.getNamespaces()));
		}

		List<List<XMLEvent>> postStartEvents = new ArrayList<List<XMLEvent>>();

		for (Decorator d : decorators) {
			postStartEvents.add(d.postBrickStartElement(xmlEventFactory));
		}

		Pair<List<Attribute>, List<XMLEvent>> sortedPostStartEvents = sortOpenEvents(postStartEvents);
		if (!cachedRenderBodyOnly) {
			Iterator elementAttributes = startElementEvent.getAttributes();
			Map<QName, Attribute> attributesAccum = new HashMap<QName, Attribute>();
			while (elementAttributes.hasNext()) {
				Attribute a = (Attribute) elementAttributes.next();
				attributesAccum.put(a.getName(), a);
			}
			for (Attribute a : sortedPostStartEvents.getLeft()) {
				attributesAccum.put(a.getName(), a);
			}

			for (Attribute a : attributesAccum.values()) {
				evw.add(a);
			}

		}
		for (XMLEvent xmlEvent : sortedPostStartEvents.getRight()) {
			evw.add(xmlEvent);
		}
	}

	protected void renderTail(EndElement e, ThEventReader evr, ThEventWriter evw)
			throws XMLStreamException {
		if (!isVisible()) {
			return;
		}

		XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
		Collection<Decorator> decorators = getDecorators();
		for (Decorator d : decorators) {
			List<XMLEvent> preEndEvents = d.preBrickEndElement(xmlEventFactory);

			for (XMLEvent xmlEvent : preEndEvents) {
				evw.add(xmlEvent);
			}
		}

		boolean cachedRenderBodyOnly = isRenderBodyOnly();
		if (!cachedRenderBodyOnly) {
			evw.add(e);
		} else {
			return;
		}

		for (Decorator d : decorators) {
			List<XMLEvent> postEndEvents = d
					.postBrickEndElement(xmlEventFactory);
			for (XMLEvent xmlEvent : postEndEvents) {
				evw.add(xmlEvent);
			}
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

	private Pair<List<Attribute>, List<XMLEvent>> sortOpenEvents(
			List<List<XMLEvent>> eventsList) {
		List<Attribute> headAttrs = new ArrayList<Attribute>();
		List<XMLEvent> others = new ArrayList<XMLEvent>();
		for (List<XMLEvent> events : eventsList) {
			boolean head = true;
			for (XMLEvent ev : events) {
				if (head) {
					if (ev.isAttribute()) {
						headAttrs.add((Attribute) ev);
					} else {
						head = false;
						others.add(ev);
					}
				} else {
					others.add(ev);
				}
			}
		}

		return new Pair<List<Attribute>, List<XMLEvent>>(headAttrs, others);
	}

}
