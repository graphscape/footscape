/**
 * Jun 30, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.event;

import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class ChangeEvent extends Event {

	public static Type<ChangeEvent> TYPE = new Type<ChangeEvent>("change");

	/** */
	public ChangeEvent(EditorI source) {
		super(TYPE, source);
	}

	public EditorI getEditor() {
		return (EditorI) this.source;
	}

	public <T> T getData() {
		return (T) this.getEditor().getData();
	}

}
