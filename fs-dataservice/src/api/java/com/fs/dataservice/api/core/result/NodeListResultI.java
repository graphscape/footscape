/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package com.fs.dataservice.api.core.result;

import java.util.List;

import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.ResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public interface NodeListResultI<R extends ResultI<R, List<T>>, T extends NodeI>
		extends ListResultI<R, T> {

	public <X extends NodeWrapper> List<X> list(Class<X> class1);

}
