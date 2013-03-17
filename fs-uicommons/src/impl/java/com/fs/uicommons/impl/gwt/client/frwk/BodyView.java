/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.frwk.BodyViewI;
import com.fs.uicommons.api.gwt.client.mvc.simple.LightWeightView;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.MapProperties;

/**
 * @author wu
 * 
 */
public class BodyView extends LightWeightView implements BodyViewI {

	private TabberWI tabber;// TODO replace by a stack and a menu view.

	// private Map<Path, WidgetI> itemMap = new HashMap<Path, WidgetI>();

	/**
	 * @param ctn
	 */
	public BodyView(ContainerI c) {
		super(c, "body");
		UiPropertiesI<Object> pts = new MapProperties<Object>();
		pts.setProperty(TabberWI.PK_IS_VERTICAL, Boolean.TRUE);
		pts.setProperty(TabberWI.PK_IS_CLOSABLE, Boolean.TRUE);
		pts.setProperty(TabberWI.PK_IS_REVERSE, Boolean.TRUE);

		this.tabber = this.factory.create(TabberWI.class, pts);//
		this.tabber.parent(this);
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
		final TabWI sitem = this.tabber.addTab(path, prt);
		w.parent(prt);
		// this.itemMap.put(path, w);
		return w;
	}

	/**
	 * @param t
	 */
	public void select(Path path) {

		final TabWI sitem = this.tabber.getTab(path, true);
		sitem.select();
	}

	/**
	 * @param b
	 */
	public <T extends WidgetI> T getItem(Path path, boolean force) {
		TabWI ta = this.getTabOfItem(path, false);
		if (ta == null) {
			if (force) {
				throw new UiException("no item found :" + path);
			}
			return null;
		}
		PanelWI p = (PanelWI) ta.getManaged();
		return (T) p.getChildWidgetList().get(0);//

	}

	protected TabWI getTabOfItem(Path path, boolean force) {
		TabWI ta = this.tabber.getTab(path, false);
		if (ta == null) {
			if (force) {
				throw new UiException("no item found :" + path);
			}
			return null;
		}
		return ta;
	}

	@Override
	public <T extends WidgetI> T getOrCreateItem(Path path, com.fs.uicommons.api.gwt.client.CreaterI<T> crt) {
		T rt = this.getItem(path, false);
		if (rt != null) {
			return rt;
		}
		rt = crt.create(this.getContainer());
		this.addItem(path, rt);
		return rt;
	}

	@Override
	public void setTitleOfItem(Path path, String title, boolean force) {
		TabWI tab = this.getTabOfItem(path, false);
		if (tab == null) {
			if (force) {
				throw new UiException("no item found:" + path);
			}
		}
		tab.setText(false, title);

	}

	/*
	 * Mar 16, 2013
	 */
	@Override
	public void tryCloseItem(Path path) {
		//
		
		this.tabber.remove(path);

	}
}
