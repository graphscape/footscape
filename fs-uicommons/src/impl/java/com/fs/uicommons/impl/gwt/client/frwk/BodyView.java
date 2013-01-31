/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.frwk.BodyModelI;
import com.fs.uicommons.api.gwt.client.frwk.BodyViewI;
import com.fs.uicommons.api.gwt.client.mvc.simple.LightWeightView;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public class BodyView extends LightWeightView implements BodyViewI{

	private TabberWI tabber;// TODO replace by a stack and a menu view.

	private Map<Path, WidgetI> itemMap = new HashMap<Path, WidgetI>();

	/**
	 * @param ctn
	 */
	public BodyView(String name, ContainerI ctn, BodyModelI mb) {
		super(name, ctn, mb);
		this.tabber = this.factory.create(TabberWI.class);//
		this.tabber.parent(this);
	}

	@Override
	public BodyModelI getModel() {
		return (BodyModelI) this.model;
	}

	/**
	 * @param t
	 */
	public <T extends WidgetI> T addItem(Path path, T w) {
		WidgetI old = this.getItem(path, false);

		if (old != null) {
			throw new UiException("already exist:" + path + ",widget:" + old);
		}
		final PanelWI prt = this.factory.create(PanelWI.class);
		String tname = path.toString();
		final TabWI sitem = this.tabber.addTab(tname, prt);
		w.parent(prt);
		this.itemMap.put(path, w);
		return w;
	}

	/**
	 * @param t
	 */
	public void select(Path path) {

		String tname = path.toString();
		final TabWI sitem = this.tabber.getTab(tname, true);
		sitem.select();
	}

	/**
	 * @param b
	 */
	public <T extends WidgetI> T getItem(Path path, boolean force) {
		WidgetI rt = this.itemMap.get(path);
		if (rt == null && force) {
			throw new UiException("not found item in body view,path:" + path);
		}
		return (T) rt;
	}

	public <T extends WidgetI> T getOrCreateItem(Path path, com.fs.uicommons.api.gwt.client.CreaterI<T> crt) {
		T rt = this.getItem(path, false);
		if (rt != null) {
			return rt;
		}
		rt = crt.create(this.getContainer());
		this.addItem(path, rt);
		return rt;
	}

}
