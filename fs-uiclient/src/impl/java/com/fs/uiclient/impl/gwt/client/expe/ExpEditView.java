/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 19, 2012
 */
package com.fs.uiclient.impl.gwt.client.expe;

import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class ExpEditView extends FormsView implements ManagableI {

	private ManagedModelI managed;

	/**
	 * @param ctn
	 */
	public ExpEditView(String name, ContainerI ctn) {
		super(name, ctn);
	}

	@Override
	protected void doModel(ModelI model) {
		super.doModel(model);
		this.model.addValueHandler(ExpEditModelI.L_ISOPEN,
				new HandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ExpEditView.this.onOpen(e);
					}
				});
	}

	protected void onOpen(ModelValueEvent e) {
		this.managed.select(e.getValue(Boolean.FALSE));// open this view.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI.ManagableI
	 * #getManager()
	 */
	@Override
	public String getManager() {
		return BossModelI.M_POPUP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI.ManagableI
	 * #
	 * setManaged(com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI
	 * .ManagedModelI)
	 */
	@Override
	public void setManaged(ManagedModelI mgd) {
		this.managed = mgd;
	}

}
