/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 20, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wuzhen
 * 
 */
public abstract class AttachEvent extends Event {

	public static final Type<AttachEvent> TYPE = new Type<AttachEvent>("attach");

	/** */
	public AttachEvent(Type<? extends AttachEvent> type, UiObjectI src) {
		super(type, src);
	}
}
