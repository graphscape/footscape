/**
 *  Jan 21, 2013
 */
package com.fs.expector.gridservice.api.mock;

import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.api.value.support.ProxyPropertiesSupport;

/**
 * @author wuzhen
 * 
 */
public class MockExpMessage extends ProxyPropertiesSupport<Object> {

	public MockExpMessage(PropertiesI<Object> pts) {
		super(pts);
	}

	public String getExpId2() {
		return (String) this.getProperty("expId2", true);
	}

	public String getAccountId2() {
		return (String) this.getProperty("accountId2", true);
	}

	public String getBody() {
		return (String) this.getProperty("body", true);
	}

	public String getAccountId1() {
		return (String) this.getProperty("accountId1", true);
	}

	public String getTimestamp() {
		return (String) this.getProperty("timestamp", true);
	}
}
