/**
 * Jul 13, 2012
 */
package com.fs.webserver.impl.jetty;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.support.ProxyContainerSupport;

/**
 * @author wu
 * 
 */
public class WebResourceContainer extends ProxyContainerSupport {

	private JettWebAppImpl jw;

	/** */
	public WebResourceContainer(ContainerI t, JettWebAppImpl jw) {
		super(t);
		this.jw = jw;
	}

	/*
	
	 */
	@Override
	public void addObject(SPI spi, String name, Object o) {
		super.addObject(spi, name, o);

	}

}
