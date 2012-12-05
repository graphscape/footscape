/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.editor.basic;

import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wuzhen
 * 
 */
public interface EnumEditorI extends EditorI<StringData> {

	public void addOption(String option);

}
