/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 12, 2012
 */
package com.fs.commons.impl.test.cases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class CodecTest extends TestBase {
	public void testDateConvert() {

		Date d = new Date();
		CodecI.FactoryI cf = this.container.find(CodecI.FactoryI.class, true);
		CodecI c = cf.getCodec(Date.class);
		Object o = c.encode(d);
		assertTrue(o instanceof JSONArray);
		JSONArray jA = (JSONArray) o;
		String json = jA.toJSONString();
		System.out.println("convert from date to json,from:" + d + ",to:" + json);
		//
		JSONArray jA2 = (JSONArray) JSONValue.parse(json);
		Date d2 = (Date) c.decode(jA2);
		System.out.println("convert from json to date,from:" + json + ",to:" + d2);
		assertEquals(d, d2);
	}

	public void testErrorInfos() {
		CodecI.FactoryI cf = this.container.find(CodecI.FactoryI.class, true);
		PropertiesI<Object> obj = new MapProperties<Object>();
		obj.setProperty("_ERROR_INFO_S", new ErrorInfos().add(new ErrorInfo("message-value")));
		CodecI c = cf.getCodec(PropertiesI.class);

		JSONArray ser = (JSONArray) c.encode(obj);
		System.out.println(ser.toJSONString());
	}

	public void test() {
		CodecI.FactoryI cf = this.container.find(CodecI.FactoryI.class, true);

		PropertiesI<Object> req = new MapProperties<Object>();
		// string
		req.setProperty("string1", "stringValue");
		// integer
		req.setProperty("integer1", 1);
		// property data
		PropertiesI<Object> opd = new MapProperties<Object>();
		opd.setProperty("string2", String.valueOf("stringValue"));
		opd.setProperty("integer2", Integer.valueOf(2));
		req.setProperty("objectProperties", opd);
		req.setProperty("boolean1", Boolean.TRUE);//
		// list data
		List<Object> lpd = new ArrayList<Object>();
		lpd.add(String.valueOf("0-stringValue"));
		lpd.add(Integer.valueOf(1));
		req.setProperty("objectList", lpd);
		// errorinfo
		ErrorInfo ei = new ErrorInfo("erroMessage1");
		ei.getDetail().add("detailMessage1");
		ei.getDetail().add("detailMessage2");
		req.setProperty("errorInfo", ei);
		// errorinfos
		ErrorInfo ei2 = new ErrorInfo((String) null);
		ei2.getDetail().add("dm21");
		ei2.getDetail().add("dm22");
		ErrorInfo ei3 = new ErrorInfo("errorMessage3");
		ei3.getDetail().add("dm31");
		ei3.getDetail().add("dm32");
		ErrorInfos eis = new ErrorInfos();

		eis.add(ei2).add(ei3);
		req.setProperty("errorInfos", eis);
		CodecI c = cf.getCodec(PropertiesI.class);
		JSONArray ser = (JSONArray) c.encode(req);
		System.out.println(ser.toJSONString());
		PropertiesI<Object> actual = (PropertiesI<Object>) c.decode(ser);

		Assert.assertEquals("encode or decode failed.", req, actual);

	}
}
