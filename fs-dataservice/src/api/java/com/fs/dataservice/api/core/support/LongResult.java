/**
 *  Nov 28, 2012
 */
package com.fs.dataservice.api.core.support;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.result.LongResultI;

/**
 * @author wuzhen
 * 
 */
public class LongResult extends ResultSupport<LongResultI, Long> implements LongResultI {

	/**
	 * @param ds
	 */
	public LongResult(DataServiceI ds) {
		super(ds);
	}

}
