/**
 * 
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import java.util.HashSet;
import java.util.Set;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf.CheckerSupport;
import com.fs.uicore.api.gwt.client.simple.OffspringValueDeliver;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class OffspringValueDeliverTest extends TestBase {

	private Set<String> finishing = new HashSet<String>();

	private SimpleModel m1;
	private SimpleModel m11;
	private ChildModel m111;

	private SimpleModel m2;

	public static class ChildModel extends SimpleModel {

		public static final Location LOC1 = Location.valueOf("loc1");

		/**
		 * @param name
		 */
		public ChildModel(String name,Object def) {
			super(name);
			this.defaultValue(def);
		}

		public void setLoc1(String value) {
			this.setValue(LOC1, value);
		}

	}

	public void testValueDeliver() {
		InstanceOf.addChecker(new CheckerSupport(ChildModel.class) {

			@Override
			public boolean isInstance(Object o) {
				//
				return o instanceof ChildModel;
			}
		});

		finishing.add("set-model111");//
		finishing.add("set-model2");

		m1 = SimpleModel.valueOf("model1", "1");
		m11 = SimpleModel.valueOf("model11", "11").parent(m1).cast();
		m111 = new ChildModel("model111", "111").parent(m11).cast();
		m111.setLoc1("value1");

		m2 = SimpleModel.valueOf("model2", null);//

		OffspringValueDeliver<String, String> vd = new OffspringValueDeliver<String, String>(
				m1, ChildModel.class, ChildModel.LOC1, m2, ModelI.L_DEFAULT);
		vd.mapValue("VALUE1", "value1");
		vd.mapDefaultDirect();

		vd.start();//

		assertEquals("m2 's value should be updated to m111's loc1 value",
				"value1", m2.getDefaultValue());

		EventHandlerI<ModelValueEvent> eh = new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				Object v = e.getValue();

				OffspringValueDeliverTest.this.handle(e);

			}
		};
		m111.addValueHandler(ChildModel.LOC1, eh);
		m2.addValueHandler(ModelI.L_DEFAULT, eh);
		m111.setLoc1("value2");// directly to m2.default value.
		this.delayTestFinish(1000 * 10);
	}

	/**
	 * @param e
	 */
	protected void handle(ModelValueEvent e) {
		String mname = e.getModel().getName();
		Object obj = e.getValue();

		System.out.println(mname + "," + e.getLocation() + "=" + obj);
		if (mname.equals("model111") && this.finishing.contains("set-model111")) {

			this.tryFinishing("set-model111");
		}
		if (mname.equals("model2") && this.finishing.contains("set-model2")) {
			assertEquals("value2", m2.getDefaultValue());
			this.tryFinishing("set-model2");
		}

	}

	protected void tryFinishing(String item) {
		this.finishing.remove(item);
		System.out.println("finished:" + item + ",waiting:" + this.finishing);

		if (this.finishing.isEmpty()) {
			this.finishTest();
			return;
		}
	}

}
