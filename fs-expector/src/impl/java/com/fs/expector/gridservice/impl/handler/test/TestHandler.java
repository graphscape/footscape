/**
 *  Feb 6, 2013
 */
package com.fs.expector.gridservice.impl.handler.test;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;

/**
 * @author wuzhen
 * 
 */
public class TestHandler extends ExpectorTMREHSupport {

	@Handle("reset")
	public void handleInit(MessageI req, ResponseI res, MessageContext hc) {
		//this.dataService.
	}
}
