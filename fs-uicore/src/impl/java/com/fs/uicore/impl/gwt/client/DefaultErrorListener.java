/**
 * Jun 12, 2012
 */
package com.fs.uicore.impl.gwt.client;

import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ErrorEvent;

/**
 * @author wuzhen
 * 
 */
public class DefaultErrorListener implements HandlerI<ErrorEvent> {
	/**
	 * @param uiClientImpl
	 */
	public DefaultErrorListener() {

	}

	/* */
	@Override
	public void handle(ErrorEvent e) {
		System.out.println("error event,msg:" + e.getMessage() + ",cause:\n");
		Throwable t = e.getCause();
		if (t != null) {
			t.printStackTrace();
		}
	}

}
