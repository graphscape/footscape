/**
 * Jun 30, 2012
 */
package com.fs.uicommons.api.gwt.client.editor.support;

import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class EditorSupport<T> extends LayoutSupport implements EditorI<T> {

	protected T data;

	/** */
	public EditorSupport(ContainerI c, String name, Element ele) {
		super(c, name, ele);
		this.data = this.newData();
	}

	/* */
	@Override
	public T getData() {
		return this.data;
	}

	protected T newData() {
		return null;
	}

	/* */
	@Override
	public void input(T d) {
		this.setData(d, true);//
	}

	@Override
	public void setData(T d) {
		this.setData(d, true);//
	}

	protected void setData(T d, boolean dispatch) {
		this.data = d;
		if (dispatch) {// TODO
			new ChangeEvent(this).dispatch();
		}
	}

}
