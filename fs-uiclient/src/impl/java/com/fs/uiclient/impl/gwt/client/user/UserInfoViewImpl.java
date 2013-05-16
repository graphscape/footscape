/**
 *  
 */
package com.fs.uiclient.impl.gwt.client.user;

import com.fs.uiclient.api.gwt.client.user.UserInfo;
import com.fs.uiclient.api.gwt.client.user.UserInfoViewI;
import com.fs.uicommons.api.gwt.client.mvc.simple.LightWeightView;
import com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.BodyI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.CellI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.RowI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.ImageUrl;

/**
 * @author wu
 * Show the user(any user) 's information.
 */
public class UserInfoViewImpl extends LightWeightView implements UserInfoViewI {

	private TableI table;

	/**
	 * @param ctn
	 */
	public UserInfoViewImpl(ContainerI ctn) {
		super(ctn);

	}

	@Override
	public void setUserInfo(UserInfo ui) {
		if (this.table != null) {
			this.table.parent(null);//
		}

		this.table = this.factory.create(TableI.class);
		this.table.getElement().addClassName("user-info");
		this.table.parent(this);
		
		int rows = 3;//note
		BodyI body = this.table.getBody();
		{// row 1
			RowI r = body.createRow();
			{// cell1
				CellI cell = r.createCell().rowspan(rows);
				cell.getElement().addClassName("user-icon");
				AnchorWI ar = this.factory.create(AnchorWI.class);
				String src =  ui.getIconImageUrl().getAsSrc(this.getClient(true));
				
				ar.getElement().addClassName("user-icon");

				ar.setImage(src);

				cell.child(ar);
			}
			{
				CellI cell = r.createCell().rowspan(rows);
				cell.getElement().addClassName("seperator");
			}

			{// cell1
				CellI cell = r.createCell().colspan(2);
				cell.getElement().addClassName("nick");
				LabelI l = this.factory.create(LabelI.class);
				l.setText(ui.getNick());
				cell.child(l);
			}
			{
				CellI cell = r.createCell();
				cell.rowspan(rows);
				cell.getElement().addClassName("last");
				
			}
		}
		{// row 2
			RowI r = body.createRow();
			{// cell1
				CellI cell = r.createCell();
				LabelI l = this.factory.create(LabelI.class);
				l.setText("gender", true);
				cell.child(l);
			}

			{// cell1
				CellI cell = r.createCell();
				LabelI l = this.factory.create(LabelI.class);
				l.setText("" + ui.getGender());
				cell.child(l);

			}
		
		}
		{// row 3
			RowI r = body.createRow();
			{// cell1
				CellI cell = r.createCell();
				LabelI l = this.factory.create(LabelI.class);
				l.setText("age", true);
				cell.child(l);
			}

			{// cell1
				CellI cell = r.createCell();
				LabelI l = this.factory.create(LabelI.class);
				l.setText("" + ui.getAge());
				cell.child(l);

			}
			
		}
		
	}

}
