/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.event.view.ViewUpdateEvent;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.support.ObjectElementHelper;
import com.fs.uicore.api.gwt.client.util.DateUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class UserExpItemView extends ViewSupport {

	protected TableWrapper table;

	protected UserExpModel model;

	protected TDWrapper actionsTd;
	
	/**
	 * @param ctn
	 */
	public UserExpItemView(ContainerI ctn, String name, UserExpModel um) {
		super(ctn, name, DOM.createDiv());
		this.element.addClassName("uel-item");
		this.model = um;
		UserExpModel t = (UserExpModel) this.model;
		this.table = new TableWrapper();
		this.element.appendChild(this.table.getElement());
		

		
		// actions

		//

		// icon | expbody | select
		// icon | timpstamp | open
		// icon | nick |
		// first
		{
			// left
			TRWrapper tr0 = this.table.addTr();
			TDWrapper td0 = tr0.addTd();
			td0.addClassName("uel-item-icon");
			td0.setAttribute("rowspan", "3");
			ElementWrapper image = new ElementWrapper(DOM.createImg());
			image.setAttribute(
					"src",
					"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACIAAAAiCAYAAAA6RwvCAAAAAXNSR0IArs4c6QAAAL1JREFUWMPtl9ENgCAMRKFxIhZyDTfQMWQgXUm/VCSFUtMPYq5/QnM+7hqifl63w3VQ5DopgAAkrWkMbhoDHLndwIxwtcT99TzULMub0758r7Re0k7Xlrg/IJdAKUPNDOQwnHbeQ63WWUDWtOnLizghbq3LYZUOSdaxSM6U9skiX4v5oRYhKzdU0XwRk0Clu6cpGu5eaL3INLpkcVILBwepqWanNs7azerTr3gthCYOKUKP3wmAAAQgAPkryAkar2RFS9XbgwAAAABJRU5ErkJggg==");//
			td0.append(image);
			// middle
			TDWrapper td01 = tr0.addTd();
			td01.setAttribute("rowspan", "1");
			// right
			TDWrapper td02 = tr0.addTd();
			td02.setAttribute("rowspan", "3");
			this.actionsTd = td02;
			this.actionsTd.addClassName("uel-item-actions");
		}
		//
		{// tr1
			TRWrapper tr1 = this.table.addTr();
			TDWrapper td1 = tr1.addTd();

			td1.addClassName("uel-item-expbody");
			td1.setAttribute("colspan", "1");//
			td1.getElement().setInnerText(t.getBody());
			// td1,1
		}

		{// tr 2
			// timestamp
			TRWrapper tr2 = this.table.addTr();
			TDWrapper td = tr2.addTd();
			td.addClassName("uel-item-timestamp");
			String dateS = DateUtil.format(t.getTimestamp(false), false);
			td.getElement().setInnerText(dateS);

		}


		ListI actions = this.factory.create(ListI.class);//
		actions.parent(this);
		
		ButtonI select = this.factory.create(ButtonI.class);
		select.setText(true, "search");
		select.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				//TODO open search view?
				UserExpItemView.this.dispatchActionEvent(Actions.A_UEXPI_SELECT);
			}
		});
		select.parent(actions);
		
		ButtonI open = this.factory.create(ButtonI.class);
		open.setText(true, "open");
		open.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				//TODO open search view?
				UserExpItemView.this.dispatchActionEvent(Actions.A_UEXPI_OPEN);
			}
		});
		open.parent(actions);
		
	}

	@Override
	protected void processAddChildElementObject(ElementObjectI ceo) {
		if (ceo instanceof ListI) {// actions
			this.actionsTd.append(ceo.getElement());
		} else {
			super.processAddChildElementObject(ceo);
		}
	}

	public void select(boolean sel) {
		this.elementWrapper.addAndRemoveClassName(sel, "selected", "unselected");
	}

	public void update() {
		
		new ViewUpdateEvent(this).dispatch();
	}

	public String getExpId() {
		return this.model.getExpId();
	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	protected void beforeActionEvent(ActionEvent ae) {
		//
		super.beforeActionEvent(ae);
		ae.setProperty("expId", this.getExpId());
	}

}
