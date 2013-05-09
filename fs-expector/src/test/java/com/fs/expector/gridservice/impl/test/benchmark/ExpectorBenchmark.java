/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 28, 2013
 */
package com.fs.expector.gridservice.impl.test.benchmark;

import com.fs.expector.gridservice.impl.test.ExpectorGsTestSPI;
import com.fs.webcomet.api.WebCometComponents;

/**
 * @author wu
 * 
 */
public class ExpectorBenchmark {

	public static void main(String[] args) {

		new ExpSearchBenchmark(WebCometComponents.AJAX, ExpectorGsTestSPI.DEFAULT_AJAX_URI, 1000, 100,
				100000, 20).start();
	}
}
