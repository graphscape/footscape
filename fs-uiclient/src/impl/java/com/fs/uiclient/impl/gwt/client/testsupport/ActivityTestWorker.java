/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.event.view.ViewUpdateEvent;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.activity.ActivityView;
import com.fs.uiclient.impl.gwt.client.exps.item.ExpItemView;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;

/**
 * @author wu
 * 
 */
public class ActivityTestWorker extends ExpTestWorker {

	protected String expIdSelected;// selected

	protected String expIdCooperTo;// cooper with

	protected Map<String, UserExpView> userExpViewMap;

	protected ActivityView activityView;

	/**
	 * @param user
	 * @param email
	 * @param pass
	 * @param totalExp
	 */
	public ActivityTestWorker(String user, String email, String pass, int totalExp) {
		super(user, email, pass, totalExp);

		userExpViewMap = new HashMap<String, UserExpView>();

		this.tasks.add("selectuserexp");

		this.tasks.add("cooper.submit");// user select one item from search
										// result,a coper is created.
		this.tasks.add("cooper.confirm");
		//
		this.tasks.add("activity.created");// the coper is confirmed by
											// other user and activity
											// created.
		this.tasks.add("activity.view");
	}

	@Override
	protected void onRegisterUserLogin(UserInfo ui) {
		super.onRegisterUserLogin(ui);
	}

	@Override
	public void onEvent(Event e) {
		super.onEvent(e);

		if (e instanceof ViewUpdateEvent) {
			this.onViewUpdateEvent((ViewUpdateEvent) e);
		}

	}

	/**
	 * @param e
	 */
	private void onViewUpdateEvent(ViewUpdateEvent e) {
		ViewI v = e.getView();
		if (v instanceof UserExpView) {
			this.onUserExpViewUpdate((UserExpView) v, e);
		}
	}

	@Override
	protected void onNewExpView(int idx, UserExpView e) {
		super.onNewExpView(idx, e);
		String id = e.getModel().getExpId();//
		this.ueViewMap.put(id, e);

		if (idx == this.totalExp - 1) {
			// select the last exp
			this.expIdSelected = id;
			ControlUtil.triggerAction(e.getModel(), Actions.A_UEXP_SELECT);
			// select exp will cause select event and
			// then
			// exp search.
			this.tryFinish("selectuserexp");
		}
	}

	@Override
	public void onAttachedEvent(AttachedEvent ae) {
		super.onAttachedEvent(ae);
		UiObjectI src = ae.getSource();

		// search result list's item
		if (src instanceof ExpItemView) {
			this.onExpItemView((ExpItemView) src);
		}
		if (src instanceof ActivityView) {
			this.onActivityViewAttached((ActivityView) src);
		}
	}

	/**
	 * search result
	 * 
	 * @param src
	 */
	private void onExpItemView(ExpItemView src) {
		if (this.expIdSelected == null) {

			throw new UiException("user exp not selected,should not search ");// not
																				// selected
																				// one
																				// exp,ignore
																				// the
																				// search
																				// result?
		}

		if (this.expIdCooperTo != null) {// already find one to cooper
			return;// only cooper one
		}

		this.expIdCooperTo = src.getModel().getExpId();

		src.clickAction(Actions.A_EXPS_COOPER);// listener to the
												// UserExpIncomingCrEvent on the
												// UserExpModel
		// cooper-request->notify/incomingCr->refreshIncomingCr->UeListControl
		// coper request,wait the
		// activities refresh and the
		// user's exp 001's activity id
		// is set.

		this.tryFinish("cooper.submit");
		// next:onUserExpViewUpdate,incomingCrId is rendered.

	}

	/**
	 * @param v
	 * @param e
	 */
	private void onUserExpViewUpdate(UserExpView v, ViewUpdateEvent e) {
		UserExpModel uem = v.getModel();
		String crId = uem.getIncomingCrId();
		if (crId != null) {
			Boolean pro = (Boolean) v.getProperty("incomingCrConfirmProcessing");
			if (pro != null) {
				throw new UiException(
						"should not be here, CooperConfirmSuccessMH should cause the crId to be null");
			}

			v.setProperty("incomingCrConfirmProcessing", true);// next
																// onUserExpViewUpdate
																// should not
																// here

			v.clickAction(Actions.A_UEXP_COOPER_CONFIRM);
			this.tryFinish("cooper.confirm");
		}
		String actId = uem.getActivityId();
		if (actId != null) {
			Boolean pro = (Boolean) v.getProperty("activityOpened");
			if (pro == null) {
				v.setProperty("activityOpened", Boolean.TRUE);//
				this.tryFinish("activity.created");//
				v.clickAction(Actions.A_UEXP_OPEN_ACTIVITY);

			}
		}

	}

	/**
	 * @param e
	 */

	/**
	 * @param src
	 */
	protected void onActivityViewAttached(ActivityView src) {
		this.activityView = src;
		this.tryFinish("activity.view");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.impl.test.gwt.client.cases.support.ExpTestBase#expText
	 * (int)
	 */
	@Override
	protected String expText(int idx) {
		return "exp text for #" + idx;
	}

}