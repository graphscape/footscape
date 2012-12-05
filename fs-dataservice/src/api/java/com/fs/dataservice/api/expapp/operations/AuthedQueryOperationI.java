/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.expapp.operations;

import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 *         <p>
 *         TODO add ProxyQuerySupport
 */
public interface AuthedQueryOperationI<W extends NodeWrapper> extends
		NodeQueryOperationI<W> {

	public AuthedQueryOperationI<W> loginId(String id);

}
