/**
 * Jun 19, 2012
 */
package com.fs.commons.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.components.ComponentFactoryI;
import com.fs.commons.api.filter.ChainI;
import com.fs.commons.api.future.FutureMonitorI;
import com.fs.commons.api.jexl.JexlEngineI;
import com.fs.commons.api.support.SPISupport;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.impl.codec.JsonCodecFactory;
import com.fs.commons.impl.components.ComponentFactoryImpl;
import com.fs.commons.impl.factory.ConverterFactoryImpl;
import com.fs.commons.impl.filter.ChainImpl;
import com.fs.commons.impl.future.FutureMonitorImpl;
import com.fs.commons.impl.jexl.JexlEngineImpl;
import com.fs.commons.impl.mail.MailSenderImpl;
import com.fs.commons.impl.session.SessionServerImpl;
import com.fs.commons.impl.validator.ValidatorFactoryImpl;

/**
 * @author wuzhen
 * 
 */
public class CommonsSPI extends SPISupport {

	/** */
	public CommonsSPI(String id) {
		super(id);

	}

	private void registerComponents(ComponentFactoryI cf) {
		// register components
		cf.addComponentClass(ContainerI.class, ContainerImpl.class);
		cf.addComponentClass(ChainI.class, ChainImpl.class);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		ContainerI c = ac.getContainer();
		ComponentFactoryI cf = new ComponentFactoryImpl(c);
		c.addObject(this, cf);
		this.registerComponents(cf);//
		
		// Codec json
		JsonCodecFactory jdf = new JsonCodecFactory();
		ac.getContainer().addObject(this, "JSON_CODEC_FACTORY", jdf);
		// Future Monitor
		FutureMonitorI fm = new FutureMonitorImpl();
		ac.active("FUTURE_MONITOR", fm);
		// JexlEngine
		JexlEngineI je = new JexlEngineImpl();
		ac.active("JEXL_ENGINE", je);//

		// converter factory
		ConverterFactoryImpl cvf = new ConverterFactoryImpl();
		ac.active("CONVERTER_FACTORY", cvf);
		// validator factory
		ValidatorI.FactoryI vf = new ValidatorFactoryImpl();
		ac.active("VALIDATOR_FACTORY", vf);
		// mail sender
		MailSenderImpl ms = new MailSenderImpl();
		ac.active("MAIL_SENDER", ms);

		//
		ac.active("dispatcherFactory");
		ac.active("messageServiceFactory");
		//
		ac.active("SESSION_SERVER", new SessionServerImpl());
	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {

	}

}
