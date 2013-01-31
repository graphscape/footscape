package com.fs.uicommons.api.gwt.client.mvc;

import com.fs.uicore.api.gwt.client.core.UiObjectI;

public interface ControlManagerI extends UiObjectI {

	public ControlManagerI addControl(ControlI c);

	public <T extends ControlI> T getControl(Class<T> cls, String name, boolean force);

	public <T extends ControlI> T getControl(Class<T> cls, boolean force);

}
