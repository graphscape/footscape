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

	/**
	 * @param ele
	 */
	public TabWImpl(String name, PanelWI panel, StackWI.ItemModel sitem,
			TabberWImpl tabber) {
		super(name, DOM.createDiv());
		this.panel = panel;
		this.tabber = tabber;
		this.stackItem = sitem;

		this.addGwtHandler(
				com.google.gwt.event.dom.client.ClickEvent.getType(),
				new ClickHandler() {

					@Override
					public void onClick(
							com.google.gwt.event.dom.client.ClickEvent event) {
						TabWImpl.this.onGwtClick(event);
					}//
				});
	}

	private void onGwtClick(com.google.gwt.event.dom.client.ClickEvent event) {
		this.select();
	}

	// this not impact the tabber,only impact this widget itself
	// please call select,it will connected with tabber.
	public void setSelected(boolean sel) {
		this.model.setValue(TabWI.MK_SELECTED, sel);

		this.getElementWrapper().addAndRemoveClassName(sel,
				"position-selected", "position-unselected");
		
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
	protected void processModelDefaultValue(ValueWrapper vw) {
		String text = this.name;
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
		// return this.model
		// .getProperty(Boolean.class, MK_SELECTED, Boolean.FALSE);

		return this.model.getValue(Boolean.class, MK_SELECTED, Boolean.FALSE);
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
