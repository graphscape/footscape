/**
 * Jun 20, 2012
 */
package com.fs.engine.impl.dispatcher;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.support.ProxyContainerSupport;
import com.fs.engine.api.HandlerI;

/**
 * @author wuzhen
 * 
 */
public class HandlerContainer extends ProxyContainerSupport {

	private static final Logger LOG = LoggerFactory
			.getLogger(HandlerContainer.class);

	protected List<HandlerEntry> patternEntryList;

	protected List<HandlerEntry> defaultEntryList;

	protected Boolean dirty = true;

	public HandlerContainer(ContainerI c) {
		super(c);
	}

	private void tryPopulate(boolean force) {

		if (!force && !this.dirty) {
			return;
		}
		synchronized (this.dirty) {
			List<HandlerI> hL = this.finder(HandlerI.class).find();
			this.defaultEntryList = new ArrayList<HandlerEntry>();
			this.patternEntryList = new ArrayList<HandlerEntry>();
			for (HandlerI h : hL) {
				HandlerEntry he = new HandlerEntry(h);
				if (he.isDefault()) {
					this.defaultEntryList.add(he);
				} else {
					this.patternEntryList.add(he);
				}

			}
			this.dirty = false;
		}
	}

	public List<HandlerEntry> getByPath(String path) {
		this.tryPopulate(false);
		List<HandlerEntry> rt = new ArrayList<HandlerEntry>();
		if (path == null) {
			return rt;// NOTE
		}
		for (HandlerEntry he : this.patternEntryList) {
			if (he.isMatch(path)) {
				rt.add(he);
			}
		}
		return rt;
	}

	public List<HandlerEntry> getAllEntryList() {
		return this.patternEntryList;
	}

	/**
	 * @return the defaultEntryList
	 */
	public List<HandlerEntry> getDefaultEntryList() {
		return defaultEntryList;
	}

	/*
	
	 */
	@Override
	public void addObject(SPI spi, String name, Object o) {
		synchronized (this.dirty) {
			super.addObject(spi, name, o);
			HandlerI h = (HandlerI) o;
			this.dirty = true;
			LOG.info("add handler:" + name + ",cfg:" + h.getConfiguration());
		}
	}
}
