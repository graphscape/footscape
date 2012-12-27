/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.expector.dataservice.impl.elastic.support;

import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.ResultI;
import com.fs.dataservice.api.core.support.OperationSupport;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;

/**
 * @author wu
 * 
 */
public abstract class ElasticOperationSupport<O extends OperationI<O, T>, T extends ResultI<T, ?>>
		extends OperationSupport<O, T> {

	private ElasticClientI elastic;

	/**
	 * @param ds
	 */
	public ElasticOperationSupport(T rst) {
		super(rst);
		this.elastic = (ElasticClientI) rst.getDataService();
	}

}
