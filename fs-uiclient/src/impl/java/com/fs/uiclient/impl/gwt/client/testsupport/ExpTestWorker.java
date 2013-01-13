/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wuzhen
 * 
 */
public class ExpTestWorker extends LoginTestWorker {

	protected UserExpListView ueListView;

	protected Map<String, UserExpView> ueViewMap;

	protected ExpEditView eeView;

	protected int totalExp;

	protected int nextExpIdx = 0;

	protected int expCreatedEventIdx = 0;

	public ExpTestWorker(String user, String email, String pass, int totalExp) {
		super(user, email, pass);
		this.totalExp = totalExp;
		this.ueViewMap = new HashMap<String, UserExpView>();
		this.tasks.add("founduelistview");// 1
		this.tasks.add("editview");// 1
		this.tasks.add("editrequest");// 1
		this.tasks.add("editok");// 2
		this.tasks.add("expcreated");// 3 the new item child event
		this.tasks.add("allexpcreated");

	}

	@Override
	public void onAttachedEvent(AttachedEvent ae) {
		super.onAttachedEvent(ae);
		UiObjectI obj = ae.getSource();

		if (obj instanceof ExpEditView) {
			this.onExpEditViewAttached((ExpEditView) obj);
		}
		if (obj instanceof UserExpView) {
			this.onUserExpViewAttached((UserExpView) obj);
		}
	}

	/**
	 * Dec 26, 2012
	 */
	private void onExpEditViewAttached(ExpEditView v) {
		//
		this.eeView = v;
		this.tryFinish("editview");
		
		this.submitExp();
		this.tryFinish("editrequest");
	}

	@Override
	protected void onRegisterUserLogin(UserInfo ui) {
		this.ueListView = this.client.getRoot().find(new UiCallbackI<UiObjectI, UserExpListView>() {

			@Override
			public UserExpListView execute(UiObjectI t) {
				//
				if (!(t instanceof UserExpListView)) {
					return null;
				}
				return (UserExpListView) t;
			}
		});
		this.tryFinish("founduelistview");
		this.ueListView.clickAction(Actions.A_UEL_CREATE);// open ths edit
		
	}

	protected String expText(int idx) {
		return "i exp " + idx;
	}

	protected void submitExp() {

		String text = this.expText(this.nextExpIdx);
		FormView fv = this.eeView.find(FormView.class, "default", true);

		EditorI bodyE = fv.find(EditorI.class, ExpEditModelI.F_BODY, true);

		bodyE.input((text));//

		this.eeView.clickAction(Actions.A_EXPE_SUBMIT);// after submit action

	}

	@Override
	protected void onSuccessMessageEvent(SuccessMessageEvent e) {
		super.onSuccessMessageEvent(e);
		Path p = e.getMessage().getPath().getParent();

		if (p.equals(Path.valueOf(EndpointMessageEvent.TYPE.getAsPath().toString() + "/expe/submit"))) {
			this.nextExpIdx++;
			if (this.nextExpIdx < this.totalExp) {
				this.submitExp();
			} else {
				this.tryFinish("editok");
			}

		}

	}

	public void onUserExpViewAttached(UserExpView v) {

		int size = this.ueViewMap.size();
		UserExpModel uem = v.getModel();
		String expId = uem.getExpId();
		this.ueViewMap.put(expId, v);//
		int newSize = this.ueViewMap.size();
		if (newSize > size) {
			this.onNewExpView(size, v);//
		}
		if (this.ueViewMap.size() == this.totalExp) {
			this.tryFinish("expcreated");
		}

	}

	protected void onNewExpView(int idx, UserExpView e) {
		if (idx + 1 == this.totalExp) {
			this.tryFinish("allexpcreated");
		}
	}

	/**
	 * @return the nextExpIdx
	 */
	public int getNextExpIdx() {
		return nextExpIdx;
	}

}
