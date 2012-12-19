/**
 * Jun 14, 2012
 */
package com.fs.engine.impl.dispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.factory.ConfigFactoryI;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.filter.FilterI;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.engine.api.DispatcherI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.impl.service.HandleContextImpl;

/**
 * @author wuzhen
 * 
 */
public class DispatcherImpl extends ConfigurableSupport implements
		DispatcherI<RequestI, ResponseI> {

	private static final Logger LOG = LoggerFactory
			.getLogger(DispatcherImpl.class);// TODO API?

	private HandlerContainer internal;//

	private Map<String, CallbackI<DispatcherImpl, PopulatorI>> populatorFactoryMap;

	/**
	 * @param cfg
	 */
	public DispatcherImpl() {
		super();

		this.populatorFactoryMap = new HashMap<String, CallbackI<DispatcherImpl, PopulatorI>>();
		this.populatorFactoryMap.put("handler",
				new CallbackI<DispatcherImpl, PopulatorI>() {

					@Override
					public PopulatorI execute(DispatcherImpl dis) {
						ConfigFactoryI cf = DispatcherImpl.this.container.find(
								ConfigFactoryI.class, true);

						PopulatorI rt = cf.newPopulator()
								.container(DispatcherImpl.this.internal)
								.type("handler");

						return rt;
					}
				});
	}

	/*
	
	 */
	@Override
	public PopulatorI populator(String type) {
		PopulatorI rt = this.populatorFactoryMap.get(type).execute(this);
		return rt;
	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		ContainerI c = ac.getContainer().finder(ContainerI.FactoryI.class)
				.withParent(true).find(true).newContainer(ac.getContainer());

		this.internal = new HandlerContainer(c);
	}

	@Override
	public void doAttach() {
		// TODO Auto-generated method stub
		super.doAttach();
		this.internal.attach();// NOTE
	}

	@Override
	public void doDettach() {
		// TODO Auto-generated method stub
		super.doDettach();
		this.internal.dettach();// NOTE
	}

	/*
	
	 */
	@Override
	public void dispatch(FilterI.Context<RequestI, ResponseI> fc) {
		RequestI req = fc.getRequest();

		String path = req.getPath();

		List<HandlerEntry> heL = this.internal.getByPath(path);
		if (heL.isEmpty()) {
			heL = this.internal.getDefaultEntryList();// NOTE
		}
		if (heL.isEmpty()) {
			fc.getResponse()
					.getErrorInfos()
					.add(new ErrorInfo(this.toString(),
							"there is no handler found for:" + path));

			return;
		}
		HandleContextImpl hc = new HandleContextImpl(fc);// TODO

		for (HandlerEntry he : heL) {
			he.getHandler().handle(hc);
		}

	}

	/*
	 * Nov 3, 2012
	 */
	@Override
	public ContainerI getHandlerContainer() {
		return this.internal;
	}

}
