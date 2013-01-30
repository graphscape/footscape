/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk.util;

import com.fs.uicommons.api.gwt.client.frwk.BodyModelI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class FrwkUtil {

	public static FrwkModelI getFrwkModel(ModelI model) {
		FrwkModelI fc = model.getTopObject().getChild(FrwkModelI.class, true);

		return fc;
	}

	public static HeaderModelI getHeader(ModelI model) {
		return getFrwkModel(model).getHeader();
	}

	public static FrwkControlI getFrwkControl(ModelI model) {
		return model.getClient(true).getChild(ControlManagerI.class, true)
				.getControl(FrwkControlI.class, true);
	}

	public static BodyModelI getBodyModel(ModelI model) {
		return getFrwkModel(model).getBody();
	}

	public static void manageByCenter(ViewI view) {
		getBodyModel(view.getModel()).manage(view.getModel(), view);
	}
	
	public static ViewReferenceI manage(ModelI model, ViewI view){
		return getBodyModel(view.getModel()).manage(model, view);
	}
}
