package com.tap.connection;

import java.util.Arrays;

public class string_split {
	@SuppressWarnings("null")
	public static void main(String args[]) throws Exception {
//        String s="Nusa Penida-Bali-004- ";
// String r[]=s.split("- ");
// System.out.println(Arrays.toString(r));
		//getplaces("Singapore : Aqueen Hotel Lavender or\r\nSimilar + Sentosa : Amara Sanctuary\r\nResort or Similar");
		
	}

	
	public static String[] getplaces(String string) {
		String word = "Or Similar\r\n Similar";
		String word2 = "Or\r\n Similar";
		String word3 = "\r\nOr Similar";
		String word4 = "or\r\nSimilar";
		String word5 = "Or\r\nSimilar";
		String word6 = "\r\nor Similar";
		String word7 = "\r\n Similar";
		String word8 = "Or similar";
		String word9 = "Or Similar";
		String word10 = "or similar";
		String word11 = "or Similar";
		String word12 = "OR SIMILAR";
		String word13 = "OrSimilar";
		String word14 = "\r\nor";
		String word15 = "\r\n";
		String word16 = "Similar";
		String word17 = "Similar ";
		String word18 = "similar";
		String word19 = "Or";
		String word20 = "or ";
		String word21 = " or";
		String result1 = removeWord(string, word);
		String result2 = removeWord(result1, word2);
		String result3 = removeWord(result2, word3);
		String result4 = removeWord(result3, word4);
		String result5 = removeWord(result4, word5);
		String result6 = removeWord(result5, word6);
		String result7 = removeWord(result6, word7);
		String result8 = removeWord(result7, word8);
		String result9 = removeWord(result8, word9);
		String result10 = removeWord(result9, word10);
		String result11 = removeWord(result10, word11);
		String result12 = removeWord(result11, word12);
		String result13 = removeWord(result12, word13);
		String result14 = removeWord(result13, word14);
		String result15 = removeWord(result14, word15);
		String result16 = removeWord(result15, word16);
		String result17 = removeWord(result16, word17);
		String result18 = removeWord(result17, word18);
		String result19 = removeWord(result18, word19);
		String result20 = removeWord(result19, word20);
		String result21 = removeWord(result20, word21);
		System.out.println(result21);

		String[] words = result21.split("[:+]");
		System.out.println(words.length);
//		for (String str1 : words) {
//			System.out.println(str1);
//		}
		// System.out.println(words.length);
		int l = words.length / 2;
		System.out.println(l);
		String places[] = new String[l];
		//String hotels[] = new String[l];
		int i = 0;
		for (int j = 0; j < words.length; j++) {

			if (j % 2 == 0) {

				String replace = words[j].replace("Xi'an", "Xian");
				replace = replace.replace("D' Ma", "D Ma");
				replace = replace.replace("D' Ma", "D Ma");
				places[i] = replace;

				System.out.println("places-------" + Arrays.toString(places));
				j++;
				i++;
			}
			// System.out.println(places.length);
		}
		return places;

	}

	public static String[] gethotels(String string) {
		String wrd = "\r\n";
		String word = "Or Similar\r\n Similar";
		String word2 = "Or\r\n Similar";
		String word3 = "\r\nOr Similar";
		String word4 = "or\r\nSimilar";
		String word5 = "Or\r\nSimilar";
		String word6 = "\r\nor Similar";
		String word7 = "\r\n Similar";
		String word8 = "Or similar";
		String word9 = "Or Similar";
		String word10 = "or similar";
		String word11 = "or Similar";
		String word12 = "OR SIMILAR";
		String word13 = "OrSimilar";
		String word14 = "\r\nor";
		String word15 = "\r\n ";
		String word16 = "\r\n";
		String word17 = "Similar";
		String word18 = "Similar ";
		String word19 = "similar";
		String word20 = "Or";
		String word21 = "or ";
		String word22 = " or";
		String word23 = " category";
		String word24 = "category";
		String word25 = "category ";
		String r = removeWord1(string, wrd);
		String result1 = removeWord(r, word);
		String result2 = removeWord(result1, word2);
		String result3 = removeWord(result2, word3);
		String result4 = removeWord(result3, word4);
		String result5 = removeWord(result4, word5);
		String result6 = removeWord(result5, word6);
		String result7 = removeWord(result6, word7);
		String result8 = removeWord(result7, word8);
		String result9 = removeWord(result8, word9);
		String result10 = removeWord(result9, word10);
		String result11 = removeWord(result10, word11);
		String result12 = removeWord(result11, word12);
		String result13 = removeWord(result12, word13);
		String result14 = removeWord(result13, word14);
		String result15 = removeWord(result14, word15);
		String result16 = removeWord(result15, word16);
		String result17 = removeWord(result16, word17);
		String result18 = removeWord(result17, word18);
		String result19 = removeWord(result18, word19);
		String result20 = removeWord(result19, word20);
		String result21 = removeWord(result20, word21);
		String result22 = removeWord(result21, word22);
		String result23 = removeWord(result22, word23);
		String result24 = removeWord(result23, word24);
		String result25 = removeWord(result24, word25);
		String[] words = result25.split("[:+]");
		int l = words.length / 2;
		// System.out.println(l);
		//String places[] = new String[l];
		String hotels[] = new String[l];
		int k = 0;
		for (int j = 0; j < words.length; j++) {

			if (j % 2 != 0) {

				String replace = words[j].replace("Xi'an", "Xian");
				replace = replace.replace("D' Ma", "D Ma");
				replace = replace.replace("IORA – The Retreat", "IORA The Retreat");
				replace = replace.replace(" IORA - The Retreat", "IORA The Retreat");
				replace = replace.replace("Marriott Regent’s Park", "Marriott Regents Park");
				replace = replace.replace("Drei Könige", "Drei Konige");
				hotels[k] = replace;
				// hotels[k] = words[j];
				System.out.println("Hotel Names-------" + Arrays.toString(hotels));

				j++;
				k++;
			}
			System.out.println(hotels.length);
		}
		return hotels;

	}

