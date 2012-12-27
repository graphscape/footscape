/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.support;

import org.junit.Before;

import com.fs.uiclient.api.gwt.client.event.ActivityCreatedEvent;
import com.fs.uiclient.api.gwt.client.event.AfterExpSearchEvent;
import com.fs.uiclient.api.gwt.client.event.AfterExpSelectedEvent;
import com.fs.uiclient.api.gwt.client.event.CooperRequestRefreshEvent;
import com.fs.uiclient.api.gwt.client.event.ExpCreatedEvent;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.activity.ActivityView;
import com.fs.uiclient.impl.gwt.client.exps.item.ExpItemView;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uicommons.api.gwt.client.frwk.login.event.AfterAuthEvent;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;

/**
 * @author wu
 * 
 */
public class ActivityTestBase extends ExpTestBase {

	protected UserExpView userExpViewSelected;

	protected ExpItemModel expToBeCooper;

	protected ActivityView activityView;

	@Before
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		this.finishing.add("search.result");// user select user's exp,the search
											// result is got
		this.finishing.add("coper.submit");// user select one item from search
											// result,a coper is created.
		this.finishing.add("activity.created");// the coper is confirmed by
												// other user and activity
												// created.
		this.finishing.add("activity.open");
	}

	public void start() {
		this.delayTestFinish(timeoutMillis);//
		super.start();

	}

	protected void onAfterAuthEvent(AfterAuthEvent e) {

	}

	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof AfterExpSearchEvent) {
			this.onAfterExpSearchEvent((AfterExpSearchEvent) e);
		}
		if (e instanceof AfterExpSelectedEvent) {
			this.onAfterExpSelectedEvent((AfterExpSelectedEvent) e);
		}
		if (e instanceof ActivityCreatedEvent) {
			this.onActivityCreatedEvent((ActivityCreatedEvent) e);
		}

		if (e instanceof CooperRequestRefreshEvent) {
			this.onCooperRequestRefreshEvent((CooperRequestRefreshEvent) e);
		}
	}

	/**
	 * @param e
	 */
	private void onCooperRequestRefreshEvent(CooperRequestRefreshEvent e) {
		// confirm this reqest

	}

	/**
	 * @param e
	 */
	private void onAfterExpSelectedEvent(AfterExpSelectedEvent e) {

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

	@Override
	public void onUserExpViewAttached(UserExpView v) {
		super.onUserExpViewAttached(v);//
		this.userExpViewSelected = v;//
		this.userExpViewSelected.getModel().select(true);// AfterExpSelectedEvent
	}

	/**
	 * search result
	 * 
	 * @param src
	 */
	private void onExpItemView(ExpItemView src) {

		if (expToBeCooper != null) {
			return;// only cooper one
		}

		this.expToBeCooper = src.getModel();

		src.clickAction(ExpItemModel.A_COPER);
		// coper request,wait the
		// activities refresh and the
		// user's exp 001's activity id
		// is set.

		this.tryFinish("coper.submit");
	}

	@Override
	protected void onSuccessResposne(String path, UiResponse sre) {
		super.onSuccessResposne(path, sre);
		if (path.endsWith("uelist/" + UserExpListModelI.A_REFRESH)) {
		}
	}

	// after search
	private void onAfterExpSearchEvent(AfterExpSearchEvent e) {

		this.tryFinish("search.result");

	}

	/**
	 * @param e
	 */
	private void onActivityCreatedEvent(ActivityCreatedEvent e) {
		this.tryFinish("activity.created");//

		this.userExpViewSelected.clickAction(UserExpModel.A_OPEN_ACTIVITY);
	}

	/**
	 * @param src
	 */
	protected void onActivityViewAttached(ActivityView src) {
		this.activityView = src;
		this.tryFinish("activity.open");
	}

	@Override
	protected int totalExp() {
		// TODO Auto-generated method stub
		return 3;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.impl.test.gwt.client.cases.support.ExpTestBase#onExpCreated
	 * (int, com.fs.uiclient.api.gwt.client.event.ExpCreatedEvent)
	 */
	@Override
	protected void onExpCreated(int idx, ExpCreatedEvent e) {
		// TODO Auto-generated method stub

	}

}
