/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 12, 2013
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.uexp.ExpConnect;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.BodyI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.CellI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.RowI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.ImageUrl;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class ConnectedExpView extends ViewSupport {

	/**
	 * @param c
	 * @param ele
	 */
	public ConnectedExpView(ContainerI c, ExpConnect ec) {
		super(c, DOM.createDiv());
		TableI table = this.factory.create(TableI.class);
		table.parent(this);
		BodyI bd = table.getBody();
		{// row 1
			RowI r = bd.createRow();
			// cell00,10
			CellI c00 = r.createCell();
			c00.rowspan(2);
			//
			{

				String accId = ec.getAccountId1();
				ImageUrl icon = ec.getIcon1AsImageUrl();
				UserIconView iv = new UserIconView(c, accId, icon);
				iv.parent(c00);
			}
			// cell01
			{
				CellI c01 = r.createCell();
				LabelI l2 = this.factory.create(LabelI.class);
				l2.setText(ec.getExpTitle2(true));
				l2.setTitle(ec.getExpBody2(true));//
				l2.parent(c01);
			}

		}
		{// row 2
			RowI r = bd.createRow();
			// cell 11
			CellI c11 = r.createCell();
			LabelI l1 = this.factory.create(LabelI.class);
			l1.getElement().addClassName("user-nick");
			l1.setText(ec.getNick2(true) + "");
			l1.parent(c11);
		}

		//

	

	}

}
