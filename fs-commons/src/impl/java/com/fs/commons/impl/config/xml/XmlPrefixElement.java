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
public class XmlPrefixElement extends XmlElement {

	/**
	 * @param parent
	 * @param ele
	 */
	public XmlPrefixElement(XmlElement parent, Element ele) {
		//
		super(parent, ele);
		//
	}

	public String getPrefix() {
		return this.element.attributeValue("name");
	}
}
