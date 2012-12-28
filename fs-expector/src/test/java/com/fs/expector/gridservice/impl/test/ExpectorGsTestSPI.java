/**
 * Jul 3, 2012
 */
package com.fs.expector.gridservice.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.api.DispatcherI;
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

	/** */
	public ExpectorGsTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		// test scenario
		DispatcherI dis = ac.getContainer().find(GlobalEventDispatcherI.class, true).getEngine()
				.getDispatcher();
		SignupHandler sh = dis.getHandlerContainer().find(SignupHandler.class, true);
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
	@Override
	public void doDeactive(ActiveContext ac) {
	}

}
