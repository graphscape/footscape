/**
 * Jun 11, 2012
 */
package com.fs.uicore.impl.gwt.client;

import java.util.ArrayList;
import java.util.List;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiFilterI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.ClientStartEvent;
import com.fs.uicore.api.gwt.client.event.ErrorEvent;
import com.fs.uicore.api.gwt.client.event.ResponseEvent;
import com.fs.uicore.api.gwt.client.event.StateChangeEvent;
import com.fs.uicore.api.gwt.client.support.ContainerAwareUiObjectSupport;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.fs.uicore.impl.gwt.client.factory.JsonCodecFactoryC;
import com.fs.uicore.impl.gwt.client.filter.ErrorResponseFilter;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu TOTO rename to UiCoreI and impl.
 */
public class UiClientImpl extends ContainerAwareUiObjectSupport implements
		UiClientI {
	private String sessionId;

	private static class LasterFilter implements UiFilterI {

		private UiClientImpl client;

		public LasterFilter(UiClientImpl c) {
			this.client = c;
		}

		@Override
		public void filter(final Context fc,
				final UiCallbackI<UiResponse, Object> cb) {
			//

			UiRequest req = fc.getRequest();
			if (!req.isInit()) {
				// sessionid
				if (this.client.sessionId == null) {
					throw new UiException(
							"sessionId is null,cannot continue,please re init to get a sessionId");
				}
				req.setHeader(UiRequest.SESSION_ID, this.client.sessionId);
			}

			// if the path is relative,it will be explained to prefix with
			// context path:
			// for instance relative: x/y will be full path: /uiserver/x/y
			// for instance full path: /x/y will not change.
			String path = req.getRequestPath();
			if (!path.startsWith("/")) {// is full path
				//
				path = "/uiserver/" + path;// TODO configurable.
				req.setRequestPath(path);//
			}
			String url = this.client.getUrl();

			Resource res = new Resource(url);
			ObjectPropertiesData ds = req.getPayloads();
			CodecI codec = this.client.cf.getCodec(ds.getClass());
			JSONValue json = (JSONValue) codec.encode(ds);//

			Method m = res.post().json(json);

			// String path = req.getPath();
			// m.header(UiRequest.PATH, path);
			for (String key : req.getHeaders().keyList()) {
				String value = req.getHeader(key);
				m.header(key, value);
			}

			JsonCallback jcb = new JsonCallback() {

				@Override
				public void onFailure(Method method, Throwable exception) {
					//
					// TODO
					LasterFilter.this.onFailure(exception); //
				}

				@Override
				public void onSuccess(Method method, JSONValue response) {
					//
					LasterFilter.this.onSuccess(fc, response, cb);
					//
				}
			};
			m.send(jcb);
			//
		}

		private void onFailure(Throwable t) {
			new ErrorEvent(this.client, t).dispatch();
		}

		private void onSuccess(UiFilterI.Context fc, JSONValue res,
				UiCallbackI<UiResponse, Object> cb) {

			new ResponseEvent(this.client).dispatch();
			this.client.processResponse(fc.getResponse(), res, cb);
		}

	}

	private CodecI.FactoryI cf;

	private RootI root;

	private List<UiFilterI> filterList = new ArrayList<UiFilterI>();

	private UiPropertiesI<String> parameters;

	public UiClientImpl(RootI root) {
		this.root = root;
		this.filterList.add(new LasterFilter(this));

	}

	protected void processResponse(UiResponse res, JSONValue resJson,
			UiCallbackI<UiResponse, Object> cb) {
		try {
			CodecI cd = this.cf.getCodec(ObjectPropertiesData.class);
			ObjectPropertiesData dt = (ObjectPropertiesData) cd.decode(resJson);
			res.setPayloads(dt);//
			// TODO header?
			cb.execute(res);
		} catch (Throwable t) {
			new ErrorEvent(this, t).dispatch();
		}
	}

	@Override
	public void doAttach() {

		// TODO move to SPI.active.
		this.cf = new JsonCodecFactoryC();

		// um.setDefaultProperty(this);// TODO//

		this.addFilter(new ErrorResponseFilter(this));
		this.addHandler(ErrorEvent.TYPE, new DefaultErrorListener());
		new StateChangeEvent(this).dispatch();// TODO

		UiRequest req = new UiRequest();
		req.setRequestPath("client/init");
		req.setInit(true);//
		String locale = this.getPreferedLocale();
		req.setPayload("preferedLocale", StringData.valueOf(locale));
		this.sendRequest(req, new UiCallbackI<UiResponse, Object>() {

			@Override
			public Object execute(UiResponse t) {

				if (t.getErrorInfos().hasError()) {
					throw new UiException(t.getErrorInfos().toString());// TODO
																		// more
																		// friendly
				}

				UiClientImpl.this.onInitResponse(t);
				return null;
			}
		});

	}
	
	protected String getPreferedLocale(){
		return null;//
	}

	/**
	 * Client got the sessionId from server,client stared on. Nov 14, 2012
	 */
	protected void onInitResponse(UiResponse t) {
		StringData sd = (StringData) t.getPayloads().getProperty("sessionId");
		String sid = sd.getValue();
		if (sid == null) {
			throw new UiException("got a null sessionId");
		}
		this.sessionId = sd.getValue();
		ObjectPropertiesData opd = t.getPayload("parameters", true);//
		// parameters:
		this.parameters = new MapProperties<String>();
		for (String key : opd.keyList()) {
			StringData valueS = (StringData) opd.getProperty(key);

			this.parameters.setProperty(key, valueS.getValue());

		}

		new ClientStartEvent(this, this.sessionId).dispatch();
	}

	public String getParameter(String key, String def) {
		return this.parameters.getProperty(key, def);
	}

	public String getParameter(String key, boolean force) {
		return this.parameters.getProperty(key, force);
	}

	protected WidgetFactoryI getWidgetFactory() {
		return this.getContainer().get(WidgetFactoryI.class, true);

	}

	@Override
	public void addFilter(UiFilterI f) {
		// NOTE this may be called at the spi active time,there is no
		// LasterFilter added at that time.

		this.filterList.add(this.filterList.size() - 1, f);

	}

	private String getUrl() {

		return (String) this.getProperty(UiClientI.ROOT_URi, true);
	}

	/*
	
	 */
	@Override
	public void sendRequest(UiRequest req,
			final UiCallbackI<UiResponse, Object> cb) {

		final UiResponse rt = new UiResponse(req);
		UiFilterI.Context fc = new UiFilterI.Context(req, rt, this.filterList);
		fc.next().filter(fc, cb);// TODO

	}

	/**
	 * @return the root
	 */
	public RootI getRoot() {
		return root;
	}

	/* */
	@Override
	public void attach() {

		super.attach();
		this.root.attach();// TODO remove this call,add root as a child.

	}

	/* */
	@Override
	public void detach() {
		this.root.detach();//
		super.detach();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.UiClientI#getRootModel()
	 */
	@Override
	public ModelI getRootModel() {
		// TODO Auto-generated method stub
		return this.container.get(ModelI.class, false);//
	}

	/*
	 * Nov 26, 2012
	 */
	@Override
	public String getSessionId() {
		//
		return this.sessionId;
	}

}
