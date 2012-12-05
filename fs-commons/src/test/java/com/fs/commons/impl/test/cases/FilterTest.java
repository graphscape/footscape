/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 2, 2012
 */
package com.fs.commons.impl.test.cases;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.SPI;
import com.fs.commons.api.filter.ChainI;
import com.fs.commons.api.filter.FilterI;
import com.fs.commons.api.filter.support.FilterSupport;
import com.fs.commons.impl.test.TestSPI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class FilterTest extends TestBase {
	private static class Req {
		List<FilterI<Req, Res>> flist = new ArrayList<FilterI<Req, Res>>();

	}

	private static class Res extends Req {

	}

	private static class TestFilter extends FilterSupport<Req, Res> {

		public TestFilter(int p) {
			super(p);
		}

		@Override
		protected boolean doFilter(
				com.fs.commons.api.filter.FilterI.Context<Req, Res> fc) {
			// //
			fc.getRequest().flist.add(this);//
			fc.getResponse().flist.add(this);
			//
			return false;
			//
		}

	}

	public void test() {

		ChainI.FactoryI cf = this.container.find(ChainI.FactoryI.class);
		assertNotNull("chain factory not provided by any spi", cf);
		// TODO
		SPI spi = this.container.find(TestSPI.class, true);//
		ActiveContext ac = new ActiveContext(this.container, spi);

		ChainI<Req, Res> c = cf.createChain(ac, Req.class, Res.class);
		TestFilter tf1 = new TestFilter(2);
		TestFilter tf2 = new TestFilter(1);
		c.addFilter(spi, "filter1", tf1);
		c.addFilter(spi, "filter2", tf2);
		Req req = new Req();
		Res res = new Res();
		c.service(req, res);
		assertEquals("filter calls check failed", 2, req.flist.size());
		assertEquals("filter calls check failed", 2, res.flist.size());
		FilterI<Req, Res> actualF1 = req.flist.get(0);
		FilterI<Req, Res> actualF2 = req.flist.get(1);

		assertEquals("filter priority check failed.", tf2, actualF1);
		assertEquals("filter priority check failed.", tf1, actualF2);

	}
}
