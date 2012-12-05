/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.xmpps.impl;

import java.util.List;

import tigase.jaxmpp.core.client.xml.DefaultElement;
import tigase.jaxmpp.core.client.xml.Element;
import tigase.jaxmpp.core.client.xml.XMLException;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.CollectionUtil;
import com.fs.xmpps.api.ElementI;

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

			throw new FsException(e);
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
			throw new FsException(e);
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
			throw new FsException(e);
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
			throw new FsException(e);
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
			throw new FsException(e);
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
	 * Oct 26, 2012
	 */
	@Override
	public String getXmlns() {
		//
		return this.getAttribute("xmlns");
	}

	/*
	 * Oct 26, 2012
	 */
	@Override
	public String getName() {
		//
		try {
			return this.element.getName();
		} catch (XMLException e) {
			throw new FsException(e);
		}//
	}
}
