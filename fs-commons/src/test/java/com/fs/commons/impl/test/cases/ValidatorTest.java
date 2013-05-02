/**
 * Jul 22, 2012
 */
package com.fs.commons.impl.test.cases;

import java.util.List;

import com.fs.commons.api.struct.Path;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.validator.ValidateItem;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ValidatorTest extends TestBase {

	public static class Target {
		private String string1;

		private PropertiesI<Object> pts1;

		public String getString1() {
			return this.string1;
		}

		/**
		 * @return the pts1
		 */
		public PropertiesI<Object> getPts1() {
			return pts1;
		}

	}

	public static class Context {
		private Target target;

		/**
		 * @return the target
		 */
		public Target getTarget() {
			return target;
		}

		/**
		 * @param target
		 *            the target to set
		 */
		public void setTarget(Target target) {
			this.target = target;
		}
	}

	public void xtest() {
		Boolean b1 = true;
		Boolean b2 = false;
		assertTrue(b1.equals(true));
		assertTrue(b2.equals(false));
		assertTrue(b1.equals(true) && b2.equals(false));
		assertFalse(b1.equals(true) && b2.equals(true));
		assertFalse((b1 == null || b1.equals(true))
				&& (b2 == null || b2.equals(true)));

	}

	public void testValidator() {
		ValidatorI.FactoryI vf = this.container.find(ValidatorI.FactoryI.class,
				true);
		ValidatorI<Context> v = vf.createValidator();
		v.addExpression(Path.valueOf("test"),"target.string1=='string1-value'");
		v.addExpression(Path.valueOf("test"),"target.pts1.property['string2']=='string2-value'");

		Target target = new Target();
		target.string1 = "string1-value";
		target.pts1 = new MapProperties<Object>();
		target.pts1.setProperty("string2", "string2-value");
		Context ctx = new Context();
		ctx.target = target;
		// do validate
		ValidateResult<Context> vr = v.validate(ctx);
		//
		List<ValidateItem<Context>> il = vr.getItemList();
		assertEquals(2, il.size());
		ValidateItem<Context> vi0 = il.get(0);
		assertTrue(vi0.isEvaluated());
		assertTrue(vi0.isValid());

		ValidateItem<Context> vi1 = il.get(1);
		assertTrue(vi1.isEvaluated());
		assertTrue(vi1.isValid());

		il = vr.getItemList(true, true);
		assertEquals(2, il.size());
		il = vr.getItemList(true, false);
		assertEquals(0, il.size());

		// test not meet.
		target.pts1.setProperty("string3", "string3-value");
		v.addExpression(Path.valueOf("test"),"target.pts1.property['string3']=='no-value'");
		//
		vr = v.validate(ctx);
		il = vr.getItemList(true, false);
		assertEquals(1, il.size());
		vi0 = il.get(0);
		// assertEquals();
	}
}
