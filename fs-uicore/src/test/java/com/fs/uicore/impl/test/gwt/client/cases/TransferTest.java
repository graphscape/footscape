/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import org.junit.Test;

import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.basic.IntegerData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.list.ObjectListData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class TransferTest extends TestBase {
	

	@Test
	public void test() {
		
		UiRequest req = new UiRequest();
		req.setRequestPath("/handler1/echo");
		//string
		req.setPayload("string1", StringData.valueOf("stringValue"));
		//integer
		req.setPayload("integer1", IntegerData.valueOf(1));
		//property data
		ObjectPropertiesData opd = new ObjectPropertiesData();
		opd.setProperty("string2", StringData.valueOf("stringValue"));
		opd.setProperty("integer2", IntegerData.valueOf(2));
		req.setPayload("objectProperties", opd);
		//list data
		ObjectListData lpd = new ObjectListData();
		lpd.add(StringData.valueOf("0-stringValue"));
		lpd.add(IntegerData.valueOf(1));
		req.setPayload("objectList", lpd);
		
		//
		final ObjectPropertiesData exp = req.getPayloads();
		this.client.sendRequest(req, new UiCallbackI<UiResponse, Object>() {

			@Override
			public Object execute(UiResponse t) {
				TransferTest.this.onResponseEcho(exp, t);
				return null;

			}
		});
		this.delayTestFinish(3*10000);
		// delayTestFinish(10000);// wait the callback
	}

	private void onResponseEcho(ObjectPropertiesData expected, UiResponse res) {

		ObjectPropertiesData pd = res.getPayloads();
		assertNotNull(pd);
		assertEquals(expected, pd);
		this.finishTest();
	}


}
