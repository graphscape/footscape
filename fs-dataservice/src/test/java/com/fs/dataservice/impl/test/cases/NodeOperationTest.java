/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.impl.test.cases;

import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.operations.NodeCreateOperationI;
import com.fs.dataservice.api.core.operations.NodeGetOperationI;
import com.fs.dataservice.api.core.result.NodeCreateResultI;
import com.fs.dataservice.api.core.result.NodeResultI;
import com.fs.dataservice.api.expapp.NodeTypes;
import com.fs.dataservice.api.expapp.wrapper.SignupRequest;
import com.fs.dataservice.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class NodeOperationTest extends TestBase {

	public void testNodeOperations() {

		NodeCreateOperationI nco = this.datas
				.prepareOperation(NodeCreateOperationI.class);
		nco.nodeType(NodeTypes.SIGNUP_REQUEST);
		nco.uniqueId(null);//
		nco.property(SignupRequest.PK_CONFIRM_CODE, "test1");
		nco.property(SignupRequest.PK_EMAIL, "email1");
		nco.property(SignupRequest.PK_NICK, "nick1");
		nco.property(SignupRequest.PK_PASSWORD, "password1");

		NodeCreateResultI rst = nco.execute().getResult();
		rst.assertNoError();
		String uid = rst.get(true);

		assertNotNull("uid is null", uid);

		//
		NodeGetOperationI ngo = this.datas
				.prepareOperation(NodeGetOperationI.class)
				.nodeType(NodeTypes.SIGNUP_REQUEST).uniqueId(uid).execute()
				.cast();
		NodeResultI rst2 = ngo.getResult().cast();

		NodeI node = rst2.getNode(true);
		String uid2 = node.getUniqueId();
		assertEquals("uniqueId not correct", uid, uid2);
		String uname = (String) node.getProperty(SignupRequest.PK_CONFIRM_CODE);
		assertEquals("property issue.", "test1", uname);
	}

	public void testBulk() {
		// BulkResponse br = null;
	}
}
