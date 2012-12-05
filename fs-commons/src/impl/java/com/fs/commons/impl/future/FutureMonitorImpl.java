/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 4, 2012
 */
package com.fs.commons.impl.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.future.FutureMonitorI;

/**
 * @author wuzhen
 * 
 */
public class FutureMonitorImpl extends ConfigurableSupport implements
		FutureMonitorI, Runnable {
	private static final Logger LOG = LoggerFactory
			.getLogger(FutureMonitorImpl.class);

	private static class Entry {

		private boolean checked;

		private Object source;

		private Future<?> future;

		private Object result;

		private Throwable exception;

		public boolean isDone() {
			return this.future.isDone();
		}

		public boolean checkDone() {

			if (!this.isDone()) {
				return false;
			}
			try {
				this.result = this.future.get();
			} catch (InterruptedException e1) {
				this.exception = e1;
			} catch (ExecutionException e1) {
				this.exception = e1.getCause();
			}
			this.checked = true;
			if (this.isError()) {
				LOG.error("future done with error:" + this, this.exception);

			} else {
				if (LOG.isTraceEnabled()) {
					LOG.trace("future done:" + this);
				}
			}
			return true;
		}

		public boolean isError() {
			return this.exception != null;
		}

		@Override
		public String toString() {
			return "done:" + this.isDone() + ",source:" + source + ",result:"
					+ result + ",exception:" + exception;
		}

	}

	private ExecutorService executor;
	private List<Entry> runningList = new ArrayList<Entry>();

	@Override
	public void addFuture(Object source, Future<?> f) {
		//
		Entry e = new Entry();
		e.source = source;
		e.future = f;
		this.runningList.add(e);
		//
	}

	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		this.executor = Executors.newSingleThreadExecutor();
		Future<?> f = this.executor.submit(this);
		//
		this.addFuture(this, f);

	}

	@Override
	public void run() {
		//
		while (true) {
			List<Entry> doneL = new ArrayList<Entry>();

			for (int i = 0; i < this.runningList.size(); i++) {
				Entry e = this.runningList.get(i);
				if (e.checkDone()) {
					doneL.add(e);
				}
			}
			for (Entry e : doneL) {
				this.runningList.remove(e);//
			}

			try {
				// TODO opertimize
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOG.error("", e);
			}

		}
		//
	}
}
