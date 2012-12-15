/**
 * Jun 19, 2012
 */
package com.fs.commons.api.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.AttachableI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.config.Configuration;

/**
 * @author wu
 *         <p>
 *         active->attach->deattach->deactive;
 *         <p>
 *         active means its ready to work,the only thing is to attach to the
 *         env.
 */
public abstract class SPISupport implements SPI {

	protected String id;

	protected List<String> dependenceList;
	
	protected Configuration config;

	public SPISupport(String id) {
		this.id = id;
		this.config = Configuration.properties(id);//
		this.dependenceList = new ArrayList<String>();// TODO
		
	}

	/* */
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public List<String> getDependenceList() {

		return this.dependenceList;

	}

	@Override
	public void active(ActiveContext ac) {
		this.doActive(ac);
	}

	@Override
	public void deactive(ActiveContext ac) {
		List<Object> objL = ac.getContainer().finder(Object.class).spi(this)
				.find();
		// deattach
		for (Object obj : objL) {
			if (obj instanceof AttachableI) {
				AttachableI att = (AttachableI) obj;
				if (att.isAttached()) {
					att.dettach();
				}
			}
		}
		// deactive
		for (Object obj : objL) {
			// deactive
			if (obj instanceof ActivableI) {
				((ActivableI) obj).deactive(ac);
			}
		}
		// remove
		for (Object obj : objL) {
			ac.getContainer().removeObject(obj);// dettach from env.
			// deactive
		}
		this.doDeactive(ac);

	}

	public abstract void doActive(ActiveContext ac);

	protected abstract void doDeactive(ActiveContext ac);

}
