/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.api.gwt.client.event;

import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class HeaderItemEvent extends Event {

	public static final Type<HeaderItemEvent> TYPE = new Type<HeaderItemEvent>();

	protected Path path;

	/**
	 * @param type
	 */
	public HeaderItemEvent(HeaderModelI.ItemModel hi) {
		super(TYPE, hi);
		this.path = hi.getPath();
	}

	public ItemModel getModel() {
		return (ItemModel) this.source;

	}

}
