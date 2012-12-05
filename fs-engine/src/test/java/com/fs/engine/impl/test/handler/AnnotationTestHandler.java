/**
 * Jun 22, 2012
 */
package com.fs.engine.impl.test.handler;

import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.engine.api.support.HandlerSupport;

/**
 * @author wu
 * 
 */
public class AnnotationTestHandler extends HandlerSupport {

	@Override
	public void handle(HandleContextI hc) {
		super.handle(hc);
	}

	@Handle("path1")
	public void handlePathOne(RequestI req, ResponseI res) {
		String path = req.getPath();
		res.setPayload(path);//
	}

	public void handlePath2(RequestI req, ResponseI res) {
		String path = req.getPath();
		res.setPayload(path);//
	}

}
