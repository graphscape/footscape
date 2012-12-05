/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 10, 2012
 */
package com.fs.commons.impl.config.xml;

import org.dom4j.Element;

/**
 * @author wuzhen
 * 
 */
public class XmlPropertyElement extends XmlElement {

	/**
	 * @param parent
	 * @param ele
	 */
	public XmlPropertyElement(XmlElement parent, Element ele) {
		//
		super(parent, ele);
		//
	}

	public String getKey() {
		String name = this.element.getName();
		if (name.equals("property")) {
			name = this.element.attributeValue("name");
		}

		return this.prefix(name);
	}

	public String getValue() {

		String rt = this.element.attributeValue("value");
		if (rt == null) {
			rt = this.element.getTextTrim();
			if (rt.length() == 0) {
				rt = null;
			}
		}
		return rt;
	}

}
