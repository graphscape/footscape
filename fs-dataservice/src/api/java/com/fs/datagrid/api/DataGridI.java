/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.api;

import java.util.List;

import com.fs.commons.api.callback.CallbackI;
import com.fs.datagrid.api.objects.DgMapI;
import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.datagrid.api.objects.DgTopicI;

/**
 * @author wuzhen
 * 
 */
public interface DataGridI {

	public List<DgObjectI> getObjectList();

	public <T extends DgObjectI> List<T> getObjectList(Class<T> cls);

	public <T extends DgObjectI> List<T> getObjectList(Class<T> cls, String name);

	public <T extends DgObjectI> T getObject(Class<T> cls, String name,
			boolean force);

	public void forEachObject(CallbackI<DgObjectI, Boolean> cb);

	public <T> DgQueueI<T> getQueue(String name);

	public <K, V> DgMapI<K, V> getMap(String name);

	public <K, V, W> DgMapI<K, W> getMap(String name, Class<W> wrapperClass);

	public <T> DgTopicI<T> getTopic(String name);

	public void destroyAll();

}
