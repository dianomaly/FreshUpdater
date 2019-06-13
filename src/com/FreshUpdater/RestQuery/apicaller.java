package com.FreshUpdater.RestQuery;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.json.Json;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class apicaller {

	private String apiurl = "https://servicedesk.freshservice.com/api/v2/requesters";
	private String authkey = "lU6OpkRqj2EZVL8cRPBP";
	private CloseableHttpResponse firstnewresponse;
	private JsonNode jsonrespobj;
	
	public apicaller() throws ClientProtocolException, IOException, URISyntaxException {
		//int pagenumber = 1;
		setFirstnewresponse(restcall());
		System.out.println(firstnewresponse.getFirstHeader("link").getValue());
				
		while((firstnewresponse.getStatusLine().getStatusCode() == 200) && apiurl != null ) {
			try {
				System.out.println(firstnewresponse.getStatusLine().getStatusCode());
				System.out.println(getNextLink(firstnewresponse));
				
				setFirstnewresponse(restcall());
				jsonresponsereader(getFirstnewresponse());
				
				//pagenumber++;
			} catch (JsonParseException e) {
				System.out.println(e);
			}
		}
		
		
	}

	public void jsonresponsereader(CloseableHttpResponse newresponse)
			throws JsonParseException, UnsupportedOperationException, IOException {
		// Accepts HTTP response and parses the json object then commits relevant
		// response to mysqltable.
		try {
		JsonFactory allapiusers = new JsonFactory();
		JsonParser allusersparser = allapiusers.createParser(newresponse.getEntity().getContent());
		ObjectMapper newjson = new ObjectMapper();
		JsonNode jsonparsed = (JsonNode) newjson.readTree(allusersparser).get("requesters");
		setJsonrespobj(jsonparsed);
		System.out.println(jsonparsed);
		
		if (jsonparsed.isArray()) {
			for (final JsonNode objNode : jsonparsed) {
				Hibertester.setUserID(objNode.get("id").asInt());
				Hibertester.setFirst_name(objNode.get("first_name").asText());
				Hibertester.setLast_name(objNode.get("last_name").asText());
				Hibertester.setLocationid(objNode.get("location_id").asInt());
				Hibertester.setJob_title(objNode.get("job_title").asText());
				Hibertester.setEmail(objNode.get("primary_email").asText());
				Hibertester.setPhone(objNode.get("work_phone_number").asText());
				Hibertester.setAddress(objNode.get("address").asText());
				Hibertester.setsqluser();

			}
			
		}
		}
		catch(JsonParseException e)
		{
			System.err.println("Unable to parse json response");
			System.err.println(e);
		}
		
		
	}
	
	public CloseableHttpResponse restcall() throws UnsupportedOperationException, IOException, URISyntaxException {
		
	//	URI uriraw = new URI(apiurl);
	//	URIBuilder newuri = new URIBuilder(uriraw);
	//	newuri.setParameter("per_page", "100").setParameter("page", Integer.toString(pagenumber));
		HttpGet newget = new HttpGet(apiurl);
		String newauth = authkey + ":";
		byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(newauthbytes);
		newget.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
		CloseableHttpClient newclient = HttpClients.createDefault();
		CloseableHttpResponse newresponse = newclient.execute(newget);
		
		setApiurl(getNextLink(newresponse));
		
		return newresponse;
		
		

	}
	
	private String getNextLink(CloseableHttpResponse r) {
		if(r.getFirstHeader("link") != null){
	      String link = r.getFirstHeader("link").getValue();   
	      if (link != null) {
	         String[] links = link.split(",");
	         for (String l : links) {               
	            if (l.contains("rel=\"next\"")) {
	               String[] tmp1 = l.split("<", 2);
	               if (tmp1.length == 2) {
	                  return tmp1[1].split(">", 2)[0];  
	               }
	            }
	         }
	      }
		}
	      return null;
	   }
	
	public CloseableHttpResponse getFirstnewresponse() {
		return firstnewresponse;
	}

	public void setFirstnewresponse(CloseableHttpResponse firstnewresponse) {
		this.firstnewresponse = firstnewresponse;
	}

	public JsonNode getJsonrespobj() {
		return jsonrespobj;
	}

	public void setJsonrespobj(JsonNode jsonrespobj) {
		this.jsonrespobj = jsonrespobj;
	}

	public String getApiurl() {
		return apiurl;
	}

	public void setApiurl(String apiurl) {
		this.apiurl = apiurl;
	}

}