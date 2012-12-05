/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 12, 2012
 */
package com.fs.webserver.api;

import javax.servlet.Servlet;

/**
 * @author wuzhen
 * 
 */
public interface ServletHolderI {

	public String getPath();

	public Servlet getServlet();
}
