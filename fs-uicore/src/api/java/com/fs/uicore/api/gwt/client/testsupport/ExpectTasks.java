/**
 *  Feb 6, 2013
 */
package com.fs.uicore.api.gwt.client.testsupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuzhen
 *
 */
public class ExpectTasks {
	
	private List<String> taskList = new ArrayList<String>();
	
	public void add(String task){
		this.taskList.add(task);
	}
	
}
