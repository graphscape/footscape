/**
 * Jun 18, 2012
 */
package com.fs.commons.api.filter;

import com.fs.commons.api.context.support.ContextSupport;
import com.fs.commons.api.iterator.IteratorI;
import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public interface FilterI<REQ, RES> {

	public static class Context<REQ, RES> extends ContextSupport {

		private ChainI<REQ, RES> chain;

		private IteratorI<FilterI<REQ, RES>> iterator;

		private REQ request;

		private RES response;

		private FilterI.Context<REQ, RES> parent;

		public Context(REQ req, RES res, ChainI<REQ, RES> c) {
			this(req, res, c, null);
		}

		public Context(REQ req, RES res, ChainI<REQ, RES> c,
				FilterI.Context<REQ, RES> parent) {
			this.chain = c;
			this.iterator = this.chain.iterator();
			this.request = req;
			this.response = res;
			this.parent = parent;
		}

		public FilterI<REQ, RES> next(boolean force) {
			if (this.iterator.hasNext()) {
				return this.iterator.next();

			} else {
				if (force) {
					throw new FsException("no next filter");
				}
				return null;
			}

		}

		public REQ getRequest() {
			return this.request;
		}

		public RES getResponse() {
			return this.response;
		}

		public FilterI.Context<REQ, RES> getParent() {
			return parent;
		}

	}

	public void filter(Context<REQ, RES> fc);

	public int getPriority();

}
