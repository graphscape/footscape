/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionFailedEvent;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionSuccessEvent;
import com.fs.uicommons.api.gwt.client.mvc.event.AfterActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.event.BeforeActionEvent;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.efilter.ModelValueEventFilter;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.model.ModelChildProcessorI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wuzhen
 * 
 */
public abstract class AbstractControl extends UiObjectSupport implements ControlI, ModelChildProcessorI {

	protected String name;

	protected ModelI model;

	protected Map<String, Boolean> localMap = new HashMap<String, Boolean>();

	protected Map<String, List<ActionProcessorI>> arhMap = new HashMap<String, List<ActionProcessorI>>();

	public AbstractControl(String name) {
		this.name = name;
	}

	@Override
	public ControlI model(ModelI cm) {
		if (this.isAttached()) {
			throw new UiException("NOT support after already attached:" + this);
		}
		this.model = cm;
		this.doModel(cm);
		return this;
	}

	protected void doModel(ModelI cm) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#doAttach()
	 */
	@Override
	protected void doAttach() {
		super.doAttach();
		ModelChildProcessorI.Helper.onAttach(this.model, this);//
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.mvc.ControlI#getModel()
	 */
	@Override
	public <T extends ModelI> T getModel() {
		return (T) this.model;
	}

	@Override
	public ControlManagerI getManager() {

		return (ControlManagerI) this.parent;

	}

	@Override
	public void processChildModelAdd(final ModelI parent, final ModelI cm) {
		if (cm instanceof ActionModelI) {
			this.processChildAModelAdd((ActionModelI) cm);
		}
	}

	protected void processChildAModelAdd(ActionModelI amodel) {
		amodel.addHandler(new ModelValueEventFilter(ActionModelI.L_STATE),
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						AbstractControl.this.handleActionStateChanging(e);
					}
				});
	}

	protected void handleActionStateChanging(ModelValueEvent e) {
		ActionModelI am = (ActionModelI) e.getSource();
		ValueWrapper vw = e.getValueWrapper();

		if (vw.isValue(ActionModelI.TRIGGERED)) {
			vw.setValue(ActionModelI.PROCESSING);// NOTE //TODO raise event.
			new BeforeActionEvent(this, am.getName()).dispatch();

			this.processRequest(this, am.getName());

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ControlI#getActionRequestHandler(
	 * java.lang.String)
	 */
	@Override
	public List<ActionProcessorI> getActionRequestHandlerList(String ah) {

		List<ActionProcessorI> rt = this.arhMap.get(ah);
		if (rt == null) {
			rt = new ArrayList<ActionProcessorI>();
		}
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ControlI#actionPerformed(java.lang
	 * .String, com.fs.uicore.api.gwt.client.UiResponse)
	 */
	@Override
	public void actionPerformed(String a, UiResponse res) {
		// TODO Auto-generated method stub

		List<ActionProcessorI> apL = this.getActionRequestHandlerList(a);

		// reversly process response
		for (int i = apL.size() - 1; i >= 0; i--) {
			ActionProcessorI ap = apL.get(i);//
			ap.processResponse(this, a, res);
		}
		ActionModelI am = ControlUtil.getAction(this.model, a);
		if (res.getErrorInfos().hasError()) {
			this.onActionFailed(a, res);
		} else {
			this.onActionSuccess(a, res);
		}
		am.processed(res.getErrorInfos());

		new AfterActionEvent(this, a, res).dispatch();
	}

	protected void onActionSuccess(String action, UiResponse res) {
		new ActionSuccessEvent(this, action).dispatch();
	}

	protected void onActionFailed(String action, UiResponse res) {
		new ActionFailedEvent(this, action, res.getErrorInfos()).dispatch();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ControlI#addActionProcessor(java.
	 * lang.String, com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI)
	 */
	@Override
	public void addActionProcessor(String a, ActionProcessorI ap) {
		List<ActionProcessorI> apL = this.arhMap.get(a);
		if (apL == null) {
			apL = new ArrayList<ActionProcessorI>();
			this.arhMap.put(a, apL);
		}
		apL.add(ap);
	}

	public boolean isLocalAction(String a) {
		Boolean b = this.localMap.get(a);
		return b != null && b;

	}

	public void processRequest(final ControlI c, final String a) {

		UiRequest req = new UiRequest(this.isLocalAction(a));
		// at
		// client
		// side

		String cname = c.getName();

		if (cname == null) {
			throw new UiException("control has no name:" + c);
		}
		// relative path see the UiClientI.RHK_CONTEXT_PATH

		String path = cname + "/" + a;

		req.setRequestPath(path);

		List<ActionProcessorI> apL = this.getActionRequestHandlerList(a);
		for (ActionProcessorI ap : apL) {
			ap.processRequest(this, a, req);
		}

		UiClientI cli = c.getClient(false);// TODO
		if (cli == null) {
			throw new UiException("not attached to client");
		}
		UiCallbackI<UiResponse, Object> callback = new UiCallbackI<UiResponse, Object>() {

			@Override
			public Object execute(UiResponse t) {
				AbstractControl.this.actionPerformed(a, t);
				return null;
			}

		};
		cli.sendRequest(req, callback);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ControlI#addActionEventHandler(java
	 * .lang.String, com.fs.uicore.api.gwt.client.core.Event.HandlerI)
	 */
	@Override
	public void addActionEventHandler(String a, EventHandlerI<ActionEvent> eh) {
		// TODO Auto-generated method stub

	}

}
