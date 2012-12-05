/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client.core;

/**
 * @author wuzhen
 * 
 */
public interface ListenerI<E extends Event> {

	public void onEvent(E e);

}
