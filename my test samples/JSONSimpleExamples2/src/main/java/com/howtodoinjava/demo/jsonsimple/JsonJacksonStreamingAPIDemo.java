package com.howtodoinjava.demo.jsonsimple;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;

/**
 * Java program to demonstrate how to use Jackson Streaming API to read and
 * write JSON Strings efficiently and fast.
 *
 * @author Javin Paul
 */
public class JsonJacksonStreamingAPIDemo {

	public static void main(String args[]) {


		try {
			JsonFactory jsonfactory = new JsonFactory();
			File source = new File("employees2.json");

			JsonParser parser = jsonfactory.createJsonParser(source);
           int i=0;
			// starting parsing of JSON String
			for(i=0;i<15;i++) {
			while (parser.nextToken() != JsonToken.END_OBJECT) {
				//System.out.println(parser.nextToken());
				String token = parser.getCurrentName();
				if ("continent".equals(token)) {
					parser.nextToken();
					String continent = parser.getText();
					System.out.println("continent : " + continent);

				}
				if ("package_name".equals(token)) {
					parser.nextToken(); // next token contains value
					String package_name = parser.getText(); // getting text field
					System.out.println("package_name : " + package_name);

				}		
			}
			}
			parser.close();
			
		} catch (JsonGenerationException jge) {
			jge.printStackTrace();
		} catch (JsonMappingException jme) {
			jme.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
}
