/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.header;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderViewI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.bar.BarWidgetI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class HeaderView extends SimpleView implements HeaderViewI {

	private BarWidgetI itemList;

	private Map<Path, ItemView> itemViewMap = new HashMap<Path, ItemView>();

	/**
	 * @param ctn
	 */
	public HeaderView(ContainerI c) {
		super(c, "header");
		this.itemList = this.factory.create(BarWidgetI.class);
		this.itemList.parent(this);//

	}

	public ItemView getOrCreateItem(Path path) {

		ItemView rt = this.itemViewMap.get(path);
		if (rt != null) {
			return rt;
		}
		rt = new ItemView(this.getContainer(), path);

		this.itemList.addItem(BarWidgetI.P_RIGHT, rt);
		this.itemViewMap.put(path, rt);
		return rt;
	}

	public void addItem(Path path) {

		if (path.size() == 1) {
			ItemView rt = this.getOrCreateItem(path);
		} else if (path.size() == 2) {
			ItemView rt = this.getOrCreateItem(path.getParent());

			rt.getOrAddMenuItem(path.getName());

		} else {
			throw new UiException("not support deeper menu for path:" + path);
		}

	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public void setItemDisplayText(Path path, boolean toloc, String text) {
		if (toloc) {
			text = this.getClient(true).localized(text);
		}
		ItemView iv = this.getOrCreateItem(path);
		iv.setDisplayText(false, text);
	}

}
