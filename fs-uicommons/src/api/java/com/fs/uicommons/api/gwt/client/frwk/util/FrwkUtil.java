/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk.util;

import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
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

}
