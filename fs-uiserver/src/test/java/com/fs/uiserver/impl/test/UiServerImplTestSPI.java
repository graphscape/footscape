/**
 * Jul 3, 2012
 */
package com.fs.uiserver.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.api.DispatcherI;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.scenario.ScenarioI;
import com.fs.uiserver.TestHelperI;
import com.fs.uiserver.impl.UiServerSPI;
import com.fs.uiserver.impl.handler.signup.SignupHandler;
import com.fs.uiserver.impl.test.helper.TestHelperImpl;

/**
 * @author wu
 * 
 */
public class UiServerImplTestSPI extends SPISupport {

	/** */
	public UiServerImplTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		// test scenario
		DispatcherI dis = ac.getContainer().find(EngineFactoryI.class, true)
				.getEngine(UiServerSPI.ENAME_UISERVER).getDispatcher();
		SignupHandler sh = dis.getHandlerContainer().find(SignupHandler.class,
				true);
		TestHelperI th = new TestHelperImpl();

		ac.active("TEST_HELPER", th);//

		// sh.setTestHelper(th); TODO

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
