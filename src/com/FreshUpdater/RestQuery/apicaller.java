package com.FreshUpdater.RestQuery;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class apicaller {

	public apicaller() throws ClientProtocolException, IOException {
		HttpGet newget = new HttpGet("https://servicedesk.freshservice.com/itil/requesters.json");
		String newauth = "lU6OpkRqj2EZVL8cRPBP:";
		byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(newauthbytes);

		newget.addHeader(HttpHeaders.AUTHORIZATION, authHeader);

		CloseableHttpClient newclient = HttpClients.createDefault();

		CloseableHttpResponse newresponse = newclient.execute(newget);

		JsonFactory allapiusers = new JsonFactory();

		JsonParser allusersparser = allapiusers.createParser(newresponse.getEntity().getContent());
		JsonToken newusertoken;
		while ((newusertoken = allusersparser.nextToken()) != null) {

			{
				final String currentfield = allusersparser.getCurrentName();

				if (currentfield != null && !newusertoken.START_ARRAY.equals(newusertoken)) {

					switch (currentfield) {

					case "name":
						//newuser.setName(allusersparser.getText());
						System.out.println(allusersparser.getText());
						break;
					case "id":
						//newuser.setUserID(allusersparser.getIntValue());
						System.out.println(allusersparser.getText());
						break;
					case "email":
					//	newuser.setEmail(allusersparser.getText());
						System.out.println(allusersparser.getText());
						break;
					case "active":
						//newuser.setActive(allusersparser.getText());
						System.out.println(allusersparser.getText());
						break;
					case "address":
						//newuser.setAddress(allusersparser.getText());
						System.out.println(allusersparser.getText());
						break;
					case "job_title":
						//newuser.setJob_title(allusersparser.getText());
						System.out.println(allusersparser.getText());
						break;
					case "phone":
						//newuser.setPhone(allusersparser.getText());
						System.out.println(allusersparser.getText());
						break;
					case "location_name":
						//newuser.setLocation_name(allusersparser.getText());
						System.out.println(allusersparser.getText());
						break;
					case "deleted":
						//newuser.setDeleted(allusersparser.getText());
						System.out.println(allusersparser.getText());
						break;

					}

					// String curfieldvalue = allusersparser.getText();

					// System.out.println(newusertoken);
					// System.out.printf("%s: %s%n", currentfield, curfieldvalue);
					//newuser.setsqluser();
				}
			}
		
	}
}
}