/**
 * Jul 22, 2012
 */
package com.fs.commons.impl.validator;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.jexl.JexlEngineI;
import com.fs.commons.api.validator.ValidatorI;

/**
 * @author wu
 * 
 */
public class ValidatorFactoryImpl extends ConfigurableSupport implements
		ValidatorI.FactoryI {

	protected JexlEngineI jexl;

	/* */
	@Override
	public <T> ValidatorI<T> createValidator() {

		return new ValidatorImpl<T>(this.jexl);

	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);

		this.jexl = ac.getContainer().find(JexlEngineI.class, true);
	}

}
