/**
 *  Dec 31, 2012
 */
package com.fs.commons.impl.message;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.filter.ChainI;
import com.fs.commons.api.filter.FilterI;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.service.support.ServiceSupport;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.support.CollectionHandler;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.impl.message.converter.MessageContextC;
import com.fs.commons.impl.message.converter.RequestC;
import com.fs.commons.impl.message.converter.ResponseC;

/**
 * @author wuzhen
 * 
 */
public class MessageServiceImpl extends ServiceSupport<MessageI, ResponseI, MessageContext> implements
		MessageServiceI {

	private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

	public static final String FILTER = "filter";

	public static final String DEFUALT = "default";

	protected DispatcherI<MessageContext> dispatcher;

	protected ChainI<MessageI, ResponseI> chain;

	protected Map<String, CollectionHandler<ResponseI>> epMap;

	protected String name;

	public MessageServiceImpl(String name) {
		super();
		this.name = name;
		this.epMap = new HashMap<String, CollectionHandler<ResponseI>>();
	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);

		this.dispatcher = this.top.find(DispatcherI.FactoryI.class, true).create("dispatcher-" + name);

		this.chain = this.components.newComponent(this.spi, ChainI.class);
		((ActivableI)this.chain).active(ac);//
		FilterI<MessageI, ResponseI> lf = new LastFilter(this);
		this.chain.addFilter(ac.getSpi(), "LAST_FILTER", lf);

		//
		ConverterI.FactoryI cf = top.find(ConverterI.FactoryI.class, true);

		cf.addConverter(new RequestC(cf));
		cf.addConverter(new ResponseC(cf));//
		cf.addConverter(new MessageContextC(cf));//
	}

	protected void lastFilter(FilterI.Context<MessageI, ResponseI> fc) {
		MessageI req = fc.getRequest();
		MessageContext sc = new MessageContext(req, fc.getResponse());
		Path p = req.getPath();
		this.dispatcher.dispatch(p, sc);

	}

	@Override
	public void service(MessageI req, ResponseI res) {

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("message service:" + this.name + " is processing message:" + req);
			}
			this.chain.service(req, res);

		} catch (Throwable t) {
			res.getErrorInfos().add(new ErrorInfo(t));

		} finally {

			ErrorInfos es = res.getErrorInfos();
			if (es.hasError()) {
				this.processError(req, res, es);
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("message service:" + this.name + " have processed message:" + req + ",response:"
						+ res);
			}
		}

	}

	protected void processError(MessageI req, ResponseI res, ErrorInfos es) {
		String ep = req.getErrorProcessor();
		CollectionHandler<ResponseI> ch = this.epMap.get(ep);
		if (ch == null) {
			ch = this.epMap.get(DEFUALT);
		}
		if (ch == null) {
			LOG.error(es.toString());
		} else {
			ch.handle(res);
		}

	}

	@Override
	public DispatcherI<MessageContext> getDispatcher() {
		return this.dispatcher;
	}

	@Override
	protected MessageI newRequest() {
		return new MessageSupport();
	}

	@Override
	protected ResponseI newResponse(MessageI req) {
		return new ResponseImpl(req);
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.api.service.ServiceI#getChain()
	 */
	@Override
	public ChainI<MessageI, ResponseI> getChain() {
		return this.chain;
	}

}
