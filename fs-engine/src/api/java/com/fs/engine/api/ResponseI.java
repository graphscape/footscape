/**
 * Jun 11, 2012
 */
package com.fs.engine.api;

import com.fs.commons.api.value.ErrorInfos;

/**
 * @author wuzhen
 * 
 */
public interface ResponseI extends RRContextI {

	public ErrorInfos getErrorInfos();

	public void assertNoError();
}
