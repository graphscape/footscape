/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases;

import org.junit.Before;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.exps.item.ExpItemView;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpItemView;
import com.fs.uiclient.impl.test.gwt.client.cases.support.TestBase;
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
	// UserExpListControl and then init its msglist of
	// model.
	protected void listenToTheUserExpInitBeforeAuth() {

		UserExpListControlI uec = this.manager.getControl(UserExpListControlI.class, true);

	}

	protected void onAuthed() {
		System.out.println("authed ");
	}

	// init action should get value from server side,then
	// // the UserExpListControl should init action and the msglist is filled
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

		this.tryFinish("activity.created");//

		// listen to the activity open
		this.listenToTheActivityOpen();
		// open the activity by click the view.
		String vname = "userExpView-" + expId;// the view name is same as the
												// exp id;
		this.dump();
		UserExpItemView uev = this.root.find(UserExpItemView.class, vname, true);
		uev.clickAction(Actions.A_UEXPI_OPEN);

	}

	protected void listenToTheActivityOpen() {

	}

}
