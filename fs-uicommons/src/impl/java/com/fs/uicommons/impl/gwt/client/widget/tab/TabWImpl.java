/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.tab;

import com.fs.uicommons.api.gwt.client.widget.event.SelectEvent;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.stack.StackWI;
import com.fs.uicommons.api.gwt.client.widget.support.WidgetSupport;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class TabWImpl extends WidgetSupport implements TabWI {

	protected TabberWImpl tabber;

	protected PanelWI panel;

	protected StackWI.ItemModel stackItem;

	protected boolean selected;

	/**
	 * @param ele
	 */
	public TabWImpl(ContainerI c, String name, PanelWI panel, StackWI.ItemModel sitem, TabberWImpl tabber) {
		super(c, name, DOM.createDiv());
		this.panel = panel;
		this.tabber = tabber;
		this.stackItem = sitem;

		this.addGwtHandler(com.google.gwt.event.dom.client.ClickEvent.getType(), new ClickHandler() {

			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				TabWImpl.this.onGwtClick(event);
			}//
		});
		this.setText(name);
	}

	private void onGwtClick(com.google.gwt.event.dom.client.ClickEvent event) {
		this.select();
	}

	// this not impact the tabber,only impact this widget itself
	// please call select,it will connected with tabber.
	public void setSelected(boolean sel) {
		this.selected = sel;

		this.getElementWrapper().addAndRemoveClassName(sel, "position-selected", "position-unselected");

		new SelectEvent(this, sel).dispatch();//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.tab.TabWI#show()
	 */
	@Override
	public void select() {

		this.tabber._select(this.name);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.tab.TabWI#getPanel()
	 */
	@Override
	public PanelWI getPanel() {

		return this.panel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.tab.TabWI#getName()
	 */
	@Override
	public String getName() {

		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.support.WidgetBase#doUpdate(com.fs.uicore
	 * .api.gwt.client.core.ModelI)
	 */
	@Override
	public void setText(String text) {
		if (this.isSelected()) {
			text += "*";// TODO other way to show the selected tab.
		}
		this.element.setInnerText(text);//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.tab.TabWI#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * @return the stackItem
	 */
	public StackWI.ItemModel getStackItem() {
		return stackItem;
	}

	@Override
	public void addSelectEventHandler(EventHandlerI<SelectEvent> eh) {
		this.addHandler(SelectEvent.TYPE, eh);
	}

}
