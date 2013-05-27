/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 2, 2012
 */
package com.fs.commons.impl.filter;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.factory.ConfigFactoryI;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.filter.ChainI;
import com.fs.commons.api.filter.FilterI;
import com.fs.commons.api.iterator.IteratorI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.IteratorSupport;

/**
 * @author wuzhen
 * 
 */
public class ChainImpl<REQ, RES> extends ConfigurableSupport implements ChainI<REQ, RES> {

	private FilterContainer<REQ, RES> internal;

	private static class IteratorImpl<REQ, RES> extends IteratorSupport<FilterI<REQ, RES>> {

		private int next = 0;

		private List<FilterI> filterList;

		/**
		 * @param fl
		 */
		public IteratorImpl(List<FilterI> fl) {
			//
			this.filterList = fl;
			//
		}

		@Override
		public boolean hasNext() {
			//
			return this.next < this.filterList.size();
			//
		}

		@Override
		public FilterI next() {
			if (!this.hasNext()) {
				throw new FsException("no next");
			}
			return this.filterList.get(this.next++);
			//
		}

	}

	public ChainImpl() {

	}

	@Override
	public void addFilter(SPI spi, String name, FilterI<REQ, RES> f) {
		//
		this.internal.addObject(spi, name, f);// TODO

		//
	}

	@Override
	public IteratorI<FilterI<REQ, RES>> iterator() {
		//
		List<FilterI<REQ, RES>> fl = this.internal.getFilterList();
		IteratorI rt = new IteratorImpl(fl);

		return rt;
		//
	}

	@Override
	public void service(REQ req, RES res) {
		List<FilterI<REQ, RES>> feL = this.internal.getFilterList();

		FilterI.Context<REQ, RES> fc = new FilterI.Context<REQ, RES>(req, res, this);//

		fc.next(true).filter(fc);
	}

	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		ContainerI c2 = this.components.newComponent(this.spi, ContainerI.class);
		c2.parent(this.container);//
		this.internal = new FilterContainer<REQ, RES>(c2);
		//
	}

	/* */
	@Override
	public PopulatorI newPopulator() {
		ConfigFactoryI cf = this.top.find(ConfigFactoryI.class, true);

		PopulatorI rt = cf.newPopulator().container(this.internal);
		return rt;

	}

}
