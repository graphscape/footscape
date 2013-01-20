/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 19, 2013
 */
package com.fs.dataservice.impl.test.cases;

import java.util.List;

import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.impl.test.MockNode;
import com.fs.dataservice.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class MatchQueryTest extends TestBase {
	/**
	 * <code>
	 
curl -XGET 'http://localhost:9200/node-index/mockNode/_search?pretty=true' -d '
{
  "from" : 0,
  "size" : 100,
  "query" : {
    "bool" : {
      "must" : {
        "term" : {
          "type_" : "mockNode"
        }
      },
      "must" : {
        "match" : {
          "field3" : {
            "query" : "value13",
            "operator" : "AND"
          }
        }
      }
    }
  },
  "explain" : false
}'


curl -XGET 'http://localhost:9200/node-index/expectation/_search?pretty=true' -d '
{
  "from" : 0,
  "size" : 100,
  "query" : {
    "bool" : {
      "must" : {
        "term" : {
          "type_" : "expectation"
        }
      },
      "must" : {
        "match" : {
          "body" : {
            "query" : "hello",
            "operator" : "AND"
          }
        }
      }
    }
  },
  "explain" : false
}'

curl -XGET 'http://localhost:9200/node-index/expectation/_search?pretty=true' -d '
{
  "from" : 0,
  "size" : 100,
  "query" : {
    "bool" : {
      "must" : {
        "term" : {
          "_type" : "expectation"
        }
      },
      "must" : {
        "match" : {
          "body" : {
            "query" : "hello,this is a check for",
            "operator" : "AND"
          }
        }
      }
    }
  },
  "explain" : false
}'
</code> Jan 19, 2013
	 */
	public void testQueryByMatch() {
		MockNode mn1 = new MockNode().forCreate(this.datas);
		mn1.setProperty(MockNode.FIELD1, "value11");
		mn1.setProperty(MockNode.FIELD2, "value12");
		mn1.setProperty(MockNode.FIELD3, "value13 and value131 value132 value133");
		mn1.save(true);

		MockNode mn2 = new MockNode().forCreate(this.datas);
		mn2.setProperty(MockNode.FIELD1, "value21");
		mn2.setProperty(MockNode.FIELD2, "value22");
		mn2.setProperty(MockNode.FIELD3, "value23 and value231 value232 value233");
		mn2.save(true);
		NodeQueryOperationI<MockNode> qo = this.datas.prepareNodeQuery(MockNode.class);
		qo.propertyMatch(MockNode.FIELD3, "value13 value132");
		List<MockNode> mnl = qo.execute().getResult().list();
		assertEquals(1, mnl.size());
		MockNode mn = mnl.get(0);
		assertEquals(mn1, mn);
	}

}
