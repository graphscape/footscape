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
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.wrapper.PropertiesWrapper;

/**
 * @author wu
 * 
 */
public class SPIManagerImpl implements SPIManagerI {

	private static final Logger LOG = LoggerFactory
			.getLogger(SPIManagerImpl.class);

	public static final int S_INIT = 0;

	public static final int S_RUNNING = 1;

	public static final int S_SHUTINGDOWN = 10;

	public static final int S_SHUTDOWN = 20;

	private ContainerI container;

	private List<SPI> spiList;

	private int status = S_INIT;

	public SPIManagerImpl() {
		ContainerI.FactoryI tf = new ContainerImpl.FactoryImpl();
		this.container = tf.newContainer();
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
			throw new FsException("status:" + this.status
					+ " must be init before load.");
		}

		PropertiesWrapper pw = PropertiesWrapper.load(res, true);

		for (int i = 0;; i++) {

			String key = "SPI." + i;
			String ckey = key + ".class";
			String ikey = key + ".id";

			Class<? extends SPI> cls = pw.getPropertyAsClass(ckey, false);
			if (cls == null) {
				break;
			}
			String id = pw.getProperty(ikey, cls.getName());

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
			throw new FsException("status:" + this.status
					+ " must be running for shutdown.");
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
		while (true) {
			SPI spi = this.getLast(false);
			if (spi == null) {
				break;
			}
			this.remove(spi.getId());//
		}
		this.container.dettach();
		this.log("spi manager shut down.");
	}

	public void log(String msg) {
		System.out.println("INFO " + new Date()
				+ SPIManagerImpl.class.getName() + " " + "" + msg);
	}

	@Override
	public void add(String id) {
		Class<? extends SPI> cls = ClassUtil.forName(id);
		this.add(id, cls);
	}

	/* */
	@Override
	public void add(String id, Class<? extends SPI> cls) {
		if (null != this.getById(id, false)) {
			throw new FsException("duplicated spi:" + id + ",cls:" + cls);
		}
		this.log("start	spi:" + id + ",cls:" + cls);
		SPI s = ClassUtil.newInstance(cls, new Class[] { String.class },
				new Object[] { id });
		this.assertDependenceList(s);

		this.spiList.add(s);// register all this spi.
		// SPI itself should not be in container.
		// this.container.addObject(s, id, s);
		ActiveContext ac = new ActiveContext(this.container, s);

		s.active(ac);//
		this.log("		done spi:" + id + ",cls:" + cls);

	}

	protected void assertDependenceList(SPI spi) {
		List<String> missing = new ArrayList<String>();
		for (String did : spi.getDependenceList()) {
			SPI dSPI = this.getById(did, false);
			if (dSPI == null) {
				missing.add(did);
			}
		}

		if (!missing.isEmpty()) {
			throw new FsException("spi:" + spi.getId()
					+ " cannot active for not founding dependence:" + missing);
		}
	}

	public SPI getById(String id, boolean force) {
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

	public SPI getLast(boolean force) {
		if (this.spiList.isEmpty()) {
			if (force) {
				throw new FsException("no spi in list");
			}
			return null;
		}
		return this.spiList.get(this.spiList.size() - 1);
	}

	/* */
	@Override
	public void remove(String id) {
		this.log("removing spi:" + id + "");
		SPI lspi = this.getLast(true);
		if (!lspi.getId().equals(id)) {
			throw new FsException("not support to remove none-last spi.");
		}
		this.spiList.remove(lspi);
		//

		//
		ActiveContext ac = new ActiveContext(this.container, lspi);
		lspi.deactive(ac);
		this.log("		done remove spi:" + id);
	}

	/**
	 * @return the container
	 */
	public ContainerI getContainer() {
		return container;
	}

}
