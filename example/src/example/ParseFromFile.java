package example;

import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.MatchResult;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.json.DataObjectFactory;

public class ParseFromFile {
	Logging log=new Logging();
	public DatabaseLink db;
	public ParseFromFile()
	{
		this.db=new DatabaseLink(1);
	}
	public void Parse(File file)
	{
		   
		    try {
		    	BufferedReader br = new BufferedReader(new FileReader(file));
		    	
		    	String line;
		    	Date started= new Date();
		    	Status status=null;
		        while ((line = br.readLine())!=null) {
		    //    	System.out.println(line);
		        	
		        	
		        //	System.out.println(user.getName()+user.getDescription()+user.getScreenName()+user.getId());
		        	try{
		      //  	User user=DataObjectFactory.createStatus(line).getUser();
		        	status=DataObjectFactory.createStatus(line);
		        	}catch(TwitterException twie)
		        	{
		        		log.invalidRawStatus(line, 1);
		       // 		System.out.println(line);
		       // 		System.out.println(line);
		     //   		System.exit(0);
		        	}
		        	this.db.insertTweet(status,false);
		   //         System.out.println(line);
		        }
		        this.db.finalize();
		        Date finished= new Date();
		        Double took=20000000d/(finished.getTime()-started.getTime());
		        System.out.println(started.getTime()-finished.getTime()+"   "+took);
		        br.close();
		       file.delete();
		    }catch(IOException exept)
		    {
		    	log.logTerribleException(exept.toString(), 0);

		    } 
	}
	

}
