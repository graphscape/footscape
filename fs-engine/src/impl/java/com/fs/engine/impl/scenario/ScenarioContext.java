/**
 * Jul 25, 2012
 */
package com.fs.engine.impl.scenario;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.context.support.ContextSupport;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.engine.api.scenario.RoundI;
import com.fs.engine.api.scenario.ScenarioI;

/**
 * @author wu
 * 
 */
public class ScenarioContext extends ContextSupport implements
		ScenarioI.ContextI {
	protected List<RoundI.ContextI> roundContextList = new ArrayList<RoundI.ContextI>();

	private ScenarioI scenario;

	public ScenarioContext(ScenarioI s) {
		this.scenario = s;
	}

	/* */
	@Override
	public ScenarioI getScenario() {
		return this.scenario;
	}

	public List<RoundI.ContextI> getRoundContextList() {
		return this.roundContextList;
	}

	/* */
	@Override
	public ErrorInfos getAllErrorInfos() {
		ErrorInfos rt = new ErrorInfos();
		for (RoundI.ContextI rc : this.roundContextList) {
			ErrorInfos eis = rc.getErrorInfos();
			rt.addAll(eis);

		}
		return rt;
	}

}
