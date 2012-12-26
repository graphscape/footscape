/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import org.junit.Before;
import org.junit.Test;

import com.fs.uiclient.api.gwt.client.expe.ExpEditControlI;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uiclient.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionSuccessEvent;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wuzhen
 * 
 */
public class UserExpTest extends TestBase {

	private static String BODY = "I expecting ...";

	@Before
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		this.finishing.add("edit.open");// 1
		this.finishing.add("edit.submit");// 2
		this.finishing.add("list.newitem");// 3 the new item child event
		this.finishing.add("list.newitem.body");// the new item's value event.

	}

	@Test
	public void testDefaultCase() {
		this.dump();

		//
		// edit a new exp
		ExpEditControlI control = this.manager.getChild(ExpEditControlI.class,
				true);
		ExpEditModelI eem = this.rootModel.find(ExpEditModelI.class, true);

		// this editor can be open from the other view.

		eem.addValueHandler(ExpEditModelI.L_ISOPEN, Boolean.TRUE,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						UserExpTest.this.onExpEditOpen(e);// next to submit the
															// exp.
					}
				});

		// create action success listener
		control.addActionEventHandler(ActionSuccessEvent.TYPE, "submit",
				new EventHandlerI<ActionSuccessEvent>() {

					@Override
					public void handle(ActionSuccessEvent e) {
						UserExpTest.this.onSubmitActionSuccessEvent(e);
					}
				});

		//

		//
		this.delayTestFinish(10 * 1000);
		// listen new exp after edit submit,the list view should add a new item
		// exp
		UserExpListControlI uec = this.manager.getChild(
				UserExpListControlI.class, true);
		UserExpListModelI uem = this.rootModel.find(UserExpListModelI.class,
				true);
		//
		uem.addHandler(ModelChildEvent.TYPE, new EventHandlerI<ModelChildEvent>() {

			@Override
			public void handle(ModelChildEvent e) {
				UserExpTest.this.onNewExpItemAdd(e);
			}

		});//

		// click the new button in UserExpListView
		UserExpListView lv = this.root.find(UserExpListView.class, true);

		lv.clickAction(UserExpListModelI.A_CREATE);// open ths edit view

	}

	public void onExpEditOpen(ModelValueEvent e) {

		this.tryFinish("edit.open");

		FormsView fsv = this.root.find(FormsView.class, "expEdit", true);
		FormView fv = fsv.find(FormView.class, "default", true);

		EditorI bodyE = fv.find(EditorI.class, ExpEditModelI.F_BODY, true);
		bodyE.input(StringData.valueOf(BODY));//

		fsv.clickAction(ExpEditModelI.A_SUBMIT);// after submit action
												// success,the user's exp
		// list will add a new item.

	}

	/**
	 * @param e
	 */
	protected void onSubmitActionSuccessEvent(ActionSuccessEvent e) {
		System.out.println("e:" + e);
		// no need to do anything,the user's exp control should listen to the
		// edit
		// event,and get the new item from backend.

		this.tryFinish("edit.submit");

	}

	public void onNewExpItemAdd(ModelChildEvent e) {
		UserExpModel im = (UserExpModel) e.getChild();
		im.addValueHandler(UserExpModel.L_BODY,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						UserExpTest.this.onExpBodyChanging(e);
					}
				});

		this.tryFinish("list.newitem");
	}

	protected void onExpBodyChanging(ModelValueEvent e) {
		UserExpModel im = (UserExpModel) e.getModel();

		String body = im.getBody();

		assertEquals("body should be correct", BODY, body);
		this.tryFinish("list.newitem.body");
	}

}
