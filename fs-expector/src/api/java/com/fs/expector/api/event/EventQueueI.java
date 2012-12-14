/**
 *  Dec 14, 2012
 */
package com.fs.expector.api.event;

/**
 * @author wuzhen
 * 
 */
public interface EventQueueI<E extends EventI> {

	public String getName();

	public void offer(E e);

	public E take();

}
