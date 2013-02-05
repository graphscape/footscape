/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import java.util.List;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.event.view.ViewUpdateEvent;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.activity.ActivityView;
import com.fs.uiclient.impl.gwt.client.exps.item.ExpItemView;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;

/**
 * @author wu
 * 
 *         <p>
 *         This AbstractTestWorker will automatically signup one account, login
 *         to system, create number of exp, send cooper request to one exp from
 *         himself, confirm cooper request, finally open this activity.
 *         <p>
 *         see the TestEntryPoint for calling this class.
 */
public class ActivityTestWorker extends AbstractTestWorker {

	protected String expIdSelected;// selected

	protected String expIdCooperTo;// cooper with

	protected ActivityView activityView;

	/**
	 * @param user
	 * @param email
	 * @param pass
	 * @param totalExp
	 */
	public ActivityTestWorker() {

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

	public void start(UiClientI client) {
		super.start(client);
		this.getRegisterUserInfo(true);//

		UserExpListModelI uelm = client.getRootModel().find(UserExpListModelI.class, true);
		List<UserExpModel> ueml = uelm.getChildList(UserExpModel.class);
		if (ueml.size() == 0) {
			throw new UiException("no user exp in list");
		}
		UserExpModel model = ueml.get(0);
		String id = model.getExpId();//
		this.expIdSelected = id;

		new ActionEvent(client, Actions.A_UEXP_SELECT).property("expId", id).dispatch();
		// select exp will cause select event and
		// then
		// exp search.
		this.tryFinish("selectuserexp");

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

		String expId = src.getExpId();

		if (this.expIdSelected.equals(expId)) {
			return;// not cooper to the same exp,wait the next one
		}

		this.expIdCooperTo = expId;

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
		String crId = v.getIncomingCrId();
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
		String actId = v.getActivityId();
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

}
