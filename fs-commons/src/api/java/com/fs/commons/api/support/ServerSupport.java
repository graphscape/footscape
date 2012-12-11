/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.commons.api.support;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.server.ServerI;

/**
 * @author wu
 *
 */
public abstract class ServerSupport extends ConfigurableSupport implements ServerI{

	protected boolean started;
	
	protected boolean starting;
	
	/*
	 *Dec 11, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();
		
		this.start();//
	
	}

	/*
	 *Dec 11, 2012
	 */
	@Override
	public void start() {
		if(this.started){
			throw new FsException("already started");
		}
		
		if(this.starting){
			throw new FsException("already starting");
		}
		
		this.starting = true;
		try{
			this.doStart();
			this.started = true;
		}finally{
			this.starting = false;
		}
	}
	
	protected abstract void doStart();

	/*
	 *Dec 11, 2012
	 */
	@Override
	public void shutdown() {
		if(!this.started){
			throw new FsException("not started");
		}
		try{
			this.doShutdown();
			this.started = false;
		}finally{
			
		}
		
	}
	
	protected abstract void doShutdown();

	
}
