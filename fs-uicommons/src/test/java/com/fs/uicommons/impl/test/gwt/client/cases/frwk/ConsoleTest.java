/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.frwk;

import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ConsoleTest extends TestBase {

	public void testConsoleOpen() {
		FrwkModelI fc = this.client.getChild(FrwkModelI.class, true);

		HeaderModelI hc = fc.getHeader();

		System.out.println(this.client.dump());
		System.out.println(this.root.dump());

	}
}
