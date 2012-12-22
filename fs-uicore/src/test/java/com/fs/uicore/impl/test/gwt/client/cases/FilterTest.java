/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 2, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import org.junit.Test;

import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.basic.IntegerData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.support.UiFilterSupport;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class FilterTest extends TestBase {

	private static String K_FILTER_TEST = "_FILTER_TEST";

	private static String EXPECTED1 = "REQUEST";
	private static String EXPECTED2 = "RESPONSE";

	@Test
	public void test() {
		this.delayTestFinish(1000000);

	}

	@Override
	protected void onClientStart(AfterClientStartEvent e) {
		// TODO Auto-generated method stub
		super.onClientStart(e);
		final UiRequest req = new UiRequest();
		req.setRequestPath("/handler1/echo");
		req.setPayload("string1", StringData.valueOf("stringValue"));
		req.setPayload("integer1", IntegerData.valueOf(1));
		ObjectPropertiesData opd = new ObjectPropertiesData();
		opd.setProperty("string2", StringData.valueOf("stringValue"));
		opd.setProperty("integer2", IntegerData.valueOf(2));
		req.setPayload("objectProperties", opd);
		final ObjectPropertiesData exp = req.getPayloads();
		this.client.addFilter(new UiFilterSupport() {

			@Override
			protected void filterRequest(Context fc) {
				//
				fc.getRequest().setHeader(K_FILTER_TEST, EXPECTED1);
				//
			}

			@Override
			protected void filterResponse(Context fc) {
				//
				fc.getResponse().setHeader(K_FILTER_TEST, EXPECTED2);
				//

			}
		});
		this.client.sendRequest(req, new UiCallbackI<UiResponse, Object>() {

			@Override
			public Object execute(UiResponse t) {
				FilterTest.this.onResponseEcho(req, exp, t);
				return null;

			}
		});

		// delayTestFinish(10000);// wait the callback
	}

	private void onResponseEcho(UiRequest req, ObjectPropertiesData expected,
			UiResponse res) {
		//
		String freq = req.getHeader(K_FILTER_TEST);
		assertEquals("filter request check failed.", this.EXPECTED1, freq);

		String fres = res.getHeader(K_FILTER_TEST);
		assertEquals("filter response check failed", this.EXPECTED2, fres);

		//
		ObjectPropertiesData pd = res.getPayloads();
		assertNotNull(pd);
		assertEquals(expected, pd);
		this.finishTest();
	}

}
