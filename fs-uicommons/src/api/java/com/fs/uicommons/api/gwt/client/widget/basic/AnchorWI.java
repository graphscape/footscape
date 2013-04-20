package com.fs.uicommons.api.gwt.client.widget.basic;

import com.fs.uicommons.api.gwt.client.widget.BasicI;

public interface AnchorWI extends BasicI {

	public void click();

	public void setDisplayText(String txt);
	
	public void setDisplayText(String txt, boolean loc);
	
	public void setTextAndTitle(String txt, String title);
	
	public void setImage(String src);

}
