/**
 * Jun 12, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wuzhen
 * 
 */
public class UiResponse extends MsgWrapper {

	public static final String ERROR_INFO_S = "_ERROR_INFO_S";// NOTE must same
																// as
																// ResponseImpl
																// in server
																// side.

	protected MsgWrapper request;

	public UiResponse(MessageData md) {
		super(md);

	}

}
