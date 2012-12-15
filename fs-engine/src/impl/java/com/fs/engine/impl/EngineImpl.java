/**
 * Jun 11, 2012
 */
package com.fs.engine.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.filter.ChainI;
import com.fs.commons.api.filter.FilterI;
import com.fs.commons.api.filter.support.FilterSupport;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.engine.api.DispatcherI;
import com.fs.engine.api.EngineContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.engine.impl.context.EngineContext;
import com.fs.engine.impl.support.ServiceSupport;

/**
 * @author wuzhen
 * 
 */
public class EngineImpl extends ServiceSupport implements ServiceEngineI {

	public static class LastFilter extends FilterSupport<RequestI, ResponseI> {
		public EngineImpl engineImpl;

		public LastFilter(EngineImpl ei) {
			super();
			this.engineImpl = ei;
		}

		/*
		
		 */
		@Override
		public boolean doFilter(Context<RequestI, ResponseI> chain) {
			engineImpl.lastFilter(chain);
			return true;
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(EngineImpl.class);

	public static final String FILTER = "filter";

	protected DispatcherI<RequestI, ResponseI> dispatcher;

	protected ChainI<RequestI, ResponseI> chain;

	private Map<String, CallbackI<EngineImpl, PopulatorI>> populatorFactoryMap;

	protected EngineContextI engineContext;

	public EngineImpl() {
		super();
		this.populatorFactoryMap = new HashMap<String, CallbackI<EngineImpl, PopulatorI>>();
		this.populatorFactoryMap.put(FILTER, new CallbackI<EngineImpl, PopulatorI>() {

			@Override
			public PopulatorI execute(EngineImpl dis) {

				PopulatorI rt = EngineImpl.this.chain.newPopulator().type(FILTER);

				return rt;
			}
		});

	}

	protected String resolveDispatcherName() {
		return this.config.getProperty("dispatcher", true);
	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		String dname = this.config.getProperty("dispatcher", true);//

		this.engineContext = new EngineContext(this);//
		this.dispatcher = this.container.find(DispatcherI.class, dname, true);//
		ContainerI c = ac.getContainer();
		// child container
		ContainerI c2 = c.finder(ContainerI.FactoryI.class).withParent(true).find(true).newContainer(c);

		this.chain = this.container.find(ChainI.FactoryI.class).createChain(ac, RequestI.class,
				ResponseI.class);

		FilterI<RequestI, ResponseI> lf = new LastFilter(this);
		this.chain.addFilter(ac.getSpi(), "LAST_FILTER", lf);
	}

	/*
	
	 */
	@Override
	public void start() {

	}

	/*
	
	 */
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	private void lastFilter(FilterI.Context<RequestI, ResponseI> fc) {

		this.dispatcher.dispatch(fc);

	}

	@Override
	public void service(RequestI req, ResponseI res) {

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("path:" + req.getPath());
			}
			this.chain.service(req, res);

		} catch (Throwable t) {
			res.getErrorInfos().add(new ErrorInfo(t));

		} finally {

			ErrorInfos es = res.getErrorInfos();
			if (es.hasError()) {
				LOG.error(es.toString());
			}

		}

	}

	/*
	
	 */
	@Override
	public PopulatorI populator(String type) {
		PopulatorI rt = this.populatorFactoryMap.get(type).execute(this);

		return rt;
	}

}
