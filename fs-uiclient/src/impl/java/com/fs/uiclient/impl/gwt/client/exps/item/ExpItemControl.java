/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps.item;

import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class ExpItemControl extends ControlSupport {

	/**
	 * @param name
	 */
	public ExpItemControl(String name) {
		super(name);
		this.localMap.put(ExpItemModel.A_COPER, true);
		this.addActionEventHandler(ExpItemModel.A_COPER, new CooperAP());
	}

	/*
	 *Oct 20, 2012
	 */
	@Override
	protected void doModel(ModelI cm) {
		// 
		super.doModel(cm);
		
	}
	
	

}
