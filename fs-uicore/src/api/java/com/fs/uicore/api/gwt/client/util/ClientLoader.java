/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.api.gwt.client.util;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.consts.UiConstants;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.ListenerI;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;

/**
 * @author wu Test support.
 */
public class ClientLoader {

	private static Map<String, GwtSPI.Factory> CACHE = new HashMap<String, GwtSPI.Factory>();

	public static GwtSPI.Factory getOrLoadClient(GwtSPI[] spis,
			HandlerI<Event> l) {
		return getOrLoadClient(spis, UiConstants.ROOTURI, l);
	}

	public static GwtSPI.Factory getOrLoadClient(GwtSPI[] spis, String rootUri,
			HandlerI<Event> l) {
		String key = "";
		for (int i = 0; i < spis.length; i++) {
			key += spis[i] + ",";
		}

		key += rootUri;

		GwtSPI.Factory rt = CACHE.get(key);
		if (rt != null) {
			return rt;
		}
		rt = loadClient(spis, l);
		CACHE.put(key, rt);//
		return rt;
	}

	public static GwtSPI.Factory loadClient(GwtSPI[] spis, HandlerI<Event> l) {
		return loadClient(spis, UiConstants.ROOTURI, l);
	}

	public static GwtSPI.Factory loadClient(GwtSPI[] spis, String rootUri,
			HandlerI<Event> l) {

		GwtSPI.Factory factory = GwtSPI.Factory.create();

		factory.active(spis);

		ContainerI container = factory.getContainer();

		UiClientI client = container.get(UiClientI.class, true);

		client.setProperty(UiClientI.ROOT_URi, rootUri);
		//
		EventBusI eb = container.get(EventBusI.class, true);

		eb.addHandler(l);

		client.attach();// NOTE

		return factory;
	}
}
