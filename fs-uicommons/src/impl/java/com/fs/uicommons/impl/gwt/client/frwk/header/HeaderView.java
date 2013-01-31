/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.header;

import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderViewI;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.bar.BarWidgetI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class HeaderView extends SimpleView implements HeaderViewI {

	private BarWidgetI itemList;

	/**
	 * @param ctn
	 */
	public HeaderView(String name, ContainerI ctn, HeaderModelI hm) {
		super(name, ctn, hm);
		this.itemList = this.factory.create(BarWidgetI.class);
		this.itemList.parent(this);//
	}

	public void doAttach() {
		super.doAttach();
	}

	public void addItem(Path path) {
		this.itemList.addItem(BarWidgetI.P_RIGHT, new ItemView(this.getContainer(), path));
	}

}
