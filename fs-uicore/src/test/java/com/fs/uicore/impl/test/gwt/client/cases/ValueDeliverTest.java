/**
 * 
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import java.util.HashSet;
import java.util.Set;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.simple.SyncValueDeliver;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class ValueDeliverTest extends TestBase {

	private String expectedModelName;

	private Object expected;

	private Set<String> finishing = new HashSet<String>();

	private SimpleModel m1;
	private SimpleModel m2;

	public void testValueDeliver() {
		finishing.add("set-model1");//
		finishing.add("false-model2");
		finishing.add("set-model2");//
		finishing.add("true-model1");

		m1 = SimpleModel.valueOf("model1", Boolean.TRUE);
		m2 = SimpleModel.valueOf("model2", null);//
		SyncValueDeliver<Boolean, String> vd = new SyncValueDeliver<Boolean, String>(
				m1, ModelI.L_DEFAULT, m2, ModelI.L_DEFAULT);
		vd.mapValue(Boolean.TRUE, "TRUE_VALUE");
		vd.mapValue(Boolean.FALSE, "FALSE_VALUE");
		vd.start();//
		// after start,the target value should updated.
		assertEquals("TRUE_VALUE", m2.getDefaultValue());//

		// add handler for furture value change.

		EventHandlerI<ModelValueEvent> eh = new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				Object v = e.getValue();

				ValueDeliverTest.this.handle(e);

			}
		};

		m1.addValueHandler(ModelI.L_DEFAULT, eh);
		m2.addValueHandler(ModelI.L_DEFAULT, eh);
		//
		m1.setDefaultValue(Boolean.FALSE);
		this.delayTestFinish(1000 * 10);
	}

	/**
	 * @param e
	 */
	protected void handle(ModelValueEvent e) {
		String mname = e.getModel().getName();
		Object obj = e.getValue();
		if (this.finishing.contains("set-model1")) {
			assertEquals("firstly the model1's default value is set",
					m1.getName(), mname);

			this.tryFinishing("set-model1");
			return;
		}

		if (this.finishing.contains("false-model2")) {
			assertEquals("secondly,the model2's value is updated",
					m2.getName(), mname);
			assertEquals("value error,model2's value not update correctly",
					"FALSE_VALUE", obj);
			m2.setDefaultValue("TRUE_VALUE");// set model2......;
			this.tryFinishing("false-model2");
			return;
		}
		if (this.finishing.contains("set-model2")) {
			assertEquals("then the model2's default value is set",
					m2.getName(), mname);

			this.tryFinishing("set-model2");
			return;
		}

		if (this.finishing.contains("true-model1")) {
			assertFalse("lastly the model1's value is updated..",
					this.finishing.contains("false-model2"));
			assertEquals("model error,model event raised with wrong order?",
					m1.getName(), mname);
			assertEquals("value error,model1's value not update correctly",
					Boolean.TRUE, obj);

			this.tryFinishing("true-model1");
			return;
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
