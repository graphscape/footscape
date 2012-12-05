/**
 * Jul 25, 2012
 */
package com.fs.engine.api.scenario;

import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;

/**
 * @author wu
 * 
 */
public interface RoundI {

	public static interface ContextI extends PropertiesI<Object> {
		public RequestI getRequest();

		public ResponseI getResponse();

		public RoundI getRound();

		public ScenarioI.ContextI getScenarioContext();

		public ErrorInfos getErrorInfos();

	}

	public void run(RoundI.ContextI ctx);

}
