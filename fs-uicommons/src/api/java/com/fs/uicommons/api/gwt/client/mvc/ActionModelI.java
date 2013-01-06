/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.state.State;

/**
 * @author wu
 * 
 */
public interface ActionModelI extends ModelI {

	public static final Location L_LOCAL = Location.valueOf("_local");

	public static final Location L_STATE = Location.valueOf("_state");

	public static final Location L_HIDDEN = Location.valueOf("_hidden");

	public static final State TRIGGERED = State.valueOf("triggered");

	public static final State PROCESSING = State.valueOf("processing");

	public static final State PROCESSED = State.valueOf("processed");

	public static final State NORMAL = State.valueOf("normal");

	public void trigger();

	public State getState();

	public void setState(State st);

	public ModelI getControlModel();

	public void addTriggerHandler(EventHandlerI<ModelValueEvent> ah);

	public void addProcessedHandler(EventHandlerI<ModelValueEvent> ah);

	public void processed(ErrorInfosData eis);

	public ErrorInfosData getErrorInfos();

	public void setHidden(boolean hidden);

}
