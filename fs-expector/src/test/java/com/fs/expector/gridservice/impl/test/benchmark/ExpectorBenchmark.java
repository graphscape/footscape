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
		new ExpSearchBenchmark(1000, 100, 100000, 20).start();
	}
}
