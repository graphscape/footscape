/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.datagrid.impl.hazelcast.codec;

import com.fs.datagrid.impl.DgCodecI;

/**
 * @author wu
 *
 */
public class PrimaryCodec<T> implements DgCodecI<T,T>{

	/*
	 *Dec 13, 2012
	 */
	@Override
	public T readData(T s) {
		// 
		return s;
	}

	/*
	 *Dec 13, 2012
	 */
	@Override
	public T writeData(T t) {
		// 
		return t;
	}


}
