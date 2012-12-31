/**
 *  Dec 31, 2012
 */
package com.fs.commons.api.service;

import com.fs.commons.api.HasContainerI;
import com.fs.commons.api.struct.Path;

/**
 * @author wuzhen
 * 
 */
public interface DispatcherI<T> extends HasContainerI {

	public static interface FactoryI {

		public <T> DispatcherI<T> create(String name);

	}

	public void dispatch(Path p, T ctx);

	public void addHandler(Path p, HandlerI<T> h);

	public void addHandler(Path p, boolean strict, HandlerI<T> h);

	public void addHandler(String cfgId, Path p, HandlerI<T> h);

	public void addHandler(String cfgId, Path p, boolean strict, HandlerI<T> h);

	public void addHandler(String cfgId);

	public void addHandlers(String cfgId, String[] names);

}
