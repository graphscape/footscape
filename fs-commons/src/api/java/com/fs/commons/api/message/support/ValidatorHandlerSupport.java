/**
 * Jul 22, 2012
 */
package com.fs.commons.api.message.support;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.converter.support.ConverterSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.validator.ValidateItem;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;

/**
 * @author wu
 * 
 */
public class ValidatorHandlerSupport extends HandlerSupport {

	protected ValidatorI.FactoryI validatorFactory;

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.validatorFactory = top.find(ValidatorI.FactoryI.class, true);

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
		return new ConverterSupport<MessageContext, ValidateResult>(
				MessageContext.class, ValidateResult.class, null) {

			@Override
			public ValidateResult convert(MessageContext f) {

				return ValidatorHandlerSupport.this.getValidateResult(me, f);

			}
		};
	}

	protected ConverterI getValidatorConverter(final MethodWrapper me) {

		// validator
		return new ConverterSupport<MessageContext, ValidatorI>(
				MessageContext.class, ValidatorI.class, null) {

			@Override
			public ValidatorI convert(MessageContext f) {

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
	protected ValidateResult<MessageI> getValidateResult(MethodWrapper me,
			MessageContext c) {
		ValidatorI<MessageI> v = this.getValidator(me, true);
		MessageI req = c.getRequest();
		ResponseI res = c.getResponse();
		ValidateResult<MessageI> rt = v.validate(req);

		List<ValidateItem<MessageI>> viL = rt.getItemList(true, false);
		ErrorInfos eis = res.getErrorInfos();//

		for (ValidateItem<MessageI> vi : viL) {// TODO ?
			Path ecode = vi.getErrorCode();
			eis.add(new ErrorInfo(ecode));
		}

		return rt;
	}

	protected ValidatorI<MessageI> getValidator(MethodWrapper me) {
		return this.getValidator(me, false);//
	}

	protected ValidatorI<MessageI> getValidator(MethodWrapper me, boolean force) {
		ValidatorI<MessageI> rt = (ValidatorI<MessageI>) me
				.getProperty("VALIDATOR");

		if (force && rt == null) {
			throw new FsException("no validator found for:" + me + ",handler:"
					+ this);
		}

		return rt;
	}

	protected ValidatorI<MessageI> createValidator(String key) {
		ValidatorI<MessageI> rt = this.validatorFactory.createValidator();
		MethodWrapper me = this.getMethodWrapper(key, true);
		me.setProperty("VALIDATOR", rt);
		return rt;

	}
}
