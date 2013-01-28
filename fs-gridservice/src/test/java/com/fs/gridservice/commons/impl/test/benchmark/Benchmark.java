/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 27, 2013
 */
package com.fs.gridservice.commons.impl.test.benchmark;

/**
 * @author wu
 * 
 */
public class Benchmark {

	public static void main(String[] args) throws Exception {

		new GChatBenchmark(10000, 1 * 600 * 1000).start();
		//new GChatBenchmark2(10, 100, 1 * 3600 * 1000).start();

	}

}
