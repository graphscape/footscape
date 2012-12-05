/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 4, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.panel;

import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 *         <p>
 *         Panel is a simple container.
 * 
 */
public class PanelWImpl extends AbstractPanelWImpl implements PanelWI {

	/**
	 * @param ele
	 */
	public PanelWImpl(String name) {
		super(name,DOM.createDiv());
	}

}
