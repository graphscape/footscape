/**
 *  Dec 28, 2012
 */
package com.fs.commons.api.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.service.HandlerI;

/**
 * @author wuzhen
 * 
 */
public class CollectionHandler<T> implements HandlerI<T> {

	private List<HandlerI<T>> handlers;

	public CollectionHandler() {
		this.handlers = new ArrayList<HandlerI<T>>();
	}

	public int size() {
		return this.handlers.size();
	}

	public void addHandler(HandlerI<T> h) {
		this.handlers.add(h);
	}

	@Override
	public void handle(T sc) {
		for (HandlerI<T> h : this.handlers) {
			h.handle(sc);
		}
	}

}
