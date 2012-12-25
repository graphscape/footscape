/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.expapp.operations;

import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.ResultI;

/**
 * @author wu
 *         <p>
 *         TODO remove this,add interceptor.
 */
public interface AuthedOperationI<O extends OperationI<O, T>, T extends ResultI<T, ?>>
		extends OperationI<O, T> {

	public static final String PK_SESSION_ID = "sessionId";

	public O sessionId(String id);

}
