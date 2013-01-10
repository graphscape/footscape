/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.event;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 * 
 */
public class ActionEvent extends Event {

	public static final Type<ActionEvent> TYPE = new Type<ActionEvent>("action");

	/**
	 * @param src
	 * @param code
	 */
	public ActionEvent(ControlI control, Path p) {
		super(TYPE, control, p);
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return this.getPath().getName();
	}

	public UiClientI getClient(boolean force) {
		return this.getSource().getClient(force);
	}

	public ControlManagerI getManager() {
		return this.getClient(true).getChild(ControlManagerI.class, true);
	}

	public <T extends ModelI> T findModel(Class<T> cls, boolean force) {
		return this.getClient(true).getRootModel().find(cls, force);
	}

	public <T extends ControlI> T getControl(Class<T> cls) {
		return this.getManager().getChild(cls, true);
	}

}
