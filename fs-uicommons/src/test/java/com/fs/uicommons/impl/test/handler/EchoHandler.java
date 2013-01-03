/**
 * Jun 24, 2012
 */
package com.fs.uicommons.impl.test.handler;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.HandlerSupport;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public class EchoHandler extends HandlerSupport {

	public void handleEcho(MessageI req, ResponseI res) {
		PropertiesI<Object> pts = req.getPayloads();

		res.setPayloads(pts);
	}

}
