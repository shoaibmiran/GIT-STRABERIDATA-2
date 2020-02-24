package com.shoaib.Action;

public class bool {
    public static void main(String args[]){  
    int a=10;  
    int b=-10;  
    boolean c=true;  
    boolean d=false; 
    c=false;
    d=true;
    System.out.println(~a);//-11 (minus of total positive value which starts from 0)  
    System.out.println(~b);//9 (positive of total minus, positive starts from 0)  
    System.out.println(!c);//false (opposite of boolean value)  
    System.out.println(!d);//true  
    
    boolean a1 = false; // --> variable 
    if(! a1)
    {
       System.out.println("Not A i.e. A if false"+!a1);
    }
    
    
    }}  
