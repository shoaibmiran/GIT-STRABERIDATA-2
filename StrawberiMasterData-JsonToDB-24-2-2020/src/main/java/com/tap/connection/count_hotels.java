package com.tap.connection;

import java.util.regex.*; 

public class count_hotels { 
  
    // Method that returns the count of the given 
    // character in the string 
    public static long count(String s, char ch) 
    { 
  
        // Use Matcher class of java.util.regex 
        // to match the character 
        Matcher matcher 
            = Pattern.compile(String.valueOf(ch)) 
                  .matcher(s); 
  
        int res = 0; 
  
        // for every presence of character ch 
        // increment the counter res by 1 
        while (matcher.find()) { 
            res++; 
        } 
  
        return res; 
    } 
  
    // Driver method 
   /* public static void main(String args[]) 
    { 
        String str = "Macao : Grand View or Similar + Singapore :\r\nHotel Royal @ Queens or Similar"; 
        char c = 'e'; 
        System.out.println(count(str, c)); 
    } */
} 
