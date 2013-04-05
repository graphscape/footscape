/**
 * Jun 30, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.support;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class BasicSupport extends WidgetSupport {

	/** */
	public BasicSupport(ContainerI c, String name, Element ele) {
		super(c, name, ele);

	}

	/* */
	@Override
	public void addChild(UiObjectI c) {
		throw new UiException("only compisite can add child.");
	}

}
