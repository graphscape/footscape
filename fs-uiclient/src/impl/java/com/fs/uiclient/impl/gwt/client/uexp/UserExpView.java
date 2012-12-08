/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
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
	}

	protected void onModelCommit(UserExpModel t) {
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
			image.setAttribute("src", "TODO");//
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
			this.incomingCrExpId1 = td;//TODO lazy
			td.getElement().setInnerText(t.getIncomingCrId());//

		}
		{// activity
			TRWrapper tr = this.table.addTr();
			TDWrapper td = tr.addTd();
			td.getElement().setInnerText(t.getActivityId());

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.support.WidgetBase#processModelValue(com
	 * .fs.uicore.api.gwt.client.ModelI.Location,
	 * com.fs.uicore.api.gwt.client.ModelI.ValueWrapper)
	 */
	@Override
	protected void processModelValue(Location loc, ValueWrapper vw) {
		super.processModelValue(loc, vw);
		if (loc.equals(UserExpModel.L_ISSELECTED)) {
			this.processModelValueSelected(vw);
		}
	}

	@Override
	public UserExpModel getModel() {
		return (UserExpModel) this.model;
	}

	private void processModelValueSelected(ValueWrapper vw) {
		boolean sel = vw.getValue(Boolean.FALSE);

		this.elementWrapper
				.addAndRemoveClassName(sel, "selected", "unselected");

	}

}
