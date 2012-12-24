/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.impl.gwt.client.exps.item.ExpItemControl;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class ExpSearchControl extends ControlSupport implements
		ExpSearchControlI {

	/**
	 * @param name
	 */
	public ExpSearchControl(String name) {
		super(name);

		this.addActionProcessor(ExpSearchModelI.A_SEARCH, new SearchAP());
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();
		// if this is active,then active the UE list mvc firstly.
		// listen to the UserExpListModelI.selectedId
		MainControlI mc = this.getManager()
				.getControl(MainControlI.class, true);
		Mvc uel = mc.getLazyObject(MainControlI.LZ_UE_LIST, true);

		UserExpListModelI uelM = uel.getModel();
		uelM.addValueHandler(UserExpListModelI.L_SELECTED_EXP_ID,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ExpSearchControl.this.onUserExpSelected(e);
					}
				});
		// listen to the page number
		this.getModel().addValueHandler(ExpSearchModelI.L_PAGENUMBER,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ExpSearchControl.this.onPageNumber(e);
					}
				});

	}

	protected void onPageNumber(ModelValueEvent e){
		this.triggerSearchAction();
	}
	protected void onUserExpSelected(ModelValueEvent e) {
		String expId = (String) e.getValue();
		this.getModel().setValue(ExpSearchModelI.L_EXP_ID, expId);
		// do search
		this.triggerSearchAction();
	}
	
	protected void triggerSearchAction(){
		
		this.triggerAction(ExpSearchModelI.A_SEARCH);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof ExpItemModel) {
			this.processChildExpItemModelAdd((ExpItemModel) cm);
		}
	}

	/**
	 * Oct 20, 2012
	 */
	private void processChildExpItemModelAdd(ExpItemModel cm) {
		// control it

		ExpItemControl c = new ExpItemControl("expItem");

		c.model(cm);
		this.getManager().addControl(c);//

	}

}
