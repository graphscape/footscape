package com.fs.uicommons.impl.gwt.client.frwk.loading;

import com.fs.uicommons.api.gwt.client.frwk.LoadingModelI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;
import com.fs.uicore.api.gwt.client.support.UiFilterSupport;

/**
 * This control is as the indicator for the ajax request.It will display a popup
 * label during the remote processing by add a filter to monitoring the request
 * and response.
 * 
 * @author wuzhen
 * 
 */
public class LoadingModel extends ModelSupport implements LoadingModelI {

	public static class Filter extends UiFilterSupport {

		private LoadingModel loading;

		public Filter(LoadingModel ld) {
			this.loading = ld;
		}

		@Override
		protected void filterRequest(Context fc) {
			this.loading.beforeRequest();
		}

		@Override
		protected void filterResponse(Context fc) {
			this.loading.afterResponse();
		}

	}

	public LoadingModel(String name) {
		super(name);

	}

	@Override
	protected void doAttach() {
		super.doAttach();

		Filter f = new Filter(this);

		this.getClient(true).addFilter(f);

	}

	protected void setRequestCounter(int value) {
		// this.model.setProperty(MK_REQUEST_COUNTER, new Integer(value));
	}

	protected int plusRequestCounter(int i) {
		int rt = this.getRequestCounter() + i;

		this.setRequestCounter(rt);

		return rt;
	}

	// TODO by a ModelWrapper class to provide this kind of operation.
	@Override
	public int getRequestCounter() {

		Integer rt = (Integer) this.getProperty(MK_REQUEST_COUNTER);
		if (rt == null) {
			return 0;
		}
		return rt;

	}

	@Override
	public void beforeRequest() {
		this.plusRequestCounter(1);
	}

	@Override
	public void afterResponse() {
		this.plusRequestCounter(-1);

	}

}
