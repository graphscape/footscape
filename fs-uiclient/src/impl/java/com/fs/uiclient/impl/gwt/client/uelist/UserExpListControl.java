/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.coper.CooperRequestModel;
import com.fs.uiclient.api.gwt.client.event.ExpCreatedEvent;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.main.MainModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpControl;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.model.ModelChildProcessorI;
import com.fs.uicore.api.gwt.client.simple.SimpleValueDeliver;

/**
 * @author wu
 * 
 */
public class UserExpListControl extends ControlSupport implements UserExpListControlI {

	/**
	 * @param name
	 */
	public UserExpListControl(String name) {
		super(name);
		// create is local action
		this.localMap.put(UserExpListModelI.A_CREATE, true);//

		// refresh list,summary list,TODO this should be triggered by snapshot
		// changing.
		this.addActionEventHandler(UserExpListModelI.A_REFRESH, new RefreshAP());

		this.addActionEventHandler(UserExpListModelI.A_CREATE, new OpenExpEditAP());

		// get the detail of one exp.
		this.addActionEventHandler(UserExpListModelI.A_GET, new GetExpAP());

		this.addActionEventHandler(UserExpListModelI.A_SELECT, new SelectExpAP());

	}

	@Override
	protected void doAttach() {
		super.doAttach();

		// listen to the Exp edit model for the new created exp
		new SimpleValueDeliver<String, String>((MainModelI) this.getModel().getParent(),
				MainModelI.L_EXPID_CREATED, this.model, UserExpListModelI.L_EXP_ID_GET_REQUIRED)
				.mapDefaultDirect().start();

		//
		this.model.addValueHandler(UserExpListModelI.L_EXP_ID_GET_REQUIRED,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						UserExpListControl.this.triggerAction(UserExpListModelI.A_GET);

					}
				});
		// TODO listen to the SnapshotModelI ,retrieve activity id by expId;

		// listen to the MainModelI for new exp created by user self.
		this.getEventBus(true).addHandler(ExpCreatedEvent.TYPE, new EventHandlerI<ExpCreatedEvent>() {

			@Override
			public void handle(ExpCreatedEvent e) {
				String expId = e.getExpId();
				UserExpListControl.this.onExpCreated(e);
			}
		});
		// listen to the cooper model for incoming cooperrequest.
		MainControlI mc = this.getManager().getControl(MainControlI.class, true);
		Mvc mvc = mc.getLazyObject(MainControlI.LZ_COOPER, true);
		CooperModelI cpm = mvc.getModel();
		ModelChildProcessorI mcp = new ModelChildProcessorI() {

			@Override
			public void processChildModelRemove(ModelI parent, ModelI child) {
				if (child instanceof CooperRequestModel) {
					UserExpListControl.this.onIncomingCooperRequestRemove((CooperRequestModel) child);
				}

			}

			@Override
			public void processChildModelAdd(ModelI parent, ModelI child) {
				if (child instanceof CooperRequestModel) {
					UserExpListControl.this.onIncomingCooperRequestAdd((CooperRequestModel) child);
				}

			}
		};
		ModelChildProcessorI.Helper.onAttach(cpm, mcp);
	}

	/**
	 * Dec 8, 2012
	 */
	protected void onIncomingCooperRequestRemove(CooperRequestModel child) {
		UserExpListModelI uelm = this.getModel();
		String expId = child.getExpId2();// to this exp
		// UserExpModel uem = uelm.getUserExp(expId, true);//

	}

	protected void onIncomingCooperRequestAdd(CooperRequestModel child) {
		UserExpListModelI uelm = this.getModel();
		String expId = child.getExpId2();// to this exp
		UserExpModel uem = uelm.getUserExp(expId, true);//
		String expId1 = child.getExpId1();
		uem.setIncomingCrId(expId1);// FROM exp id
		uem.commit();// see UserExpView.onCommit...;
	}

	public MainControlI getMainControl() {
		return this.getManager().getControl(MainControlI.class, true);
	}

	/**
	 * Nov 29, 2012
	 */
	protected void onExpCreated(ExpCreatedEvent e) {
		// refresh whole list of the user.
		this.triggerAction(UserExpListModelI.A_REFRESH);//
	}

	@Override
	public UserExpListModelI getModel() {
		return (UserExpListModelI) this.model;
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof UserExpModel) {
			this.processChildUserExpModelAdd((UserExpModel) cm);
		}
	}

	/**
	 * Oct 20, 2012
	 */
	private void processChildUserExpModelAdd(UserExpModel cm) {
		// when new item added,listen to the select value, because zero or only
		// one should be selected.
		// control it

		this.getManager().addControl(new UserExpControl(cm.getName()).model(cm));
		// listening to the exp model's select
		cm.addValueHandler(UserExpModel.L_ISSELECTED, new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				// set selected exp id
				UserExpListControl.this.onUserExpModelSelectChanging(e);
			}
		});
	}

	/**
	 * Oct 20, 2012
	 */
	protected void onUserExpModelSelectChanging(ModelValueEvent e) {
		boolean sel = e.getValue(Boolean.FALSE);
		if (!sel) {
			return;// ignore unselect event
		}
		// select event
		// unselect other ones
		UserExpModel uem = (UserExpModel) e.getModel();
		this.getModel().select(uem.getExpId());

	}

}
