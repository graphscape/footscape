/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps.item;

import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.util.DateUtil;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class ExpItemView extends SimpleView {

	protected TableWrapper table;

	/**
	 * @param ctn
	 */
	public ExpItemView(String name, ContainerI ctn, ExpItemModel m) {
		super(name, ctn, m);

	}

	@Override
	public ExpItemModel getModel() {
		return (ExpItemModel) this.model;
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	protected void doModel(ModelI model) {
		super.doModel(model);
		model.addCommitProcessor(new UiCallbackI<ExpItemModel, Object>() {

			@Override
			public Object execute(ExpItemModel t) {
				ExpItemView.this.onModelCommit(t);
				return null;
			}
		});

	}

	/**
	 * Dec 2, 2012
	 */
	protected void onModelCommit(ExpItemModel t) {
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
			image.setAttribute("src", t.getIconDataUrl());
			td0.append(image);

		}
		//
		{
			TRWrapper tr1 = this.table.addTr();
			TDWrapper td1 = tr1.addTd();

			td1.addClassName("expBody");
			td1.setAttribute("colspan", "1");//
			td1.getElement().setInnerText(t.getExpBody());
			// td1,1
		}

		{
			TRWrapper tr2 = this.table.addTr();
			TDWrapper td = tr2.addTd();
			td.addClassName("timestamp");
			String dateS = DateUtil.format(t.getTimestamp(), false);
			td.getElement().setInnerText(dateS);

		}

		{
			TRWrapper tr = this.table.addTr();
			TDWrapper td = tr.addTd();
			td.getElement().setInnerText(t.getActivityId());

		}

	}

	/*
	 * Dec 1, 2012
	 */
	@Override
	protected void processModelValue(Location loc, ValueWrapper vw) {
		super.processModelValue(loc, vw);

	}

}
