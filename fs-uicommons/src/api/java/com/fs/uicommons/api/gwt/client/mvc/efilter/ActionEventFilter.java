/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.efilter;

import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.Type;
import com.fs.uicore.api.gwt.client.efilter.SimpleEventFilter;

/**
 * @author wu
 * 
 */
public class ActionEventFilter extends SimpleEventFilter {

	private String action;

	/**
	 * @param et
	 * @param srcCls
	 */
	public ActionEventFilter(String action) {
		this(ActionEvent.TYPE, action);
	}

	public ActionEventFilter(Type<? extends ActionEvent> type, String action) {
		super(type);
		this.action = action;
	}

	@Override
	public <T extends Event> T filter(Event e) {
		if (null == super.filter(e)) {
			return null;
		}
		ActionEvent ae = (ActionEvent) e;
		if (this.action != null && !ae.getAction().equals(this.action)) {
			return null;
		}

		return (T) ae;
	}
}
