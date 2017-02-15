package com.IgniteEvents.automation.common;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class importTest{
	
	public static void main(String[] args) throws NamingException, FileNotFoundException {
		for (int j = 1; j <= 2; j++) {
		
			File file = new File ("E:\\ig\\"  +j+ "_supportersWith.csv");	
		PrintWriter out = new PrintWriter(file);
		out.println("Cell Phone,"
				+ "City,"
				+ "Email,"
				+ "Facebook,"
				+ "First Name,"
				+ "Home Phone,"
				//+ "ID,"
				+ "Last Name,"
				+ "Preferred Language,"
				+ "State,"
				//+ "Time Zone,"
				+ "Twitter,"
				//+ "Username,"
				+ "Zip Code," 
				+ "Address line 1,"
				+ "Address line 2,"
				+ "Middle name,"
				
				
				+ "kirilltextbox," 
				+ "kirillnumber," 
				+ "kirillyesNo," 
				+ "kirillDateTime,"
				+ "kirillsingleCHoice"
	
				
		

				);
		for (int i = 0; i <= 16000; i++) {
			String cPhone = "32165498765";
			String City = "CityT";
			String Email = j+"_supporters20k" + i + "@devnull.test.ignite.net";//".3e41c646@mailosaur.in";//"@salsalabs.com";//"@devnull.test.ignite.net";
			String Facebook = "FBV";
			String First_Name = "Tester" + i;
			String Home_Phone = "98765432112";
			//String ID = Integer.toString(i*1321212);
			String Last_Name = "Testerov" + i;
			String PreferredLanguage = "English (United States)";
			String State = "NY";
			//String Time_Zone = "(GMT-05:00) Eastern Time";
			String Twitter = "TWV";
			//String Username = "Vitaliy" + i;
			String Zip_Code = "65498";
			String AddressLine1 = "Street " + i;
			String AddressLine2 = "Street line2 " + i;
			String MiddleName = "MName" ;
			String kirilltextbox = "kirilltextbox" ;
			String kirillnumber = "78136" ;
			String kirillyesNo = "True" ;
			String kirillDateTime = "01/23/2017" ;
			String kirillsingleCHoice = "1" ;
			
			
			
			out.println(cPhone + "," +
					City + "," +
					Email + "," +
					Facebook + "," +
					First_Name + "," +
					Home_Phone + "," +
					//ID + "," +
					Last_Name + "," +
					PreferredLanguage + "," +
					State + "," +
					//Time_Zone + "," +
					Twitter + "," +
					//Username + "," +
					Zip_Code + "," +
					AddressLine1 + "," +
					AddressLine2 + "," +
					MiddleName + "," + 
					kirilltextbox + "," +
					kirillnumber + "," +
					kirillyesNo + "," +
					kirillDateTime + "," +
					kirillsingleCHoice
					);
		}
		out.close();
		System.out.println("Done");

	}
	}

}


