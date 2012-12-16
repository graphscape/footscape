/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.commons.api.support;

import java.util.UUID;

import com.fs.commons.api.HasIdI;

/**
 * @author wu
 *
 */
public class HasIdSupport implements HasIdI {

	protected String id;
	
	public HasIdSupport(){
		this(UUID.randomUUID().toString());//
	}
	
	public HasIdSupport(String id){
		this.id = id; 		
	}
	/*
	 *Dec 16, 2012
	 */
	@Override
	public String getId() {
		// 
		return this.id;
	}

}
