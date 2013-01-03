/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class FrwkModelImpl extends ModelSupport implements FrwkModelI {

	/**
	 * @param name
	 */
	public FrwkModelImpl(String name) {
		super(name);

	}

	/*
	 * Nov 23, 2012
	 */
	@Override
	public HeaderModelI getHeader() {
		//
		return this.getParent().getChild(HeaderModelI.class, true);

	}

	/*
	 * Nov 23, 2012
	 */
	@Override
	protected void doAttach() {
		//
		super.doAttach();

	}

}
