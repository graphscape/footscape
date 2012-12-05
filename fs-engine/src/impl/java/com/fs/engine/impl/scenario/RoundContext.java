/**
 * Jul 25, 2012
 */
package com.fs.engine.impl.scenario;

import com.fs.commons.api.context.support.ContextSupport;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.scenario.RoundI;
import com.fs.engine.api.scenario.ScenarioI;
import com.fs.engine.api.scenario.ScenarioI.ContextI;

/**
 * @author wu
 * 
 */
public class RoundContext extends ContextSupport implements RoundI.ContextI {
	public static final String REQUEST = "request";
	public static final String RESPONSE = "response";

	private ErrorInfos errorInfos = new ErrorInfos();

	private ScenarioI.ContextI scenarioContext;

	private RoundI round;

	public RoundContext(ScenarioI.ContextI sc, RoundI rd) {
		this.scenarioContext = sc;
		this.round = rd;

	}

	/* */
	@Override
	public RequestI getRequest() {

		return (RequestI) this.getProperty(REQUEST);

	}

	/* */
	@Override
	public ResponseI getResponse() {

		return (ResponseI) this.getProperty(RESPONSE);

	}

	/* */
	@Override
	public RoundI getRound() {

		return this.round;

	}

	/* */
	@Override
	public ContextI getScenarioContext() {

		return this.scenarioContext;

	}

	/* */
	@Override
	public ErrorInfos getErrorInfos() {

		return errorInfos;

	}

}
