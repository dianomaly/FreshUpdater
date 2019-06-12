package com.FreshUpdater.RestQuery;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

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
	
	public apicaller() throws ClientProtocolException, IOException, URISyntaxException {
		int pagenumber = 1;
		setFirstnewresponse(restcall(pagenumber));
		while(firstnewresponse.getStatusLine().getStatusCode() == 200) {
			try {
				setFirstnewresponse(restcall(pagenumber));
				jsonresponsereader(getFirstnewresponse());
				pagenumber++;
			} catch (JsonParseException e) {
				System.out.println(e);
			}
		}
		
		
	}

	public void jsonresponsereader(CloseableHttpResponse newresponse)
			throws JsonParseException, UnsupportedOperationException, IOException {
		// Accepts HTTP response and parses the json object then commits relevant
		// response to mysqltable.
		JsonFactory allapiusers = new JsonFactory();
		JsonParser allusersparser = allapiusers.createParser(newresponse.getEntity().getContent());
		ObjectMapper newjson = new ObjectMapper();
		JsonNode jsonrespobj = (JsonNode) newjson.readTree(allusersparser).get("requesters");
		if (jsonrespobj.isArray()) {
			for (final JsonNode objNode : jsonrespobj) {
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
	
	public CloseableHttpResponse restcall(int pagenumber) throws UnsupportedOperationException, IOException, URISyntaxException {
		URI uriraw = new URI(apiurl);
		URIBuilder newuri = new URIBuilder(uriraw);
		newuri.setParameter("per_page", "100").setParameter("page", Integer.toString(pagenumber));
		HttpGet newget = new HttpGet(newuri.build());
		String newauth = authkey + ":";
		byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(newauthbytes);
		newget.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
		CloseableHttpClient newclient = HttpClients.createDefault();
		CloseableHttpResponse newresponse = newclient.execute(newget);
		return newresponse;
		
	}

	public CloseableHttpResponse getFirstnewresponse() {
		return firstnewresponse;
	}

	public void setFirstnewresponse(CloseableHttpResponse firstnewresponse) {
		this.firstnewresponse = firstnewresponse;
	}

}