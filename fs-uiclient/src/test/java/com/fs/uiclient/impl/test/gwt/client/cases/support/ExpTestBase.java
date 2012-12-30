/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.support;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;

import com.fs.uiclient.api.gwt.client.event.ExpCreatedEvent;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uicommons.api.gwt.client.frwk.login.event.AfterAuthEvent;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;

/**
 * @author wuzhen
 * 
 */
public abstract class ExpTestBase extends LoginTestBase {

	protected UserExpListView ueListView;

	protected UserExpView ueView;

	protected ExpEditView eeView;

	protected int nextExpIdx = 0;

	protected int expCreatedEventIdx = 0;

	@Before
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		this.finishing.add("editview");// 1
		this.finishing.add("editok");// 2
		this.finishing.add("expcreated");// 3 the new item child event

	}

	@Override
	public void start() {

		super.start();
	}

	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof ExpCreatedEvent) {
			ExpCreatedEvent ec = (ExpCreatedEvent) e;
			String id = ec.getExpId();
			this.onExpCreated(this.expCreatedEventIdx, (ExpCreatedEvent) e);
			this.expCreatedEventIdx++;
		}
	}

	@Override
	protected void onAfterAuthEvent(AfterAuthEvent e) {
		Mvc mvc = this.mcontrol.getLazyObject(MainControlI.LZ_UE_LIST, true);
	}

	@Override
	public void onAttachedEvent(AttachedEvent ae) {
		super.onAttachedEvent(ae);
		UiObjectI obj = ae.getSource();
		if (ae.getSource() instanceof UserExpListView) {
			this.onUserExpListViewAttached((UserExpListView) ae.getSource());
		}
		if (obj instanceof ExpEditView) {
			this.onExpEditViewAttached((ExpEditView) obj);
		}
		if (obj instanceof UserExpView) {
			this.onUserExpViewAttached((UserExpView) obj);
		}
	}

	public void onUserExpListViewAttached(UserExpListView v) {
		this.ueListView = v;
		this.ueListView.clickAction(UserExpListModelI.A_CREATE);// open ths edit
		// view
	}

	/**
	 * Dec 26, 2012
	 */
	private void onExpEditViewAttached(ExpEditView v) {
		//

		this.eeView = v;
		this.tryFinish("editview");

		this.submitExp();

	}

	protected void submitExp() {

		String text = this.expText(this.nextExpIdx);
		FormView fv = this.eeView.find(FormView.class, "default", true);

		EditorI bodyE = fv.find(EditorI.class, ExpEditModelI.F_BODY, true);

		bodyE.input(StringData.valueOf(text));//

		this.eeView.clickAction(ExpEditModelI.A_SUBMIT);// after submit action

	}

	protected abstract int totalExp();

	protected abstract String expText(int idx);

	@Override
	protected void onSuccessResposne(String path, UiResponse sre) {
		super.onSuccessResposne(path, sre);
		if (path.endsWith("expe/" + ExpEditModelI.A_SUBMIT)) {
			this.nextExpIdx++;
			if (this.nextExpIdx < this.totalExp()) {
				this.submitExp();
			} else {
				this.tryFinish("editok");
			}

		}

	}

	public void onUserExpViewAttached(UserExpView v) {

		UserExpModel uem = v.getModel();
		this.tryFinish("expcreated");

	}

	protected abstract void onExpCreated(int idx, ExpCreatedEvent e);

}