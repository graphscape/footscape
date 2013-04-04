/**
 * Jun 11, 2012
 */
package com.fs.commons.api.message;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.value.ErrorInfos;

/**
 * @author wuzhen
 * 
 */
public interface ResponseI extends MessageI {
	
	public MessageI getRequest();
	
}
