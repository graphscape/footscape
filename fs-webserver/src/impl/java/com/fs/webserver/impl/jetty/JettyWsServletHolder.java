/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 12, 2012
 */
package com.fs.webserver.impl.jetty;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.eclipse.jetty.servlet.ServletHolder;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.webserver.api.ServletHolderI;

/**
 * @author wuzhen
 * 
 */
public class JettyWsServletHolder extends ConfigurableSupport implements ServletHolderI {

	private String path;

	public ServletHolder jettyHolder;

	public String getPath() {
		//
		return this.path;
		//
	}

	public Servlet getServlet() {
		//
		try {
			return this.jettyHolder.getServlet();
		} catch (ServletException e) {
			throw new FsException(e);
		}
		//
	}

	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		this.path = this.config.getProperty("path", true);
		Class cls = this.config.getPropertyAsClass("servlet.class");
		Servlet obj = (Servlet) ClassUtil.newInstance(cls);
		String cfgId = this.config.getProperty("servlet.config");
		ac.activitor().object(obj).cfgId(this.config.getId() + "." + cfgId).active();
		this.jettyHolder = new ServletHolder(obj);//

		Configuration icfg = Configuration.properties(this.configId + ".init-parameters");

		this.jettyHolder.setInitParameters(icfg.getAsMap());

		//
	}
}