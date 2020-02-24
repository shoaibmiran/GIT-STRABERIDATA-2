package com.java.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Arrays;
import java.util.Iterator;

import com.java.model.Person;

public class Test {
	public static void main(String[] args) throws ParseException {

		String text = "John.Davidson / 05051988 / Belgrade Michael.Barton / 01011968 / Krakow Ivan.Perkinson / 23051986 / Moscow";

		// ########### spliting on [/.]##########

		String[] words = text.split("[/.]");
		//System.out.println(Arrays.toString(words));
		

		// ###########INSERTING THE ARRAY STRING INTO NEW STRING TO OVRCOME THE PROBLEM
		// OF UNWANTED SPACE OR TO REMOVE UNWANTED SPACES#########3333333333
		String final_text = "";
		for (int i = 0; i < words.length; i++) {
			final_text = final_text + words[i].trim() + " ";
		}
		// System.out.println(final_text.trim());

		// #############Spliting on space#################3

		String[] words1 = final_text.split("[ ]");
		//System.out.println(Arrays.toString(words1));

		// #############ARRAY list COLLECTION#####################

		ArrayList<Person> array = new ArrayList<Person>();

		for (int i = 0; i < words1.length; i++) {

			String first_name = words1[i].trim();
			i++;

			String last_name = words1[i].trim();
			i++;

			String date_of_birth = words1[i].trim();

			// #########3 CALLING GETLOCAL METHOD TO GET THE DATE IN LOCAL
			// FORMAT#############

			date_of_birth = getLocal_date(date_of_birth);

			i++;
			String place_of_birth = words1[i].trim();

			// #############CONSTRUCTOR CALL OF PERSON#############33

			Person p = new Person(first_name, last_name, date_of_birth, place_of_birth);

			// ############ADDING PERSON OBJECT INTO ARRAYLIST###########

			array.add(p);
		}

		int j = 1;

		// ############### DISPLAYING THE DATA USING FOR-EACH LOOP##############

		for (Person s : array) {
			System.out.println("-----------------PERSON " + j + " RECORD DERAILS STARTED-----------------\n");

			System.out.println("First_Name : " + s.getFirst_name());
			System.out.println("Last_Name : " + s.getLast_name());
			System.out.println("DOB : " + s.getDate_of_birth());
			System.out.println("POB : " + s.getPlace_of_birth());

			System.out.println("\n-----------------PERSON " + j + " RECORD DERAILS ENDED-----------------\n");
			j++;
		}
	}

	private static String getLocal_date(String date_of_birth) throws ParseException {

		// #########EXTRATING SUBSTRING AND INSERTIN / IN BETWEEN FOR DATE
		// FORMAT############

		date_of_birth = date_of_birth.substring(0, 2) + "/" + date_of_birth.substring(2, 4) + "/"
				+ date_of_birth.substring(4);

		// ########CONVERTING DATE INTO LOCAL DATE FORMAT########

		Date date1 = new SimpleDateFormat("dd/mm/yyyy").parse(date_of_birth);
		//System.out.println(date1);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		formatter = new SimpleDateFormat("MMMM dd, yyyy");
		String strDate = formatter.format(date1);

		// System.out.println("Date Format with dd MMMM yyyy : " + strDate);

		return strDate;
	}
}
