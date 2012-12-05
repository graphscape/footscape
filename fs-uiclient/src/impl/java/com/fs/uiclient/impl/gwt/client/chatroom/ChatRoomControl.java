/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.chatroom;

import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.activity.ParticipantModel;
import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomControlI;
import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomModelI;
import com.fs.uiclient.api.gwt.client.chatroom.PeerModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.model.ModelChildProcessorI;
import com.fs.uixmpp.groupchat.api.gwt.client.room.MessageModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.OccupantModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomControlI;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsControlI;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsModelI;

/**
 * @author wu
 *         <p>
 *         This control connect the activity with the room. It is a bridge/agent
 *         to convert the concept of activity's chat into/from the underlying
 *         xmpp's room concept.
 */
public class ChatRoomControl extends ControlSupport implements ChatRoomControlI {

	/**
	 * @param name
	 */
	public ChatRoomControl(String name) {
		super(name);
	}

	@Override
	public ChatRoomModelI getModel() {
		return (ChatRoomModelI) this.model;
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	protected void doModel(ModelI cm) {
		super.doModel(cm);
		// find the activity,listen to the activity event.

		// add all the participant into the chat room as the PeerModel

		ActivityModelI am = this.getModel().getActivityModel();

		// NOTE====Listen to the activity event======

		ModelChildProcessorI.Helper.onAttach(am, this);// NOTE this will process
														// not only chatroom's
														// child event,but also
														// process
														// ActivityModelI's
														// child event.

		// find the room in model ,listen to the room event.

		String rname = this.getModel().getRoomName();
		RoomsModelI rsm = this.model.getTopObject().find(RoomsModelI.class,
				true);
		// there must be one room model is created before this control .
		RoomModelI rm = rsm.getRoomModelByRoomName(rname, true);
		// NOTE=== Listen to the room event====
		ModelChildProcessorI.Helper.onAttach(rm, this);// also process child
														// event from room
														// model.
		//
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI parent, ModelI cm) {
		super.processChildModelAdd(parent, cm);

		if (cm instanceof ParticipantModel) {// the parent must by
												// ActivityModelI
			if (!(parent instanceof ActivityModelI)) {
				throw new UiException("bug");
			}
			this.processActivityParticipantChildModelAdd((ParticipantModel) cm);
		}
		if (cm instanceof OccupantModel) {
			this.processRoomOccupantChildModelAddOrRemove(true,
					(OccupantModel) cm);
		}
		if (cm instanceof MessageModel) {
			this.processRoomMessageChildModelAdd((MessageModel) cm);
		}
	}

	// TODO this is not effective,see MoelChildProcessorI.
	//
	@Override
	public void processChildModelRemove(ModelI parent, ModelI cm) {
		super.processChildModelRemove(parent, cm);

		if (cm instanceof OccupantModel) {
			this.processRoomOccupantChildModelAddOrRemove(false,
					(OccupantModel) cm);
		}

	}

	protected void processActivityParticipantChildModelAdd(ParticipantModel p) {

		String expId = p.getExpId();//
		//
		String accId = p.getAccountId();
		// convert accId to xmppUserId
		// cannot find from session,there is no this info for another
		// user,it must be from activity information?
		String xmppUser = accId;// TODO
		ChatRoomModelI crm = this.getModel();
		PeerModel pm = crm.addPeer(expId, xmppUser);// convert the part in act
													// into peer in this model.

	}

	protected void processRoomOccupantChildModelAddOrRemove(boolean add,
			final OccupantModel om) {
		if (add) {// TODO remove this when remove is affective.
			om.addValueHandler(OccupantModel.L_ISEXIT,
					new HandlerI<ModelValueEvent>() {

						@Override
						public void handle(ModelValueEvent e) {
							if (e.getValue(Boolean.FALSE)) {// exit,not here
								ChatRoomControl.this
										.processRoomOccupantChildModelAddOrRemove(
												false, om);
							}
						}
					});
		}
		// presence should be processed here or processed by some where?
		// see as presence when added one occupant.
		// TODO by the L_ISEXIT value
		// of occupant model.
		String nick = om.getNick();// TODO nick is accId.is xmppUser all is same
									// for now.

		String xmppUser = nick;

		PeerModel pm = this.getModel().getPeerByXmppUser(xmppUser, false);
		if (pm == null) {// the occupant in xmpp layer's room has no
							// PeerModel,which means some one is enter/exit the
							// room by another way that no activity attached
							// with him.
			// TODO how to notify/process this event? may be a message should be
			// send into the room.

			return;

		}
		// TODO this event may before the Peer is added into ChatRoomModelI.
		// for instance the new expId is enter the activity.

		String presence = add ? "online" : "offline";// todo add Presence state
														// class.

		pm.setValue(PeerModel.L_PRESENCE, presence);// TODO

		this.getModel().setValue(
				ChatRoomModelI.L_LAST_PEER_MODEL_OF_PRESENCE_CHANGED, pm);//

	}

	/**
	 * Oct 22, 2012
	 */
	protected void processRoomMessageChildModelAdd(MessageModel child) {
		// no need to process message,it is processed by the RoomView in xmpp
		// module.
		this.getModel().setValue(ChatRoomModelI.L_LAST_MESSAGE_MODEL, child);
	}

	/*
	 * Oct 25, 2012
	 */
	@Override
	public void send(String message) {
		// behalf the login user's expId, to send the message
		this.getRoomControl(true).send(message);

	}

	public RoomControlI getRoomControl(boolean force) {

		String rname = this.getModel().getRoomName();//
		RoomsControlI rc = this.getManager().getControl(RoomsControlI.class,
				true);

		return rc.getRoomControl(rname, force);
	}

}
