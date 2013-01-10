/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class GChatModel extends ModelSupport {

	private String currentGroupId;
	
	private String groupIdToJoin;

	/**
	 * @param name
	 */
	public GChatModel(String name) {
		super(name);
		ControlUtil.addAction(this, Actions.A_GCHAT_JOIN);
		ControlUtil.addAction(this, Actions.A_GCHAT_SEND);

	}

	public ChatGroupModel createGroup(String id) {
		ChatGroupModel rt = new ChatGroupModel(id);
		this.child(rt);
		return rt;
	}

	public ChatGroupModel getGroup(String id, boolean force) {
		return this.getChild(ChatGroupModel.class, id, force);
	}

	public String getCurrentGroupId() {
		return currentGroupId;
	}

	public void setCurrentGroupId(String currentGroupId) {
		this.currentGroupId = currentGroupId;
	}

	public void setGroupIdToJoin(String gid) {
		this.groupIdToJoin = gid;
	}

	/**
	 * Dec 23, 2012
	 */
	public String getGroupIdToJoin(boolean force) {
		if (force && this.groupIdToJoin == null) {
			throw new UiException("no gropuIdToJoin");
		}
		return this.groupIdToJoin;

	}

}
