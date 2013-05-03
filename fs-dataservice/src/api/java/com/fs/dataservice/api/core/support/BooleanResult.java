/**
 *  Nov 28, 2012
 */
package com.fs.dataservice.api.core.support;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.result.BooleanResultI;

/**
 * @author wuzhen
 * 
 */
public class BooleanResult extends ResultSupport<BooleanResultI, Boolean> implements BooleanResultI {

	/**
	 * @param ds
	 */
	public BooleanResult(DataServiceI ds) {
		super(ds);
	}

}
