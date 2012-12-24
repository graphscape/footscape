/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class MessageModel extends ModelSupport {

	protected MessageData target;

	protected MessageData message;

	/**
	 * @param name
	 */
	public MessageModel(String name, MessageData md) {
		super(name);
		this.target = md;
		this.message = (MessageData) this.target.getPayload("message", true);
	}

	public String getFormat() {
		return this.message.getHeader("format", true);
	}

	public String getText() {
		return this.message.getString("text");
	}

	public String getGroupId() {
		return this.target.getHeader("groupId", true);
	}

	public String getParticipantId() {
		return this.target.getHeader("participantId", true);
	}

}
