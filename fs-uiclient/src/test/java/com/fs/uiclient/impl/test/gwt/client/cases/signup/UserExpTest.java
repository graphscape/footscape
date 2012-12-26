/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import org.junit.Before;
import org.junit.Test;

import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uiclient.impl.test.gwt.client.cases.support.LoginTestBase;
import com.fs.uicommons.api.gwt.client.frwk.login.event.AfterAuthEvent;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wuzhen
 * 
 */
public class UserExpTest extends LoginTestBase {

	private static String BODY = "I expecting ...";

	protected UserExpListView ueListView;

	protected UserExpView ueView;

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

		//
		this.delayTestFinish(10 * 1000);

	}

	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof ModelChildEvent) {
			this.onModelChildEvent((ModelChildEvent) e);
		}
		if (e instanceof ModelValueEvent) {
			this.onModelValueEvent((ModelValueEvent) e);
		}

	}

	@Override
	protected void onAfterAuthEvent(AfterAuthEvent e) {
		this.ueListView.clickAction(UserExpListModelI.A_CREATE);// open ths edit
	}

	@Override
	public void onAttachedEvent(AttachedEvent ae) {
		super.onAttachedEvent(ae);
		UiObjectI obj = ae.getSource();
		if (ae.getSource() instanceof UserExpListView) {
			this.onUserExpListViewAttached((UserExpListView) ae.getSource());
		}
		if (obj instanceof UserExpView) {
			this.onUserExpViewAttached((UserExpView) obj);
		}
	}

	public void onUserExpListViewAttached(UserExpListView v) {
		this.ueListView = v;
		// view
	}

	public void onUserExpViewAttached(UserExpView v) {
		this.ueView = v;
		this.tryFinish("edit.open");

		FormView fv = v.find(FormView.class, "default", true);

		EditorI bodyE = fv.find(EditorI.class, ExpEditModelI.F_BODY, true);
		bodyE.input(StringData.valueOf(BODY));//

		v.clickAction(ExpEditModelI.A_SUBMIT);// after submit action
												// success,the user's exp
		// list will add a new item.

	}

	@Override
	protected void onSuccessResposne(String path, UiResponse sre) {
		super.onSuccessResposne(path, sre);
		if (path.endsWith("expe/" + ExpEditModelI.A_SUBMIT)) {
			this.tryFinish("edit.submit");

		}

	}

	/**
	 * @param e
	 */
	protected void onModelChildEvent(ModelChildEvent e) {
		ModelI cm = e.getChild();
		if (cm instanceof UserExpModel) {
			this.tryFinish("list.newitem");
		}
	}

	protected void onModelValueEvent(ModelValueEvent e) {
		if (e.getModel() instanceof UserExpModel) {
			UserExpModel uem = (UserExpModel) e.getModel();
			String body = uem.getBody();
			assertEquals("body should be correct", BODY, body);

			this.tryFinish("list.newitem.body");
		}

	}

}
