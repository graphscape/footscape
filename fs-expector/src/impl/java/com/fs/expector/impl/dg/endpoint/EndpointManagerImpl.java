/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.endpoint;

import com.fs.expector.api.endpoint.EndpointI;
import com.fs.expector.api.endpoint.EndpointManagerI;
import com.fs.expector.impl.dg.support.DgMapManagerImplSupport;

/**
 * @author wuzhen
 * 
 */
public class EndpointManagerImpl extends
		DgMapManagerImplSupport<EndpointI, EndpointImpl> implements
		EndpointManagerI {

	public EndpointManagerImpl() {
		super(EndpointI.class, EndpointImpl.class);
	}

}
