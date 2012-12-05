/**
 * Jul 25, 2012
 */
package com.fs.engine.impl.scenario;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.engine.api.scenario.ScenarioI;

/**
 * @author wu
 * 
 */
public class ScenarioFactory implements ScenarioI.FactoryI {
	// TODO use container:
	private Map<String, ScenarioI> smap = new HashMap<String, ScenarioI>();

	/* */
	@Override
	public ScenarioI createScenario(ActiveContext ac, String id) {
		ScenarioI old = this.getScenario(id);
		if (old != null) {
			throw new FsException("duplicated:" + id);
		}
		ScenarioImpl rt = new ScenarioImpl();
		ac.activitor().object(rt).cfgId(id).active();

		smap.put(id, rt);
		return rt;

	}

	/* */
	@Override
	public ScenarioI getScenario(String id) {

		return this.smap.get(id);

	}

}
