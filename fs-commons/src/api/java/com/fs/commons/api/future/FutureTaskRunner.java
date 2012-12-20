/**
 *  Dec 20, 2012
 */
package com.fs.commons.api.future;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author wuzhen
 * 
 */
public class FutureTaskRunner {

	public static <T> Future<T> execute(Callable<T> c) {
		final FutureTask<T> rt = new FutureTask<T>(c);
		new Thread() {
			@Override
			public void run() {
				rt.run();
			}
		}.start();
		return rt;

	}
}
