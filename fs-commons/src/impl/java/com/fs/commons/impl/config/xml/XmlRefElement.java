package com.fs.commons.impl.config.xml;

import org.dom4j.Element;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class XmlRefElement extends XmlElement {

	/** */
	public XmlRefElement(XmlElement parent, Element ele) {
		super(parent, ele);
	}

	public String getReference() {
		String rt = this.element.attributeValue("ref");
		if (rt == null) {
			throw new FsException("no ref set for:" + this.getName());
		}
		return rt;

	}

}
