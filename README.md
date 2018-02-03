jsonXAssert 
===================

Usage
---
Add the jar file (```\jar\jsonxassert_1.0.0.jar```) in your dependency

Description
---
This library has methods which can be used to do partial JSON assertion based on the JSONAssert library.

There are 4 methods that are exposed in this library.

* **jxAssertEquals** - Assert 2 JSON completely without ignoring any nodes
* **jxAssertEqualsIgnoreNodes** - Assert 2 JSON by ignoring a few nodes completely.
* **jxAssertEqualsIgnoreValues** - Assert 2 JSON by ignoring values in few fields.
* **jxAssertEqualsPartial** - Assert 2 JSON by ignoring few values and ignoring few nodes.

Sample of how to use these are below. More samples are in the test file.

* json1 and json2 are JSON strings
* ignoredNodes and ignoredValues are string arrays holding the JsonPath of the fields to be ignored.

jxAssertEquals
---
```
        String json1 = "{\"value\" : 2}";
        String json2 = "{\"value\" : 1}";
        jsonXAssertEquals("Response Match : ", json1, json2, JSONCompareMode.NON_EXTENSIBLE);
```

jxAssertEqualsIgnoreNodes
---
```
        String json1 = "{\"value\" : 1, \"value2\" : 2}";
        String json2 = "{\"value\" : 1}";
        String[] ignoredNodes = new String[1];
        ignoredNodes[0] = "value2";
        jsonXAssertEqualsIgnoreNodes("Response Match : ", json1, json2, ignoredNodes, JSONCompareMode.NON_EXTENSIBLE);
```

jxAssertEqualsIgnoreValues
---
```
        String json1 = "{\"value\" : 1, \"value2\" : 2}";
        String json2 = "{\"value\" : 1, \"value2\" : 3}";
        String[] ignoredValues = new String[1];
        ignoredValues[0] = "value2";
        jsonXAssertEqualsIgnoreValues("Response Match : ", json1, json2, ignoredValues, JSONCompareMode.NON_EXTENSIBLE);
```

jxAssertEqualsPartial
---
```
        String json1 = "{\"value\" : 1, \"value2\" : 2, \"value3\" : 2}";
        String json2 = "{\"value\" : 1, \"value2\" : 3}";
        String[] ignoredNodes = new String[1];
        ignoredNodes[0] = "value3";
        String[] ignoredValues = new String[1];
        ignoredValues[0] = "value2";
        jsonXAssertEqualsPartial("Response Match : ", json1, json2, ignoredNodes, ignoredValues, JSONCompareMode.NON_EXTENSIBLE);
```


Version History
-

0.0.1 - Initial Version (stable)

---

Contact @arundharmar ( :mailbox:  <arundharmar@gmail.com>) for any queries.

- - - - 