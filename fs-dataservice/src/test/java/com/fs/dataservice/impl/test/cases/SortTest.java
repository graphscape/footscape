/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 18, 2012
 */
package com.fs.dataservice.impl.test.cases;

import java.util.Map;

import com.fs.dataservice.api.expapp.wrapper.Profile;
import com.fs.dataservice.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class SortTest extends TestBase {

	public void testSortOnTimetamp() {
		Profile p = new Profile().forCreate(this.datas);
		p.setAge(1);
		// p.setEmail("email");
		p.setGender("gender1");
		p.setIcon("icon1");
		p.setAccountId("acc1");
		p.save();

		p = new Profile().forCreate(this.datas);
		p.setAge(2);
		// p.setEmail("email");
		p.setGender("gender2");
		p.setIcon("icon2");
		p.setAccountId("acc2");
		p.save(true);

		Profile p2 = this.datas.getNewest(Profile.class, Profile.ACCOUNTID,
				"acc2", false);
		Map m1 = p.getTarget().getAsMap();
		Map m2 = p2.getTarget().getAsMap();

		assertEquals("p:" + m1 + ",p2:" + m2, p.getUniqueId(), p2.getUniqueId());

	}
}
