/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 4, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.panel;

import com.fs.uicommons.api.gwt.client.widget.event.ClosingEvent;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtClickHandler;
import com.fs.uicore.api.gwt.client.support.ObjectElementHelper;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 *         <p>
 *         Panel is a simple container.
 * 
 */
public class AbstractPanelWImpl extends LayoutSupport implements PanelWI{
	
	protected ObjectElementHelper close;
	
	/**
	 * @param ele
	 */
	public AbstractPanelWImpl(ContainerI c, String name, Element ele) {
		super(c, name, ele);
		this.close = this.helpers.addHelper("Close", DOM.createDiv());
		this.close.getElement().setInnerText("X");
		this.close.getElement().addClassName("button");
		this.close.addGwtHandler(ClickEvent.getType(), new GwtClickHandler() {

			@Override
			protected void handleInternal(ClickEvent evt) {
				AbstractPanelWImpl.this.onClose();
			}
		});
		
		ele.appendChild(this.close.getElement());//
		this.setClosable(false);
	}
	
	/**
	 * 
	 */
	protected void onClose() {
		new ClosingEvent(this).dispatch();
	}

	@Override
	public void setClosable(boolean b) {
		this.close.setVisible(b);
	}
}
