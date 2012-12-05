/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 12, 2012
 */
package com.fs.webserver.api.support;

import javax.servlet.Servlet;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.webserver.api.ServletHolderI;

/**
 * @author wuzhen
 * 
 */
public class ServletHolderSupport extends ConfigurableSupport implements
		ServletHolderI {

	private String path;

	private Servlet servlet;

	public String getPath() {
		//
		return this.path;
		//
	}

	public Servlet getServlet() {
		//
		return this.servlet;
		//
	}

	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		this.path = this.config.getProperty("path", true);
		Class cls = this.config.getPropertyAsClass("servlet.class");
		Servlet obj = (Servlet) ClassUtil.newInstance(cls);
		this.servlet = obj;
		String cfgId = this.config.getProperty("servlet.config");
		ac.activitor().object(obj).cfgId(this.config.getId() + "." + cfgId)
				.active();
		//
	}
}