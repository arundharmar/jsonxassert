package com.org.jsonxassert;

import static com.org.jsonxassert.jsonXAssert.jsonXAssertEquals;
import static com.org.jsonxassert.jsonXAssert.jsonXAssertEqualsIgnoreNodes;
import static com.org.jsonxassert.jsonXAssert.jsonXAssertEqualsIgnoreValues;
import static com.org.jsonxassert.jsonXAssert.jsonXAssertEqualsPartial;

import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.*;


public class jsonXAssertTest {

    //jsonXAssertEquals
    @Test
    public void testJsonXAssertEquals_Pass(){
        String json1 = "{\"value\" : 2}";
        String json2 = "{\"value\" : 2}";
        jsonXAssertEquals("Response Match : ", json1, json2, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = java.lang.AssertionError.class)
    public void testJsonXAssertEquals_Fail(){
        String json1 = "{\"value\" : 1}";
        String json2 = "{\"value\" : 2}";
        jsonXAssertEquals("Response Match : ", json1, json2, JSONCompareMode.NON_EXTENSIBLE);
    }

    //jsonXAssertEqualsIgnoreNodes
    @Test
    public void testJsonXAssertEqualsIgnoreNodes_Pass(){
        String json1 = "{\"value\" : 1, \"value2\" : 2}";
        String json2 = "{\"value\" : 1}";
        String[] ignoredNodes = new String[1];
        ignoredNodes[0] = "value2";
        jsonXAssertEqualsIgnoreNodes("Response Match : ", json1, json2, ignoredNodes, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = java.lang.AssertionError.class)
    public void testJsonXAssertEqualsIgnoreNodes_Fail(){
        String json1 = "{\"value\" : 1, \"value2\" : 2}";
        String json2 = "{\"value\" : 2, \"value2\" : 4}";
        String[] ignoredNodes = new String[1];
        ignoredNodes[0] = "value2";
        jsonXAssertEqualsIgnoreNodes("Response Match : ", json1, json2, ignoredNodes, JSONCompareMode.NON_EXTENSIBLE);
    }

    //jsonXAssertEqualsIgnoreValues
    @Test
    public void testJsonXAssertEqualsIgnoreValues_Pass(){
        String json1 = "{\"value\" : 1, \"value2\" : 2}";
        String json2 = "{\"value\" : 1, \"value2\" : 3}";
        String[] ignoredValues = new String[1];
        ignoredValues[0] = "value2";
        jsonXAssertEqualsIgnoreValues("Response Match : ", json1, json2, ignoredValues, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = java.lang.AssertionError.class)
    public void testJsonXAssertEqualsIgnoreValues_Fail(){
        String json1 = "{\"value\" : 1, \"value2\" : 2}";
        String json2 = "{\"value\" : 2, \"value2\" : 3}";
        String[] ignoredValues = new String[1];
        ignoredValues[0] = "value2";
        jsonXAssertEqualsIgnoreValues("Response Match : ", json1, json2, ignoredValues, JSONCompareMode.NON_EXTENSIBLE);
    }

    //jsonXAssertEqualsPartial
    @Test
    public void testJsonXAssertEqualsPartial_Pass(){
        String json1 = "{\"value\" : 1, \"value2\" : 2, \"value3\" : 2}";
        String json2 = "{\"value\" : 1, \"value2\" : 3}";
        String[] ignoredNodes = new String[1];
        ignoredNodes[0] = "value3";
        String[] ignoredValues = new String[1];
        ignoredValues[0] = "value2";
        jsonXAssertEqualsPartial("Response Match : ", json1, json2, ignoredNodes, ignoredValues, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = java.lang.AssertionError.class)
    public void testJsonXAssertEqualsPartial_Fail(){
        String json1 = "{\"value\" : 1, \"value2\" : 2, \"value3\" : 2}";
        String json2 = "{\"value\" : 2, \"value2\" : 3}";
        String[] ignoredNodes = new String[1];
        ignoredNodes[0] = "value2";
        String[] ignoredValues = new String[1];
        ignoredValues[0] = "value2";
        jsonXAssertEqualsPartial("Response Match : ", json1, json2, ignoredNodes, ignoredValues, JSONCompareMode.NON_EXTENSIBLE);
    }


}
