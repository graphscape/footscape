/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.impl.gwt.client;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.ClientLoader;
import com.fs.uicore.api.gwt.client.Console;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.event.ClientConnectLostEvent;
import com.fs.uicore.api.gwt.client.event.ClientStartFailureEvent;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;

/**
 * @author wu Test support.
 */
public class ClientLoaderImpl extends ClientLoader {

	private static Map<String, GwtSPI.Factory> CACHE = new HashMap<String, GwtSPI.Factory>();

	protected Element table;

	protected ElementWrapper tbody;

	protected Element element;

	protected int size;

	protected int maxSize = 1000;

	private UiCallbackI<Object, Boolean> handler;

	private boolean show;

	private int retry;

	public ClientLoaderImpl() {
		Element root = RootWImpl.getRootElement();

		this.element = DOM.createDiv();
		root.appendChild(this.element);//
		this.element.addClassName("loader");
		this.table = DOM.createTable();
		this.tbody = new ElementWrapper(DOM.createTBody());
		this.table.appendChild(this.tbody.getElement());
		this.element.appendChild(this.table);
		this.handler = new UiCallbackI<Object, Boolean>() {

			@Override
			public Boolean execute(Object t) {
				//
				ClientLoaderImpl.this.println(t);//
				return null;
			}
		};
		this.show();
	}

	private void println(Object msg) {
		Element tr = DOM.createTR();
		DOM.appendChild(this.tbody.getElement(), tr);

		Element td = DOM.createTD();
		String text = "" + msg;
		td.setInnerText(text);
		DOM.appendChild(tr, td);

		this.size++;
		this.element.setAttribute("scrollTop", "10000px");// Scroll to
															// bottom.
		this.shrink();

	}

	public void shrink() {
		// shrink
		while (true) {
			if (this.size <= this.maxSize) {
				break;
			}
			com.google.gwt.dom.client.Element ele = this.tbody.getElement().getFirstChildElement();
			if (ele == null) {
				break;
			}
			ele.removeFromParent();
			this.size--;
		}

	}

	@Override
	public GwtSPI.Factory getOrLoadClient(GwtSPI[] spis, EventHandlerI<Event> l) {

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

		final ContainerI container = factory.getContainer();
		EventBusI eb = container.getEventBus();
		if (l != null) {
			eb.addHandler(l);
		}

		eb.addHandler(AfterClientStartEvent.TYPE, new EventHandlerI<AfterClientStartEvent>() {

			@Override
			public void handle(AfterClientStartEvent t) {
				ClientLoaderImpl.this.afterClientStart(container);
			}
		});
		eb.addHandler(ClientStartFailureEvent.TYPE, new EventHandlerI<ClientStartFailureEvent>() {

			@Override
			public void handle(ClientStartFailureEvent t) {
				//
				ClientLoaderImpl.this.onClientStartFailureEvent(container, t);
			}
		});
		eb.addHandler(ClientConnectLostEvent.TYPE, new EventHandlerI<ClientConnectLostEvent>() {

			@Override
			public void handle(ClientConnectLostEvent t) {
				//
				ClientLoaderImpl.this.onClientConnectLostEvent(container, t);
			}
		});

		factory.active(spis);

		UiClientI client = container.get(UiClientI.class, true);

		client.attach();// NOTE

		return factory;
	}

	/**
	 * Apr 21, 2013
	 */
	protected void onClientConnectLostEvent(ContainerI container, ClientConnectLostEvent t) {
		this.retry("Connection to server is lost, retry?");
	}

	protected void onClientStartFailureEvent(ContainerI container, ClientStartFailureEvent t) {
		this.retry("Client starting failed, retry?");
	}

	protected void retry(String msg) {
		// disconnected to server,
		// this.show();
		boolean rec = Window.confirm(msg);
		if (rec) {

			UrlBuilder urlB = Window.Location.createUrlBuilder();
			String uri = urlB.buildString();
			Window.Location.assign(uri);
		}
	}

	/**
	 * Apr 21, 2013
	 */
	protected void afterClientStart(ContainerI container) {
		//
		UiClientI client = container.get(UiClientI.class, true);
		this.hide();
	}

	private void show() {
		if (this.show) {
			return;
		}
		Console.getInstance().addMessageCallback(this.handler);//
		// hide the loader view.
		this.element.removeClassName("invisible");
		this.show = true;

	}

	private void hide() {
		if (!this.show) {
			return;
		}
		// not listen more message from console
		Console.getInstance().removeMessageCallback(this.handler);//
		// hide the loader view.
		this.element.addClassName("invisible");// hiden
		this.show = false;
	}
}
