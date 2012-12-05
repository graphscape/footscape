/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.expapp.operations;

import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.expapp.wrapper.Expectation;

/**
 * @author wu
 * 
 */
public interface ExpSearchOperationI extends NodeQueryOperationI<Expectation> {

	public static final String PK_EXP_ID = "expId";
	
	public static final String PK_KEYWORDS = "keywords";
	
	public ExpSearchOperationI forExp(String expUid);//

	public ExpSearchOperationI keywords(String kws);
}
