package com.tap.connection;

import com.tap.model.Hotels;

public class split_hotels_places {

	public static void main(String[] args) {
//		String s1 = "Phuentsholing : Hotel Druk Or Similar + Thimphu : Kisa Villa Or Similar + Punakha : Zhingkham Resort Or Similar + Paro : Tashi Namgay Resort & Spa Or\r\nSimilar";
//
//		String ch1 = "";
//		String ch2 = "";
//		String c = "";
//		String fistr = "";
//		char[] ch = s1.toCharArray();
//		int o = s1.length();
//		for (int i = 0; i < ch.length; i++) {
//			System.out.println("char at " + i + " index is: " + ch[i]);
//			ch2 = ch1;
//			ch1 = ch1 + ch[i];
//			if (ch[i] == ':') {
//				System.out.println(ch2);
//				System.out.println(ch2.length());
//
//				c = c + ch[i - 1];
//				System.out.println("last character--->:" + c);
//
//				if (c.equalsIgnoreCase(" ")) {
//					char[] chr = ch2.toCharArray();
//					for (int k = 0; k < chr.length - 1; k++) {
//						fistr = fistr + chr[k];
//					}
//
//					System.out.println("Place--->:" + fistr);
//					System.out.println(fistr.length());
//
//					// attributes.setPlace(fistr);
//				} else if (c != " ") {
//					System.out.println("PLACE:" + ch2);
//					System.out.println(ch2.length());
//					// attributes.setPlace(ch2);
//				}
//				ch2 = "";
//				ch1 = "";
//				c = "";
//				fistr = "";
//				if (ch[i + 1] == ' ') {
//					i++;
//				}
//			} else if (ch[i] == ':') {
//				ch2 = "";
//				ch1 = "";
//				if (ch[i + 1] == ' ') {
//					i++;
//				}
//			} else if (ch[i] == '+') {
//				System.out.println("hotel name:::" + ch2);
//				ch2 = "";
//				ch1 = "";
//				if (ch[i + 1] == ' ') {
//					i++;
//				}
//			}
//			else if(i==o-2)
//			{
//				System.out.println("hotel name:::" + ch2);
//				ch2 = "";
//				ch1 = "";
//				if (ch[i + 1] == ' ') {
//					i++;
//				}
//			}
//
//			
//		}
		
		
		String str1 = "Hello World  "; 
        System.out.println(str1); 
        System.out.println(str1.trim()); 
  
        String str2 = "      Hey  there    Joey!!!      "; 
        System.out.println(str2); 
        System.out.println(str2.trim()); 
	}

	public static void getplacename(String hotel_name) {
		Hotels attributes = new Hotels();
		// String s1 = "Phuentsholing: Lhaki Hotel Or Similar + Thimphu : Hotel White :
		// Tara Or Similar";
		String ch1 = "";
		String ch2 = "";
		String c = "";
		String fistr = "";
		char[] ch = hotel_name.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			System.out.println("char at " + i + " index is: " + ch[i]);
			ch2 = ch1;
			ch1 = ch1 + ch[i];
			if (ch[i] == ':') {
				System.out.println(ch2);
				System.out.println(ch2.length());

				c = c + ch[i - 1];
				System.out.println("last character--->" + c);

				if (c.equalsIgnoreCase(" ")) {
					char[] chr = ch2.toCharArray();
					for (int k = 0; k < chr.length - 1; k++) {
						fistr = fistr + chr[k];
					}

					System.out.println("final result--->" + fistr);
					System.out.println(fistr.length());

					attributes.setPlace(fistr);
				} else if (c != " ") {
					System.out.println("Final result" + ch2);
					System.out.println(ch2.length());
					attributes.setPlace(ch2);
				}
				ch2 = "";
				ch1 = "";
				c = "";
				fistr = "";

			} else if (ch[i] == '+') {
				System.out.println(ch2);
				ch2 = "";
				ch1 = "";
			}
		}
	}
}
