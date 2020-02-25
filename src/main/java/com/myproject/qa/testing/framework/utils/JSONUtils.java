package com.myproject.qa.testing.framework.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.myproject.qa.testing.framework.exceptions.ApplicationException;
import com.myproject.qa.testing.framework.logs.ScriptLogger;


public class JSONUtils 
{

	//Get expected no of JSONObject from a JSONArray which has large number of JSONobjects.
	@SuppressWarnings("unchecked")
	public static JSONArray getLessJSONObjects(JSONArray largeArray, int objectCount) {
		JSONArray array = new JSONArray();
		for(int i =0; i < objectCount; i++) {
			array.add(largeArray.get(i));
		}
		return array;
	}

	//KeyName from a string payload by index
	//payload is a String form of JSON
	public static String getKeyName(String payload, int index) throws Exception{	
		return (String) getKeyList(payload).get(index);		
	}

	//List of keys from a string payload
	@SuppressWarnings("unchecked")
	public static <T> List<T> getKeyList(String payload) throws Exception{	

		if(payload.startsWith("{") && payload.endsWith("}")) {
			JSONObject jsonObject = (JSONObject)toJson(payload);
			Set<T> set = new HashSet<T>(jsonObject.keySet());
			return new ArrayList<T>(set);
		} 
		else{
			throw new ApplicationException("Payload is not JSONObject"+payload);
		}	
	}

	//Returns deserialized responseObject
	// T json can be either JSONArray or JSONObject
	public static <T> T toPojo(T json, Class<T> clazz) throws Exception{
		Gson gson = new Gson();
		return gson.fromJson(json.toString(), clazz);	
	}

	//String to JSON object
	public static Object toJson(String jsonString) throws Exception {
		try {
			return (Object) (new JSONParser()).parse(jsonString);
		} catch (Exception e) {
			throw new ApplicationException(e, "Unable to parse jsonString : "+jsonString);
		}
	}


	//Get Object by key
	public static Object getObjectByKey(String jsonString, String keyOrIndex) throws Exception {
		Object object = null;
		if(jsonString.startsWith("{") && jsonString.endsWith("}")) 
			object = (JSONObject) toJson(jsonString);	
		else if(jsonString.startsWith("[") && jsonString.endsWith("]"))
			object = (JSONArray) toJson(jsonString);
		return 	getObjectByKey(object, keyOrIndex);		
	}

	//Get Object by key
	public static Object getObjectByKey(Object object, String keyOrIndex) throws Exception {
		ScriptLogger.info();
		String objectString = object.toString();
		if(objectString.startsWith("{") && objectString.endsWith("}")) {
			JSONObject jsonobject = (JSONObject) toJson(objectString);
			Object value = jsonobject.get(keyOrIndex);

			if( value.toString().startsWith("{") && value.toString().endsWith("}"))
				return (JSONObject)value;
			else if(value.toString().startsWith("[") && value.toString().endsWith("]"))
				return (JSONArray)value;
			else
				return (Object)value;
		}
		else if(objectString.startsWith("[") && objectString.endsWith("]")) {
			JSONArray jsonArray = (JSONArray) toJson(objectString);
			Object value = jsonArray.get(Integer.parseInt(keyOrIndex));

			if(value.toString().startsWith("{") &&  value.toString().endsWith("}"))
				return (JSONObject)value;
			else if(value.toString().startsWith("[") && value.toString().endsWith("]"))
				return (JSONArray)value;
			else
				return (Object)value;
		}
		else {
			throw new Exception("Object is not a json parsable");
		}			
	}

	//compare two json nodes and return either { or [ or exception.
	public static String compareJSONNodes(String jsonString1, String jsonString2) throws Exception {
		if(jsonString1.startsWith("{") && jsonString2.startsWith("{")) 
			return "{";
		else if(jsonString1.startsWith("[") && jsonString2.startsWith("["))
			return "[";
		else if(jsonString1.startsWith("{") && jsonString2.startsWith("[")) 
			ScriptLogger.info("nodes mismatch : JSON A starts with { and JSON B starts with [ ");
		else if(jsonString1.startsWith("[") && jsonString1.startsWith("}"))
			ScriptLogger.info("nodes mismatch : JSON A starts with [ and JSON B starts with  {");	
		else if(jsonString1.equals(jsonString2)) {
			return "sameKey";
		}
		return null;
	}

