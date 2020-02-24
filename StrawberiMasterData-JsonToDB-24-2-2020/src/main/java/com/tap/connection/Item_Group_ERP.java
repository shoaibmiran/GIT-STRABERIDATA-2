package com.tap.connection;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.tap.model.Item_Group;

public class Item_Group_ERP {
	
	//##################################FIRST GET THE ITEM GROUP FROM ERP USING REST CLIENT ADD IN A FILE THEN RUN THIS PROGRAM##############
		//CHANGE THE K VALUE EVERY TIME BASES ON NUMBE OF ITEM GROUP ARE IN ERP
	static int k = 212;
	

	private Client client = ClientBuilder.newClient();

	public static void main(String[] args)throws JSONException, Exception {

		int i = 0;
		String arry[] = new String[k];

		try {
			JsonFactory f = new MappingJsonFactory();
			JsonParser jp = f.createParser(new File("Item_group.txt"));
			JsonToken current;

			current = jp.nextToken();
			if (current != JsonToken.START_OBJECT) {
				System.out.println("Error: root should be object: quiting.");
				return;

			}

			while (jp.nextToken() != JsonToken.END_OBJECT) {

				current = jp.nextToken();

				String fieldName = jp.getCurrentName();

				if (fieldName.equals("data")) {
					if (current == JsonToken.START_ARRAY) {

						while (jp.nextToken() != JsonToken.END_ARRAY) {
							JsonNode node = jp.readValueAsTree();
							arry[i] = node.get("name").asText();
							i++;
						}

					} else {
						System.out.println("Error: records should be an array: skipping.");
						jp.skipChildren();
					}
				} else {
					// System.out.println("Unprocessed/unused property: " + fieldName);
					jp.skipChildren();
				}
				System.out.println(i);
			}
		} catch (IOException ie) {
			ie.printStackTrace();

		}
		createItem_group(arry);

	}

	private static void createItem_group(String[] arry) {
		Connection conn = null;
		Statement stmt = null;
		Item_Group_ERP rc = new Item_Group_ERP();
		
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT  *  from fit_package_catalog_basic";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				Item_Group attributes=new Item_Group();
				String country=rs.getString("country");
				attributes.setItem_group_name(rs.getString("state"));
				if (rs.getString("state").equalsIgnoreCase("") && rs.getString("country").equalsIgnoreCase("")) {
					attributes.setItem_group_name(rs.getString("continent"));
				} else if (rs.getString("state").equalsIgnoreCase("") && rs.getString("country") != "") {
					attributes.setItem_group_name(rs.getString("country"));
				}
				else if (rs.getString("state").equalsIgnoreCase("") && rs.getString("country").equalsIgnoreCase("")
						&& rs.getString("continent").equalsIgnoreCase("")) {
					attributes.setItem_group_name("All Item Groups");
				}
				String s = null;
				for (int i = 0; i < k; i++) {
					s = arry[i];
					System.out.println(arry[i]);
					if (s.equalsIgnoreCase(attributes.getItem_group_name())) {
						System.out.println("ALREADY PRESENT_______________"+s);
						System.out.println(true);
						break;
					}

					if (s != attributes.getItem_group_name() && i == k - 1) {
						System.out.println(false);
						if(country.equalsIgnoreCase("INDIA"))
						{
							attributes.setParent_item_group("INDIA");
						}
						else
						{
							attributes.setParent_item_group("ASIA");
						}
						
						System.out.println(
								"--------------------------------------------------------INSERTING ITEM GROUP DATA IN ERPNEXT SETUP IS STARTED--------------------------------------------------------\n");
						JSONObject obj = new JSONObject(attributes);
						System.out.println("GROUP---" + obj.toString());
						Response r = rc.createItemgroup(attributes);
						System.out.println("Status---" + r.getStatus());
						System.out.println("Status Info---" + r.getStatusInfo());
						System.out.println("Status Info---" + r.getStringHeaders());
						System.out.println(r.readEntity(String.class));
						System.out.println("\n");
						System.out.println(
								"--------------------------------------------------------INSERTING ITEM GROUP DATA IN ERPNEXT SETUP IS ENDED--------------------------------------------------------\n");

					}
				}

			}
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}
		
	}

	public Response createItemgroup(Item_Group item_Group) {
		JSONObject obj = new JSONObject(item_Group);
		System.out.println("Item GROUP List---" + obj.toString());

		try {
			return client.target(ITEM_Group_URL).request(MediaType.APPLICATION_JSON)
					.header("Authorization", "token a7d14051ef3941b:a738356d197551d")
					.post(Entity.entity(item_Group, MediaType.APPLICATION_JSON));

		} catch (Exception e) {
			System.out.println("Exception " + e);
		}
		return null;
	}
}
