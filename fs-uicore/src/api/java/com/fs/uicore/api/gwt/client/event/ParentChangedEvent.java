/**
 * Jul 18, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class ParentChangedEvent extends Event {

	public static final Type<ParentChangedEvent> TYPE = new Type<ParentChangedEvent>(
			"parent-change");

	public static final String OLD_PARENT = "OLD_PARENT";
	public static final String NEW_PARENT = "NEW_PARENT";

	/** */
	public ParentChangedEvent(UiObjectI src, UiObjectI oldP, UiObjectI newP) {
		super(TYPE, src);
		this.setProperty(OLD_PARENT, oldP);
		this.setProperty(NEW_PARENT, newP);
	}

	public UiObjectI getOldParent() {
		return (UiObjectI) this.getProperty(OLD_PARENT);
	}

	public UiObjectI getNewParent() {
		return (UiObjectI) this.getProperty(NEW_PARENT);
	}

}
