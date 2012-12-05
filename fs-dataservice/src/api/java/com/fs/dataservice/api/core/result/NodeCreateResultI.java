/**
 *  Nov 28, 2012
 */
package com.fs.dataservice.api.core.result;

import com.fs.dataservice.api.core.ResultI;

public interface NodeCreateResultI extends ResultI<NodeCreateResultI,String> {

	public String getUid(boolean force);

}