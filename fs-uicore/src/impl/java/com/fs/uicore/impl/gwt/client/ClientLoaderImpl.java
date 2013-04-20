/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.impl.gwt.client;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.ClientLoader;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu Test support.
 */
public class ClientLoaderImpl extends ClientLoader{

	private static Map<String, GwtSPI.Factory> CACHE = new HashMap<String, GwtSPI.Factory>();

	private Element progress;
	
	public ClientLoaderImpl(){
		BodyElement be = Document.get().getBody();
		this.progress = DOM.createDiv();
		this.progress.addClassName("loader-loading");
		this.progress.setInnerHTML("Loading<br/>");
		be.appendChild(this.progress);
	}
	
	@Override
	public GwtSPI.Factory getOrLoadClient(GwtSPI[] spis,
			EventHandlerI<Event> l) {
		
		String key = "";
		for (int i = 0; i < spis.length; i++) {
			key += spis[i] + ",";
		}

		GwtSPI.Factory rt = CACHE.get(key);
		if (rt != null) {
			return rt;
		}
		rt = loadClient(spis, l);
		CACHE.put(key, rt);//
		return rt;
	}

	@Override
	public GwtSPI.Factory loadClient(GwtSPI[] spis, EventHandlerI<Event> l) {
		
		GwtSPI.Factory factory = GwtSPI.Factory.create();

		ContainerI container = factory.getContainer();
		EventBusI eb = container.getEventBus();
		eb.addHandler(l);

		factory.active(spis);

		UiClientI client = container.get(UiClientI.class, true);

		
		//

		client.attach();// NOTE
		this.progress.addClassName("invisible");
		return factory;
	}
}
