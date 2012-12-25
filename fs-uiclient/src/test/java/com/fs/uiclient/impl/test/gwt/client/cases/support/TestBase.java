/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.support;

import java.util.HashSet;
import java.util.Set;

import com.fs.uiclient.api.gwt.client.UiClientGwtSPI;
import com.fs.uiclient.impl.gwt.client.Constants;
import com.fs.uicommons.api.gwt.client.UiCommonsGPI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.fs.uicore.api.gwt.client.support.UiFilterSupport;
import com.fs.uicore.api.gwt.client.util.ClientLoader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author wuzhen
 * 
 */
public class TestBase extends GWTTestCase {
	public static final int timeoutMillis = 100000;
	
	public static class CaseIdFilter extends UiFilterSupport {
		protected String caseId;

		protected boolean useOnce;

		@Override
		protected void filterRequest(Context fc) {
			//
			if (this.caseId == null) {
				return;//
			}
			fc.getRequest().setHeader(Constants.X_FS_TEST_CASE, this.caseId);
			if (this.useOnce) {
				this.caseId = null;// reset the case id//only use once.
			}
			//

		}

		@Override
		protected void filterResponse(Context fc) {
			//
			//
		}

		public void setCaseId(String cid) {
			this.setCaseId(cid, false);//
		}

		public void setCaseId(String cid, boolean once) {
			this.caseId = cid;
			this.useOnce = once;
		}

	}

	protected GwtSPI.Factory factory;

	protected UiClientI client;

	protected ContainerI container;

	protected WidgetFactoryI wf;

	protected RootI root;

	protected ControlManagerI manager;

	protected ModelI rootModel;

	protected static CaseIdFilter caseIdFilter;

	protected Set<String> finishing = new HashSet<String>();

	/* */
	@Override
	public String getModuleName() {
		return "com.fs.uiclient.impl.test.gwt.UiClientImplTest";
	}

	public void dump() {
		System.out.println(this.rootModel.dump());
		System.out.println(this.root.dump());
		System.out.println(this.client.dump());

	}

	/*
	
	 */
	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		GwtSPI[] spis = new GwtSPI[] { GWT.create(UiCoreGwtSPI.class),
				GWT.create(UiCommonsGPI.class),
				GWT.create(UiClientGwtSPI.class),
				};

		factory = ClientLoader.getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				TestBase.this.onEvent(e);
			}
		});
		this.container = this.factory.getContainer();
		this.client = this.container.get(UiClientI.class, true);
		this.caseIdFilter = new CaseIdFilter();
		this.client.addFilter(this.caseIdFilter);

		this.wf = this.container.get(WidgetFactoryI.class, true);
		this.root = this.container.get(UiClientI.class, true).getRoot();
		this.rootModel = this.client.getRootModel();//
		this.manager = this.client.getChild(ControlManagerI.class, true);
		this.dump();
	}

	public void onEvent(Event e) {
		
	}

	/*
	
	 */
	@Override
	protected void gwtTearDown() throws Exception {
		super.gwtTearDown();

	}

	protected void tryFinish(String item) {
		this.finishing.remove(item);
		System.out.println("finished:" + item + ",waiting:" + this.finishing);
		if (this.finishing.isEmpty()) {
			this.finishTest();
		}
	}
}
