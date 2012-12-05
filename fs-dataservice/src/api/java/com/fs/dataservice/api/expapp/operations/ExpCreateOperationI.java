/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.expapp.operations;

import com.fs.dataservice.api.expapp.result.ExpCreateResultI;

/**
 * @author wu
 * 
 */
public interface ExpCreateOperationI extends
		AuthedOperationI<ExpCreateOperationI, ExpCreateResultI> {
	
	public ExpCreateOperationI expBody(String body);//

}
