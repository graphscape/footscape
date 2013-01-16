/**
 *  Jan 16, 2013
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;

/**
 * @author wuzhen
 * 
 */
public class ErrorReportProxyHandler<T> implements HandlerI<T> {

	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(ErrorReportProxyHandler.class);

	private HandlerI<T> target;

	public ErrorReportProxyHandler(HandlerI<T> target) {
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.HandlerI#handle(java.lang.Object)
	 */
	@Override
	public void handle(T t) {
		try {
			this.target.handle(t);
		} catch (Throwable e) {
			LOG.error("handler error in handler:" + this.target, e);
			throw UiException.toRtE(e);
		}
	}

}
