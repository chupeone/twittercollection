package example;

import java.io.*;
import java.util.Scanner;
import java.util.regex.MatchResult;
public class compare_files {
	public static void main(String args[])
	{
		   
		    try {
		    	BufferedReader br = new BufferedReader(new FileReader("filterproblem.txt"));
		    	String line;
		    	String line2;
		    	int found=0;
		    	int notfound=0;
		    
		    	String[] subl2=null;
		        while ((line = br.readLine())!=null) {
		        	boolean flag=false;
		        	String subl1=line.substring(0,18);
		        	//System.out.println(subl1);
		        	BufferedReader br2 = new BufferedReader(new FileReader("Filtered_over1percent_lab_pc_obama_all.txt"));	        	
		        	while ((line2 = br2.readLine())!=null) {
		        		
		        	if(line2.contains(subl1)){
		        	found++;
		        	System.out.println("Encontre "+line2+" "+subl1);
		        	flag=true;
		        	}
		        	}
		        	br2.close();
		        	if(!flag){System.out.println("No Encontre "+line);
		        	notfound++;}
		           
		       //     System.out.println(line);
		        }
		        
		        
		        System.out.println("Encontre 	"+found);
		        System.out.println("No Encontre 	"+notfound);
		        System.out.println("Percentage 	"+notfound/found);
		        br.close();
		    }catch(Exception exept)
		    {
		    	exept.printStackTrace();
		    } 
	}
	

}
