/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uicommons.api.gwt.client.manage.util;

import com.fs.uicommons.api.gwt.client.manage.BossControlI;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagerModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class BossUtil {

	public static BossControlI getBossControl(ModelI model) {
		return model.getClient(true).getChild(ControlManagerI.class, true)
				.getControl(BossControlI.class, true);
	}

	public static ManagerModelI getCenterModel(ModelI model) {
		return getBossControl(model).getManager(BossModelI.M_CENTER);
	}

	public static void manageByCenter(ViewI view) {
		getCenterModel(view.getModel()).manage(view.getModel(), view);
	}
	
	public static ManagedModelI manage(ModelI model, ViewI view){
		BossControlI bc = getBossControl(model);
		return bc.manage(model, view);
	}
}
