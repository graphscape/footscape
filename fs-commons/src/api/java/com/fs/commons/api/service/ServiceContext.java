/**
 *  Dec 31, 2012
 */
package com.fs.commons.api.service;

/**
 * @author wuzhen
 * 
 */
public class ServiceContext<REQ, RES> {

	private REQ request;
	private RES response;

	public ServiceContext(REQ req, RES res) {
		this.request = req;
		this.response = res;
	}

	public REQ getRequest() {
		return this.request;
	}

	public RES getResponse() {
		return this.response;
	}

}
