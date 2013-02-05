/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 15, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.support;

import com.fs.uicommons.api.gwt.client.AdjusterI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.support.ElementObjectSupport;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wuzhen
 *         <p>
 *         Note: Adjuster is not a WidgetI
 *         <p>
 *         TODO extends a UiElementSupport,add a common interface: UiElementI
 */

public class AdjusterImpl extends ElementObjectSupport implements AdjusterI {

	public AdjusterImpl(ContainerI c, String name) {
		super(c, name, DOM.createDiv());
		this.element.setInnerText(name);// TODO i18n/image
		this.element.setTitle(name);// TODO title
		this.element.addClassName("adjuster");
		this.addGwtHandler(com.google.gwt.event.dom.client.ClickEvent.getType(), new ClickHandler() {

			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				AdjusterImpl.this.onClick();
			}
		});
	}

	protected void onClick() {
		new ClickEvent(this).dispatch();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.core.AdjusterI#addClickHandler(com.fs.uicore
	 * .api.gwt.client.core.Event.HandlerI)
	 */
	@Override
	public void addClickHandler(EventHandlerI<ClickEvent> eh) {
		this.addHandler(ClickEvent.TYPE, eh);//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.core.AdjusterI#getOwner()
	 */
	@Override
	public WidgetI getOwner() {
		// TODO Auto-generated method stub
		return (WidgetI) this.parent;
	}

}
