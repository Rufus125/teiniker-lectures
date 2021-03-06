package org.se.lab;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class RestCRUDTest 
	extends RestTestBase
{
	private final String WEB_APP_NAME = "/RemoteFacade-REST-UserService/v1";
	
	@Ignore
	@Test
	public void testInsert() throws IOException, JAXBException
	{
		// Request
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users");
		String requestContent = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<user id=\"0\">"
				+ 	"<username>maggie</username>"
				+ 	"<password>**********</password>"
				+ "</user>";
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/xml");
		connection.setRequestProperty("Accept", "application/xml");		
		OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream());
		w.write(requestContent);
		w.flush();
		w.close();
		
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(201, httpResponseCode);
		Assert.assertEquals(0, connection.getContentLengthLong());
	}

	
	@Ignore
	@Test
	public void testUpdate() throws IOException, JAXBException
	{
		// Request
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/1");
		String requestContent = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<user id=\"1\">"
				+ 	"<username>homer</username>"
				+ 	"<password>xxxxxxxxx</password>"
				+ "</user>";
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);		
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/xml");
		connection.setRequestProperty("Accept", "application/xml");
		
		OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream());
		w.write(requestContent);
		w.flush();
		w.close();
		
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(204, httpResponseCode);
		Assert.assertEquals(0, connection.getContentLengthLong());
	}
	
	
	@Ignore
	@Test
	public void testDelete() throws IOException, JAXBException
	{
		// Request
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/2");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("DELETE");
		
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(204, httpResponseCode);
	}
	
	
	@Test
	public void testFindById() throws IOException, JAXBException
	{
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/3");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
		
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		final String EXPECTED = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<user id=\"3\">"
				+ 	"<username>bart</username>"
				+ 	"<password>**********</password>"
				+ "</user>\n";		
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}
	
	
	@Test
	public void testFindAll() throws IOException, JAXBException
	{
		// Request
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
	
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		
		final String EXPECTED = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<collection>"
				+ 	"<user id=\"1\">"
				+ 		"<username>homer</username>"
				+ 		"<password>**********</password>"
				+ 	"</user>"
				+ 	"<user id=\"2\">"
				+ 		"<username>marge</username>"
				+ 		"<password>**********</password>"
				+ 	"</user>"
				+ 	"<user id=\"3\">"
				+ 		"<username>bart</username>"
				+ 		"<password>**********</password>"
				+ 	"</user>"
				+ 	"<user id=\"4\">"
				+ 		"<username>lisa</username>"
				+ 		"<password>**********</password>"
				+ 	"</user>"
				+ "</collection>\n";
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}


	@Test
	public void testFindAllFromIndex() throws IOException, JAXBException
	{
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users?index=1&size=2");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
	
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		
		final String EXPECTED = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<collection>"
				+ 	"<user id=\"2\">"
				+ 		"<username>marge</username>"
				+ 		"<password>**********</password>"
				+ 	"</user>"
				+ 	"<user id=\"3\">"
				+ 		"<username>bart</username>"
				+ 		"<password>**********</password>"
				+ 	"</user>"
				+ "</collection>\n";
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}
}
