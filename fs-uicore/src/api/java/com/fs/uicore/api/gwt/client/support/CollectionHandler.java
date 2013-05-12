/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.HandlerI;

/**
 * @author wu
 * 
 */
public class CollectionHandler<T> implements HandlerI<T> {

	protected List<HandlerI> handlers = new ArrayList<HandlerI>();

	public void addHandler(HandlerI<? extends T> h) {
		this.handlers.add(h);
	}

	public int size() {
		return this.handlers.size();
	}
	
	public int cleanAll(){
		int rt = this.handlers.size();
		this.handlers.clear();
		return rt;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(T t) {
		for (HandlerI h : this.handlers) {
			h.handle(t);
		}
	}

}
