/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.support;

import java.util.UUID;

import com.fs.gridservice.commons.api.GridedObjectI;

/**
 * @author wu
 * 
 */
public class GridedObjectSupport implements GridedObjectI {

	protected String id;
	
	

	public GridedObjectSupport() {
		this(UUID.randomUUID().toString());
	}

	public GridedObjectSupport(String id) {
		this.id = id;
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public String getId() {
		//
		return this.id;
	}

}
