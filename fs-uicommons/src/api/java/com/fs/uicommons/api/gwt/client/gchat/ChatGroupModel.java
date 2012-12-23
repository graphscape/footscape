/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat;

import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ChatGroupModel extends ModelSupport {

	/**
	 * @param name
	 */
	public ChatGroupModel(String name) {
		super(name);
	}

	/**
	 * Dec 23, 2012
	 */
	public void addParticipant(ParticipantModel p) {
		this.child(p);
	}

	/**
	 * Dec 23, 2012
	 */
	public void setMessageEditing(String string) {

	}

}
