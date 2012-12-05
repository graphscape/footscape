/**
 * Jul 25, 2012
 */
package com.fs.engine.impl.test.cases;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.engine.api.scenario.ScenarioI;
import com.fs.engine.impl.test.EngineTestSPI;
import com.fs.engine.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ScenarioTest extends TestBase {

	public void testScenario() throws Exception {
		ScenarioI.FactoryI sf = this.container.find(ScenarioI.FactoryI.class,
				true);
		ScenarioI s = sf.getScenario(EngineTestSPI.class.getName()
				+ ".test-scenario");
		ScenarioI.ContextI sc = s.run();
		ErrorInfos eis = sc.getAllErrorInfos();
		if (eis.hasError()) {
			String msg = eis.getErrorInfoList() + "";
			System.err.println(msg);
			throw new FsException(msg);

		}
	}
}
