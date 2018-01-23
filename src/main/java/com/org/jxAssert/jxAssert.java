package com.org.jxAssert;

import org.json.JSONArray;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static org.testng.Assert.assertEquals;


/*
Created by kon7017 - This is a custom built workaround for ignoring few nodes while comparing JSON strings.

Update 10/12/2017 - kon7017 - Added support for Array
format for single node -  data.cards[0].cardId
format for multiple node -  data.cards[*].cardId

Use it with caution as it is not completely tested. Feel free to upgrade.
 */
public class jxAssert {
    public static void jxAssertEqualsIgnoreNodes(String message, String actualJSON, String expectedJSON, String[] ignoreNodes, String[] ignoreValueForNodes){
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

            JSONAssert.assertEquals(message, expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
        }else{
            assertEquals(actualJSON, expectedJSON, message);
        }
    }
    public static void jxAssertEquals(String message, String actualJSON, String expectedJSON){
        System.out.println("***** " + message + " JSON Comparison *****");
        System.out.println("Actual JSON : " + actualJSON);
        System.out.println("Expected JSON : " + expectedJSON);
        if(!expectedJSON.equalsIgnoreCase("")) {
            JSONAssert.assertEquals(message, expectedJSON, actualJSON, false);
        }else{
            assertEquals(actualJSON, expectedJSON, message);
        }
    }

    public static String populateDefaultValueForNodes(String inputJSON, String[] ignoreValueForNodes){
        String returnJSON;
        int iter = 0;
        while (iter < ignoreValueForNodes.length) { // Iterate through the list of ignore nodes
            try {
                JSONObject actualJSONObject = new JSONObject(inputJSON);
                traverseJSONObjectPopulateDefault(actualJSONObject,ignoreValueForNodes[iter].toString());
                inputJSON = actualJSONObject.toString();
            }
            catch(Exception e){
                //System.out.println("Warning! JSON Ignore Nodes Exception: " + e.getMessage());
            }
            System.out.println("After populating defaults : " + inputJSON);
            iter++;
        }
        returnJSON = inputJSON;
        return returnJSON;
    }

    public static void traverseJSONObjectPopulateDefault(JSONObject jsonObject, String path){ //Recursive Function
        String[] pathArray = path.split("\\.");
        String currentNode = pathArray[0];

        //Get remaining path
        int pathTempIter = 1;
        String remainingPath = "";
        while(pathTempIter < pathArray.length){
            if(pathTempIter == pathArray.length-1) remainingPath = remainingPath + pathArray[pathTempIter];
            else remainingPath = remainingPath + pathArray[pathTempIter] + ".";
            pathTempIter++;
        }

        if(currentNode.indexOf("[")==-1) { //Not an array
            if(0 == pathArray.length - 1) { //last object
                //jsonObject.remove(currentNode);
                if(jsonObject.has(currentNode)) {
                    jsonObject.put(currentNode, "<<Value_Comparison_Ignored>>");
                }
            }
            else{
                JSONObject childObject = jsonObject.getJSONObject(currentNode); //Get the child object
                traverseJSONObject(childObject,remainingPath); //recursive call
            }
        }
        else { //It is an array
            String strIndex = currentNode.substring(currentNode.indexOf("[") + 1, currentNode.indexOf("]"));
            String arrayName = currentNode.substring(0, currentNode.indexOf("["));
            JSONArray jsonArray =  jsonObject.getJSONArray(arrayName);
            if(!strIndex.equalsIgnoreCase("*")){ //Traverse a specific Node
                int index = Integer.parseInt(currentNode.substring(currentNode.indexOf("[") + 1, currentNode.indexOf("]")));
                traverseJSONObject(jsonArray.getJSONObject(index),remainingPath);
            }
            else{//Traverse the entire Array
                int intIndex = 0;
                while(intIndex<jsonArray.length()) {
                    traverseJSONObject(jsonArray.getJSONObject(intIndex),remainingPath);
                    intIndex++;
                }
            }
        }
    }

    public static String removeIgnoredNodes(String inputJSON, String[] ignoreNodes){
        String returnJSON;
        int iter = 0;
        while (iter < ignoreNodes.length) { // Iterate through the list of ignore nodes
            try {
                JSONObject actualJSONObject = new JSONObject(inputJSON);
                traverseJSONObject(actualJSONObject,ignoreNodes[iter].toString());
                inputJSON = actualJSONObject.toString();
            }
            catch(Exception e){
                //System.out.println("Warning! JSON Ignore Nodes Exception: " + e.getMessage());
            }
            //System.out.println("After removing Nodes : " + inputJSON);
            iter++;
        }
        returnJSON = inputJSON;
        return returnJSON;
    }

    public static void traverseJSONObject(JSONObject jsonObject, String path){ //Recursive Function
        String[] pathArray = path.split("\\.");
        String currentNode = pathArray[0];

        //Get remaining path
        int pathTempIter = 1;
        String remainingPath = "";
        while(pathTempIter < pathArray.length){
            if(pathTempIter == pathArray.length-1) {
                remainingPath = remainingPath + pathArray[pathTempIter];
            }
            else{
                remainingPath = remainingPath + pathArray[pathTempIter] + ".";
            }
            pathTempIter++;
        }

        if(currentNode.indexOf("[")==-1) { //Not an array
            if(0 == pathArray.length - 1) { //last object
                jsonObject.remove(currentNode);
            }
            else{
                JSONObject childObject = jsonObject.getJSONObject(currentNode); //Get the child object
                traverseJSONObject(childObject,remainingPath); //recursive call
            }
        }
        else { //It is an array
            String strIndex = currentNode.substring(currentNode.indexOf("[") + 1, currentNode.indexOf("]"));
            String arrayName = currentNode.substring(0, currentNode.indexOf("["));
            JSONArray jsonArray =  jsonObject.getJSONArray(arrayName);
            if(!strIndex.equalsIgnoreCase("*")){ //Traverse a specific Node
                int index = Integer.parseInt(currentNode.substring(currentNode.indexOf("[") + 1, currentNode.indexOf("]")));
                traverseJSONObject(jsonArray.getJSONObject(index),remainingPath);
            }
            else{//Traverse the entire Array
                int intIndex = 0;
                while(intIndex<jsonArray.length()) {
                    traverseJSONObject(jsonArray.getJSONObject(intIndex),remainingPath);
                    intIndex++;
                }
            }
        }
    }
}
