/**
 *  Dec 24, 2012
 */
package com.fs.uicommons.api.gwt.client.message;

import com.fs.uicore.api.gwt.client.HandlerI;

/**
 * @author wuzhen
 * 
 */
public interface MessageExceptionHandlerI extends HandlerI<MessageException> {

	public void handle(MessageException me);

}
