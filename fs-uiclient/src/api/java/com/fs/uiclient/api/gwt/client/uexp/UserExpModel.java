/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.api.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.impl.gwt.client.NodeFields;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class UserExpModel {

	private ObjectPropertiesData target;
	private boolean selected;

	/**
	 * @param name
	 */

	public UserExpModel(ObjectPropertiesData target) {
		this.target = target;
	}

	public String getTitle() {
		return (String) this.target.getProperty(ExpEditView.F_TITLE);
	}

	public String getBody() {
		return (String) this.target.getProperty(ExpEditView.F_BODY);
	}

	public String getBodyAsHtml() {
		String rt = this.getBody();
		return ExpItemModel.getExpBodyAsHtml(rt);
	}

	public String getExpId() {
		return (String) this.target.getProperty(NodeFields.PK_ID);
	}

	public void select(boolean sel) {
		this.selected = sel;
	}

	public boolean isExpId(String expId) {
		return this.getExpId().equals(expId);
	}

	public boolean isSelected() {
		return this.selected;
	}

	public DateData getTimestamp(boolean force) {
		return (DateData) this.target.getProperty(NodeFields.PK_TIMESTAMP);
	}

	/**
	 * Apr 14, 2013
	 */
	public String getIcon() {
		//
		return (String) this.target.getProperty(ExpEditView.F_ICON);

	}

}
