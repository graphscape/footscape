/**
 * Jun 30, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.event;

import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiData;

/**
 * @author wu
 * 
 */
public class ChangeEvent<T extends UiData> extends Event {

	public static Type<ChangeEvent<?>> TYPE = new Type<ChangeEvent<?>>();

	/** */
	public ChangeEvent(EditorI<T> source) {
		super(TYPE, source);
	}

	public EditorI<T> getEditor() {
		return (EditorI<T>) this.source;
	}

	public T getData() {
		return this.getEditor().getData();
	}

}
