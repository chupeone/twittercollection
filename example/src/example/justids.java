package example;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
public class justids {
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

	        			File file = new File("Filtered_over1percent_lab_pc_obama_all.txt");        
	        			File file2 = new File("Filtered_over1percent_lab_pc_obama.txt");
	         
	        			// if file doesnt exists, then create it
	         
	        			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
	        			BufferedWriter bw = new BufferedWriter(fw);
	        			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile(),true);
	        			BufferedWriter bw2 = new BufferedWriter(fw2);
	         
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
        			bw.write(status.getId()+"\n");
        			bw.close();
        			if(status.getText().contains("obama")){
            			bw2.write(status.getId()+"\n");
            			bw2.close();       				
        			}

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
	            	System.out.println(numberOfLimitedStatuses+" are missing from here");
	            	
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
					
				}
	        };

	        
	        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
	        RawStreamListener rawst=new RawStreamListener(){
	        	public Double count=0d;
	        	public Double lengthsum=0d;
	        	Date started= new Date();
	        	Date previous= new Date();
	        	@Override
	    
				public void onMessage(String message) {
	        		if(!message.startsWith("{\"delete"))
	        			{
	        		
	        		count++;
	        		lengthsum+=message.length();
	        			if(count%1000==0)
	        			{
	        				System.out.println(lengthsum/count);
//	        				lengthsum=0d;
//	        				count=0d;
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
	        String[] searchfor={"language","people","problem","microsoft","epidemic","obama","zoo"};
	        FilterQuery query=new FilterQuery();
	        query.track(searchfor);
	        twitterStream.addListener(listener);
	        twitterStream.filter(query);
	        
	        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
	//        twitterStream.sample();
	    }
	}

