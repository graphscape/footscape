/*
 * Created by wu on 2007-6-20.
 * Shanghai Biheng Software Co., Ltd
 */
package com.fs.commons.impl.freemarker;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.impl.freemarker.wrapper.CalendarModel;
import com.fs.commons.impl.freemarker.wrapper.DateModel;
import com.fs.commons.impl.freemarker.wrapper.PropertiesModel;
import com.fs.commons.impl.freemarker.wrapper.ThrowableModel;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.util.ModelCache;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author wu
 * 
 */
public class DataWrapper extends BeansWrapper {

	private Log LOG = LogFactory.getLog(DataWrapper.class);

	private final ModelCache modelCache = new ModelCache(this);

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemarker.ext.beans.BeansWrapper#wrap(java.lang.Object)
	 */
	@Override
	public TemplateModel wrap(Object object) throws TemplateModelException {

		if (object != null) {
			if (object instanceof Throwable) {
				return modelCache.getInstance(object, ThrowableModel.FACTORY);
			} else if (object instanceof Calendar) {
				return modelCache.getInstance(object, CalendarModel.FACTORY);
			} else if (object instanceof Date) {
				return modelCache.getInstance(object, DateModel.FACTORY);
			} else if (object instanceof PropertiesI) {
				return modelCache.getInstance(object, PropertiesModel.FACTORY);
			}
		}
		return super.wrap(object);

	}
}
