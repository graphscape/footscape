/**
 * Jun 19, 2012
 */
package com.fs.commons.api;

import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.config.Configuration;

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

		/* */
		@Override
		public ActivitorI active() {
			if (this.container != null) {
				this.container.addObject(this.spi, this.name, this.obj);

			}
			if (this.obj instanceof ConfigurableI) {
				String cid = this.cfgId;
				if (this.cfgId == null) {
					cid = this.obj.getClass().getName();
				}
				Configuration cfg = Configuration.properties(cid);
				((ConfigurableI) obj).configure(cfg);
			}
			if (this.obj instanceof ActivableI) {
				((ActivableI) obj).active(this.activeContext);
			}

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

	public void active(String name, Object o) {
		this.active(name, o, this.container);
	}

	public void active(String name, Object o, ContainerI c) {
		this.activitor().object(o).name(name).container(c).active();
	}

	public ActivitorI activitor() {
		return new Activitor().context(this);

	}

}
