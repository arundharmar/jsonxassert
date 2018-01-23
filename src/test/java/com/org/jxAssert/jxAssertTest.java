package com.org.jxAssert;

import static com.org.jxAssert.jxAssert.jxAssertEquals;
import static com.org.jxAssert.jxAssert.jxAssertEqualsIgnoreNodes;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.*;


public class jxAssertTest {

    @Test
    public void testSimpleJSON_PASS(){
        String json1 = "{\"value\" : 1}";
        String json2 = "{\"value\" : 1}";
        jxAssertEquals ("Response Match : ", json1, json2);
    }

    @Test
    public void testIgnoreNodes(){
        String json1 = "{\"value\" : 1, \"value2\" : 2}";
        String json2 = "{\"value\" : 1, \"value2\" : 4}";
        String[] ignoredNodes = new String[1];
        ignoredNodes[0] = "value2";
        //jsonAassertEqualsIgnoreNodes ("Response Match : ", json1, json2, ignoredNodes);
    }

    @Test
    public void testIgnoreNodesNotPresent(){
        String json1 = "{\"value\" : 1, \"value2\" : 2}";
        String json2 = "{\"value\" : 1}";
        String[] ignoredNodes = new String[1];
        ignoredNodes[0] = "value2";
        //jsonAassertEqualsIgnoreNodes ("Response Match : ", json1, json2, ignoredNodes);
    }

    @Test
    public void testIgnoreValuesNotPresent(){
        String json1 = "{\"value\" : 1, \"value2\" : 2}";
        String json2 = "{\"value\" : 1, \"value2\" : 5}";
        String[] ignoredNodes = new String[1];
        ignoredNodes[0] = "value3";
        String[] ignoredValues = new String[1];
        ignoredValues[0] = "value2";
        jxAssertEqualsIgnoreNodes ("Response Match : ", json1, json2, ignoredNodes, ignoredValues);
    }

}
