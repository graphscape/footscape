/**
 *  Jan 23, 2013
 */
package com.fs.gridservice.commons.impl.test.benchmark;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.impl.test.GsCommonsTestSPI;
import com.fs.websocket.api.mock.WSClientRunner;

/**
 * @author wuzhen
 * 
 * @deprecated
 */
public class GChatBenchmark extends WSClientRunner<GChatClientWrapper> {

	private GChatClientWrapper gclient;

	public GChatBenchmark(int max, int duration) {
		this(GsCommonsTestSPI.DEFAULT_WS_URI, GChatClientWrapper.class, 1, max, duration);

	}

	protected GChatBenchmark(URI uri, Class<? extends GChatClientWrapper> wcls, int cc, int max, int duration) {
		super(null, uri, wcls, 1, cc, max, -1);

	}

	/*
	 * Jan 27, 2013
	 */
	@Override
	public void init() {
		super.init();

	}

	@Override
	protected GChatClientWrapper createClient(int idx) {
		if (idx != 0) {
			throw new FsException("should only 1 initClients");
		}
		PropertiesI<Object> pts = new MapProperties<Object>();
		pts.setProperty(GChatClientWrapper.AUTH_AT_CONNECT, true);
		pts.setProperty(GChatClientWrapper.JOIN_AT_CONNECT, true);
		String gid = "group-0";
		pts.setProperty(GChatClientWrapper.GROUPID, gid);

		String aid = "acc-0";
		PropertiesI<Object> cre = new MapProperties<Object>();
		cre.setProperty("accountId", aid);//

		pts.setProperty(GChatClientWrapper.CREDENTIAL, cre);
		// this.gclient = this.clients.createClient(true, pts);
		return this.gclient;
	}

	@Override
	protected void work(int idx) {
		int eft = this.effort.get();
		LOG.debug("work-" + eft + "..., send message to group and wait message");
		String text = "test-text-" + this.effort.get() + "";
		this.gclient.sendChatMessage(text);
		MessageI msg = this.gclient.acquireNextMessage(10, TimeUnit.SECONDS);
		LOG.debug("work-" + eft + " done");
	}

}
