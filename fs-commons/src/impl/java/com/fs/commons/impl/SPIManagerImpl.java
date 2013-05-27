/**
 * Jun 19, 2012
 */
package com.fs.commons.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.event.BeforeActiveEvent;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.StringProperties;

/**
 * @author wu
 * 
 */
public class SPIManagerImpl implements SPIManagerI {

	private static final Logger LOG = LoggerFactory.getLogger(SPIManagerImpl.class);

	public static final int S_INIT = 0;

	public static final int S_RUNNING = 1;

	public static final int S_SHUTINGDOWN = 10;

	public static final int S_SHUTDOWN = 20;

	private ContainerI container;

	private List<SPI> spiList;

	private int status = S_INIT;

	private int maxShutdownLoop = 100;

	public SPIManagerImpl() {
		this.container = new ContainerImpl();
		this.spiList = new ArrayList<SPI>();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				SPIManagerImpl.this.shutdownHook();
			}
		});
	}

	@Override
	public void load(String res) {
		if (this.status != S_INIT) {
			throw new FsException("status:" + this.status + " must be init before load.");
		}

		StringProperties pw = StringProperties.load(res, true);

		for (int i = 0;; i++) {

			String key = "SPI." + i;
			String ckey = key + ".class";
			String ikey = key + ".id";

			Class<? extends SPI> cls = pw.getPropertyAsClass(ckey, false);
			if (cls == null) {
				break;
			}
			String id = pw.getPropertyWithDefault(ikey, cls.getName());

			this.add(id, cls);
		}
		this.container.attach();//
		this.status = S_RUNNING;

	}

	private void shutdownHook() {
		if (this.status == S_SHUTINGDOWN || this.status == S_SHUTDOWN) {
			return;
		}

		if (this.status == S_INIT) {

			LOG.warn("init may failed,cannot shutdown.");
			return;
		}
		this.shutdown();
	}

	@Override
	public void shutdown() {
		if (this.status != S_RUNNING) {
			throw new FsException("status:" + this.status + " must be running for shutdown.");
		}
		this.status = S_SHUTINGDOWN;
		try {
			this.doShutdown();
		} finally {
			this.status = S_SHUTDOWN;
		}
	}

	public void doShutdown() {
		this.log("spi manager shutting down...");
		for (int i = 0; i < this.maxShutdownLoop; i++) {
			for (int j = this.spiList.size() - 1; j >= 0; j--) {

				SPI spi = this.spiList.get(j);
				spi.beforeShutdown(i);
			}
		}

		this.log("spi manager shut down.");
	}

	public void log(String msg) {
		System.out.println("INFO " + new Date() + SPIManagerImpl.class.getName() + " " + "" + msg);
	}

	@Override
	public void add(String id) {
		Class<? extends SPI> cls = ClassUtil.forName(id);
		this.add(id, cls);
	}

	/* */
	@Override
	public void add(String id, Class<? extends SPI> cls) {
		if (null != this.getSpi(id, false)) {
			throw new FsException("duplicated spi:" + id + ",cls:" + cls);
		}
		this.log("start	spi:" + id + ",cls:" + cls);
		SPI s = ClassUtil.newInstance(cls, new Class[] { String.class }, new Object[] { id });
		s.setSPIManager(this);//
		this.assertDependenceList(s);

		this.spiList.add(s);// register all this spi.
		// SPI itself should not be in container.
		// this.container.addObject(s, id, s);
		ActiveContext ac = new ActiveContext(this.container, s);
		new BeforeActiveEvent(s).dispatch(this.container);
		s.active(ac);//
		this.log("		done spi:" + id + ",cls:" + cls);

	}

	protected void assertDependenceList(SPI spi) {
		List<String> missing = new ArrayList<String>();
		for (String did : spi.getDependenceList()) {
			SPI dSPI = this.getSpi(did, false);
			if (dSPI == null) {
				missing.add(did);
			}
		}

		if (!missing.isEmpty()) {
			throw new FsException("spi:" + spi.getId() + " cannot active for not founding dependence:"
					+ missing);
		}
	}

	@Override
	public SPI getSpi(String id, boolean force) {
		for (SPI spi : this.spiList) {
			if (spi.getId().equals(id)) {
				return spi;
			}
		}
		if (force) {
			throw new FsException("no spi with id:" + id);
		}
		return null;
	}

	/**
	 * @return the container
	 */
	public ContainerI getContainer() {
		return container;
	}

}
