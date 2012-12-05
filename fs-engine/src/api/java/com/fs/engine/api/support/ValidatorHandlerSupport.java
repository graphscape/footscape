/**
 * Jul 22, 2012
 */
package com.fs.engine.api.support;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.converter.support.ConverterSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.validator.ValidateItem;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;

/**
 * @author wu
 * 
 */
public class ValidatorHandlerSupport extends HandlerSupport {

	protected ValidatorI.FactoryI validatorFactory;

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.validatorFactory = ac.getContainer().find(
				ValidatorI.FactoryI.class, true);
		// validators

		/**
		 * <code> TODO
		Configuration vcf = Configuration.properties(this.configId
				+ ".validator");
		
		List<PropertiesI<String>> conditions = vcf.parseAsPropertiesList(
				"condition", new String[] { "expression" });
		for (PropertiesI<String> cps : conditions) {
			
		}</code>
		 */
	}

	@Override
	protected ConverterI getDefaultConverter(final Class ptype,
			final MethodWrapper me) {
		if (ValidatorI.class.isAssignableFrom(ptype)) {
			return this.getValidatorConverter(me);//
		} else if (ValidateResult.class.isAssignableFrom(ptype)) {
			return this.getValidateResultConverter(me);
		} else {
			return null;
		}
	}

	protected ConverterI getValidateResultConverter(final MethodWrapper me) {

		// validator
		return new ConverterSupport<HandleContextI, ValidateResult>(
				HandleContextI.class, ValidateResult.class, null) {

			@Override
			public ValidateResult convert(HandleContextI f) {

				return ValidatorHandlerSupport.this.getValidateResult(me, f);

			}
		};
	}

	protected ConverterI getValidatorConverter(final MethodWrapper me) {

		// validator
		return new ConverterSupport<HandleContextI, ValidatorI>(
				HandleContextI.class, ValidatorI.class, null) {

			@Override
			public ValidatorI convert(HandleContextI f) {

				return ValidatorHandlerSupport.this.getValidator(me);

			}
		};
	}

	/**
	 * Validate and convert to error info,put into response.
	 * 
	 * @param me
	 * @param c
	 * @return
	 */
	protected ValidateResult<RequestI> getValidateResult(MethodWrapper me,
			HandleContextI c) {
		ValidatorI<RequestI> v = this.getValidator(me, true);
		RequestI req = c.getRequest();
		ResponseI res = c.getResponse();
		ValidateResult<RequestI> rt = v.validate(req);

		List<ValidateItem<RequestI>> viL = rt.getItemList(true, false);
		ErrorInfos eis = res.getErrorInfos();//

		for (ValidateItem<RequestI> vi : viL) {// TODO ?
			eis.add(new ErrorInfo(vi.getCondition().toString(),
					"validate failed"));
		}

		return rt;
	}

	protected ValidatorI<RequestI> getValidator(MethodWrapper me) {
		return this.getValidator(me, false);//
	}

	protected ValidatorI<RequestI> getValidator(MethodWrapper me, boolean force) {
		ValidatorI<RequestI> rt = (ValidatorI<RequestI>) me
				.getProperty("VALIDATOR");

		if (force && rt == null) {
			throw new FsException("no validator found for:" + me + ",handler:"
					+ this);
		}

		return rt;
	}

	protected ValidatorI<RequestI> createValidator(String key) {
		ValidatorI<RequestI> rt = this.validatorFactory.createValidator();
		MethodWrapper me = this.getMethodWrapper(key, true);
		me.setProperty("VALIDATOR", rt);
		return rt;

	}
}
