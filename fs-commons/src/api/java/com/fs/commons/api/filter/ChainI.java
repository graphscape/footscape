/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 2, 2012
 */
package com.fs.commons.api.filter;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.SPI;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.iterator.IteratorI;

/**
 * @author wuzhen
 * 
 */
public interface ChainI<REQ, RES> {
	public static interface FactoryI {

		public <REQ, RES> ChainI<REQ, RES> createChain(ActiveContext ac,
				Class<REQ> cls1, Class<RES> cls2);

	}

	public void addFilter(SPI spi, String name, FilterI<REQ, RES> f);// TODO

	public IteratorI<FilterI<REQ, RES>> iterator();

	public void service(REQ req, RES res);
	
	public PopulatorI newPopulator();

}
