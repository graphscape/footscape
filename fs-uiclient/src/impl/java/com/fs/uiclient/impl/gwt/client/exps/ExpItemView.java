/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
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
import com.fs.uicore.api.gwt.client.util.DateUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

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
		this.element.addClassName("exps-item");
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

		{
			String rowspan = "3";
			// first line
			TRWrapper tr0 = this.table.addTr();
			// Left td,3 rows
			TDWrapper td0 = tr0.addTd();
			td0.addClassName("exps-item-icon");
			td0.setAttribute("rowspan", rowspan);
			Element ar = DOM.createAnchor();
			ar.addClassName("user-icon");
			
			ElementWrapper image = new ElementWrapper(DOM.createImg());
			image.setAttribute("src", ei.getIconDataUrl());
			
			ar.appendChild(image.getElement());
			td0.append(ar);
			
			// middle,exp title
			TDWrapper td01 = tr0.addTd();
			td01.addClassName("exps-item-exptitle");
			
			td01.getElement().setInnerText(ei.getExpTitle());
			
			// right
			TDWrapper td02 = tr0.addTd();
			td02.setAttribute("rowspan", rowspan);
			this.actionsTd = td02;
			this.actionsTd.addClassName("exps-item-actions");

		}
		//
		{//middle
			//second line
			TRWrapper tr1 = this.table.addTr();
			TDWrapper td1 = tr1.addTd();

			td1.addClassName("exps-item-expbody");
			String html = ei.getExpBodyAsHtml();
			td1.getElement().setInnerHTML(html);
			// td1,1
		}
		//
		{//middle
			//third line
			TRWrapper tr1 = this.table.addTr();
			TDWrapper td1 = tr1.addTd();

			td1.addClassName("exps-item-timestamp");
			String dateS = DateUtil.format(ei.getTimestamp(), false);
			td1.getElement().setInnerText(dateS);
			// td1,1
		}

		// actions
		ListI actions = this.factory.create(ListI.class);//
		actions.parent(this);// note,see on add child method

		ButtonI cooper = this.factory.create(ButtonI.class);
		cooper.setText(true, UiClientConstants.AP_COOPER.toString());
		cooper.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				// TODO open search view?
				ExpItemView.this.onCooperClicked();
			}
		});
		cooper.parent(actions);

	}

	protected void onCooperClicked() {
		
		this.dispatchActionEvent(Actions.A_EXPS_COOPER);

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
