/**
 * Jun 30, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.event;

import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class ClosingEvent extends Event {

	public static Type<ClosingEvent> TYPE = new Type<ClosingEvent>("closing");

	/** */
	public ClosingEvent(PanelWI source) {
		super(TYPE, source);
	}

	public PanelWI getPanel() {
		return (PanelWI) this.source;
	}

}
