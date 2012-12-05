/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 19, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.image;

import com.fs.uicommons.api.gwt.client.editor.image.ImageCropEditorI;
import com.fs.uicommons.api.gwt.client.editor.support.FileUrlDataEditorSupport;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.event.DataEvent;
import com.google.gwt.user.client.DOM;

/**
 * @author wuzhen
 *         <p>
 *         http://code.google.com/p/gwt-examples/wiki/gwt_hmtl5#Crop_Image
 */
public class ImageCropEditorImpl extends FileUrlDataEditorSupport implements
		ImageCropEditorI {

	protected ElementWrapper image;

	protected ImageCroper imageCroper;

	public ImageCropEditorImpl(String name) {
		super(name);

		this.image = ElementWrapper.valueOf(DOM.createImg());
		this.dataRender.append(this.image);//

	}

	/**
	 * Nov 21, 2012
	 */
	protected void onImageCroperData(DataEvent e) {
		//
		String ds = e.getData();

		this.setData(StringData.valueOf(ds), true);

	}

	/*
	 * Nov 21, 2012
	 */
	@Override
	protected void processModelDefaultValue(StringData data) {
		super.processModelDefaultValue(data);
		this.image.setAttribute("src", data == null ? null : data.getValue());
	}

	// data is from file input,not setData directly,process it first,see
	// onImageCroperData() method.
	@Override
	protected void onDataLoad(StringData data) {

		if (this.imageCroper != null) {
			this.imageCroper.getElement().removeFromParent();//
			this.imageCroper = null;
		}

		imageCroper = new ImageCroper();

		imageCroper.parent(this);//
		imageCroper.addHandler(DataEvent.TYPE, new HandlerI<DataEvent>() {

			@Override
			public void handle(DataEvent e) {
				ImageCropEditorImpl.this.onImageCroperData(e);
			}
		});

		imageCroper.setDataUrl(data == null ? null : data.getValue());
		// open croper
		imageCroper.open();

	}

}
