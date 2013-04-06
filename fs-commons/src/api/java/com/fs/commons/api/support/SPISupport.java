/**
 * Jun 19, 2012
 */
package com.fs.commons.api.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.config.Configuration;

/**
 * @author wu
 *         <p>
 *         active->attach->deattach->deactive;
 *         <p>
 *         active means its ready to work,the only thing is to attach to the
 *         env.
 */
public abstract class SPISupport implements SPI {

	protected String id;

	protected List<String> dependenceList;

	protected Configuration config;

	protected SPIManagerI manager;
	
	protected ContainerI container;

	public SPISupport(String id) {
		this.id = id;
		this.config = Configuration.properties(id);//
		this.dependenceList = new ArrayList<String>();// TODO

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.SPI#getSPIManager()
	 */
	@Override
	public SPIManagerI getSPIManager() {
		// TODO Auto-generated method stub
		return this.manager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.SPI#setSPIManager(com.fs.commons.api.SPIManagerI)
	 */
	@Override
	public void setSPIManager(SPIManagerI sm) {
		this.manager = sm;
		this.container = sm.getContainer();
	}

	/* */
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public List<String> getDependenceList() {

		return this.dependenceList;

	}

	@Override
	public void active(ActiveContext ac) {
		this.doActive(ac);
	}
	

	/*
	 *Apr 6, 2013
	 */
	@Override
	public void beforeShutdown(int loop) {
		// 
		this.doBeforeShutdown(loop);
	}

	public abstract void doActive(ActiveContext ac);

	protected abstract void doBeforeShutdown(int loop) ;
	

}
