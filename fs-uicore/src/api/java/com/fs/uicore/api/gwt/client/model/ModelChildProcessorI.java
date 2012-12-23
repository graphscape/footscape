/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicore.api.gwt.client.model;

import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.efilter.ModelChildEventFilter;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;

/**
 * @author wu
 * 
 */
public interface ModelChildProcessorI {

	// this is used for the situation that you want to both listening the
	// ModelChildEvent and treat the current already added child with the same
	// manner.
	// when your object attaching,call the helper to both register the handler
	// for child event and iterate the current children.
	public static class Helper {

		public static void onAttach(final ModelI model, final ModelChildProcessorI mm) {
			List<ModelI> il = model.getChildList(ModelI.class);

			for (final ModelI iw : il) {
				mm.processChildModelAdd(model, iw);
			}
			// for child adding event
			model.addHandler(new ModelChildEventFilter(ModelI.class,null),
			new EventHandlerI<ModelChildEvent>() {

				@Override
				public void handle(ModelChildEvent e) {
					
					ModelI im = e.getChild();
					if(e.isAdd()){
						mm.processChildModelAdd(model, im);
					}else{
						mm.processChildModelRemove(model, im);//
					}
				}
			});
		}

	}
	
	public void processChildModelRemove(final ModelI parent, final ModelI child);
	
	public void processChildModelAdd(final ModelI parent, final ModelI child);

}
