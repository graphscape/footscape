/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases;

import org.junit.Before;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.exps.item.ExpItemView;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uiclient.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class ExpSearchTest extends TestBase {

	private static final String expId = "exp-001";

	private static final String expId2 = "exp-002";

	private static final String actId1 = "act-001";

	private static final String account = "acc-001";//

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

	public void testSearch() {

		this.delayTestFinish(timeoutMillis);//

		this.listenToTheUserExpInitBeforeAuth();//

	}

	// auth event will be monitored by the
	// UserExpListControl and then init its list of
	// model.
	protected void listenToTheUserExpInitBeforeAuth() {

		UserExpListControlI uec = this.manager.getControl(UserExpListControlI.class, true);

	}

	protected void onAuthed() {
		System.out.println("authed ");
	}

	// init action should get value from server side,then
	// // the UserExpListControl should init action and the list is filled
	// already.

	// find the user exp and select it, this will cause the search and result
	protected void onUserExpListInitActionAndSelectOne() {
		// listen to the main model for the activity model,all activity model
		// will be the main model's children
		//

		UserExpModel ue = this.getTheUserExpModel();
		ue.select(true);// select will cause the search control to refresh the
						// recommended keywords and then search the result.

	}

	private UserExpModel getTheUserExpModel() {
		return null;

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
		eiv.clickAction(Actions.A_EXPS_COOPER);// coper request,wait the
												// activities refresh and the
												// user's exp 001's activity id
												// is set.

		this.tryFinish("coper.submit");
	}

	protected void listenToTheActivityIdSettingBeforeCoper() {
		UserExpModel ue = this.getTheUserExpModel();
		ue.addValueHandler(null,// TODO UserExpModel.L_ACTIVITY_ID,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ExpSearchTest.this.onActivityIdSet(e);//
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

		assertEquals("the activity id set on the wrong exp", expId2, ue.getExpId());

		// the selected item should have an activity with it.
		String actId = ue.getActivityId();
		assertNotNull("activity id is nulll for the exp model with id:" + ue.getExpId(), actId);

		assertEquals("selected activity not correct or not selected.", actId1, actId);

		this.tryFinish("activity.created");//

		// listen to the activity open
		this.listenToTheActivityOpen();
		// open the activity by click the view.
		String vname = "userExpView-" + expId;// the view name is same as the
												// exp id;
		this.dump();
		UserExpView uev = this.root.find(UserExpView.class, vname, true);
		uev.clickAction(Actions.A_UEXP_OPEN_ACTIVITY);

	}

	protected void listenToTheActivityOpen() {

	}

	// the activity is opened.
	protected void onActivityModelAdd(ModelChildEvent e) {
		if (!(e.getChild() instanceof ActivityModelI)) {
			return;
		}
		ActivityModelI am = (ActivityModelI) e.getChild();
		String actId2 = am.getActivityId();
		assertEquals("activity opened but the id not correct", this.actId1, actId2);
		this.tryFinish("activity.open");
	}

	/**
	 * Oct 20, 2012
	 */
	protected void onMainChildEvent(ModelChildEvent e) {
		//
		ModelI cm = e.getChild();
		if (!(cm instanceof ActivityModelI)) {
			return;
		}
		ActivityModelI am = (ActivityModelI) cm;
		String actId = am.getActivityId();
		assertEquals("new added activity id not correct.", this.actId1, actId);

	}
}
