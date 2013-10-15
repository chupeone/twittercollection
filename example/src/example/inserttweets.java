package example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.postgresql.util.PSQLException;

import twitter4j.Status;

public class inserttweets {

	public static void main2(String[] args) {
		
		Connection connection=null;
		try {
			Class.forName("org.postgresql.Driver");

			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/twittercollector",
					"postgres", "postgres");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
    	try {
    		File file=new File("C:\\testall2.csv");
			BufferedReader br = new BufferedReader(new FileReader(file));
	    	String line;
	    	Date started= new Date();
	    	Status status=null;
	
	        	PreparedStatement ps=connection
				.prepareStatement("INSERT INTO shubidubi("
       +  "select * from new_tweets where tweet_id=?)");
	        	
	    	 int counter=0;
	    	 int counter2=0;
	        while ((line = br.readLine())!=null) {
	        	try {
	        		Long twid=Long.parseLong(line);
	        	ps.setLong(1,twid);
	     //   	System.out.println(ps.toString());
	        	ps.addBatch();
	        	if(counter==10000)
	        	{
	        		counter=0;
	        		ps.executeBatch();
	        		System.out.println( counter2++);
	        	}
	        	counter++;
	    //    	System.out.println(line);
	        }catch (BatchUpdateException esql_1) {
				PSQLException esql=(PSQLException) ((java.sql.BatchUpdateException) esql_1).getNextException();
				System.out.println(esql.getMessage());
	        }
	        }	ps.executeBatch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
		System.out.println("exception");
		}
	        	
	}
	
	public static void main(String[] args) {
		

		// TODO Auto-generated method stub
		
    	try {
    		File file=new File("C:\\testall2.csv");
			BufferedReader br = new BufferedReader(new FileReader(file));
	    	String line;

    	 int counter=0;
	    	 int counter2=0;
	        while ((line = br.readLine())!=null) {
	        	

	        	counter++;
	    //    	System.out.println(line);
	        
	        }	
	        System.out.println(counter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} 
	        	
	}

}
