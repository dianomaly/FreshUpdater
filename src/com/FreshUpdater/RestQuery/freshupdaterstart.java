package com.FreshUpdater.RestQuery;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.prefs.Preferences;

import javax.naming.NamingException;

import org.apache.http.client.ClientProtocolException;

import com.FreshUpdater.ADbringer.ADconnection;

public class Freshupdaterstart {

	public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException, NamingException {
		
	//	try {
		/* Preferences freshappprefs = Preferences.systemNodeForPackage(freshupdaterstart.class);
		 freshappprefs.put("firststart", "true");
		String startval = freshappprefs.get("firststart", "true");
		if(startval.equals("true") ) {*/
			//new apicaller().locationapicaller();
		//	new apicaller().allapidepts();
			//new apicaller().userapicaller();
			//new ADconnection().adsearch();
			//new	Hiberexec().aduserapiuserdiff();
			//new Hiberexec().addnewusers();
			new Apicaller().getagents();
		/*System.out.println("This is the first time");
		freshappprefs.put("firststart", "false");
			}else {
			
			System.out.println("This is not the first time");
		}
		*/
	//	}
	/*catch(IOException| URISyntaxException| NamingException e) {
		
	}*/
	
	//	new ADconnection().adsearch();		
	//	new apicaller().locationapicaller();
	//	  new apicaller().userapicaller();
	//	new Hibertester().addnewdept();
	//	new ADconnection().adsearch();
	//	new Hibertester().addnewusers();
	//	new apicaller().allapidepts();
	//	new	Hibertester().aduserapiuserdiff();
	//	new Hibertester().getalladusers();
	}
	
	
	
	


}
