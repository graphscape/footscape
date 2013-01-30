/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 19, 2012
 */
package com.fs.uiclient.impl.gwt.client.expe;

import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class ExpEditView extends FormsView implements ViewReferenceI.AwareI {

	private ViewReferenceI managed;

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
				new EventHandlerI<ModelValueEvent>() {

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
	 * com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI.ViewReferenceI.AwareI
	 * #
	 * setManaged(com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI
	 * .ViewReferenceI)
	 */
	@Override
	public void setViewReference(ViewReferenceI mgd) {
		this.managed = mgd;
	}

}
