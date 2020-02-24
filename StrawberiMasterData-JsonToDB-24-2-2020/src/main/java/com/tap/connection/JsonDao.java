package com.tap.connection;

import java.io.FileReader;
import java.util.Properties;

public class JsonDao {

	public static void main(String[] args) throws Exception {
		getPackage_name();

	}

	private static void getPackage_name() throws Exception {
		FileReader reader = new FileReader("db2.properties");

		Properties p = new Properties();
		p.load(reader);

		String package_name = p.getProperty("package_name");
		package_name=package_name.toUpperCase();
		
		Json_To_Db.setdata(package_name);

	}

}
