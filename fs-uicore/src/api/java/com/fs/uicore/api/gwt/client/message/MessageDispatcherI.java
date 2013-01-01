/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicore.api.gwt.client.message;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public interface MessageDispatcherI extends MessageHandlerI, UiObjectI {

	public static interface FactoryI extends UiObjectI {
		public MessageDispatcherI get(String name);
	}
	
	public void addHandler(Path path, MessageHandlerI mh);
	
	public void addHandler(Path path, boolean strict, MessageHandlerI mh);

	public void addDefaultHandler(MessageHandlerI mh);

	public void addExceptionHandler(MessageExceptionHandlerI eh);
}
