/**
 *  Nov 30, 2012
 */
package com.fs.uicommons.impl.gwt.client.i18n;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.i18n.I18nResolverI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.commons.Holder;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wuzhen
 * 
 */
public class I18nResolverImpl extends UiObjectSupport implements I18nResolverI {

	/**
	 * @param c
	 */
	public I18nResolverImpl(ContainerI c) {
		super(c);
		
	}

	protected Map<String, Holder<String>> cache;

	@Override
	public void resolve(final String key, final UiCallbackI<String, String> callback) {

		if (this.cache == null) {
			this.refresh(key, callback);
			return;
		}

		Holder<String> value = this.cache.get(key);
		String vs = value == null ? null : value.getTarget();
		callback.execute(vs);
		return;

	}

	protected void refresh(final String key, final UiCallbackI<String, String> callback) {

		UiClientI client = this.getClient(true);

		client.getEndpoint().sendMessage(new MsgWrapper(Path.valueOf("/i18n/refresh")));
	}

	/**
	 * @param t
	 * @param key
	 * @param callback
	 */
	protected void onResoved(UiResponse t, String key, UiCallbackI<String, String> callback) {
		this.cache = new HashMap<String, Holder<String>>();

		ObjectPropertiesData opd = t.getPayload("resources", true);

		for (String k : opd.keyList()) {
			String value = (String) opd.getProperty(k);

			this.cache.put(k, new Holder<String>(value));

		}

	}

	/*
	 *Feb 27, 2013
	 */
	@Override
	public String resolve(String key) {
		// 
		return null;
	}

}
