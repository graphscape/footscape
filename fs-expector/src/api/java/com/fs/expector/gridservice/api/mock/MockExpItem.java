/**
 *  Jan 21, 2013
 */
package com.fs.expector.gridservice.api.mock;

import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.api.value.support.ProxyPropertiesSupport;
import com.fs.dataservice.api.core.NodeI;

/**
 * @author wuzhen
 * 
 */
public class MockExpItem extends ProxyPropertiesSupport<Object> {

	public MockExpItem(PropertiesI<Object> pts) {
		super(pts);
	}

	public String getExpId() {
		return (String) this.getProperty(NodeI.PK_ID, true);
	}

	public String getNick() {
		return (String) this.getProperty("nick", true);
	}

	public String getBody() {
		return (String) this.getProperty("body", true);
	}

	public String getAccountId() {
		return (String) this.getProperty("accountId", true);
	}

	public String getTimestamp() {
		return (String) this.getProperty(NodeI.PK_TIMESTAMP, true);
	}

	// iconDataUrl

}
