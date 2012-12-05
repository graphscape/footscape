/**
 * Jun 19, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiFilterI;

/**
 * @author wu
 * 
 */
public abstract class UiFilterSupport implements UiFilterI {

	/** */
	public UiFilterSupport() {
		super();

	}

	/*
	
	 */
	@Override
	public void filter(final Context fc,
			final UiCallbackI<UiResponse, Object> cb) {

		this.filterRequest(fc);

		UiFilterI f = this.next(fc);

		if (f == null) {// last

			this.onResponse(fc, cb);// NOTE,STOP here.

		} else {
			UiCallbackI<UiResponse, Object> cb2 = new UiCallbackI<UiResponse, Object>() {

				@Override
				public Object execute(UiResponse t) {
					//
					UiFilterSupport.this.onResponse(fc, cb);
					return null;
					//
				}

			};
			f.filter(fc, cb2);
		}
	}

	protected UiFilterI next(Context fc) {
		UiFilterI rt = fc.next();
		return rt;
	}

	protected abstract void filterRequest(Context fc);

	protected abstract void filterResponse(Context fc);

	protected void onResponse(Context fc, UiCallbackI<UiResponse, Object> cb) {
		this.filterResponse(fc);// Note first call filter
		cb.execute(fc.getResponse());//
	}

}
