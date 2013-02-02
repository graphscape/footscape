/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.api.gwt.client.event;

import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicore.api.gwt.client.event.ModelUpdateEvent;

/**
 * @author wu
 * @deprecated remove
 */
public class HeaderItemDisplayNameUpdateEvent extends ModelUpdateEvent {

	public static final Type<HeaderItemDisplayNameUpdateEvent> TYPE = new Type<HeaderItemDisplayNameUpdateEvent>(
			ModelUpdateEvent.TYPE, "header-item-display-name");

	/**
	 * @param type
	 */
	public HeaderItemDisplayNameUpdateEvent(HeaderModelI.ItemModel hi, String dname) {
		super(TYPE, hi, "displayName", dname);
	}

}
