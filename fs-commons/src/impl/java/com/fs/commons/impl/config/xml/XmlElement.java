/**
 * Jul 9, 2012
 */
package com.fs.commons.impl.config.xml;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class XmlElement {

	protected XmlElement parent;

	protected Element element;

	protected List<XmlElement> childList = new ArrayList<XmlElement>();

	public XmlElement(XmlElement parent, Element ele) {
		this.parent = parent;
		this.element = ele;

		List<Element> el = ele.elements();// child list

		for (int i = 0; i < el.size(); i++) {
			Element ce = el.get(i);
			String name = ce.getName();
			XmlElement xe = null;
			if (name.equals("config")) {
				xe = new XmlConfigElement(this, ce);
			} else if (name.equals("prefix")) {
				xe = new XmlPrefixElement(this, ce);
			} else {
				xe = new XmlPropertyElement(this, ce);
			}
			this.childList.add(xe);
		}
	}
	/**
	 * @return
	 */
	public String getName() {
		return this.element.getName();
	}
	
	public XmlPrefixElement getPrefixElement() {
		if (this.parent != null
				&& XmlPrefixElement.class.isInstance(this.parent)) {
			return (XmlPrefixElement) this.parent;
		}
		return null;

	}

	protected String prefix(String name) {
		if (name == null) {
			return null;
		}
		XmlPrefixElement pe = this.getPrefixElement();
		if (pe != null) {
			name = pe.getPrefix() + "." + name;
		}
		return name;
	}

	public XmlConfigElement getParentConfig(boolean force) {
		return this.findParent(XmlConfigElement.class, force);
	}

	protected <T extends XmlElement> T findParent(Class<T> cls, boolean force) {
		XmlElement rt = this.parent;
		while (rt != null) {
			if (cls.isInstance(rt)) {
				return (T) rt;
			}
			rt = rt.parent;
		}
		if (force) {
			throw new FsException("not found parent:" + cls + ",in:" + this);
		}
		return null;
	}

	protected <T extends XmlElement> List<T> findList(Class<T> cls,
			boolean includeme) {
		List<T> rt = new ArrayList<T>();
		if (includeme) {
			if (cls.isInstance(this)) {
				rt.add((T) this);
			}
		}
		for (XmlElement c : this.childList) {
			List<T> cL = c.findList(cls, true);
			rt.addAll(cL);
		}
		return rt;
	}

	protected <T extends XmlElement> List<T> getChildList(Class<T> cls) {
		List<T> rt = new ArrayList<T>();
		for (XmlElement e : this.childList) {
			if (cls.isInstance(e)) {
				rt.add((T) e);
			}
		}
		return rt;
	}

}
