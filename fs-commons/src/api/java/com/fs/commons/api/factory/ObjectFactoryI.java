/**
 *  Dec 28, 2012
 */
package com.fs.commons.api.factory;

/**
 * @author wuzhen
 * 
 */
public interface ObjectFactoryI<T> {

	public <X extends T> X create(Class<X> cls);

	public <X extends T> X create(Class<X> cls, String cfgId);

	public <X extends T> void regsiterCreator(Class<X> cls, ObjectCreatorI<X> oc);

}
