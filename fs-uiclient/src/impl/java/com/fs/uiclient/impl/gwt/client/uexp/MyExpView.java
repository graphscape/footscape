/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uiclient.api.gwt.client.uexp.ExpConnect;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class MyExpView extends ViewSupport implements MyExpViewI {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp list

	// message list
	protected ListI list;

	protected ListI connected;

	protected Map<String, ExpMessage> map;

	protected Map<String, ExpConnect> map2;

	protected String expId;

	/**
	 * @param ctn
	 */
	public MyExpView(ContainerI ctn, String expId) {
		super(ctn, "myexp", DOM.createDiv());

		this.list = this.factory.create(ListI.class);
		this.list.parent(this);
		this.map = new HashMap<String, ExpMessage>();

		this.connected = this.factory.create(ListI.class);
		this.connected.parent(this);
		this.map2 = new HashMap<String, ExpConnect>();

	}

	/*
	 * Mar 6, 2013
	 */
	@Override
	public void addOrUpdateMessage(ExpMessage msg) {
		//
		String id = msg.getId();
		ExpMessage msg2 = this.map.get(id);
		if (msg2 != null) {
			return;//
		}
		ExpMessageView ev = ExpMessageView.createViewForMessage(this.container, msg);
		ev.parent(this.list);
		this.map.put(id, msg);

	}

	/*
	 * Mar 10, 2013
	 */
	@Override
	public void addOrUpdateConnected(ExpConnect exp) {
		//
		String cid = exp.getConnectId();
		ExpConnect ec = this.map2.get(cid);
		if (ec != null) {
			return;
		}
		LabelI l = this.factory.create(LabelI.class);
		l.setText("connect:" + exp);
		l.parent(this.connected);

		this.map2.put(cid, exp);

	}
}
