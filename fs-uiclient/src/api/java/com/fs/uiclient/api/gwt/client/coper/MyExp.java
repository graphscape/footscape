/**
 *  
 */
package com.fs.uiclient.api.gwt.client.coper;

import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uicore.api.gwt.client.data.PropertiesData;

/**
 * @author wu
 * 
 */
public class MyExp extends PropertiesData<Object> {

	public MyExp(PropertiesData<Object> pts) {
		super();
		this.setProperties(pts);
	}

	public String getId() {
		return (String) this.getProperty(UiClientConstants.NK_ID);
	}

	public String getTitle() {
		return (String) this.getProperty("title");
	}

	public String getBody() {
		return (String) this.getProperty("body");
	}

	public String getImage() {
		String rt = (String) this.getProperty("image");
		if ("n/a".equals(rt)) {
			rt = null;
		}
		return rt;

	}

}
