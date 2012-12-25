/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.api.gwt.client.achat;

import java.util.List;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ChatActivityModel extends ModelSupport {

	protected String groupId;

	protected String activityId;

	/**
	 * @param name
	 */
	public ChatActivityModel(String actId) {
		super(actId);
		this.activityId = actId;
		this.groupId = AChatModel.PREFIX_ACTIVITY + actId;
	}

	public String getActivityId() {
		//
		return this.activityId;
	}

	/*
	 * Oct 22, 2012
	 */
	public String getGroupId() {
		//
		return this.groupId;
	}

	public boolean isActivityId(String aid) {
		//
		return this.getActivityId().equals(aid);
	}

	/*
	 * Oct 22, 2012
	 */
	public ActivityModelI getActivityModel() {
		ActivitiesModelI asm = this.getTopObject().find(ActivitiesModelI.class, true);
		ActivityModelI rt = asm.getActivity(this.getActivityId());

		return rt;
	}

	/*
	 * Oct 24, 2012
	 */
	public ChatGroupModel getGroupModel() {
		GChatModel cm = this.getTopObject().find(GChatModel.class, true);

		ChatGroupModel rt = cm.getGroup(this.groupId, true);
		return rt;
	}

	/*
	 * Oct 24, 2012
	 */
	public PeerModel addPeer(String expId, String accId) {
		//
		PeerModel rt = new PeerModel(expId, expId, accId);
		rt.parent(this);//
		return rt;
	}

	/*
	 * Oct 24, 2012
	 */
	public PeerModel getPeerByAccountId(String accId, boolean force) {
		List<PeerModel> pL = this.getChildList(PeerModel.class);
		for (PeerModel p : pL) {
			if (p.isXmppUser(accId)) {
				return p;
			}
		}
		if (force) {
			throw new UiException("no peer model found for accId:" + accId + " in chat room:" + this);
		}
		return null;
	}

}
