/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class MyExpView extends SimpleView implements MyExpViewI {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp list

	protected ListI list;

	/**
	 * @param ctn
	 */
	public MyExpView(ContainerI ctn, String expId) {
		super(ctn, "myexp");
	}

	/*
	 * Mar 6, 2013
	 */
	@Override
	public void addOrUpdateMessage(ExpMessage msg) {
		//

	}
}
