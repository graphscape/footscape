/**
 *  Nov 28, 2012
 */
package com.fs.dataservice.api.core.support;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.result.VoidResultI;

/**
 * @author wuzhen
 * 
 */
public class VoidResult extends ResultSupport<VoidResultI, Object> implements
		VoidResultI {

	/**
	 * @param ds
	 */
	public VoidResult(DataServiceI ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

}
