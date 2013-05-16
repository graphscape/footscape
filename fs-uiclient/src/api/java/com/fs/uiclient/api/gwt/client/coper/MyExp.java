/**
 *  
 */
package com.fs.uiclient.api.gwt.client.coper;
import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uicore.api.gwt.client.commons.ImageUrl;
import com.fs.uicore.api.gwt.client.data.PropertiesData;

/**
 * @author wu
 * 
 */
public class MyExp extends PropertiesData<Object> {

	public static final String F_STATUS = "status";

	public static String OPEN = "open";// NOTE see entity def.

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

	public ImageUrl getImageUrl() {
		String rt = (String) this.getProperty("image");

		return ImageUrl.parse(rt, true);

	}

	public String getStatus() {
		return (String) this.getProperty("status");
	}

	/**
	 * May 3, 2013
	 */
	public boolean getIsOpen() {
		//
		String status = this.getStatus();
		return OPEN.equals(status);
	}

}
