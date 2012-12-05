/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.fs.uicore.api.gwt.client;

import java.util.Map;

/**
 * @author wu
 * 
 */
public interface SimpleValueDeliverI<S, T> extends ModelI.ValueDeliverI {

	public static interface ValueConverterI<X, Y> {
		public Y convert(X s);
	}
	
	public static interface ValueSourceI<X>{
		public X getValue();
		public void setValue(X x);
	}

	public SimpleValueDeliverI<S, T> mapValue(Map<S, T> map);

	public SimpleValueDeliverI<S, T> mapValue(S s, T t);

	public SimpleValueDeliverI<S, T> mapDefault(T t);

	public SimpleValueDeliverI<S, T> mapDefault(ValueConverterI<S, T> vc);

	public SimpleValueDeliverI<S, T> mapDefaultDirect();
}
