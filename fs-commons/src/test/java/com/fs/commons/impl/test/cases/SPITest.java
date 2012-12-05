/**
 * Jun 15, 2012
 */
package com.fs.commons.impl.test.cases;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.commons.impl.test.TestSPI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class SPITest extends TestBase {
	public abstract static class TestingSPI extends SPISupport {
		public boolean active;

		/**
		 * @param id
		 */
		public TestingSPI(String id) {
			super(id);

		}

		/*
		
		 */
		@Override
		public void active(ActiveContext ac) {
			this.active = true;
		}

		/*
		
		 */
		@Override
		public void deactive(ActiveContext ac) {
			this.active = false;
		}

	}

	public static class TestSPI1 extends TestingSPI {

		/** */
		public TestSPI1(String id) {
			super(id);

		}

	}

	public static class TestSPI2 extends TestingSPI {

		/** */
		public TestSPI2(String id) {
			super(id);

		}

	}

	public void testSPI() {
		String spi1 = TestSPI1.class.getName();
		String spi2 = TestSPI2.class.getName();

		this.sm.add(spi1);
		this.sm.add(spi2);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				SPITest.this.shutting();
			}

		});
		// TODO
	}

	public void shutting() {
		System.out.println("shutting down.");
	}

}
