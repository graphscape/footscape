/**
 * Jun 19, 2012
 */
package com.fs.commons.api;

import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.event.AfterActiveEvent;
import com.fs.commons.api.event.BeforeActiveEvent;
import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class ActiveContext {
	
	private static class Activitor implements ActivitorI {
		private SPI spi;
		private String name;
		private ActiveContext activeContext;
		private ContainerI container;
		private String cfgId;
		private Configuration configuration;
		private Object obj;

		/* */
		@Override
		public ActivitorI spi(SPI spi) {
			this.spi = spi;
			return this;

		}

		/* */
		@Override
		public ActivitorI context(ActiveContext ac) {
			this.activeContext = ac;
			if (this.spi == null) {
				this.spi = ac.spi;
			}
			if (this.container == null) {
				this.container = ac.container;
			}

			return this;

		}

		/* */
		@Override
		public ActivitorI cfgId(String cfgId) {
			this.cfgId = cfgId;

			return this;

		}

		/* */
		@Override
		public ActivitorI container(ContainerI c) {
			this.container = c;
			return this;

		}

		/* */
		@Override
		public ActivitorI name(String name) {
			this.name = name;
			if (this.cfgId == null) {
				String cid = this.spi == null ? name
						: (this.spi.getId() + "." + name);
				this.cfgId(cid);
			}
			return this;

		}

		/* */
		@Override
		public ActivitorI object(Object obj) {
			this.obj = obj;
			return this;

		}

		/* */
		@Override
		public ActivitorI clazz(Class cls) {

			return this;

		}

		protected Configuration resolveConfiguration() {
			Configuration cfg;
			if (this.configuration != null) {
				if (cfgId != null) {
					throw new FsException(
							"configuration is present,id not allowed");
				}
				cfg = this.configuration;
			} else {

				String cid = this.cfgId;
				if (this.cfgId == null) {
					cid = this.obj.getClass().getName();
				}
				cfg = Configuration.properties(cid);
			}
			return cfg;
		}

		/* */
		@Override
		public ActivitorI active() {
			Configuration cfg = null;
			if (this.obj == null) {
				// try to create object
				cfg = this.resolveConfiguration();
				this.obj = cfg.getPropertyAsNewInstance("class", true);
			}

			if (this.obj instanceof ConfigurableI) {

				if (cfg == null) {
					cfg = this.resolveConfiguration();
				}

				((ConfigurableI) obj).configure(cfg);
			}
			if (this.obj instanceof ActivableI) {
				ActivableI ao = (ActivableI) obj;
				new BeforeActiveEvent(obj).dispatch(this.container);
				ao.active(this.activeContext);
				new AfterActiveEvent(obj).dispatch(this.container);
			}
			if (this.container != null) {
				this.container.addObject(this.spi, this.name, this.obj);

			}
			return this;

		}

		public <T> T getObject() {
			return (T) this.obj;
		}

		/*
		 * Dec 11, 2012
		 */
		@Override
		public ActivitorI configuration(Configuration cfg) {
			//
			this.configuration = cfg;
			this.cfgId = null;//
			return this;
		}

	}

	private ContainerI container;
	private SPI spi;

	public ActiveContext(ContainerI ctn, SPI spi) {
		this.container = ctn;
		this.spi = spi;
	}

	public ContainerI getContainer() {
		return this.container;
	}

	/**
	 * @return the spi
	 */
	public SPI getSpi() {
		return spi;
	}

	@Deprecated
	public <T> T active(String name) {
		ActivitorI act = this.activitor().container(this.container).name(name);
		act.active();
		return act.getObject();
	}

	@Deprecated
	public void active(String name, Object o) {
		this.active(name, o, this.container);
	}

	@Deprecated
	public void active(String name, Object o, ContainerI c) {
		this.activitor().object(o).name(name).container(c).active();
	}

	@Deprecated
	public void active(Configuration cfg, Object o) {

		ActivitorI act = this.activitor().object(o);
		if (cfg != null) {
			act.name(cfg.getName());
		}
		act.configuration(cfg);
		act.active();
	}

	public ActivitorI activitor() {
		return new Activitor().context(this);

	}

	@Deprecated
	public ActiveContext newActiveContext(ContainerI internal) {
		return new ActiveContext(internal, this.spi);
	}

}
