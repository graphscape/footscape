/**
 * Jun 19, 2012
 */
package com.fs.commons.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.factory.ConfigFactoryI;
import com.fs.commons.api.filter.ChainI;
import com.fs.commons.api.future.FutureMonitorI;
import com.fs.commons.api.jexl.JexlEngineI;
import com.fs.commons.api.mail.MailReceiverI;
import com.fs.commons.api.support.SPISupport;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.impl.codec.JsonCodecFactory;
import com.fs.commons.impl.exec.ExecutorFactory;
import com.fs.commons.impl.factory.ConfigFactoryImpl;
import com.fs.commons.impl.factory.ConverterFactoryImpl;
import com.fs.commons.impl.filter.ChainFactoryImpl;
import com.fs.commons.impl.freemarker.TemplateFactory;
import com.fs.commons.impl.future.FutureMonitorImpl;
import com.fs.commons.impl.jexl.JexlEngineImpl;
import com.fs.commons.impl.mail.MailReceiverFactory;
import com.fs.commons.impl.mail.MailSenderImpl;
import com.fs.commons.impl.ssh.client.SshClientFactory;
import com.fs.commons.impl.ssh.shell.SshShellFactory;
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

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		ConfigFactoryI cf = new ConfigFactoryImpl();
		//
		
		ac.getContainer().addObject(this, "CONFIG_FACTORY", cf);
		//

		ContainerI.FactoryI tf = new ContainerImpl.FactoryImpl();
		ac.getContainer().addObject(this, "CONTAINTER_FACTORY", tf);
		// Codec json
		JsonCodecFactory jdf = new JsonCodecFactory();
		ac.getContainer().addObject(this, "JSON_CODEC_FACTORY", jdf);
		// FilterChain factory
		ChainI.FactoryI fcf = new ChainFactoryImpl();
		ac.getContainer().addObject(this, "FILTER_CHAIN_FACTORY", fcf);
		// Future Monitor
		FutureMonitorI fm = new FutureMonitorImpl();
		ac.active("FUTURE_MONITOR", fm);
		// JexlEngine
		JexlEngineI je = new JexlEngineImpl();
		ac.active("JEXL_ENGINE", je);//
		//
		SshClientFactory scf = new SshClientFactory();
		ac.active("SSH_CLIENT_FACTORY", scf);
		//
		SshShellFactory ssf = new SshShellFactory();
		ac.active("SSH_SHELL_FACTORY", ssf);
		// converter factory
		ConverterFactoryImpl cvf = new ConverterFactoryImpl();
		ac.active("CONVERTER_FACTORY", cvf);
		// validator factory
		ValidatorI.FactoryI vf = new ValidatorFactoryImpl();
		ac.active("VALIDATOR_FACTORY", vf);
		// mail sender
		MailSenderImpl ms = new MailSenderImpl();
		ac.active("MAIL_SENDER", ms);
		// freemarker
		TemplateFactory ftf = new TemplateFactory();
		ac.active("TEMPLATE_FACTORY", ftf);//
		// mail receiver factory
		MailReceiverI.FactoryI mf = new MailReceiverFactory();
		ac.active("MAIL_RECEIVER_FACTORY", mf);
		// executor factory
		ExecutorFactory ef = new ExecutorFactory();
		ac.active("EXECUTOR_FACTORY", ef);
	}

	/* */
	@Override
	public void doDeactive(ActiveContext ac) {
	}

}
