/**
 *  Jan 23, 2013
 */
package com.fs.gridservice.commons.impl.test.benchmark;

import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.impl.test.GsCommonsTestSPI;
import com.fs.websocket.api.mock.WSClientRunner;

/**
 * @author wuzhen
 * 
 * 
 */
public class GChatBenchmark2 extends WSClientRunner<GChatClientWrapper> {

	protected AtomicInteger nextGid;

	protected AtomicInteger nextAid;

	/**
	 * @param uri
	 * @param wcls
	 * @param cc
	 * @param max
	 * @param rate
	 * @param duration
	 */
	public GChatBenchmark2(int cc, int max, int duration) {
		this(GsCommonsTestSPI.DEFAULT_WS_URI, GChatClientWrapper.class, cc, max, duration);

	}

	public GChatBenchmark2(URI uri, Class<? extends GChatClientWrapper> wcls, int cc, int max, int duration) {
		super(uri, wcls, cc, max, duration);
		this.nextGid = new AtomicInteger();
		this.nextAid = new AtomicInteger();

	}

	/*
	 * Jan 27, 2013
	 */
	@Override
	public void init() {
		super.init();

	}

	@Override
	protected void work() {

		LOG.debug("tryOpenOrCloseClient,size:" + this.clients.size());
		if (this.concurrent > this.clients.size()) {
			PropertiesI<Object> pts = new MapProperties<Object>();
			int idx = this.nextGid.incrementAndGet();
			String gid = "group-" + idx;
			String aid = "acc-" + this.nextAid.incrementAndGet();
			pts.setProperty("groupId", gid);

			PropertiesI<Object> cre = new MapProperties<Object>();
			cre.setProperty("accountId", aid);//
			pts.setProperty("auth", true);
			pts.setProperty("credential", cre);

			GChatClientWrapper mc = this.clients.createClient(true, pts);

		} else {
			this.clients.removeClient(true);
		}

	}

}
