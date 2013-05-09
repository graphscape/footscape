/**
 * Jul 3, 2012
 */
package com.fs.expector.gridservice.impl.test;

import java.net.URI;
import java.net.URISyntaxException;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.support.SPISupport;
import com.fs.expector.gridservice.api.TestHelperI;
import com.fs.expector.gridservice.impl.handler.signup.SignupHandler;
import com.fs.expector.gridservice.impl.test.helper.TestHelperImpl;
import com.fs.expector.gridservice.impl.test.signup.TestConfirmCodeNotifier;
import com.fs.gridservice.commons.api.GlobalEventDispatcherI;

/**
 * @author wu
 * 
 */
public class ExpectorGsTestSPI extends SPISupport {
	public static final URI DEFAULT_WS_URI;
	public static final URI DEFAULT_AJAX_URI;
	static {
		try {
			DEFAULT_WS_URI = new URI("ws://localhost:8080/wsa/default");
			DEFAULT_AJAX_URI = new URI("http://localhost:8080/aja/default");
		} catch (URISyntaxException e) {
			
			throw new FsException(e);
		}
	}
	/** */
	public ExpectorGsTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		// test scenario
		DispatcherI<MessageContext> dis = ac.getContainer().find(GlobalEventDispatcherI.class, true).getEngine()
				.getDispatcher();
		
		SignupHandler sh = dis.getInternal().find(SignupHandler.class, true);
		TestHelperI th = new TestHelperImpl();

		ac.active("TEST_HELPER", th);//
		//
		ac.active("test", new TestConfirmCodeNotifier());//
		// sh.setTestHelper(th); //TODO

		// ScenarioI.FactoryI sf = ac.getContainer().find(
		// ScenarioI.FactoryI.class, true);
		//
		// sf.createScenario(ac, this.getId() + ".test-signup");
		// sf.createScenario(ac, this.getId() + ".test-client");
		// sf.createScenario(ac, this.getId() + ".test-login");// TODO report
		// // exception when no
		// // config.
		// sf.createScenario(ac, this.getId() + ".test-cfgf");

	}

	/* */
	/*
	 *Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		// 
		
	}

}
