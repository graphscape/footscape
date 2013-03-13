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
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class MyExpView extends ViewSupport implements MyExpViewI {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp msglist

	protected ListI outer;

	protected LabelI body;
	
	protected ListI middle;
	

	// message msglist
	protected ListI msglist;

	protected ListI connected;

	protected Map<String, ExpMessage> map;

	protected Map<String, ExpConnect> map2;

	protected String expId;

	protected boolean isNew = true;

	/**
	 * @param ctn
	 */
	public MyExpView(ContainerI ctn, String expId) {
		super(ctn, "myexp", DOM.createDiv());

		this.outer = this.factory.create(ListI.class);
		this.outer.parent(this);
		
		this.body = this.factory.create(LabelI.class);
		this.body.parent(this.outer);
		
		UiPropertiesI<Object> pts = new MapProperties<Object>();
		pts.setProperty(ListI.PK_IS_VERTICAL, Boolean.FALSE);
		
		this.middle = this.factory.create(ListI.class, pts);
		this.middle.parent(this.outer);

		this.msglist = this.factory.create(ListI.class);
		this.msglist.parent(this.middle);
		this.map = new HashMap<String, ExpMessage>();

		this.connected = this.factory.create(ListI.class);
		this.connected.parent(this.middle);
		this.map2 = new HashMap<String, ExpConnect>();

	}
	
	@Override
	public void setMyExp(String body){
		this.body.setText(body);
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
		ev.parent(this.msglist);
		this.map.put(id, msg);

	}

	/*
	 * Mar 10, 2013
	 */
	@Override
	public void addOrUpdateConnected(ExpConnect ec) {
		//
		String cid = ec.getConnectId();
		ExpConnect old = this.map2.get(cid);
		if (old != null) {
			return;
		}
		ConnectedExpView l = new ConnectedExpView(this.container, ec);

		l.parent(this.connected);

		this.map2.put(cid, ec);

	}
}
