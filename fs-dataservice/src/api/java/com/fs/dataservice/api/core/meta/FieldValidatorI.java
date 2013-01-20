/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 28, 2012
 */
package com.fs.dataservice.api.core.meta;

import com.fs.commons.api.value.ErrorInfos;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public interface FieldValidatorI {

	public void validate(FieldMeta fc, NodeWrapper nw, ErrorInfos eis);

}
