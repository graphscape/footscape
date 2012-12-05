/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 17, 2012
 */
package com.fs.xmpps.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class FutureUtil {

	public static <X> Future<X> runInFuture(Callable<X> cl) {
		FutureTask<X> rt = new FutureTask<X>(cl);
		new Thread(rt).start();
		return rt;

	}

	public static <X> X get(Future<X> f) {
		try {
			return f.get();
		} catch (InterruptedException e) {
			throw new FsException(e);

		} catch (ExecutionException e) {
			throw new FsException(e);
		}
	}
}
