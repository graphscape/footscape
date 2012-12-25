/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.achat;

import com.fs.uiclient.api.gwt.client.achat.AChatControlI;
import com.fs.uiclient.api.gwt.client.achat.AChatModel;
import com.fs.uiclient.api.gwt.client.achat.ChatActivityModel;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;

/**
 * @author wu
 * 
 */
public class AChatControlImpl extends ControlSupport implements AChatControlI {

	/**
	 * @param name
	 */
	public AChatControlImpl(String name) {
		super(name);
		// this.addActionProcessor(ActivityModelI.A_REFRESH, new RefreshAP());
		this.localMap.put(AChatModel.A_OPEN, true);

		this.addActionProcessor(AChatModel.A_OPEN, new OpenAP());
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	protected void doModel(ModelI cm) {
		//
		super.doModel(cm);
		GChatModel rsm = cm.getTopObject().find(GChatModel.class, true);
		// listen to the room created,it must be open from activity.
		// then here to attach the activity with the chat room.
		rsm.addHandler(ModelChildEvent.TYPE, new EventHandlerI<ModelChildEvent>() {

			@Override
			public void handle(ModelChildEvent e) {
				AChatControlImpl.this.onChildRoomModel(e);
			}
		});
	}

	public GChatControlI getGChatControl() {
		return this.getManager().getControl(GChatControlI.class, true);
	}

	protected void onChildRoomModel(ModelChildEvent e) {
		// create chat room mvc
		if (!e.isAdd() || !(e.getChild() instanceof ChatGroupModel)) {
			return;// ignore removing event.
		}

		ChatGroupModel rm = (ChatGroupModel) e.getChild();

		String rshortname = rm.getName();
		// TODO only allow activity room.
		String actId = this.getActivityIdFromRoomName(rshortname, true);

		// model
		AChatModel csm = e.getModel().getTopObject().find(AChatModel.class, true);//
		ChatActivityModel crm = csm.addChatActivity(actId);

		// control

	}

	/**
	 * Oct 22, 2012
	 */
	public static String convertActivityId2RoomName(String actId) {
		//
		return AChatModel.PREFIX_ACTIVITY + actId;
	}

	public static String getActivityIdFromRoomName(String rname, boolean force) {
		if (!rname.startsWith(AChatModel.PREFIX_ACTIVITY)) {
			if (force) {
				throw new UiException("cannot convert the room name:" + rname + " to activity id");
			}
			return null;
		}
		return rname.substring(AChatModel.PREFIX_ACTIVITY.length());
	}

	/*
	 * Dec 25, 2012
	 */
	@Override
	public void send(String actId, String text) {
		String gid = this.convertActivityId2RoomName(actId);
		this.getGChatControl().send(gid, text);

	}

}
