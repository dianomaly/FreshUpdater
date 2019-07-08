package com.FreshUpdater.RestQuery;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.FreshUpdater.Models.Aduserhibernateobj;
import com.FreshUpdater.Models.Apideptpostobject;
import com.FreshUpdater.Models.Apiuserpostobj;
import com.FreshUpdater.Models.Apiuserputobj;
import com.FreshUpdater.appconfig.Appconfig;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Apicaller {

	private String apiurl;
	private String authkey;
	private String locationapiurl;
	private String putuserapiurl;
	private String postuserapiurl;
	private String getapifilterurl;
	private String alldeptsapiurl;
	private CloseableHttpResponse firstnewresponse;
	private JsonNode jsonrespobj;
	private String getagents;

	// Gets paginated list of all users in requesters endpoint and saves to mysql
	// db.
	public void userapicaller() {
		// Getting api users url and api authkey from config file
		try {
			setApiurl(Appconfig.loadapiuserurl());
			setAuthkey(Appconfig.loadApikey());
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		// Doing Api Call and parsing the json response
		try {
			setFirstnewresponse(alluserapirestcall());
			jsonuserresponsereader(getFirstnewresponse());

			while ((firstnewresponse.getStatusLine().getStatusCode() == 200) && apiurl != null) {

				setFirstnewresponse(alluserapirestcall());
				jsonuserresponsereader(getFirstnewresponse());
			}
		} catch (UnsupportedOperationException e1) {

			e1.printStackTrace();
		}
	}

	// Gets all the locations from the freshservice api and saves them to mysql db
	public void locationapicaller() {
		// Getting location url and api authkey from config file
		try {
			setLocationapiurl(Appconfig.loadLocationUrl());
			setAuthkey(Appconfig.loadApikey());
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		try {
			setFirstnewresponse(alllocationapirestcall());

			jsonreadalllocations(getFirstnewresponse());
		} catch (UnsupportedOperationException e) {

			e.printStackTrace();
		}

	}

	public void useremailfilterupdate(List<String> alladuserobj) {
		try {
			setGetapifilterurl(Appconfig.loadapiuserurl() + Appconfig.loadUsersFilter());
			setAuthkey(Appconfig.loadApikey());

		} catch (IOException e1) {

			e1.printStackTrace();
		}
		try {
			if (!alladuserobj.isEmpty()) {
				for (String aduser : alladuserobj) {
					if (!aduser.isEmpty() || aduser != null) {
						String userfilter = getapifilterurl + aduser;
						HttpGet newget = new HttpGet(userfilter);
						String newauth = authkey + ":";
						byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
						String authHeader = "Basic " + new String(newauthbytes);
						newget.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
						CloseableHttpClient newclient = HttpClients.createDefault();
						CloseableHttpResponse newresponse = newclient.execute(newget);

						if (newresponse.getStatusLine().getStatusCode() == 200) {

							jsonuserresponsereader(newresponse);
						}

					}

				}

			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void allapidepts() {
		try {
			setAlldeptsapiurl(Appconfig.loadDeptUrl());

			setAuthkey(Appconfig.loadApikey());

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			setFirstnewresponse(allapideptsget());
			jsonreadalldepts(getFirstnewresponse());
			while ((firstnewresponse.getStatusLine().getStatusCode() == 200) && alldeptsapiurl != null) {

				setFirstnewresponse(allapideptsget());
				jsonreadalldepts(getFirstnewresponse());

			}
		} catch (UnsupportedOperationException e) {

			e.printStackTrace();
		}
	}

	public void jsonuserresponsereader(CloseableHttpResponse newresponse) {
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
					boolean nodeval = objNode.get("primary_email").asText().equals("null");

					if (!nodeval) {
						Hiberexec.setapisqluser(objNode.get("id").asInt(), objNode.get("first_name").asText(),
								objNode.get("last_name").asText(), objNode.get("primary_email").asText(),
								objNode.get("address").asText(), objNode.get("job_title").asText(),
								objNode.get("work_phone_number").asText(), objNode.get("location_id").asInt(),
								objNode.get("reporting_manager_id").asInt(), objNode.get("department_ids").asInt());

					}
				}

			}
		} catch (UnsupportedOperationException | IOException e) {
			System.err.println("Unable to parse json response");
			System.err.println(e);
			e.printStackTrace();
		}

	}
	public void jsonagentsresponsereader(CloseableHttpResponse newresponse) {
		// Accepts HTTP response and parses the json object then commits relevant
		// response to mysqltable.
		try {
			JsonFactory allapiusers = new JsonFactory();
			JsonParser allusersparser = allapiusers.createParser(newresponse.getEntity().getContent());
			ObjectMapper newjson = new ObjectMapper();
			JsonNode jsonparsed = (JsonNode) newjson.readTree(allusersparser).get("agents");
			setJsonrespobj(jsonparsed);
			System.out.println(jsonparsed);

			if (jsonparsed.isArray()) {
				for (final JsonNode objNode : jsonparsed) {
					boolean nodeval = objNode.get("email").asText().equals("null");

					if (!nodeval) {
						Hiberexec.setapisqluser(objNode.get("id").asInt(), objNode.get("first_name").asText(),
								objNode.get("last_name").asText(), objNode.get("email").asText(), objNode.get("job_title").asText(),
								objNode.get("work_phone_number").asText(), objNode.get("location_id").asInt(),
								objNode.get("reporting_manager_id").asInt());

					}
				}

			}
		} catch (UnsupportedOperationException | IOException e) {
			System.err.println("Unable to parse json response");
			System.err.println(e);
			e.printStackTrace();
		}

	}
	public CloseableHttpResponse allapideptsget() {
		try {
			HttpGet newget = new HttpGet(alldeptsapiurl);
			String newauth = authkey + ":";
			byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(newauthbytes);
			newget.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
			CloseableHttpClient newclient = HttpClients.createDefault();
			CloseableHttpResponse newresponse = newclient.execute(newget);
			setAlldeptsapiurl(getNextLink(newresponse));
			return newresponse;
		} catch (IOException e) {
			System.err.println(e);
		}
		return null;
	}

	public CloseableHttpResponse alllocationapirestcall() {
		try {
			HttpGet newget = new HttpGet(locationapiurl);
			String newauth = authkey + ":";
			byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(newauthbytes);
			newget.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
			CloseableHttpClient newclient = HttpClients.createDefault();
			CloseableHttpResponse newresponse = newclient.execute(newget);
			return newresponse;
		} catch (IOException e) {
			System.err.println(e);
		}
		return null;
	}

	public CloseableHttpResponse alluserapirestcall() {
		try {
			HttpGet newget = new HttpGet(apiurl);
			String newauth = authkey + ":";
			byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(newauthbytes);
			newget.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
			CloseableHttpClient newclient = HttpClients.createDefault();
			CloseableHttpResponse newresponse = newclient.execute(newget);
			setApiurl(getNextLink(newresponse));

			return newresponse;
		} catch (UnsupportedOperationException | IOException e) {
			System.err.println(e);
		}
		return null;
	}

	private String getNextLink(CloseableHttpResponse r) {
		if (r.getFirstHeader("link") != null) {
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

	private void jsonreadalllocations(CloseableHttpResponse newresponse) {

		try {
			System.out.println(newresponse);
			JsonFactory alllocation = new JsonFactory();
			JsonParser alllocationparser = alllocation.createParser(newresponse.getEntity().getContent());
			ObjectMapper newjson = new ObjectMapper();
			JsonNode jsonparsed = (JsonNode) newjson.readTree(alllocationparser).get("locations");
			System.out.println(jsonparsed);

			if (jsonparsed.isArray()) {
				for (final JsonNode objNode : jsonparsed) {

					Hiberexec.setapilocationobj(objNode.get("id").asInt(), objNode.get("name").asText(),
							objNode.get("parent_location_id").asInt(), objNode.get("address").get("line1").asText(),
							objNode.get("address").get("line2").asText(), objNode.get("address").get("city").asText(),
							objNode.get("address").get("state").asText(),
							objNode.get("address").get("country").asText(),
							objNode.get("address").get("zipcode").asText());
				}

			}
		} catch (UnsupportedOperationException | IOException e) {
			System.err.println("Unable to parse json response");
			System.err.println(e);
		}

	}

	private void jsonreadalldepts(CloseableHttpResponse newresponse) {

		try {
			System.out.println(newresponse);
			JsonFactory alllocation = new JsonFactory();
			JsonParser alllocationparser = alllocation.createParser(newresponse.getEntity().getContent());
			ObjectMapper newjson = new ObjectMapper();

			JsonNode jsonparsed = (JsonNode) newjson.readTree(alllocationparser).get("departments");

			System.out.println(jsonparsed);
			if (jsonparsed.isArray()) {
				for (final JsonNode objNode : jsonparsed) {

					Hiberexec.setapideptobj(objNode.get("id").asInt(),
							objNode.get("name").toString().replaceAll("\"", ""));
				}

			}
		} catch (IOException | UnsupportedOperationException e) {
			System.err.println("Unable to parse json response");
			System.err.println(e);
		}

	}

	public int updateapiuser(Apiuserputobj apiuser, int apiuserid) {
		try {
			setPutuserapiurl(Appconfig.loadapiuserurl());
			setAuthkey(Appconfig.loadApikey());
			ObjectMapper newputmappper = new ObjectMapper();

			newputmappper.enable(SerializationFeature.INDENT_OUTPUT);

			String newjson = newputmappper.writeValueAsString(apiuser);
			System.out.println(newjson);
			StringEntity newputentity = new StringEntity(newjson);
			newputentity.setContentType("application/json");
			System.out.println(newputentity);
			String apiputcallurl = putuserapiurl + "/" + apiuserid;
			System.out.println(apiputcallurl);
			HttpPut httpputrequest = new HttpPut(apiputcallurl);
			String newauth = authkey + ":";
			byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(newauthbytes);
			httpputrequest.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
			httpputrequest.setEntity(newputentity);
			CloseableHttpClient newclient = HttpClients.createDefault();
			CloseableHttpResponse newresponse = newclient.execute(httpputrequest);
			System.out.println(newresponse.getStatusLine().getReasonPhrase());
			return newresponse.getStatusLine().getStatusCode();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return 0;

	}
	
	public void getagents() {
		try {
			setApiurl(Appconfig.loadAgent());
			setAuthkey(Appconfig.loadApikey());
			
			try {
				setFirstnewresponse(alluserapirestcall());
				jsonagentsresponsereader(getFirstnewresponse());

				while ((firstnewresponse.getStatusLine().getStatusCode() == 200) && apiurl != null) {

					setFirstnewresponse(alluserapirestcall());
					jsonagentsresponsereader(getFirstnewresponse());
				}
			} catch (UnsupportedOperationException e1) {

				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int updatedepts(Apiuserputobj apidept, int deptapiid) {
		try {
			setAlldeptsapiurl(Appconfig.loadDeptUrl());
			setAuthkey(Appconfig.loadApikey());
			ObjectMapper newputmappper = new ObjectMapper();

			newputmappper.enable(SerializationFeature.INDENT_OUTPUT);

			String newjson = newputmappper.writeValueAsString(apidept);
			System.out.println(newjson);
			StringEntity newputentity = new StringEntity(newjson);
			newputentity.setContentType("application/json");
			System.out.println(newputentity);
			String apiputcallurl = alldeptsapiurl + "/" + deptapiid;
			System.out.println(apiputcallurl);
			HttpPut httpputrequest = new HttpPut(apiputcallurl);
			String newauth = authkey + ":";
			byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(newauthbytes);
			httpputrequest.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
			httpputrequest.setEntity(newputentity);
			CloseableHttpClient newclient = HttpClients.createDefault();
			CloseableHttpResponse newresponse = newclient.execute(httpputrequest);
			System.out.println(newresponse.getStatusLine().getReasonPhrase());
			return newresponse.getStatusLine().getStatusCode();
		}

		catch (IOException e) {

			e.printStackTrace();
		}

		return 0;

	}

	public int addapidept(Apiuserputobj apidept) {
		try {
			setPostuserapiurl(Appconfig.loadDeptUrl());
			setAuthkey(Appconfig.loadApikey());
			ObjectMapper newpostmappper = new ObjectMapper();
			newpostmappper.enable(SerializationFeature.INDENT_OUTPUT);

			String newjson = newpostmappper.writeValueAsString(apidept);
			System.out.println(newjson);
			StringEntity newpostentity = new StringEntity(newjson);
			newpostentity.setContentType("application/json");
			System.out.println(newpostentity);

			HttpPost httppostrequest = new HttpPost(postuserapiurl);
			String newauth = authkey + ":";
			byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(newauthbytes);
			httppostrequest.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
			httppostrequest.setEntity(newpostentity);
			CloseableHttpClient newclient = HttpClients.createDefault();
			CloseableHttpResponse newresponse = newclient.execute(httppostrequest);
			System.out.println(newresponse.getStatusLine().getReasonPhrase());
			return newresponse.getStatusLine().getStatusCode();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return 0;

	}

	public int addapiuser(Apiuserpostobj apiuser) {

		try {
			setPostuserapiurl(Appconfig.loadapiuserurl());

			System.out.println(postuserapiurl);
			setAuthkey(Appconfig.loadApikey());
			ObjectMapper newpostmappper = new ObjectMapper();
			newpostmappper.enable(SerializationFeature.INDENT_OUTPUT);

			String newjson = newpostmappper.writeValueAsString(apiuser);
			System.out.println(newjson);
			StringEntity newpostentity = new StringEntity(newjson);
			newpostentity.setContentType("application/json");
			System.out.println(newpostentity);

			HttpPost httppostrequest = new HttpPost(postuserapiurl);
			String newauth = authkey + ":";
			byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(newauthbytes);
			httppostrequest.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
			httppostrequest.setEntity(newpostentity);
			CloseableHttpClient newclient = HttpClients.createDefault();
			CloseableHttpResponse newresponse = newclient.execute(httppostrequest);
			System.out.println(newresponse.getStatusLine().getReasonPhrase());
			return newresponse.getStatusLine().getStatusCode();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return 0;

	}

	public int addapidept(Apideptpostobject apidept) throws ClientProtocolException, IOException {
		try {
			setAlldeptsapiurl(Appconfig.loadDeptUrl());
			setAuthkey(Appconfig.loadApikey());
			ObjectMapper newpostmappper = new ObjectMapper();
			newpostmappper.enable(SerializationFeature.INDENT_OUTPUT);

			String newjson = newpostmappper.writeValueAsString(apidept);
			System.out.println(newjson);
			StringEntity newpostentity = new StringEntity(newjson);
			newpostentity.setContentType("application/json");
			System.out.println(newpostentity);

			HttpPost httppostrequest = new HttpPost(alldeptsapiurl);
			String newauth = authkey + ":";
			byte[] newauthbytes = Base64.encodeBase64(newauth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(newauthbytes);
			httppostrequest.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
			httppostrequest.setEntity(newpostentity);
			CloseableHttpClient newclient = HttpClients.createDefault();
			CloseableHttpResponse newresponse = newclient.execute(httppostrequest);
			System.out.println(newresponse.getStatusLine().getReasonPhrase());
			return newresponse.getStatusLine().getStatusCode();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return 0;

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

	public String getGetapifilterurl() {
		return getapifilterurl;
	}

	public void setGetapifilterurl(String getapifilterurl) {
		this.getapifilterurl = getapifilterurl;
	}

	public String getAlldeptsapiurl() {
		return alldeptsapiurl;
	}

	public void setAlldeptsapiurl(String alldeptsapiurl) {
		this.alldeptsapiurl = alldeptsapiurl;
	}

	public String getAuthkey() {
		return authkey;
	}

	public String getLocationapiurl() {
		return locationapiurl;
	}

	public String getGetagents() {
		return getagents;
	}

	public void setGetagents(String getagents) {
		this.getagents = getagents;
	}

	public String getPutuserapiurl() {
		return putuserapiurl;
	}

	public String getPostuserapiurl() {
		return postuserapiurl;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public void setLocationapiurl(String locationapiurl) {
		this.locationapiurl = locationapiurl;
	}

	public void setPutuserapiurl(String putuserapiurl) {
		this.putuserapiurl = putuserapiurl;
	}

	public void setPostuserapiurl(String postuserapiurl) {
		this.postuserapiurl = postuserapiurl;
	}

}