/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 3, 2012
 */
package com.fs.webserver.impl.jetty;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.support.ProxyContainerSupport;
import com.fs.webserver.api.ServletHolderI;

/**
 * @author wuzhen
 * 
 */
public class ServletContainer extends ProxyContainerSupport {

	protected Boolean dirty = true;
	private List<ServletHolderI> filterList;

	/**
	 * @param t
	 */
	public ServletContainer(ContainerI t, JettyWebAppImpl webs) {
		//
		super(t);

		//
	}

	/**
	 * @return the filterList
	 */
	public List<ServletHolderI> getRuleList() {
		synchronized (this.dirty) {
			if (!this.dirty) {
				return this.filterList;
			}

			List<ServletHolderI> fl = this.finder(ServletHolderI.class).find();

			ServletHolderI[] feA = fl.toArray(new ServletHolderI[fl.size()]);
			Arrays.sort(feA, new Comparator<ServletHolderI>() {

				@Override
				public int compare(ServletHolderI fe1, ServletHolderI fe2) {

					return 0;

				}
			});

			this.filterList = Arrays.asList(feA);
			this.dirty = false;
			return filterList;
		}
	}

	/*
	
	 */
	@Override
	public void addObject(SPI spi, String name, Object o) {
		synchronized (this.dirty) {
			super.addObject(spi, name, o);

			this.dirty = true;

		}
	}

}
