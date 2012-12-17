/**
 *  Dec 17, 2012
 */
package com.fs.commons.api.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.event.ListenerI;

/**
 * @author wuzhen
 * 
 */
public class CollectionListener<T> implements ListenerI<T> {

	private List<ListenerI<T>> childList = new ArrayList<ListenerI<T>>();;

	public void addListener(ListenerI<T> i) {
		this.childList.add(i);
	}

	@Override
	public void handle(T t) {
		for (ListenerI<T> ii : this.childList) {
			ii.handle(t);
		}
	}

}
