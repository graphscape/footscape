/**
 *  Jan 21, 2013
 */
package com.fs.expector.gridservice.impl.test.cases;

import java.util.List;

import com.fs.expector.gridservice.api.mock.MockExpItem;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.impl.test.cases.support.AuthedTestBase;

/**
 * @author wuzhen <code>
 curl -XGET 'http://localhost:9200/nodes/expectation/_search' -d '{
    "query": {
    	"match" : {
       	 	"body" : {
            	"query" : "hello",
            	"type" : "phrase"
        	}
    	}
	}
}'
 * </code>
 */
public class ExpSearchTest extends AuthedTestBase {

	private MockExpectorClient client1;

	private int totalExp = 100;

	private MockExpectorClient client2;

	public void testExpSearch() {

		this.client1 = this.startClient("user1@domain1.com", "user1");

		this.client2 = this.startClient("user2@domain2.com", "user2");

		int max = totalExp * 3;

		for (int i = 0; i < totalExp; i++) {

			String body1 = "hello,user1 is expecting number" + i + ", what's your expected?";

			String body2 = "hello,user2 is expecting number" + i + ", what's your expected?";

			String expId1 = this.client1.newExp(body1);

			String expId2 = this.client2.newExp(body2);

		}
		// search by client 1
		String phrase = "user1 is expecting number0";

		List<MockExpItem> meL = this.client1.search(true, 0, totalExp * 2 + 100, null, phrase);

		//
		assertEquals("", 1, meL.size());
		meL = this.client1.search(true, 0, max, null, "hello, expecting, expected?");
		assertEquals(totalExp * 2, meL.size());
	}
}
