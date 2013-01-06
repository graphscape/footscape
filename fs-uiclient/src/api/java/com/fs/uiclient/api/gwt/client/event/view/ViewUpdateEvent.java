/**
 *  Jan 5, 2013
 */
package com.fs.uiclient.api.gwt.client.event.view;

import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.event.ModelUpdateEvent;

/**
 * @author wuzhen
 *         <p>
 *         This event is raised on user exp model, when it have received one
 *         incoming cr(cooper request).
 * @see UserExpModel
 * @see UserExpView
 */
public class ViewUpdateEvent extends Event {
	public static final Type<ViewUpdateEvent> TYPE = new Type<ViewUpdateEvent>(
			ModelUpdateEvent.TYPE);

	private String key;

	private Object value;

	public ViewUpdateEvent(ViewI m) {
		this(m, null, null);
	}

	/**
	 * @param m
	 * @param loc
	 * @param vw
	 */
	public ViewUpdateEvent(ViewI m, String key, Object value) {
		super(TYPE, m);
		this.key = key;
		this.value = value;
	}

	public ViewI getView() {
		return (ViewI) this.source;
	}

}
