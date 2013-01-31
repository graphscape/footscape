/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.api.gwt.client.support;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public abstract class UiHandlerSupport2<T extends MsgWrapper> extends UiHandlerSupport implements MessageHandlerI<T> {

	/**
	 * @param c
	 */
	public UiHandlerSupport2(ContainerI c) {
		super(c);
	}
	
	public MainControlI getMainControl(){
		return	this.getControl(MainControlI.class, true);
	}
	
	public CooperModelI getCooperModel(){
		return this.getMainControl().getCooperModel();
	}

}
