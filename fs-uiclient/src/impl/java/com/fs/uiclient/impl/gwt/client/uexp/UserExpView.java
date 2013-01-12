/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.event.model.UserExpActivityEvent;
import com.fs.uiclient.api.gwt.client.event.model.UserExpIncomingCrEvent;
import com.fs.uiclient.api.gwt.client.event.model.UserExpSelectEvent;
import com.fs.uiclient.api.gwt.client.event.view.ViewUpdateEvent;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.util.DateUtil;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class UserExpView extends SimpleView {

	protected TableWrapper table;

	protected TDWrapper incomingCrExpId1;// incoming cooper request exp id from.

	/**
	 * @param ctn
	 */
	public UserExpView(ContainerI ctn) {
		super(ctn);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport#model(com.fs.
	 * uicore.api.gwt.client.ModelI)
	 */
	@Override
	public void doModel(ModelI model) {
		super.doModel(model);
		model.addCommitProcessor(new UiCallbackI<UserExpModel, Object>() {

			@Override
			public Object execute(UserExpModel t) {
				UserExpView.this.onModelCommit(t);
				return null;
			}
		});
		model.addHandler(UserExpSelectEvent.TYPE,
				new EventHandlerI<UserExpSelectEvent>() {

					@Override
					public void handle(UserExpSelectEvent t) {
						UserExpView.this.onUserExpSelected(t);
					}
				});
		model.addHandler(UserExpIncomingCrEvent.TYPE,
				new EventHandlerI<UserExpIncomingCrEvent>() {

					@Override
					public void handle(UserExpIncomingCrEvent t) {
						UserExpView.this.onUserExpIncomingCr(t);
					}
				});
		model.addHandler(UserExpActivityEvent.TYPE,
				new EventHandlerI<UserExpActivityEvent>() {

					@Override
					public void handle(UserExpActivityEvent t) {
						UserExpView.this.onUserExpActivityEvent(t);
					}
				});

	}

	/**
	 * @param t
	 */
	protected void onUserExpActivityEvent(UserExpActivityEvent t) {
		this.update(this.getModel());
	}

	/**
	 * @param t
	 */
	protected void onUserExpIncomingCr(UserExpIncomingCrEvent t) {
		this.update(this.getModel());// TODO only update needed part.
	}

	private void onUserExpSelected(UserExpSelectEvent t) {
		boolean sel = t.getModel().isSelected();

		this.elementWrapper
				.addAndRemoveClassName(sel, "selected", "unselected");

	}

	protected void onModelCommit(UserExpModel t) {
		this.update(t);
	}

	protected void update(UserExpModel t) {

		//
		if (this.table != null) {
			this.table.getElement().removeFromParent();//
		}

		this.table = new TableWrapper();
		this.body.appendChild(this.table.getElement());

		// icon | expbody
		// icon | timpstamp
		// icon | expId1,refresh to the body?
		// icon | activityId
		// first
		{
			// td 0,0-1-2
			TRWrapper tr0 = this.table.addTr();
			TDWrapper td0 = tr0.addTd();
			td0.addClassName("icon");
			td0.setAttribute("rowspan", "4");
			ElementWrapper image = new ElementWrapper(DOM.createImg());
			image.setAttribute("src", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACIAAAAiCAYAAAA6RwvCAAAAAXNSR0IArs4c6QAAAL1JREFUWMPtl9ENgCAMRKFxIhZyDTfQMWQgXUm/VCSFUtMPYq5/QnM+7hqifl63w3VQ5DopgAAkrWkMbhoDHLndwIxwtcT99TzULMub0758r7Re0k7Xlrg/IJdAKUPNDOQwnHbeQ63WWUDWtOnLizghbq3LYZUOSdaxSM6U9skiX4v5oRYhKzdU0XwRk0Clu6cpGu5eaL3INLpkcVILBwepqWanNs7azerTr3gthCYOKUKP3wmAAAQgAPkryAkar2RFS9XbgwAAAABJRU5ErkJggg==");//
			td0.append(image);

		}
		//
		{
			TRWrapper tr1 = this.table.addTr();
			TDWrapper td1 = tr1.addTd();

			td1.addClassName("expBody");
			td1.setAttribute("colspan", "1");//
			td1.getElement().setInnerText(t.getBody());
			// td1,1
		}

		{// timestamp
			TRWrapper tr2 = this.table.addTr();
			TDWrapper td = tr2.addTd();
			td.addClassName("timestamp");
			String dateS = DateUtil.format(t.getTimestamp(false), false);
			td.getElement().setInnerText(dateS);

		}

		{// activity
			TRWrapper tr = this.table.addTr();
			TDWrapper td = tr.addTd();
			this.incomingCrExpId1 = td;// TODO lazy
			td.getElement().setInnerText(t.getIncomingCrId());//

		}
		{// activity
			TRWrapper tr = this.table.addTr();
			TDWrapper td = tr.addTd();
			td.setAttribute("colspan", "2");//
			td.getElement().setInnerText(t.getActivityId());

		}
		new ViewUpdateEvent(this).dispatch();
	}

	@Override
	public UserExpModel getModel() {
		return (UserExpModel) this.model;
	}

}
