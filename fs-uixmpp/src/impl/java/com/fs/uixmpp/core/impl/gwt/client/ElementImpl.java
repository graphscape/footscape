/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client;

import java.util.ArrayList;
import java.util.List;

import tigase.jaxmpp.core.client.xml.DefaultElement;
import tigase.jaxmpp.core.client.xml.Element;
import tigase.jaxmpp.core.client.xml.XMLException;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.util.CollectionUtil;
import com.fs.uixmpp.core.api.gwt.client.ElementI;

/**
 * @author wu
 * 
 */
public class ElementImpl implements ElementI {

	protected Element element;

	public ElementImpl(Element ele) {
		this.element = ele;
	}

	public ElementImpl(String name) {

		this(new DefaultElement(name));
	}

	public Element getElement() {
		return this.element;
	}

	@Override
	public String getName() {
		try {
			return this.element.getName();
		} catch (XMLException e) {
			throw new UiException("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmpp.api.ElementI#addChild(java.lang.String)
	 */
	@Override
	public ElementI addChild(String name) {
		ElementImpl rt = new ElementImpl(name);
		try {
			this.element.addChild(rt.element);
		} catch (XMLException e) {

			throw new UiException("", e);
		}//
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmpp.api.ElementI#setAttribute(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void setAttribute(String key, String value) {
		try {
			this.element.setAttribute(key, value);
		} catch (XMLException e) {
			throw new UiException("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmpp.api.ElementI#getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute(String name) {

		try {
			return this.element.getAttribute(name);
		} catch (XMLException e) {
			throw new UiException("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmpp.api.ElementI#setText(java.lang.String)
	 */
	@Override
	public void setText(String value) {

		try {
			this.element.setValue(value);
		} catch (XMLException e) {
			throw new UiException("", e);
		}
	}

	/**
	 * @param ele
	 * @return
	 */
	public static ElementI valueOf(Element ele) {
		if (ele == null) {
			return null;
		}
		return new ElementImpl(ele);
	}

	public ElementI getChild(String name, boolean force) {

		List<Element> el;
		try {
			el = this.element.getChildren(name);
		} catch (XMLException e) {
			throw new UiException("", e);
		}

		Element ele = CollectionUtil.single(el, force);

		return ElementImpl.valueOf(ele);
	}

	@Override
	public ElementI getOrAddChild(String name) {
		ElementI rt = this.getChild(name, false);
		if (rt == null) {
			rt = this.addChild(name);
		}
		return rt;
	}

	public void setChildText(String name, String value) {

		ElementI ele = this.addChild(name);

		ele.setText(value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmppclient.api.ElementI#setXmlns(java.lang.String)
	 */
	@Override
	public void setXmlns(String xmlns) {
		this.setAttribute("xmlns", xmlns);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uixmpp.core.api.gwt.client.ElementI#getText()
	 */
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		try {
			return this.element.getValue();
		} catch (XMLException e) {
			throw new UiException("", e);//
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uixmpp.core.api.gwt.client.ElementI#getXmlns()
	 */
	@Override
	public String getXmlns() {
		// TODO Auto-generated method stub
		return this.getAttribute("xmlns");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uixmpp.core.api.gwt.client.ElementI#getChildList(java.lang.String)
	 */
	@Override
	public List<ElementI> getChildList(String name) {
		List<ElementI> rt = new ArrayList<ElementI>();
		try {
			List<Element> rtL = this.element.getChildren(name);
			for (Element e : rtL) {
				rt.add(new ElementImpl(e));
			}
		} catch (XMLException e) {
			throw new UiException("", e);
		}

		return rt;
	}

	/* (non-Javadoc)
	 * @see com.fs.uixmpp.core.api.gwt.client.ElementI#getAttributeAsInt(java.lang.String)
	 */
	@Override
	public int getAttributeAsInt(String name) {
		String v = this.getAttribute(name);
		return Integer.parseInt(v);
	}
}
