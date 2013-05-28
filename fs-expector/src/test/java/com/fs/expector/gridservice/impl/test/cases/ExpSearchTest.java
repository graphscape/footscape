/**
 *  Jan 21, 2013
 */
package com.fs.expector.gridservice.impl.test.cases;

import java.util.List;

import com.fs.commons.api.client.BClientFactoryI.ProtocolI;
import com.fs.expector.gridservice.api.mock.MockExpItem;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.impl.test.cases.support.AuthedTestBase;
import com.fs.webcomet.api.WebCometComponents;

/**
 * @author wuzhen <code>
 curl -XGET 'http://localhost:9200/nodes/expectation/_search?pretty=true' -d '{
    "query": {
        "match_all":{}
    }
 }'
																		   hello,user2 is expecting number1, what is your expected?
 curl -XGET 'localhost:9200/nodes/_analyze?analyzer=text&pretty=true' -d '{hello,user1 is expecting number1, what is your expected?}'
 
  curl -XGET 'localhost:9200/nodes/_analyze?analyzer=text&pretty=true' -d '{hello, expecting,number1,expected?}'

 
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

 curl -XGET 'http://localhost:9200/nodes/expectation/_search?pretty=true' -d '{
  "from" : 0,
  "size" : 30,
  "query" : {
    "bool" : {
      "must" : [ {
        "term" : {
          "type_" : "expectation"
        }
      }, {
        "match" : {
          "body" : {
            "query" : "hello, expecting,number1,expected?",
            "type" : "phrase",
            "operator" : "AND",
            "slop" : 5
          }
        }
      } ]
    }
  },
  "explain" : true
}'
 * </code>
 */
public class ExpSearchTest extends AuthedTestBase {

	/**
	 * @param p
	 */
	protected ExpSearchTest(ProtocolI p) {
		super(p);
	}

	public ExpSearchTest() {
		this(WebCometComponents.AJAX);
	}

	private MockExpectorClient client1;

	private int totalExp = 10;

	private MockExpectorClient client2;

	public void testExpSearch() {

		this.client1 = this.newClient("user1@domain1.com", "user1");

		this.client2 = this.newClient("user2@domain2.com", "user2");

		int max = totalExp * 3;

		for (int i = 0; i < totalExp; i++) {

			String body1 = "hello,user1 is expecting number" + i + ", what is your expected?";

			String body2 = "hello,user2 is expecting number" + i + ", what is your expected?";

			String expId1 = this.client1.newExp(body1);

			String expId2 = this.client2.newExp(body2);

		}
		// search by client 1

		String phrase = "user1 is expecting number0";// under 3

		List<MockExpItem> meL = this.client1.search(true, 0, totalExp * 3, null, phrase, 3);

		//
		assertEquals("", 1, meL.size());

		// test long slop
		// TODO explain slop why is 4/5?
		meL = this.client1.search(true, 0, max, null, "hello, expecting,number1,expected?", 4);
		assertEquals("should be no match for slop=4", 0, meL.size());

		// test long slop
		meL = this.client1.search(true, 0, max, null, "hello, expecting,number1,expected?", 5);
		assertEquals("should be 2 match for slop=5", 2, meL.size());

		// test not include mine exp
		meL = this.client2.search(false, 0, max, null, "hello, expecting,number1", 3);
		assertEquals("", 1, meL.size());
		MockExpItem m0 = meL.get(0);
		assertNotSame("should no include exp from client2 ", this.client2.getAccountId(), m0.getAccountId());
	}
}
