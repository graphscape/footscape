/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class GChatModel extends ModelSupport {

	public static final String A_JOIN = "join";

	public static final String A_SEND = "send";

	public static final String L_ROOMNAME_EDITING = "groupIdEditing";

	private String groupIdToJoin;

	/**
	 * @param name
	 */
	public GChatModel(String name) {
		super(name);
		ControlUtil.addAction(this, A_JOIN);
		ControlUtil.addAction(this, A_SEND);

	}

	public ChatGroupModel createGroup(String id) {
		ChatGroupModel rt = new ChatGroupModel(id);
		this.child(rt);
		return rt;
	}

	public ChatGroupModel getGroup(String id, boolean force) {
		return this.getChild(ChatGroupModel.class, id, force);
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
