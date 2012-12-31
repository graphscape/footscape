/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.wrapper.internal;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.EventType;
import com.fs.gridservice.commons.api.EventWrapper;
import com.fs.gridservice.commons.api.data.EventGd;

/**
 * @author wu
 * 
 */
public class InternalMsgEW extends EventWrapper {

	public static final EventType TYPE = EventType.valueOf("InternalMsg");

	public static InternalMsgEW valueOf(Path path, MessageI msg) {
		InternalMsgEW rt = new InternalMsgEW(new EventGd(TYPE, path));
		rt.target.setPayload("message",msg);
		return rt;
	}

	public InternalMsgEW(EventGd target) {
		super(target);
	}
	
	public MessageI getMessage(){
		return (MessageI)this.target.getPayload("message",true);
	}

	
}
