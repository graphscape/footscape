/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 15, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public abstract class ModelValueHandler<T> implements HandlerI<ModelValueEvent> {

	private T defValue;

	public ModelValueHandler() {

	}

	public ModelValueHandler(T def) {
		this.defValue = def;
	}

	@Override
	public void handle(ModelValueEvent e) {
		Object value = e.getValue(this.defValue);

		this.handleValue((T) value);
	}

	public abstract void handleValue(T value);

}
