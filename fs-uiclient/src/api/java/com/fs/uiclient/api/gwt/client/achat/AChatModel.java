/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.api.gwt.client.achat;

import java.util.List;

import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class AChatModel extends ModelSupport {
	public static final String PREFIX_ACTIVITY = "ar-";// activity room,UPPER
	// case is not work,

	public static final String A_OPEN = "open";// open chat room for activity.

	public static final Location L_ACTIVITY_ID = Location.valueOf("activityID");// the

	public AChatModel(String name) {
		super(name);

		ControlUtil.addAction(this, AChatModel.A_OPEN);
	}

	/*
	 * Oct 22, 2012
	 */
	public ChatActivityModel addChatActivity(String actId) {
		ChatActivityModel rt = new ChatActivityModel(actId);//
		this.child(rt);//
		return rt;
	}

	/*
	 * Oct 22, 2012
	 */
	public ChatActivityModel getChatActivityByChatGroupId(String actId, boolean force) {
		//
		return this.getChild(ChatActivityModel.class, actId, force);
	}

	public ChatActivityModel getChatActivityByActivityId(String actId, boolean force) {
		//
		List<ChatActivityModel> cL = this.getChildList(ChatActivityModel.class);
		ChatActivityModel rt = null;
		for (ChatActivityModel cm : cL) {
			if (cm.isActivityId(actId)) {
				if (rt != null) {
					throw new UiException("too many chat room for activity:" + actId);
				}
				rt = cm;

			}
		}
		if (force && rt == null) {
			throw new UiException("force,no chat room found for activity:" + actId);
		}
		return rt;
	}

}
