/**
 * Jul 1, 2012
 */
package com.fs.uicore.impl.gwt.client;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.WindowI;
import com.fs.uicore.api.gwt.client.config.ConfigurationFactoryI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf.CheckerSupport;
import com.fs.uicore.api.gwt.client.support.WidgetCreaterSupport;
import com.fs.uicore.impl.gwt.client.config.ConfigurationFactoryImpl;

/**
 * @author wu
 * 
 */
public class UiCoreGwtSPIImpl implements UiCoreGwtSPI {

	/* */
	@Override
	public void active(ContainerI c) {

		this.activeInstanceOfChecker();

		// window
		WindowI window = new WindowImpl();
		c.add(window);
		//
		this.activeWidgetAndFactory(c);

		// RootI widget
		WidgetFactoryI wf = c.get(WidgetFactoryI.class, true);

		// Root model
		ModelI model = new RootModel("root");
		c.add(model);//
		RootI root = wf.create(RootI.class, model);// TODO
		// root
		// model
		c.add(root);// TODO move to SPI.active();

		// client
		UiClientI client = new UiClientImpl(root);
		c.add(client);
		//
		// message df

		//
		this.activeConfigFactory(c);

		//

	}

	protected void activeConfigFactory(ContainerI c) {

		ConfigurationFactoryI cf = new ConfigurationFactoryImpl();
		UiClientI client = c.get(UiClientI.class, true);
		cf.parent(client);//
	}

	protected void activeWidgetAndFactory(ContainerI c) {
		WidgetFactoryI wf = new WidgetFactoryImpl();
		c.add(wf);//
		wf.addCreater(new WidgetCreaterSupport<RootI>(RootI.class) {

			@Override
			public RootI create(String name) {
				return new RootWImpl(name);
			}
		});
	}

	protected void activeInstanceOfChecker() {

		InstanceOf.addChecker(new CheckerSupport(UiObjectI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UiObjectI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(ModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ModelI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(RootI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof RootI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(UiClientI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UiClientI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(WidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WidgetI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(WidgetFactoryI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WidgetFactoryI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(ConfigurationFactoryI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ConfigurationFactoryI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(EventBusI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EventBusI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(WindowI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WindowI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(UiObjectI.AttacherI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UiObjectI.AttacherI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(MessageDispatcherI.FactoryI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MessageDispatcherI.FactoryI;
			}
		});

	}

}
