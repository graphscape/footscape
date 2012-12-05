/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.core.support;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.ResultI;

/**
 * @author wu
 * 
 */
public abstract class ResultSupport<R extends ResultI<R,T>,T> extends MapProperties<Object> implements
		ResultI<R,T> {

	protected DataServiceI dataService;

	public ResultSupport(DataServiceI ds) {
		this.dataService = ds;
		this.setProperty(PK_ERRORINFOS, new ErrorInfos());
	}

	@Override
	public T get(String key, boolean force) {
		//
		return (T)this.getProperty(key, force);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public T get(boolean force) {
		//
		return (T)this.getProperty(PK_DEFAULT);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public void set(T value) {
		this.setProperty(PK_DEFAULT, value);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public ErrorInfos getErrorInfo() {
		//
		return (ErrorInfos) this.getProperty(PK_ERRORINFOS);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public boolean hasError() {
		//
		return this.getErrorInfo().hasError();
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public R assertNoError() {
		//
		if (this.hasError()) {
			throw new FsException(this.getErrorInfo().toString());
		}
		return (R)this;
	}

	/*
	 * Oct 29, 2012
	 */
	@Override
	public <X> X cast() {
		//
		return (X) this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.dataservice.api.core.ResultI#getDataService()
	 */
	@Override
	public DataServiceI getDataService() {
		return this.dataService;
	}

}
