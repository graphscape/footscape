/**
 * Jun 20, 2012
 */
package com.fs.commons.impl.factory;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.FinderI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.describe.DescribedI;
import com.fs.commons.api.factory.ConfigFactoryI;
import com.fs.commons.api.factory.ObjectConfigI;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * 
 */
public class PopulatorImpl implements PopulatorI {
	private SPI spi;

	private String cfgId;

	private String type;

	private boolean force;

	private ConfigFactoryI configFactory;

	private ContainerI container;

	private ActiveContext activeContext;

	public PopulatorImpl(ConfigFactoryI cf) {
		this.configFactory = cf;
		this.force(true);
	}

	@Override
	public PopulatorI active(ActiveContext ac) {
		this.activeContext = ac;
		if (this.container == null) {
			this.container(ac.getContainer());
		}
		if (this.spi == null) {
			this.spi(ac.getSpi());//
		}
		return this;
	}

	@Override
	public PopulatorI container(ContainerI c) {
		this.container = c;
		return this;
	}

	/*
	
	 */
	@Override
	public PopulatorI spi(SPI spi) {
		this.spi = spi;
		if (this.cfgId == null) {
			this.cfgId = spi.getId();
		}
		return this;
	}

	/*
	
	 */
	@Override
	public PopulatorI cfgId(String cfgId) {
		this.cfgId = cfgId;
		return this;
	}

	/*
	
	 */
	@Override
	public PopulatorI force(boolean force) {
		this.force = force;
		return this;

	}

	/*
	
	 */
	@Override
	public PopulatorI type(String type) {
		this.type = type;
		return this;
	}

	/*
	
	 */
	@Override
	public PopulatorI populate() {
		FinderI<ObjectConfigI> fd = this.configFactory.finder();
		fd.describe(DescribedI.DK_PRT_CFG_ID, cfgId).describe(
				DescribedI.DK_TYPE, type);

		List<ObjectConfigI> ocL = fd.find();
		if (this.force && ocL.isEmpty()) {
			throw new FsException("no object config found by finder:" + fd);
		}
		for (ObjectConfigI oc : ocL) {

			Object o = oc.newInstance();
			this.activeContext.activitor().container(this.container)
					.cfgId(oc.getCfgId()).spi(this.spi).name(oc.getName())
					.object(o).active();
		}
		return this;
	}

}
