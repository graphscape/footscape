/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 12, 2012
 */
package com.fs.commons.api.util;

import com.fs.commons.api.iterator.IteratorI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.IteratorSupport;

/**
 * @author wuzhen
 * 
 */
public class SuperClassIterator extends IteratorSupport<Class> implements
		IteratorI<Class> {

	private Class clazz;

	private Class[] interfaces;

	private Class superClass;

	private int nextIdx = 0;

	private SuperClassIterator currentIt;

	public SuperClassIterator(Class cls) {
		this.clazz = cls;
		this.interfaces = cls.getInterfaces();
		this.superClass = cls.getSuperclass();
	}

	@Override
	public boolean hasNext() {
		//

		return (this.nextIdx > -1 && this.nextIdx < this.interfaces.length)
				|| (this.superClass != null && this.nextIdx == -1);

	}

	private Class getNextClass() {
		if (this.nextIdx > -1 && this.nextIdx < this.interfaces.length) {
			return this.interfaces[this.nextIdx];
		} else if (this.nextIdx == this.interfaces.length) {
			return this.superClass;
		}
		return null;
	}

	@Override
	public Class next() {
		//
		Class rt = null;
		if (this.currentIt == null) {
			rt = this.getNextClass();

			if (rt == null) {// has no next class
				this.nextIdx = 0;// start iterator recursive
			} else {
				this.nextIdx++;
				return rt;
			}

		}
		if (this.currentIt != null) {
			if (this.currentIt.hasNext()) {
				return this.currentIt.next();
			} else {
				this.nextIdx++;
				this.currentIt = null;
			}
		}

		if (this.currentIt == null) {
			Class itc = this.getNextClass();//
			if (itc == null) {// no more elemtn
				throw new FsException("No more element,call hasnext first");
			}
			//
			this.currentIt = new SuperClassIterator(itc);
		}

		return this.currentIt.next();
		//
	}
}
