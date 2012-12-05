/**
 * Jul 25, 2012
 */
package com.fs.engine.api.scenario;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public interface ScenarioI {
	public static interface FactoryI {

		public ScenarioI getScenario(String id);

		public ScenarioI createScenario(ActiveContext ac, String id);

	}

	public static interface ContextI extends PropertiesI<Object> {

		public ScenarioI getScenario();

		public ErrorInfos getAllErrorInfos();
	}

	public List<RoundI> getRoundList();

	public ContextI run();

}
