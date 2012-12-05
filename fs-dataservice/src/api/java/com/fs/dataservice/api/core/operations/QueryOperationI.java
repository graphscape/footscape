/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.core.operations;

import java.util.List;

import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.RowI;
import com.fs.dataservice.api.core.VisitorI;

/**
 * @author wu
 * 
 */
public interface QueryOperationI<T extends RowI> extends OperationI {
	
	public QueryOperationI<T> execute();
	
	public List<T> get();

	public void forEach(VisitorI<T> nv);

}
