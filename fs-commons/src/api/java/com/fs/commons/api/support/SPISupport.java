/**
 * Jun 19, 2012
 */
package com.fs.commons.api.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.SPI;

/**
 * @author wu
 * 
 */
public abstract class SPISupport implements SPI {

	protected String id;

	protected List<String> dependenceList;

	public SPISupport(String id) {
		this.id = id;
		this.dependenceList = new ArrayList<String>();// TODO
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

}
