/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.ErrorInfoData;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.event.ErrorEvent;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ErrorHandleTest extends TestBase {
	public static final String MSG = "this is a test exception,this should be processed at filter both server side and client side.";

	private List<ErrorEvent> eeList;

	@Test
	public void test() {
		this.eeList = new ArrayList<ErrorEvent>();
		UiRequest req = new UiRequest();
		req.setRequestPath("/handler2/testThrowable");
		this.client.addHandler(ErrorEvent.TYPE, new HandlerI<ErrorEvent>() {

			@Override
			public void handle(ErrorEvent e) {
				ErrorHandleTest.this.eeList.add(e);

			}
		});
		this.client.sendRequest(req, new UiCallbackI<UiResponse, Object>() {

			@Override
			public Object execute(UiResponse t) {
				ErrorHandleTest.this.onResponse(t);
				return null;

			}
		});
		this.delayTestFinish(3 * 10000);
		// delayTestFinish(10000);// wait the callback
	}

	private void onResponse(UiResponse res) {
		ErrorInfosData actual = res.getErrorInfos();
		assertNotNull("error message missing.", actual);
		List<ErrorInfoData> el = actual.getErrorInfoList();
		assertEquals(1, el.size());

		ErrorInfoData ei = el.get(0);

		assertNull(ei.getMessage());//
		List<String> dL = ei.getDetail();
		assertNotNull(dL);
		assertTrue(dL.size() > 0);
		String firstD = dL.get(0);
		assertTrue(firstD.contains(MSG));
		// assert the event is
		assertEquals(1, this.eeList.size());
		ErrorEvent ee = this.eeList.get(0);
		assertTrue(ee.getMessage().contains(MSG));// TODO more detail.
		this.finishTest();
	}

}
