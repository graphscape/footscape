/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 25, 2012
 */
package com.fs.xmpps.api;

import com.fs.commons.api.lang.FsException;


/**
 * @author wu
 * 
 */
public class ElementWrapper {
	protected ElementI element;

	/**
	 * @return the element
	 */
	public ElementI getElement() {
		return element;
	}

	public ElementWrapper(ElementI ele) {
		this.element = ele;
	}

	public boolean isName(String name) {
		return name.equals(this.element.getName());
	}

	public static ElementWrapper valueOf(ElementI e) {
		return new ElementWrapper(e);
	}

	public ElementWrapper getChild(String name, String xmlNs, boolean force) {
		ElementI c = this.element.getChild(name, false);
		if (c == null || !xmlNs.equals(c.getXmlns())) {
			if (force) {
				throw new FsException("force:" + name + "," + xmlNs + " in :"
						+ this.element);
			}
			return null;
		}
		return ElementWrapper.valueOf(c);

	}
}
