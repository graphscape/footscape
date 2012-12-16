/**
 *  Dec 14, 2012
 */
package com.fs.expector.api.events;

import com.fs.expector.api.data.EventGd;

/**
 * @author wuzhen
 * 
 */
public interface MemberEventQueueI<E extends EventGd> {

	public String getName();

	public void offer(E e);

	public E take();

}
