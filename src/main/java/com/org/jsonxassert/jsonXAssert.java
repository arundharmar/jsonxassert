package com.org.jsonxassert;

import org.json.JSONArray;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static org.testng.Assert.assertEquals;


/*
Created by kon7017 - This is a custom built workaround for ignoring few nodes while comparing JSON strings.

Use it with caution as it is not completely tested. Feel free to upgrade.
 */
public class jsonXAssert {

    public static void jsonXAssertEquals(String message, String actualJSON, String expectedJSON, JSONCompareMode strict){
        System.out.println("***** " + message + " JSON Comparison *****");
        System.out.println("Actual JSON : " + actualJSON);
        System.out.println("Expected JSON : " + expectedJSON);
        if(!expectedJSON.equalsIgnoreCase("")) {
            JSONAssert.assertEquals(message, expectedJSON, actualJSON, strict);
        }else{
            assertEquals(actualJSON, expectedJSON, message);
        }
    }

    public static void jsonXAssertEqualsIgnoreNodes(String message, String actualJSON, String expectedJSON, String[] ignoreNodes, JSONCompareMode strict){
        System.out.println("***** " + message + " JSON Comparison(With Ignore Nodes) *****");
        System.out.println("Actual JSON : " + actualJSON);
        System.out.println("Expected JSON : " + expectedJSON);
        if(!expectedJSON.equalsIgnoreCase("")) {
            actualJSON = removeIgnoredNodes(actualJSON, ignoreNodes);
            expectedJSON = removeIgnoredNodes(expectedJSON, ignoreNodes);

            System.out.println("C - Actual JSON : " + actualJSON);
            System.out.println("C - Expected JSON : " + expectedJSON);

            JSONAssert.assertEquals(message, expectedJSON, actualJSON, strict);
        }else{
            assertEquals(actualJSON, expectedJSON, message);
        }
    }

    public static void jsonXAssertEqualsIgnoreValues(String message, String actualJSON, String expectedJSON, String[] ignoreValueForNodes, JSONCompareMode strict){
        System.out.println("***** " + message + " JSON Comparison(With Ignore Nodes) *****");
        System.out.println("Actual JSON : " + actualJSON);
        System.out.println("Expected JSON : " + expectedJSON);
        if(!expectedJSON.equalsIgnoreCase("")) {
            actualJSON = populateDefaultValueForNodes(actualJSON, ignoreValueForNodes);
            expectedJSON = populateDefaultValueForNodes(expectedJSON, ignoreValueForNodes);

            System.out.println("C - Actual JSON : " + actualJSON);
            System.out.println("C - Expected JSON : " + expectedJSON);

            JSONAssert.assertEquals(message, expectedJSON, actualJSON, strict);
        }else{
            assertEquals(actualJSON, expectedJSON, message);
        }
    }

    public static void jsonXAssertEqualsPartial(String message, String actualJSON, String expectedJSON, String[] ignoreNodes, String[] ignoreValueForNodes, JSONCompareMode strict){
        System.out.println("***** " + message + " JSON Comparison(With Ignore Nodes) *****");
        System.out.println("Actual JSON : " + actualJSON);
        System.out.println("Expected JSON : " + expectedJSON);
        if(!expectedJSON.equalsIgnoreCase("")) {
            actualJSON = removeIgnoredNodes(actualJSON, ignoreNodes);
            expectedJSON = removeIgnoredNodes(expectedJSON, ignoreNodes);

            actualJSON = populateDefaultValueForNodes(actualJSON, ignoreValueForNodes);
            expectedJSON = populateDefaultValueForNodes(expectedJSON, ignoreValueForNodes);

            System.out.println("C - Actual JSON : " + actualJSON);
            System.out.println("C - Expected JSON : " + expectedJSON);

            JSONAssert.assertEquals(message, expectedJSON, actualJSON, strict);
        }else{
            assertEquals(actualJSON, expectedJSON, message);
        }
    }

    private static String populateDefaultValueForNodes(String inputJSON, String[] ignoreValueForNodes){
        String returnJSON;
        int i = 0;
        while (i < ignoreValueForNodes.length) { // Iterate through the list of ignore nodes
            try {
                JSONObject actualJSONObject = new JSONObject(inputJSON);
                traverseJSONObjectPopulateDefault(actualJSONObject, ignoreValueForNodes[i]);
                inputJSON = actualJSONObject.toString();
            }
            catch(Exception e){
                //System.out.println("Warning! JSON Ignore Nodes Exception: " + e.getMessage());
            }
            System.out.println("After populating defaults : " + inputJSON);
            i++;
        }
        returnJSON = inputJSON;
        return returnJSON;
    }

