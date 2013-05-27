/**
 *  Dec 31, 2012
 */
package com.fs.commons.api.message;

import com.fs.commons.api.service.ServiceI;

/**
 * @author wuzhen
 * 
 */
public interface MessageServiceI extends
		ServiceI<MessageI, ResponseI, MessageContext> {

	@Deprecated
	public static interface FactoryI {

		public MessageServiceI create(String name);

	}

}
