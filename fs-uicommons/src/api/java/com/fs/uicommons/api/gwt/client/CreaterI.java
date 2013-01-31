/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.api.gwt.client;

import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wuzhen
 * 
 */
public interface CreaterI<T> {

	public T create(ContainerI ct);

}
