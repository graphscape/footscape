/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import java.util.List;

import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class UserExpSelectAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public UserExpSelectAP(ContainerI c) {
		super(c);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		String expId = (String) ae.getProperty("expId", true);
		MainControlI mc = this.getControl(MainControlI.class, true);
		UserExpListViewI uelv = mc.openUeList(false);
		// model
		List<UserExpModel> ueL = uelv.getUserExpList();
		UserExpModel selected = null;
		for (UserExpModel ue : ueL) {
			boolean sel = ue.getExpId().equals(expId);
			ue.select(sel);//view update
			if(sel){
				selected = ue;				
			}
		}
		// view
		
		uelv.select(expId);//

		// call search
		ExpSearchControlI sc = this.getControl(ExpSearchControlI.class, true);
		sc.search(selected,true);
	}

}
