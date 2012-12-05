/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 2, 2012
 */
package com.fs.uicore.api.gwt.client.core;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.support.MapProperties;

/**
 * @author wuzhen
 * 
 */
public interface UiFilterI {
	public static class Context extends MapProperties<Object> {

		private List<UiFilterI> filterList = new ArrayList<UiFilterI>();

		private int current = -1;

		private UiRequest request;

		private UiResponse response;

		public Context(UiRequest req, UiResponse res, List<UiFilterI> fl) {
			this.request = req;
			this.response = res;
			this.filterList = fl;
		}

		public void add(UiFilterI i) {
			this.filterList.add(i);
		}

		//
		// public UiFilterI previous() {
		//
		// if (this.current < 0) {
		// return null;
		// }
		//
		// UiFilterI rt = this.filterList.get(this.current);
		//
		// this.current--;
		//
		// return rt;
		// }

		public UiFilterI next() {

			int idx = this.current + 1;

			if (idx > this.filterList.size() - 1) {
				return null;
			}

			UiFilterI rt = this.filterList.get(idx);
			this.current = idx;

			return rt;
		}

		/**
		 * @return the request
		 */
		public UiRequest getRequest() {
			return request;
		}

		public UiResponse getResponse() {
			return this.response;
		}

	}

	public void filter(Context fc, UiCallbackI<UiResponse, Object> cb);

}
