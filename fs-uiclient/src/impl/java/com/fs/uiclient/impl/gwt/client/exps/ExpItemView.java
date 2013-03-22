/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpItemView;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
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
import com.fs.uicore.api.gwt.client.util.DateUtil;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class ExpItemView extends ViewSupport {

	protected TableWrapper table;

	protected TDWrapper actionsTd;
	ExpItemModel model;

	/**
	 * @param ctn
	 */
	public ExpItemView(String name, ContainerI ctn, ExpItemModel ei) {

		super(ctn, name, DOM.createDiv());
		this.model = ei;
		//
		if (this.table != null) {
			this.table.getElement().removeFromParent();//
		}

		this.table = new TableWrapper();

		this.element.appendChild(this.table.getElement());
		// IMAGE|time,author|Actions
		// IMAGE|Body |Actions
		// IMAGE|

		// first line
		{
			// td 0,0-1-2
			TRWrapper tr0 = this.table.addTr();
			TDWrapper td0 = tr0.addTd();
			td0.addClassName("exps-item-icon");
			td0.setAttribute("rowspan", "3");
			ElementWrapper image = new ElementWrapper(DOM.createImg());
			image.setAttribute("src", ei.getIconDataUrl());
			td0.append(image);
			// middle,timestamp
			TDWrapper td01 = tr0.addTd();
			td01.addClassName("exps-item-timestamp");
			String dateS = DateUtil.format(ei.getTimestamp(), false);
			td01.getElement().setInnerText(dateS);
			// right
			TDWrapper td02 = tr0.addTd();
			td02.setAttribute("rowspan", "3");
			this.actionsTd = td02;
			this.actionsTd.addClassName("exps-item-actions");

		}
		//
		{
			TRWrapper tr1 = this.table.addTr();
			TDWrapper td1 = tr1.addTd();

			td1.addClassName("exps-item-expbody");

			td1.getElement().setInnerText(ei.getExpBody());
			// td1,1
		}

		//actions
		ListI actions = this.factory.create(ListI.class);//
		actions.parent(this);
		

		ButtonI cooper = this.factory.create(ButtonI.class);
		cooper.setText(true, "cooper");
		cooper.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				// TODO open search view?
				ExpItemView.this.dispatchActionEvent(Actions.A_EXPS_COOPER);
			}
		});
		cooper.parent(actions);

	}

	@Override
	protected void processAddChildElementObject(ElementObjectI ceo) {
		if (ceo instanceof ListI) {// actions
			this.actionsTd.append(ceo.getElement());
		} else {
			super.processAddChildElementObject(ceo);
		}
	}

	public String getExpId() {
		ExpItemModel em = (ExpItemModel) this.model;
		return em.getExpId();
	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	protected void beforeActionEvent(ActionEvent ae) {
		//
		super.beforeActionEvent(ae);
		ae.setProperty("expId2", this.getExpId());
	}
}
