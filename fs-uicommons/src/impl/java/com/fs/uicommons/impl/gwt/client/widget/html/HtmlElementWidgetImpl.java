/**
 *  
 */
package com.fs.uicommons.impl.gwt.client.widget.html;

import com.fs.uicommons.api.gwt.client.widget.html.HtmlElementWidgetI;
import com.fs.uicommons.api.gwt.client.widget.support.WidgetSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class HtmlElementWidgetImpl extends WidgetSupport implements HtmlElementWidgetI {

	/**
	 * @param c
	 * @param name
	 * @param ele
	 */
	public HtmlElementWidgetImpl(ContainerI c, String name, UiPropertiesI<Object> pts) {
		// TODO element type as parameter?
		super(c, name, DOM.createDiv(), pts);
	}

	@Override
	public void setInnerHtml(String html) {
		this.element.setInnerHTML(html);
	}

}
