/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.impl.gwt.client.uexp.UserIconView;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.ImageUrl;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
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
		// Icon|time,author|count
		// Icon|Body 
		// Icon|Image	  |Actions
		// Icon|timestamp |Actions
		int rowspan = 5;
		int colspanOfExpBody = 2;
		int colspanOfSep = colspanOfExpBody;
		int rowspanOfActions = rowspan - 3;
		
		{// first line/TR0
			// first line
			TRWrapper tr0 = this.table.addTr();

			{// user icon 4 rows
				TDWrapper td0 = tr0.addTd();
				td0.addClassName("exps-item-usericon");
				td0.setAttribute("rowspan", "" + rowspan);
				//
				UserIconView uiv = new UserIconView(this.container, ei.getAccountId(), ei.getUserIconAsImageUrl());
				td0.append(uiv.getElement());// NOTE,parent is not
												// this.element,but td0,a nother
												// element,see
												// ElementObjectSupport.
				// note must after append to td0.other wise the element will be
				// add to the top element.
				uiv.parent(this);

			}
			{// title
				TDWrapper td01 = tr0.addTd();
				td01.addClassName("exps-item-exptitle");

				td01.getElement().setInnerText(ei.getExpTitle());
			}
			{//count 
				TDWrapper td02 = tr0.addTd();

				td02.addClassName("exps-item-count");
				String html = "<span class='count'>" + ei.getConnectionCount()
						+ "</span><span class='info'>EXPs</span>";
				td02.getElement().setInnerHTML(html);
			}
		}// end of first line
		{//seperator
			TRWrapper tr1 = this.table.addTr();
			TDWrapper td1 = tr1.addTd();
//			td1.setAttribute("colspan", ""+colspanOfSep);
//			Element hr = DOM.createElement("hr");
//			hr.addClassName("seperator");
//			td1.append(hr);
		}
		{// second line

			TRWrapper tr1 = this.table.addTr();
			{// exp body
				TDWrapper td1 = tr1.addTd();
				
				//
				td1.addClassName("exps-item-expbody");
				String html = ei.getExpBodyAsHtml();
				td1.getElement().setInnerHTML(html);
				td1.setAttribute("colspan", "" + colspanOfExpBody);
				// td1,1
			}

			
		}
		//
		{// third line

			TRWrapper tr1 = this.table.addTr();
			{// exp image

				TDWrapper td1 = tr1.addTd();

				td1.addClassName("exps-item-expimage");
				ImageUrl img = ei.getImageUrl();
				if (!img.isNone()) {// only add when as image
					Element image = DOM.createImg();
					
					image.setAttribute("src", img.getAsSrc(this.getClient(true)));//
					td1.getElement().appendChild(image);
				}
			}
			
			{//
				TDWrapper td03 = tr1.addTd();
				td03.setAttribute("rowspan", "" + rowspanOfActions);
				this.actionsTd = td03;
				this.actionsTd.addClassName("exps-item-actions");
			}

			// td1,1
		}
		{// // third line

			TRWrapper tr1 = this.table.addTr();

			{// timestamp
				TDWrapper td1 = tr1.addTd();

				td1.addClassName("exps-item-timestamp");
				String dateS = DateUtil.format(ei.getTimestamp(), false);
				String nick = ei.getNick();
				String html = "<span>" + dateS + ",by:</span>" + "<span>" + nick + "</span>";
				td1.getElement().setInnerHTML(html);

			}
			// td1,1
		}

		// actions
		{
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
		ae.setProperty("expId2", this.getExpId());// for action:cooper
	}
	/**
	 * <code>
	 {// first line, middle right,exp icon,rows pan 4
				TDWrapper td02 = tr0.addTd();
				td02.setAttribute("rowspan", rowspan);
				td02.addClassName("exps-item-expicon");
				String icon = ei.getIcon();
				if (icon != null) {// only show when has

					Element image = DOM.createImg();
					image.setAttribute("src", icon);

					Element ar = DOM.createAnchor();
					ar.addClassName("exp-icon");
					ar.appendChild(image);
					td02.append(ar);
				}
			}</code>
	 */
}
