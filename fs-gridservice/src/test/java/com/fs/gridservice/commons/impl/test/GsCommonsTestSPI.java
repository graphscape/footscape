/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.test;

import java.net.URI;
import java.net.URISyntaxException;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.SPISupport;
import com.fs.gridservice.commons.impl.test.mock.server.MockAuthProvider;

/**
 * @author wuzhen
 * 
 */
public class GsCommonsTestSPI extends SPISupport {

	public static final URI DEFAULT_WS_URI;
	
	public static final URI DEFAULT_AJAX_URI;

	static {
		try {
			DEFAULT_WS_URI = new URI("ws://localhost:8080/wsa/default");
			DEFAULT_AJAX_URI = new URI("http://localhost:8080/aja/default");
			
		} catch (URISyntaxException e) {
			throw new FsException(e);
		}

	}

	/**
	 * @param id
	 */
	public GsCommonsTestSPI(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.SPISupport#doActive(com.fs.commons.api.
	 * ActiveContext)
	 */
	@Override
	public void doActive(ActiveContext ac) {
		MockAuthProvider ap = new MockAuthProvider();
		ac.getContainer().addObject(this, "MOCK_AP", ap);
	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {

	}

}
