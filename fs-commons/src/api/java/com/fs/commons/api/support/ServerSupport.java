/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.commons.api.support;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.lang.State;
import com.fs.commons.api.server.ServerI;

/**
 * @author wu
 * 
 */
public abstract class ServerSupport extends ConfigurableSupport implements ServerI {

	protected State statue = ServerI.UNKNOW;

	/*
	 * Dec 11, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

		this.start();//

	}

	public void start() {
		this.start(false);
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void start(boolean strict) {
		if (this.isState(ServerI.RUNNING)) {

			if (strict) {
				throw new FsException("already started");
			} else {
				return;
			}
		}

		if (this.isState(ServerI.STARTING)) {
			if (strict) {
				throw new FsException("already starting");
			} else {
				return;
			}

		}

		this.setState(STARTING);
		try {
			this.doStart();
			this.setState(RUNNING);
		} finally {
		}
	}

	protected void setState(State s) {
		this.statue = s;
	}

	protected abstract void doStart();

	@Override
	public void shutdown() {

		this.shutdown(false);
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void shutdown(boolean strict) {
		if (!this.isState(ServerI.RUNNING)) {
			if (strict) {
				throw new FsException("not running");
			} else {
				return;
			}
		}

		this.setState(SHUTINGDOWN);
		try {

			this.doShutdown();
			this.setState(ServerI.SHUTDOWN);
		} finally {

		}

	}

	protected abstract void doShutdown();

	/*
	 * Dec 17, 2012
	 */
	@Override
	public State getState() {
		//
		return this.statue;
	}

	/*
	 * Dec 17, 2012
	 */
	@Override
	public boolean isState(State... ss) {
		//
		for (State s : ss) {
			if (this.statue.equals(s)) {
				return true;
			}
		}
		return false;
	}

}
