/**
 * Jun 15, 2012
 */
package com.fs.engine.impl.test.rest;

import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

/**
 * @author wuzhen
 * 
 */
public class JerseyTestObject extends JerseyTest {

	public JerseyTestObject() throws Exception {
		super(config("com.fs.engine.impl.exporter.rest"));
	}

	public static AppDescriptor config(String packages) {
		WebAppDescriptor.Builder bd = new WebAppDescriptor.Builder(packages)//
				.initParam("com.sun.jersey.api.json.POJOMappingFeature", "true")//
		;
		// bd.initParam("com.sun.jersey.spi.container.ContainerRequestFilters",
		// LoggingFilter.class.getName());
		// bd.initParam("com.sun.jersey.spi.container.ContainerResponseFilters",
		// LoggingFilter.class.getName());
		WebAppDescriptor rt = bd.build();
		return rt;
	}

}
