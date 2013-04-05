/**
 * Jun 30, 2012
 */
package com.fs.uicommons.impl.gwt.client;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.WidgetBase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu Root widget
 */
public class DisconnectedWidgetImpl extends WidgetBase implements WidgetI {

	/** */
	public DisconnectedWidgetImpl(ContainerI c) {
		super(c, "disconnected", DOM.createDiv());

		Element ele = DOM.createLabel();
		this.element.appendChild(ele);
		ele.setInnerText("Disconnected to server.");
	}

}