	//validate contains key
	public static boolean doesKeySetContainsKey(JSONObject json1,JSONObject json2, String jsonNo) {
		String keyy = null;
		for(Object key:json1.keySet()) {
			if(!(json2.containsKey((String)key))) {
				ScriptLogger.info(jsonNo+" doesn't contain "+key.toString());
				keyy = key.toString();
			}
		}	
		return (keyy !=null) ? false : true; 
	}

	//Compare two JSON schemas and print the discripency.
	public static void compareJSONSchema(String jsonString1, String jsonString2) throws Exception {
		ScriptLogger.info();
		if(compareJSONNodes(jsonString1, jsonString2) == null) {//compare nodes
			ScriptLogger.info("nodes mismatchs !");
			ScriptLogger.info("JSON 1  "+jsonString1);
			ScriptLogger.info("JSON 2  "+jsonString2);
		}
		else if(compareJSONNodes(jsonString1, jsonString2).equals("{")) {//if nodes are jsonObject
			JSONObject json1 = (JSONObject) toJson(jsonString1);
			JSONObject json2 = (JSONObject) toJson(jsonString2);
			Boolean ind1 = doesKeySetContainsKey(json1, json2, "JSON2");// jsonObject keyset is having keys in other json object.
			Boolean ind2 = doesKeySetContainsKey(json2, json1, "JSON1");// jsonObject keyset is having keys in other json object.
			if(ind1 && ind2) {//validate further if ind1 and ind2 is true.
				for(Object key : json1.keySet()) {// validate discrepancy in json1 and json2 with respect by validating each json1 key
					String value1 = getObjectByKey(json1, (String)key).toString() ;
					String value2 = getObjectByKey(json2, (String)key).toString() ;
					if(value1.startsWith("{") || value1.startsWith("[") || value2.startsWith("{") ||value1.startsWith("["))
						compareJSONSchema(value1, value2);
				}

				for(Object key : json2.keySet()) {// validate discrepancy in json1 and json2 with respect by validating each json2 key
					String value1 = getObjectByKey(json1, (String)key).toString() ;
					String value2 = getObjectByKey(json2, (String)key).toString() ;
					if(value1.startsWith("{") || value1.startsWith("[") || value2.startsWith("{") ||value1.startsWith("["))
						compareJSONSchema(value1, value2);
				}
			}
			else {
				ScriptLogger.info("JSON 1  "+json1);
				ScriptLogger.info("JSON 2  "+json2);
			}

		}		
		else if(compareJSONNodes(jsonString1, jsonString2).equals("[")) {// if nodes are jsonarray
			JSONArray json1 = (JSONArray) toJson(jsonString1);
			JSONArray json2 = (JSONArray) toJson(jsonString2);

			if(json1.size()==json2.size()) {// if size of jsonarray is same
				for(int i = 0; i < json1.size(); i++) {
					String indexedValue1 = getObjectByKey(json1, Integer.toString(i)).toString();
					String indexedValue2 = getObjectByKey(json2, Integer.toString(i)).toString();
					if(indexedValue1.startsWith("{") || indexedValue1.startsWith("[") || indexedValue2.startsWith("{") ||indexedValue2.startsWith("["))
						compareJSONSchema(indexedValue1, indexedValue2);
				}
			}
			else if(json1.size()!=json2.size()) { // if size of jsonarray is not same
				ScriptLogger.info("JSON 1"+json1);
				ScriptLogger.info("JSON 2"+json2);
				ScriptLogger.info("Size of json arrays ["+json1.size()+"] mismatchs with ["+json2.size()+"]");
			}

		}
		else {
			ScriptLogger.info("Both JSONs Are having same Schema");
		}

	}

	//Get the object by path
	//should be like : 
	//1. alert_deliveries/0/id
	//2. 0/customEvent/eventNumberDataMapping
	public static Object getObjectByPath(String filename, String jsonPath) throws Exception {
		ScriptLogger.info();
		Object obj = toJson(FileUtils.readFile(filename));
		obj = getObjectByPath(obj,jsonPath); 
		return obj;
	}

	//method overloading of getObjectByPath()
	public static Object getObjectByPath(Object object, String jsonPath) throws Exception {
		ScriptLogger.info();
		String[] nodes = jsonPath.split("/");
		for(String node : nodes) {
			try{object = getObjectByKey(object, node);}
			catch(NullPointerException e){
				ScriptLogger.info("\n\nNode ["+node+"] not found !!!\n\n\n");
				ScriptLogger.info(object.toString());
			}
		}
		return object;
	}
	
	public static String prettyPrintJSON(String json){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(json);
		return gson.toJson(je);
	}
}