	private static String removeWord1(String result14, String word15) {
		if (result14.contains(word15)) {

			String tempWord = word15 + "";
			result14 = result14.replaceAll(tempWord, " ");

			tempWord = "" + word15;
			result14 = result14.replaceAll(tempWord, " ");
		}
		return result14;
	}

	public static String removeWord(String string, String word) {

		// Check if the word is present in string
		// If found, remove it using removeAll()
		if (string.contains(word)) {

			// To cover the case
			// if the word is at the
			// beginning of the string
			// or anywhere in the middle
			String tempWord = word + "";
			string = string.replaceAll(tempWord, "");

			// To cover the edge case
			// if the word is at the
			// end of the string
			tempWord = "" + word;
			string = string.replaceAll(tempWord, "");
		}
		// System.out.println(string.toString());
		return string;
	}

	public static String[] gethotelssingle(String hotel_name) {

		// System.out.println(hotel_name);
		String wrd = "\r\n";
		String word = "Or Similar\r\n Similar";
		String word2 = "Or\r\n Similar";
		String word3 = "\r\nOr Similar";
		String word4 = "or\r\nSimilar";
		String word5 = "Or\r\nSimilar";
		String word6 = "\r\nor Similar";
		String word7 = "\r\n Similar";
		String word8 = "Or similar";
		String word9 = "Or Similar";
		String word10 = "or similar";
		String word11 = "or Similar";
		String word12 = "OR SIMILAR";
		String word13 = "OrSimilar";
		String word14 = "\r\nor";
		String word15 = "\r\n";
		String word16 = "Similar";
		String word17 = "Similar ";
		String word18 = "similar";
		String word19 = "Or";
		String word20 = "or ";
		String word21 = " or";
		String word22 = " category";
		String word23 = "category";
		String word24 = "category ";
		String r = removeWord1(hotel_name, wrd);
		String result1 = removeWord(r, word);
		String result2 = removeWord(result1, word2);
		String result3 = removeWord(result2, word3);
		String result4 = removeWord(result3, word4);
		String result5 = removeWord(result4, word5);
		String result6 = removeWord(result5, word6);
		String result7 = removeWord(result6, word7);
		String result8 = removeWord(result7, word8);
		String result9 = removeWord(result8, word9);
		String result10 = removeWord(result9, word10);
		String result11 = removeWord(result10, word11);
		String result12 = removeWord(result11, word12);
		String result13 = removeWord(result12, word13);
		String result14 = removeWord(result13, word14);
		String result15 = removeWord(result14, word15);
		String result16 = removeWord(result15, word16);
		String result17 = removeWord(result16, word17);
		String result18 = removeWord(result17, word18);
		String result19 = removeWord(result18, word19);
		String result20 = removeWord(result19, word20);
		String result21 = removeWord(result20, word21);
		String result22 = removeWord(result21, word22);
		String result23 = removeWord(result22, word23);
		String result24 = removeWord(result23, word24);
		System.out.println("r" + result24);

		String hotels[] = new String[1];
		String replace = result24.replace("Xi'an", "Xian");
		replace = replace.replace("D' Ma", "D Ma");
		replace = replace.replace("IORA – The Retreat", "IORA The Retreat");
		replace = replace.replace(" IORA - The Retreat", "IORA The Retreat");
		replace = replace.replace("Marriott Regent’s Park", "Marriott Regents Park");
		replace = replace.replace("Drei Könige", "Drei Konige");
		hotels[0] = replace;
		// hotels[0]=result22;
		String ch1 = "";
		String test = replace;
		CharSequence seq = "Similar";
		boolean bool = test.contains(seq);
		// System.out.println("Found soft?: " + bool);
		if (bool == true) {
			char[] ch = replace.toCharArray();
			for (int i = 0; i < ch.length - 7; i++) {
				// System.out.println("char at " + i + " index is: " + ch[i]);
				ch1 = ch1 + ch[i];

			}
			// System.out.println(ch1);
			// System.out.println(ch1.length());
			hotels[0] = ch1;
			result24 = String.valueOf(ch1);
			System.out.println(result24);
		}

		String ch3 = "";
		String test3 = replace;
		CharSequence seq3 = "similar";
		boolean bool3 = test3.contains(seq3);
		// System.out.println("Found soft?: " + bool3);
		if (bool3 == true) {
			char[] ch = replace.toCharArray();
			for (int i = 0; i < ch.length - 7; i++) {
				///// System.out.println("char at " + i + " index is: " + ch[i]);
				ch3 = ch3 + ch[i];

			}
			hotels[0] = ch3;
			// System.out.println(ch3);
			// System.out.println(ch3.length());
			result24 = String.valueOf(ch3);
			System.out.println(result24);
		}
		System.out.println(Arrays.toString(hotels));

		return hotels;

	}

}
