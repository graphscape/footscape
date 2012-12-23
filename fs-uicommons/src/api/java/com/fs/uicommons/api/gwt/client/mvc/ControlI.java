/**
 * Jun 12, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc;

import java.util.List;

import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wuzhen
 * 
 * 
 */
public interface ControlI extends UiObjectI {

	public String getName();

	public <T extends ModelI> T getModel();
	
	public ControlI model(ModelI model);

	public ControlManagerI getManager();

	public List<ActionProcessorI> getActionRequestHandlerList(String ah);

	public void actionPerformed(String a, UiResponse res);

	public void addActionProcessor(String a, ActionProcessorI ap);

	public void addActionEventHandler(String a, EventHandlerI<ActionEvent> eh);

	public <T extends ActionEvent> void addActionEventHandler(
			Event.Type<T> type, String action, EventHandlerI<T> eh);

}
