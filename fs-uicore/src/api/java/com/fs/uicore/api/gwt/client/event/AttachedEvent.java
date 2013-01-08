/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 20, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wuzhen
 * 
 */
public class AttachedEvent extends AttachEvent {

	public static final Type<AttachedEvent> TYPE = new Type<AttachedEvent>(
			AttachEvent.TYPE,"attached");

	/** */
	public AttachedEvent(UiObjectI src) {
		super(TYPE, src);
	}
}
