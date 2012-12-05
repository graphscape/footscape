/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 2, 2012
 */
package com.fs.dataservice.api.core.conf;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.value.ErrorInfos;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public class FieldConfig {

	private NodeConfig nodeConfig;

	private String name;

	private boolean manditory;// manditory when create.

	private List<FieldValidatorI> validatorList = new ArrayList<FieldValidatorI>();

	/**
	 * @param name2
	 */
	public FieldConfig(NodeConfig nc, String name2) {
		this(nc, name2, true);//
	}

	public FieldConfig(NodeConfig nc, String name2, boolean mand) {
		this.nodeConfig = nc;
		this.name = name2;
		this.manditory = mand;

	}

	public void addValiditor(FieldValidatorI fv) {
		this.validatorList.add(fv);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the manditory
	 */
	public boolean isManditory() {
		return manditory;
	}

	public void validate(NodeWrapper nw, ErrorInfos eis) {
		for (FieldValidatorI fv : this.validatorList) {
			fv.validate(this,nw, eis);
		}
	}

	/**
	 * @return the nodeConfig
	 */
	public NodeConfig getNodeConfig() {
		return nodeConfig;
	}
}
