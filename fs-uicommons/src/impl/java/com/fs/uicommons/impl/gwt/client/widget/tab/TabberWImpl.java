/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.tab;

import java.util.List;

import com.fs.uicommons.api.gwt.client.widget.event.ClosingEvent;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.stack.StackItemI;
import com.fs.uicommons.api.gwt.client.widget.stack.StackWI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class TabberWImpl extends LayoutSupport implements TabberWI {

	private StackWI stack;

	private TabWI first;

	private TabberLayout layout;
	
	private boolean isClosable;

	/**
	 * @param ele
	 */
	public TabberWImpl(ContainerI c, String name, UiPropertiesI<Object> pts) {
		super(c, name, DOM.createTable(), pts);
		boolean vertical = (Boolean) this.getProperty(TabberWI.PK_IS_VERTICAL, Boolean.FALSE);
		this.isClosable = (Boolean) this.getProperty(TabberWI.PK_IS_CLOSABLE, Boolean.FALSE);

		if (vertical) {
			this.layout = new VerticalTabberLayout(this.element);
		} else {
			this.layout = new HorizentalTabberLayout(this.element);
		}

		this.stack = this.factory.create(StackWI.class);

		this.child(this.stack);//

	}

	@Override
	protected void onAddChild(Element pe, ElementObjectI cw) {
		if (cw instanceof StackWI) {
			this.layout.setStack((StackWI) cw);
		} else if (cw instanceof TabWI) {
			this.layout.addTab((TabWI) cw);
		}
	}

	@Override
	protected void onRemoveChild(Element ele, WidgetI cw) {
		if (cw instanceof StackWI) {
			// should not here
		} else if (cw instanceof TabWI) {
			this.layout.removeTab((TabWI) cw);
		}
	}

	@Override
	public void remove(Path path) {
		// remove tabthis.
		TabWI t = this.getTab(path, true);
		t.parent(null);
		// remove panel
		this.stack.remove(path);
	}

	public List<TabWI> getTabList() {
		return this.getChildList(TabWI.class);
	}

	/**
	 * 
	 */
	@Override
	public TabWI addTab(Path name) {
		return this.addTab(name, null, false);

	}

	@Override
	public TabWI addTab(final Path name, WidgetI content, boolean sel) {

		TabWI old = this.getTab(name, false);
		if (old != null) {
			throw new UiException("duplicated:" + name + ",in tabber:" + this);
		}

		PanelWI pw = this.factory.create(PanelWI.class);
		pw.setClosable(this.isClosable);
		pw.addHandler(ClosingEvent.TYPE, new EventHandlerI<ClosingEvent>() {

			@Override
			public void handle(ClosingEvent t) {
				TabberWImpl.this.remove(name);
			}
		});
		StackItemI sitem = this.stack.insert(name, pw, sel);//

		if (content != null) {
			pw.child(content);
		}

		TabWImpl rt = new TabWImpl(this.container, name.toString(), pw, content, sitem, this);// TODO
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
	public TabWI addTab(Path name, WidgetI content) {
		return this.addTab(name, content, true);
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
	public TabWI getTab(Path name, boolean force) {

		return this.getChild(TabWI.class, name.toString(), force);
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
