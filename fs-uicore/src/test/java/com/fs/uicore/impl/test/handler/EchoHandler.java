/**
 * Jun 24, 2012
 */
package com.fs.uicore.impl.test.handler;

import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.HandlerSupport;

/**
 * @author wu
 * 
 */
public class EchoHandler extends HandlerSupport {

	/* */
	@Override
	public void handle(HandleContextI sc) {
		
		super.handle(sc);
		
		
	}
	
	public void handleEcho(RequestI req, ResponseI res) {
		PropertiesI<Object> pts = req.getPayloads();

		res.setPayloads(pts);
	}

}
