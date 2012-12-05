package com.fs.uicommons.api.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;

public interface LoginControlI extends ControlI {

	// client should add their auth method by this method.

	public void addAuthActionProcessor(ActionProcessorI ap);

}
