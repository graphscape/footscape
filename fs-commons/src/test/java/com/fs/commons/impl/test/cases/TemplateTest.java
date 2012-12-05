/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 12, 2012
 */
package com.fs.commons.impl.test.cases;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import com.fs.commons.api.freemarker.TemplateI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class TemplateTest extends TestBase {

	public void testErrorInfos() {
		TemplateI.FactoryI tf = this.container.find(TemplateI.FactoryI.class,
				true);
		TemplateI tl = tf.getTemplate("test1.ftl");

		//
		String string1 = "string1-value";
		String pts1_string1 = "pts1-string1-value";
		Map ctx = new HashMap();

		ctx.put("string1", string1);
		PropertiesI pts = new MapProperties();
		pts.setProperty("string1", pts1_string1);
		ctx.put("pts1", pts);

		StringBuffer sb = new StringBuffer();
		tl.process(ctx, sb);

		Assert.assertEquals("string1 is:" + string1 + "\n"
				+ "pts1.string1 is:" + pts1_string1, sb.toString());
	}
}
