/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.tab;

import java.util.List;

import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.stack.StackWI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class TabberWImpl extends LayoutSupport implements TabberWI {

	private StackWI stack;

	private Element headerTd;

	private Element middleTd;

	private Element bodyTd;

	private TabWI first;

	/**
	 * @param ele
	 */
	public TabberWImpl(ContainerI c, String name) {
		super(c, name, DOM.createTable());
		Element table = this.element;
		Element tbody = DOM.createTBody();
		DOM.appendChild(table, tbody);

		this.headerTd = this.createTrTd(tbody, "position-header");

		this.middleTd = this.createTrTd(tbody, "position-middle");

		this.bodyTd = this.createTrTd(tbody, "position-body");

		this.stack = this.factory.create(StackWI.class);
		this.child(this.stack);//

	}

	protected Element createTrTd(Element tbody, String cname) {
		Element tr = DOM.createTR();
		DOM.appendChild(tbody, tr);

		Element td = DOM.createTD();
		DOM.appendChild(tr, td);

		td.addClassName(cname);
		return td;
	}

	@Override
	protected void onAddChild(Element pe, ElementObjectI cw) {
		if (cw instanceof StackWI) {
			DOM.appendChild(this.bodyTd, cw.getElement());//
		} else if (cw instanceof TabWI) {
			this.headerTd.appendChild(cw.getElement());//
		}
	}

	public List<TabWI> getTabList() {
		return this.getChildList(TabWI.class);
	}

	/**
	 * 
	 */
	@Override
	public TabWI addTab(String name) {
		return this.addTab(name, false);

	}

	public TabWI addTab(String name, boolean sel) {

		TabWI old = this.getTab(name, false);
		if (old != null) {
			throw new UiException("duplicated:" + name + ",in tabber:" + this);
		}

		PanelWI pw = this.factory.create(PanelWI.class);

		StackWI.ItemModel sitem = this.stack.insert(pw, sel);//

		TabWImpl rt = new TabWImpl(this.container, name, pw, sitem, this);// TODO
																			// not
																			// is
																			// a
		// widget.
		// first must select
		this.child(rt);//

		if (this.first == null) {// first one
			this.first = rt;
			this.first.select();
		} else {// not first one
			if (sel) {
				rt.select();
			} else {
				this.first.select();
			}
		}

		return rt;
	}

	public TabWI getFirstTab(boolean force) {
		if (this.first == null && force) {
			throw new UiException("no tab");
		}
		return this.first;
	}

	public int getSize() {
		return this.getChildList(TabWI.class).size();

	}

	@Override
	public TabWI addTab(String name, WidgetI content) {
		return this.addTab(name, content, true);
	}

	@Override
	public TabWI addTab(String name, WidgetI content, boolean sel) {
		TabWI rt = this.addTab(name);
		rt.getPanel().child(content);
		return rt;
	}

	public void _select(String name) {
		List<TabWI> tabL = this.getChildList(TabWI.class);
		for (TabWI tb : tabL) {
			boolean sel = tb.getName().equals(name);
			TabWImpl ti = (TabWImpl) tb;
			
			ti.setSelected(sel, false);// change the tab title style.
			
			ti.getStackItem().select(sel);// pointer to stack item.

		}
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.tab.TabberWI#getTab(java.lang.
	 * String)
	 */
	@Override
	public TabWI getTab(String name, boolean force) {

		return this.getChild(TabWI.class, name, force);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.tab.TabberWI#getPanel(java.lang
	 * .String)
	 */
	@Override
	public PanelWI getPanel(String name, boolean force) {

		TabWI tb = this.getTab(name, force);

		return tb.getPanel();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.tab.TabberWI#getSelected(boolean)
	 */
	@Override
	public TabWI getSelected(boolean force) {
		List<TabWI> tl = this.getChildList(TabWI.class);
		for (TabWI t : tl) {
			if (t.isSelected()) {
				return t;
			}
		}
		if (force) {
			throw new UiException("empty or bug,there is no tab selected?");
		}
		return null;
	}

}
