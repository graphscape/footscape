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
import com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.ContainerI;
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
public class UserExpItemView extends ViewSupport {

	protected TableWrapper table;

	protected UserExpModel model;

	protected TDWrapper actionsTd;
	
	protected TDWrapper statusTd;

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
		int rowspan = 4;
		int rowspanOfActions = rowspan - 2;
		int colspanOfActions = 3;
		int colspanOfExpBody = colspanOfActions+1;
		
		{// first line,left is , middle
			// left
			// line 1
			TRWrapper tr = this.table.addTr();

			{// title
				TDWrapper td = tr.addTd();
				td.addClassName("uel-item-exptitle");
				String title = t.getTitle();
				AnchorWI ar = this.factory.create(AnchorWI.class);
				ar.setDisplayText(title);
				ar.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>(){

					@Override
					public void handle(ClickEvent t) {
						UserExpItemView.this.dispatchActionEvent(Actions.A_UEXPI_OPEN);
					}});
				td.getElement().appendChild(ar.getElement());
				ar.parent(this);//
				
			}
			{//status
				TDWrapper td = tr.addTd();
				td.addClassName("uel-item-status");
				this.statusTd = td;
				this.updateStatus(t.getStatus());
			}
			{// newMessageCount

				TDWrapper td = tr.addTd();
				td.addClassName("uel-item-count");
				td.addClassName("uel-item-msgcount");

				long msgs = um.getNewMessageCount();
				if (msgs > 0) {
					td.addClassName("gt0");
				}

				String html = "<span class='count'>" + msgs + "</span><span class='info'>MSGs</span>";
				td.getElement().setInnerHTML(html);
			}
			{// ConnectionCount, 

				TDWrapper td = tr.addTd();
				td.addClassName("uel-item-count");
				td.addClassName("uel-item-expcount");

				String text = "" + um.getConnectionCount();
				String html = "<span class='count'>" + um.getConnectionCount()
						+ "</span><span class='info'>EXPs</span>";
				td.getElement().setInnerHTML(html);
			}
		}// end of line1
			//
		{// line2
			TRWrapper tr2 = this.table.addTr();
			{// body of exp
				TDWrapper td1 = tr2.addTd();

				td1.addClassName("uel-item-expbody");
				td1.setAttribute("colspan", ""+colspanOfExpBody);//
				String body = t.getBodyAsHtml();
				td1.getElement().setInnerHTML(body);
			}
			
		}
		{// line3

			TRWrapper tr1 = this.table.addTr();
			{// image

				TDWrapper td1 = tr1.addTd();

				td1.addClassName("uel-item-expimage");
				String img = t.getImage();
				if (img != null) {
					Element image = DOM.createImg();
					image.setAttribute("src", img == null ? "" : img);
					td1.getElement().appendChild(image);
				}
			}
			{// actions

				// right
				TDWrapper td04 = tr1.addTd();
				td04.setAttribute("colspan", "" + colspanOfActions);
				td04.setAttribute("rowspan", "" + rowspanOfActions);
				this.actionsTd = td04;
				this.actionsTd.addClassName("uel-item-actions");
			}
			// td1,1
		}

		{// line4, time stamp
			// timestamp
			TRWrapper tr2 = this.table.addTr();
			{
				TDWrapper td = tr2.addTd();
				td.addClassName("uel-item-timestamp");
				String dateS = DateUtil.format(t.getTimestamp(false), false);
				td.getElement().setInnerText(dateS);
			}
		}

		ListI actions = this.factory.create(ListI.class);//
		actions.parent(this);

		ButtonI select = this.factory.create(ButtonI.class);
		select.setText(true, Actions.A_UEXPI_SELECT.toString());
		select.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				// TODO open search view?
				UserExpItemView.this.dispatchActionEvent(Actions.A_UEXPI_SELECT);
			}
		});
		select.parent(actions);
/*
		ButtonI show = this.factory.create(ButtonI.class);
		show.setText(true, Actions.A_UEXPI_OPEN.toString());
		
		show.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				// TODO open search view?
				UserExpItemView.this.dispatchActionEvent(Actions.A_UEXPI_OPEN);
			}
		});
		show.parent(actions);
*/
	}

	/**
	 *May 3, 2013
	 */
	private void updateStatus(String status) {
		// 
		String html = "<span class='status'>" + status + "</span>";
		this.statusTd.getElement().setInnerHTML(html);
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

	public void update(UserExpModel uem){
		//TODO move the init code here;
		this.updateStatus(uem.getStatus());
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
