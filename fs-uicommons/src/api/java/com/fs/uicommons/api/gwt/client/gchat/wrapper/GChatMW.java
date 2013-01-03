/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat.wrapper;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 *
 */
public class GChatMW extends MsgWrapper{

	/**
	 * @param md
	 */
	public GChatMW(MessageData md) {
		super(md);
	}

	public String getGroupId(){
		return this.getHeader("groupId",true);
	}
	
	public String getParticipantId(){
		return this.getHeader("participantId",true);
	}
}
