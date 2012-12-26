/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.achat;

import com.fs.uiclient.api.gwt.client.achat.AChatControlI;
import com.fs.uiclient.api.gwt.client.achat.AChatModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatGroupCreatedEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

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

	public GChatControlI getGChatControl() {
		return this.getManager().getControl(GChatControlI.class, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.achat.AChatControlI#join(java.lang.String)
	 */
	@Override
	public void join(String actId) {

		AChatModel acm = this.getModel();//

		GChatControlI cc = this.getManager().getControl(GChatControlI.class,
				true);

		cc.addHandler(GChatGroupCreatedEvent.TYPE,
				new EventHandlerI<GChatGroupCreatedEvent>() {

					@Override
					public void handle(GChatGroupCreatedEvent t) {
						AChatControlImpl.this.onGroupCreated(t);
					}
				});

		cc.join(actId);
	}

	/**
	 * @param t
	 */
	protected void onGroupCreated(GChatGroupCreatedEvent t) {

	}

	/*
	 * Dec 25, 2012
	 */
	@Override
	public void send(String actId, String text) {
		String gid = actId;
		this.getGChatControl().send(gid, text);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.achat.AChatControlI#join()
	 */
	@Override
	public void join() {
		AChatModel am = this.getModel();
		String actId = am.getActivityIdToJoin(true);
		this.join(actId);
	}

}
