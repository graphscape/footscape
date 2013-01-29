/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 28, 2013
 */
package com.fs.expector.gridservice.impl.test.benchmark;

/**
 * @author wu
 * 
 */
public class ExpectorBenchmark {

	public static void main(String[] args) {
		new ExpSearchBenchmark(100, 30, 10000, 10).start();
	}
}
