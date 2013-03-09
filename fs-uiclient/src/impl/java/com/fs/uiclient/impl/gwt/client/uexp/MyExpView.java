/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class MyExpView extends ViewSupport implements MyExpViewI {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp list

	protected ListI list;

	protected Map<String, ExpMessageView> map;

	protected String expId;

	/**
	 * @param ctn
	 */
	public MyExpView(ContainerI ctn, String expId) {
		super(ctn, "myexp", DOM.createDiv());

		this.list = this.factory.create(ListI.class);
		this.list.parent(this);
		this.map = new HashMap<String, ExpMessageView>();

	}

	/*
	 * Mar 6, 2013
	 */
	@Override
	public void addOrUpdateMessage(ExpMessage msg) {
		//
		String id = msg.getId();
		ExpMessageView ev = this.map.get(id);
		if (ev == null) {
			ev = ExpMessageView.createViewForMessage(this.container, msg);
			ev.parent(this.list);
		}

	}
}
