/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.widget;

import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.CellI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.HeaderI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.RowI;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.commons.Holder;

/**
 * @author wu
 * 
 */
public class TableTest extends TestBase {

	public void testTable() {
		//
		TableI t = wf.create(TableI.class);
		// headers
		String[] names = new String[] { "header1,header2,header3" };
		String[][] values = new String[][] { //
		new String[] { "v01", "v02", "v03" },//
				new String[] { "v11", "v12", "v13" },//
				new String[] { "v21", "v22", "v23" },//
		//
		};
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			LabelI hw = wf.create(LabelI.class);
			HeaderI h = t.getHeaders().createHeader(name);
			h.child(hw);//
		}
		//
		for (String[] r : values) {
			RowI rw = t.getBody().createRow();
			for (String c : r) {
				CellI ci = rw.createCell();
				LabelI li = wf.create(LabelI.class);// TODO values
				ci.child(li);
			}
		}
		final Holder<Boolean> clicked = new Holder<Boolean>();
		t.parent(this.root);// NOTE

		// assertTrue("button click not notified.", clicked.getTarget());

	}
}
