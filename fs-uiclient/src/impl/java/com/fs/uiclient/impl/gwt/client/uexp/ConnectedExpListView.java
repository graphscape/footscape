/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.uexp.ExpConnect;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class ConnectedExpListView extends ViewSupport {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp msglist

	// connected exp list
	protected ListI list;

	protected Map<String, ExpConnect> map2;

	protected String expId;

	/**
	 * @param ctn
	 */
	public ConnectedExpListView(ContainerI ctn, String expId) {
		super(ctn, "cexp", DOM.createDiv());
		this.setProperty(ListI.PK_LIST_ITEM_CLASSNAME, "cexplist-td");
		this.expId = expId;

		// connected exp list.
		this.list = this.factory.create(ListI.class);
		// NOTE set the td's cname.
		this.list.parent(this);
		this.list.getElement().addClassName("cexp-list");

		this.map2 = new HashMap<String, ExpConnect>();

	}

	public void addOrUpdateConnected(ExpConnect ec) {
		//
		String cid = ec.getConnectId();
		ExpConnect old = this.map2.get(cid);
		if (old != null) {
			return;
		}
		ConnectedExpView l = new ConnectedExpView(this.container, ec);
		l.getElement().addClassName("cexp-item");
		l.parent(this.list);

		this.map2.put(cid, ec);

	}

}
