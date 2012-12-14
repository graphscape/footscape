/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.commons.api.support;

import com.fs.commons.api.AttachableI;

/**
 * @author wu
 * 
 */
public abstract class AttachableSupport implements AttachableI {

	protected boolean attached;

	protected boolean attaching;

	/*
	 * Dec 11, 2012
	 */
	@Override
	public boolean isAttached() {
		//
		return this.attached;
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void attach() {
		this.attaching = true;
		try {
			this.doAttach();
			this.attached = true;
		} finally {
			this.attaching = false;
		}

	}

	/* (non-Javadoc)
	 * @see com.fs.commons.api.AttachableI#deattach()
	 */
	@Override
	public void dettach() {
		
		this.doDettach();
		this.attached = false;
	
	}
	protected abstract void doDettach();
	protected abstract void doAttach();

}
