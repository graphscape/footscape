/**
 * Jun 29, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.basic;

import com.fs.uicommons.api.gwt.client.widget.basic.ImageI;
import com.fs.uicommons.api.gwt.client.widget.support.WidgetSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class ImageImpl extends WidgetSupport implements ImageI {

	/** */
	public ImageImpl(ContainerI c, String name) {
		super(c, name, DOM.createImg());
	}

	@Override
	public void setSrc(String src) {
		this.element.setAttribute("src", src);
	}

}
