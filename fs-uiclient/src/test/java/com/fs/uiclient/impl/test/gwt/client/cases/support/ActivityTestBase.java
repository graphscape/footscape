/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.support;

import org.junit.Before;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.exps.item.ExpItemView;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionSuccessEvent;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public abstract class ActivityTestBase extends TestBase {

	protected static final String expId = "exp-001";

	protected static final String expId2 = "exp-002";

	protected static final String actId1 = "act-001";

	protected static final String account = "acc-001";//

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
		this.caseIdFilter.setCaseId("testSearch");
		SessionModelI sm = this.rootModel.find(SessionModelI.class, true);
		sm.addValueHandler(SessionModelI.L_IS_AUTHED,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ActivityTestBase.this.onAuthed();
					}
				});

		this.delayTestFinish(timeoutMillis);//

		sm.setAccount(this.account);// TODO more info?
		this.beforeAuth(sm);
		this.listenToTheUserExpInitBeforeAuth();//

		sm.setAuthed(true);

	}

	/**
	 * Oct 24, 2012
	 */
	protected abstract void beforeAuth(SessionModelI sm);

	// auth event will be monitored by the
	// UserExpListControl and then init its list of
	// model.
	protected void listenToTheUserExpInitBeforeAuth() {

		UserExpListControlI uec = this.manager.getControl(
				UserExpListControlI.class, true);
		uec.addActionEventHandler(ActionSuccessEvent.TYPE,
				UserExpListModelI.A_REFRESH, new EventHandlerI<ActionSuccessEvent>() {

					@Override
					public void handle(ActionSuccessEvent e) {
						ActivityTestBase.this
								.onUserExpListInitActionAndSelectOne(e);
					}
				});

	}

	protected void onAuthed() {
		System.out.println("authed ");
	}

	// init action should get value from server side,then
	// // the UserExpListControl should init action and the list is filled
	// already.

	// find the user exp and select it, this will cause the search and result
	protected void onUserExpListInitActionAndSelectOne(ActionSuccessEvent e) {

		{// this.listenToTheSearchItemBeforeSelect();//
			ExpSearchModelI esm = this.rootModel.find(ExpSearchModelI.class,
					true);
			esm.addHandler(ModelChildEvent.TYPE,
					new EventHandlerI<ModelChildEvent>() {

						@Override
						public void handle(ModelChildEvent e) {
							ActivityTestBase.this.onExpItemInSearchModel(e);
						}
					});
		}//
		UserExpModel ue = this.getTheUserExpModel();
		ue.select(true);// select will cause the search control to refresh the
						// recommended keywords and then search the result.

	}

	private UserExpModel getTheUserExpModel() {
		UserExpListModelI uem = this.rootModel.find(UserExpListModelI.class,
				true);

		// this.dump();
		UserExpModel ue = uem.getUserExp(expId, true);

		assertEquals("the selected exp should is the specified one",
				this.expId, ue.getExpId());
		return ue;
	}

	/**
	 * @param e
	 */

	// after the user exp selected, search control will search
	// the search is adding the item
	// we will find the exp item and make coper request on it.
	//
	protected void onExpItemInSearchModel(ModelChildEvent e) {
		ExpItemModel eim = (ExpItemModel) e.getChild();
		if (!e.isAdd()) {
			// remove item,ignore
			return;
		}
		if (!eim.isExp(this.expId2)) {// find the proper exp with id
			return;
		}
		this.tryFinish("search.result");
		// listen to the activity before click coper
		this.listenToTheActivityIdSettingBeforeCoper();
		// click coper
		String name = "expItem-" + eim.getName();// see ExpSearchView
		ExpItemView eiv = this.root.find(ExpItemView.class, name, true);
		eiv.clickAction(ExpItemModel.A_COPER);// coper request,wait the
												// activities refresh and the
												// user's exp 001's activity id
												// is set.

		this.tryFinish("coper.submit");
	}

	protected void listenToTheActivityIdSettingBeforeCoper() {
		UserExpModel ue = this.getTheUserExpModel();
		ue.addValueHandler(null,//TODO UserExpModel.L_ACTIVITY_ID,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ActivityTestBase.this.onActivityIdSet(e);//
					}
				});
	}

	//
	// the activity is created,the user exp model is notified and link to the
	// activity by id.
	// then we should test open the activity by click the user exp's open
	// activity action.
	protected void onActivityIdSet(ModelValueEvent e) {

		UserExpModel ue = this.getTheUserExpModel();

		UserExpModel ue2 = (UserExpModel) e.getModel();
		String expId2 = ue2.getExpId();

		assertEquals("the activity id set on the wrong exp", expId2,
				ue.getExpId());

		// the selected item should have an activity with it.
		String actId = ue.getActivityId();
		assertNotNull(
				"activity id is nulll for the exp model with id:"
						+ ue.getExpId(), actId);

		assertEquals("selected activity not correct or not selected.", actId1,
				actId);

		this.tryFinish("activity.created");//

		// listen to the activity open
		this.listenToTheActivityOpen();
		// open the activity by click the view.
		String vname = "userExpView-" + expId;// the view name is same as the
												// exp id;
		this.dump();
		UserExpView uev = this.root.find(UserExpView.class, vname, true);
		uev.clickAction(UserExpModel.A_OPEN_ACTIVITY);

	}

	protected void listenToTheActivityOpen() {
		ActivitiesModelI asm = this.rootModel
				.find(ActivitiesModelI.class, true);
		asm.addHandler(ModelChildEvent.TYPE, new EventHandlerI<ModelChildEvent>() {

			@Override
			public void handle(ModelChildEvent e) {
				ActivityTestBase.this.onActivityModelAdd(e);
			}
		});
	}

	// the activity is opened.
	protected void onActivityModelAdd(ModelChildEvent e) {
		if (!(e.getChild() instanceof ActivityModelI)) {
			return;
		}
		ActivityModelI am = (ActivityModelI) e.getChild();
		String actId2 = am.getActivityId();
		assertEquals("activity opened but the id not correct", this.actId1,
				actId2);
		this.tryFinish("activity.open");
		this.onActivityOpen(am);
	}

	protected abstract void onActivityOpen(ActivityModelI am);

}
