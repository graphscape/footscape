/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps.item;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.util.DateUtil;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class ExpItemView extends SimpleView {

	protected TableWrapper table;

	ExpItemModel model;

	/**
	 * @param ctn
	 */
	public ExpItemView(String name, ContainerI ctn,ExpItemModel ei) {

		super(ctn, name);
		this.model = ei;
		this.addAction(Actions.A_EXPS_COOPER);//
		//
		if (this.table != null) {
			this.table.getElement().removeFromParent();//
		}

		this.table = new TableWrapper();
		this.body.appendChild(this.table.getElement());

		// first line
		{
			// td 0,0-1-2
			TRWrapper tr0 = this.table.addTr();
			TDWrapper td0 = tr0.addTd();
			td0.addClassName("icon");
			td0.setAttribute("rowspan", "4");
			ElementWrapper image = new ElementWrapper(DOM.createImg());
			image.setAttribute("src", ei.getIconDataUrl());
			td0.append(image);

		}
		//
		{
			TRWrapper tr1 = this.table.addTr();
			TDWrapper td1 = tr1.addTd();

			td1.addClassName("expBody");
			td1.setAttribute("colspan", "1");//
			td1.getElement().setInnerText(ei.getExpBody());
			// td1,1
		}

		{
			TRWrapper tr2 = this.table.addTr();
			TDWrapper td = tr2.addTd();
			td.addClassName("timestamp");
			String dateS = DateUtil.format(ei.getTimestamp(), false);
			td.getElement().setInnerText(dateS);

		}

		{
			TRWrapper tr = this.table.addTr();
			TDWrapper td = tr.addTd();
			td.getElement().setInnerText(ei.getActivityId());

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
