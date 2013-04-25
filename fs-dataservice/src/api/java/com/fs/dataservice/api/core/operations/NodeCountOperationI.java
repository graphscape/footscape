/**
 *  
 */
package com.fs.dataservice.api.core.operations;

import com.fs.dataservice.api.core.result.LongResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public interface NodeCountOperationI<W extends NodeWrapper> extends
		NodeQueryOperationI<NodeCountOperationI<W>, W, LongResultI> {

}
