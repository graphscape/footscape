/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.cooper;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.coper.CoperControlI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.usshot.UserSnapshotModelI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.SimpleValueDeliverI.ValueSourceI;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.simple.SimpleValueDeliver;

/**
 * @author wu
 * 
 */
public class CooperControl extends ControlSupport implements CoperControlI {

	/**
	 * @param name
	 */
	public CooperControl(String name) {
		super(name);
		this.addActionProcessor(CooperModelI.A_REQUEST, new CooperRequestAP());
		this.addActionProcessor(CooperModelI.A_CONFIRM, new CooperConfirmAP());
		this.addActionProcessor(CooperModelI.A_REFRESH_INCOMING_CR,
				new RefreshIncomingCrAP());

	}

	public MainControlI getMainControl() {
		return this.getManager().getControl(MainControlI.class, true);
	}

	/*
	 * Dec 7, 2012
	 */
	@Override
	protected void doAttach() {
		//
		super.doAttach();
		// listen to the snapshot for new exp and cooperrequest.
		Mvc uss = this.getMainControl().getLazyObject(
				MainControlI.LZ_USERSNAPSHOT, true);
		final UserSnapshotModelI um = uss.getModel();
		new SimpleValueDeliver<DateData, DateData>(um,
				UserSnapshotModelI.L_COMMIT, new ValueSourceI<DateData>() {

					@Override
					public DateData getValue() {
						//
						return null;
					}

					@Override
					public void setValue(DateData x) {
						//
						CooperControl.this.onSnapshotCommiting(um, x);
					}
				}).mapDefaultDirect().start();

	}

	protected void onSnapshotCommiting(UserSnapshotModelI usm, DateData dd) {
		List<String> expIdL = usm.getExpIdList();//
		// NO need to process exp from snapshot for now.

		List<String> actIdL = usm.getActivityIdList();
		// NO need to process actId for now,it should be through
		// ActivitiesModelI.

		List<String> crIdL = usm.getCooperRequestIdList();

		// save value to model,model will remove the deleted child CR model 
		this.getModel().setIncomingCooperRequestIdList(crIdL);
		
		this.triggerAction(CooperModelI.A_REFRESH_INCOMING_CR);

	}

	@Override
	public CooperModelI getModel() {
		return (CooperModelI) this.model;
	}

}
