package com.tap.connection;

import com.fasterxml.jackson.databind.*;
import com.tap.model.Supplier;
import com.fasterxml.jackson.core.*;

import java.io.*;
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

public class SupplierERP {
	//##################################FIRST GET THE SUPPLIER FROM ERP USING REST CLIENT ADD IN A FILE THEN RUN THIS PROGRAM##############
	//CHANGE THE K VALUE EVERY TIME BASES ON NUMBE OF SUPPLIER ARE IN ERP
		private Client client = ClientBuilder.newClient();

	public Response createSupplier(Supplier supplier_attributes) {
		JSONObject obj = new JSONObject(supplier_attributes);
		System.out.println("Supplier---" + obj.toString());
		try {

			return client.target(SUPPLIER).request(MediaType.APPLICATION_JSON)
					.header("Authorization", "token a7d14051ef3941b:a738356d197551d")
					.post(Entity.entity(supplier_attributes, MediaType.APPLICATION_JSON));
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}
		return null;

	}

	public static void main(String[] args) throws JSONException, Exception {
		int i = 0;
		String arry[] = new String[k];

		try {
			JsonFactory f = new MappingJsonFactory();
			JsonParser jp = f.createParser(new File("supplier.txt"));
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
		createSuppliers(arry);
	}

	private static void createSuppliers(String[] arry) {

		Connection conn = null;
		Statement stmt = null;
		SupplierERP rc = new SupplierERP();

		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT  distinct supplier from fit_package_catalog_basic";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Supplier attributes = new Supplier();
				String suppliername = rs.getString("supplier");
				// String suppliername="sdfasg";
				System.out.println(suppliername);
				String s = null;
				for (int i = 0; i < k; i++) {
					s = arry[i];
					System.out.println(arry[i]);
					if (s.equalsIgnoreCase(suppliername)) {
						System.out.println("ALREADY PRESENT_______________"+s);
						System.out.println(true);
						break;
					}

					if (s != suppliername && i == k - 1) {
						System.out.println(false);
						System.out.println(
								"--------------------------------------------------------INSERTING SUPPLIER DATA IN ERPNEXT SETUP IS STARTED--------------------------------------------------------\n");

						attributes.setSupplier_group("DMC");
						attributes.setSupplier_name(suppliername);
						JSONObject obj = new JSONObject(attributes);
						System.out.println("supplier---" + obj.toString());

						Response r = rc.createSupplier(attributes);

						System.out.println("Status---" + r.getStatus());
						System.out.println("Status Info---" + r.getStatusInfo());
						System.out.println("Status Info---" + r.getStringHeaders());
						System.out.println(r.readEntity(String.class));
						System.out.println("\n");
						System.out.println(
								"--------------------------------------------------------INSERTING SUPPLIER DATA IN ERPNEXT SETUP IS ENDED--------------------------------------------------------\n");

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

}