    private static void traverseJSONObjectPopulateDefault(JSONObject jsonObject, String path){ //Recursive Function
        String[] pathArray = path.split("\\.");
        String currentNode = pathArray[0];

        //Get remaining path
        int i = 1;
        StringBuilder remainingPath = new StringBuilder();
        while(i < pathArray.length){
            if(i == pathArray.length-1) remainingPath.append(pathArray[i]);
            else remainingPath.append(pathArray[i]).append(".");
            i++;
        }

        if(!currentNode.contains("[")) { //Not an array
            if(0 == pathArray.length - 1) { //last object
                //jsonObject.remove(currentNode);
                if(jsonObject.has(currentNode)) {
                    jsonObject.put(currentNode, "<<Value_Comparison_Ignored>>");
                }
            }
            else{
                JSONObject childObject = jsonObject.getJSONObject(currentNode); //Get the child object
                traverseJSONObjectPopulateDefault(childObject, remainingPath.toString()); //recursive call
            }
        }
        else { //It is an array
            String strIndex = currentNode.substring(currentNode.indexOf("[") + 1, currentNode.indexOf("]"));
            String arrayName = currentNode.substring(0, currentNode.indexOf("["));
            JSONArray jsonArray =  jsonObject.getJSONArray(arrayName);
            if(!strIndex.equalsIgnoreCase("*")){ //Traverse a specific Node
                int index = Integer.parseInt(currentNode.substring(currentNode.indexOf("[") + 1, currentNode.indexOf("]")));
                traverseJSONObjectPopulateDefault(jsonArray.getJSONObject(index), remainingPath.toString());
            }
            else{//Traverse the entire Array
                int intIndex = 0;
                while(intIndex<jsonArray.length()) {
                    traverseJSONObjectPopulateDefault(jsonArray.getJSONObject(intIndex), remainingPath.toString());
                    intIndex++;
                }
            }
        }
    }

    private static String removeIgnoredNodes(String inputJSON, String[] ignoreNodes){
        String returnJSON;
        int i = 0;
        while (i < ignoreNodes.length) { // Iterate through the list of ignore nodes
            try {
                JSONObject actualJSONObject = new JSONObject(inputJSON);
                traverseJSONObject(actualJSONObject, ignoreNodes[i]);
                inputJSON = actualJSONObject.toString();
            }
            catch(Exception e){
                //System.out.println("Warning! JSON Ignore Nodes Exception: " + e.getMessage());
            }
            //System.out.println("After removing Nodes : " + inputJSON);
            i++;
        }
        returnJSON = inputJSON;
        return returnJSON;
    }

    private static void traverseJSONObject(JSONObject jsonObject, String path){ //Recursive Function
        String[] pathArray = path.split("\\.");
        String currentNode = pathArray[0];

        //Get remaining path
        int i = 1;
        StringBuilder remainingPath = new StringBuilder();
        while(i < pathArray.length){
            if(i == pathArray.length-1) {
                remainingPath.append(pathArray[i]);
            }
            else{
                remainingPath.append(pathArray[i]).append(".");
            }
            i++;
        }

        if(!currentNode.contains("[")) { //Not an array
            if(0 == pathArray.length - 1) { //last object
                jsonObject.remove(currentNode);
            }
            else{
                JSONObject childObject = jsonObject.getJSONObject(currentNode); //Get the child object
                traverseJSONObject(childObject, remainingPath.toString()); //recursive call
            }
        }
        else { //It is an array
            String strIndex = currentNode.substring(currentNode.indexOf("[") + 1, currentNode.indexOf("]"));
            String arrayName = currentNode.substring(0, currentNode.indexOf("["));
            JSONArray jsonArray =  jsonObject.getJSONArray(arrayName);
            if(!strIndex.equalsIgnoreCase("*")){ //Traverse a specific Node
                int index = Integer.parseInt(currentNode.substring(currentNode.indexOf("[") + 1, currentNode.indexOf("]")));
                traverseJSONObject(jsonArray.getJSONObject(index), remainingPath.toString());
            }
            else{//Traverse the entire Array
                int intIndex = 0;
                while(intIndex<jsonArray.length()) {
                    traverseJSONObject(jsonArray.getJSONObject(intIndex), remainingPath.toString());
                    intIndex++;
                }
            }
        }
    }
}
