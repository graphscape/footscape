/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.api.gwt.client.channel.event;

import com.fs.uicommons.api.gwt.client.channel.ChannelI;
import com.fs.uicore.api.gwt.client.data.ErrorInfoData;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;

/**
 * @author wuzhen
 * 
 */
public class ChannelErrorEvent extends ChannelEvent {

	public static final Type<ChannelErrorEvent> TYPE = new Type<ChannelErrorEvent>(ChannelEvent.TYPE);

	protected ErrorInfosData errors = new ErrorInfosData();

	/**
	 * @param type
	 */
	public ChannelErrorEvent(ChannelI c, String message) {
		super(TYPE, c);
		this.errors.add(new ErrorInfoData("unknow", message));
	}

	/**
	 * @return the errors
	 */
	public ErrorInfosData getErrors() {
		return errors;
	}

}
