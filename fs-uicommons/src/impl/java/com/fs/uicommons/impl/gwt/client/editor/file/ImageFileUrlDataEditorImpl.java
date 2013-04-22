/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 16, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.file;

import com.fs.uicommons.api.gwt.client.editor.image.ImageFileUrlDataEditorI;
import com.fs.uicommons.api.gwt.client.editor.support.FileUrlDataEditorSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;

/**
 * @author wuzhen
 * 
 */
public class ImageFileUrlDataEditorImpl extends FileUrlDataEditorSupport implements ImageFileUrlDataEditorI {
	// private Element image;

	/**
	 * @param ele
	 */
	public ImageFileUrlDataEditorImpl(ContainerI c, String name, UiPropertiesI<Object> pts) {
		super(c, name, pts);

	}

}
