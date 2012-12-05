/**
 * Jul 25, 2012
 */
package com.fs.engine.impl.test.handler;

import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.HandlerSupport;

/**
 * @author wu
 * 
 */
public class TestHandler4 extends HandlerSupport {

	public TestHandler4() {
		super();
	}

	//ECHO
	public void handleMethod0(RequestI req, ResponseI res) {
		res.setPayloads(req.getPayloads());//
	}
	//plus:a+b=c
	public void handleMethod1(RequestI req, ResponseI res) {
		Integer integer1 = (Integer)req.getPayload("a");
		Integer integer2 = (Integer)req.getPayload("b");
		res.setPayload("c",integer1+integer2);
	
	}
	
}
