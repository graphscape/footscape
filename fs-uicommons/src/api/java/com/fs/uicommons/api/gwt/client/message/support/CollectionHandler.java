/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.message.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.HandlerI;

/**
 * @author wu
 * 
 */
public class CollectionHandler<T> implements HandlerI<T> {

	protected List<HandlerI<T>> handlers = new ArrayList<HandlerI<T>>();

	public void addHandler(HandlerI<T> h) {
		this.handlers.add(h);
	}

	public int size() {
		return this.handlers.size();
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(T t) {
		for (HandlerI<T> h : this.handlers) {
			h.handle(t);
		}
	}

}