/**
 * Jul 26, 2012
 */
package com.fs.engine.impl.scenario;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.ClassUtil;

/**
 * @author wu
 * @see ScenarioImpl#run
 */
public class TemplateHelper {

	private ContainerI container;
	
	public TemplateHelper(ContainerI container) {
		this.container = container;
	}
	
	public Object newInstance(String cname){
		Class cls = forName(cname);
		return ClassUtil.newInstance(cls);
		
	}
	public Class forName(String cname) {
		return ClassUtil.forName(cname);
	}

	/**
	 * @return the container
	 */
	public ContainerI getContainer() {
		return container;
	}
}
