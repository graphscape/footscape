/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpItemView;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wuzhen
 * 
 */
public class ExpTestWorker extends AbstractTestWorker {

	protected UserExpListView ueListView;

	protected Map<String, UserExpItemView> ueViewMap;

	protected ExpEditView eeView;

	protected int totalExp;

	protected int nextExpIdx = 0;

	protected int expCreatedEventIdx = 0;

	protected UserInfo userInfo;

	public ExpTestWorker(int totalExp) {
		this.totalExp = totalExp;
		this.ueViewMap = new HashMap<String, UserExpItemView>();
		this.tasks.add("founduelistview");// 1
		this.tasks.add("editview");// 1
		this.tasks.add("editrequest");// 1
		this.tasks.add("editok");// 2
		this.tasks.add("allexpcreated");

	}

	@Override
	public void onAttachedEvent(AttachedEvent ae) {
		super.onAttachedEvent(ae);
		UiObjectI obj = ae.getSource();

		if (obj instanceof ExpEditView) {
			this.onExpEditViewAttached((ExpEditView) obj);
		}
		if (obj instanceof UserExpItemView) {
			this.onUserExpViewAttached((UserExpItemView) obj);
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

	public void start(UiClientI client) {
		super.start(client);
		this.userInfo = this.getRegisterUserInfo(true);//
		this.ueListView = (UserExpListView) this.manager.getControl(MainControlI.class, true).openUeList(true);

		this.tryFinish("founduelistview");
		this.ueListView.clickAction(Actions.A_UEL_CREATE);// open ths edit

	}

	protected String expText(int idx) {
		return this.userInfo.getString("nick") + " is expecting " + idx;
	}

	protected void submitExp() {

		String text = this.expText(this.nextExpIdx);
		FormView fv = this.eeView.find(FormView.class, "default", true);

		EditorI bodyE = fv.find(EditorI.class, ExpEditView.F_BODY, true);

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

	public void onUserExpViewAttached(UserExpItemView v) {

		int size = this.ueViewMap.size();
		String expId = v.getExpId();
		this.ueViewMap.put(expId, v);//
		int newSize = this.ueViewMap.size();
		
		if (newSize > size) {//has new exp created
			this.onNewExpView(size, v);//
		}
		
		if (this.ueViewMap.size() == this.totalExp) {
			this.tryFinish("allexpcreated");
		}
		
		

	}

	protected void onNewExpView(int idx, UserExpItemView e) {
		
	}

	/**
	 * @return the nextExpIdx
	 */
	public int getNextExpIdx() {
		return nextExpIdx;
	}

}
