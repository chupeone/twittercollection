package example;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class search {
	/*
	 * Copyright 2007 Yusuke Yamamoto
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License");
	 * you may not use this file except in compliance with the License.
	 * You may obtain a copy of the License at
	 *
	 *      http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS,
	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	 * See the License for the specific language governing permissions and
	 * limitations under the License.
	 */





	    /**
	     * Usage: java twitter4j.examples.search.SearchTweets [query]
	     *
	     * @param args
	     */
	    public static void main(String[] args) {
	    	


	    	
	        StatusListener listener = new StatusListener(){
	        	public Double count=0d;
	        	Date started= new Date();
	        	Date previous= new Date();
	        	@Override
	            public void onStatus(Status status) {
	        		
	        		try {

	         
	        			File file = new File("whythissucks.txt");
	         
	        			// if file doesnt exists, then create it
	         
	        			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
	        			BufferedWriter bw = new BufferedWriter(fw);

	         
		                if (this.count%1000==0){
		                	Date finished10k=new Date();
		                	
		                	System.out.println("\n\n\n\n   AVERAGE  RATE OF TWEETS is "+(this.count*1000/(finished10k.getTime()-this.started.getTime())));
		                	System.out.println( 1000000d/(finished10k.getTime()-this.previous.getTime()));
		                	System.out.println(this.count);
		                	System.out.println(finished10k.getTime()+" "+this.started.getTime()+" "+(finished10k.getTime()-this.started.getTime()));
		                   	System.out.println(finished10k.getTime()+" "+this.previous.getTime()+" "+(finished10k.getTime()-this.previous.getTime()));
		                	System.out.println(status.getSource());
		                   	System.out.println("\n\n\n\n");
		                	this.previous=finished10k;
		                	
		                }
	         
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        		this.count++;
	              //  System.out.println(status.getUser().getName() + " : " + status.getText()+" "+ this.count);

        			bw.write(status.getId()+"|"+status.getText()+"\n");
	        		
        			bw.close();

	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}	
	            }
	            @Override
	            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	            //	System.out.println(statusDeletionNotice.getUserId()+" has deleted this tweet");
	            }
	            @Override
	            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	            	System.out.println("limited "+numberOfLimitedStatuses);
	            }
	            @Override
	            public void onException(Exception ex) {
	                ex.printStackTrace();
	            }
				@Override
				public void onScrubGeo(long arg0, long arg1) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onStallWarning(StallWarning arg0) {
					// TODO Auto-generated method stub
					System.out.println(arg0.getMessage());
					
				}
	        };

	        
	        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
	        RawStreamListener rawst=new RawStreamListener(){
	        	public Double count=0d;
	        	public Double lengthsum=0d;
	        	Date started= new Date();
	        	Date previous= new Date();
	        	String filename;
	        	String previousfilename="";
	        	@Override
	        	
				public void onMessage(String message) {
	        		if(!message.startsWith("{\"delete"))
	        			{
	        		
		        		try {

		        			
		         
		        			// if file doesnt exists, then create it
		         


		         
			                if (this.count%1000==0){
			                	

				       	            
			                	
			                	Date finished10k=new Date();
			                	
			                	System.out.println("\n\n\n\n   AVERAGE  RATE OF TWEETS is "+(this.count*1000/(finished10k.getTime()-this.started.getTime())));
			                	System.out.println( 1000000d/(finished10k.getTime()-this.previous.getTime()));
			                	System.out.println(this.count);
			                	System.out.println(finished10k.getTime()+" "+this.started.getTime()+" "+(finished10k.getTime()-this.started.getTime()));
			                   	System.out.println(finished10k.getTime()+" "+this.previous.getTime()+" "+(finished10k.getTime()-this.previous.getTime()));
			                   	System.out.println("\n\n\n\n");
			                	this.previous=finished10k;
			                	
			                }
		         
			                File file = new File("tweetstoimport/"+this.filename);
		        			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		        			BufferedWriter bw = new BufferedWriter(fw);
		        		
		        		
		        		
		        		
		        		
		        		this.count++;
		              //  System.out.println(status.getUser().getName() + " : " + status.getText()+" "+ this.count);
	        			bw.write(message+"\n");
	        			bw.close();
	        			fw.close();
	        			
	        			
	        	

		        		} catch (IOException e) {
		        			e.printStackTrace();
		        		}	
		            }
	        		
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onException(Exception arg0) {
					// TODO Auto-generated method stub
					
				}
	        };
	  //      twitterStream.addListener(rawst);
	      //  String[] searchfor={"flu", "influenza", "fever", "cough", "sore", "throat", "sore throat", "headache"};
	     //   FilterQuery query=new FilterQuery();
	     //   query.track(searchfor);
	        twitterStream.addListener(listener);
	       // twitterStream.filter(query);
	        
	        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
	        twitterStream.sample();
	      //Filters from the stream
	        //twitterStream.filter(query);
	    }
	}



