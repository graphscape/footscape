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
public class ChangeEvent<T> extends Event {

	public static Type<ChangeEvent<?>> TYPE = new Type<ChangeEvent<?>>("change");

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
