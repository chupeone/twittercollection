package example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.stream.StreamFilter;

import twitter4j.RawStreamListener;

public class RawStreamListenerImplementation implements RawStreamListener {
	int count;
	Date started= new Date();
	Date previous= new Date();
	String filename="tweetstoimport/file";
	File file  ;
	FileWriter fw ;
	BufferedWriter bw;
	String previousfilename="";
	ConfigurationVariables confv=new ConfigurationVariables();
	public RawStreamListenerImplementation(){
		this.updateFileToSave();
		this.count=0;
	}
	public void onMessage(String message) {
		if(!message.startsWith("{\"delete"))
		{
			
	         
			// if file doesnt exists, then create it
	


 
            if (this.count%1000==0){
            
            	System.out.println(this.filename+"i have no filename");
            	
            	
            	
            	
            	
            	
            	
            	this.updateFileToSave();
            	
            	
    			try {
					this.fw = new FileWriter(this.file.getAbsoluteFile(),true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			Date finished10k=new Date();
    			this.bw = new BufferedWriter(this.fw);
            	System.out.println("\n\n\n\n   AVERAGE  RATE OF TWEETS is "+(this.count*1000/(finished10k.getTime()-this.started.getTime())));
            	System.out.println( 1000000d/(finished10k.getTime()-this.previous.getTime()));
            	System.out.println(this.count);
            	System.out.println(finished10k.getTime()+" "+this.started.getTime()+" "+(finished10k.getTime()-this.started.getTime()));
               	System.out.println(finished10k.getTime()+" "+this.previous.getTime()+" "+(finished10k.getTime()-this.previous.getTime()));
               	System.out.println("\n\n\n\n");
            	this.previous=finished10k;
            	
            }
 
		
		
		
		
		
		
		
		this.count++;
      //  System.out.println(status.getUser().getName() + " : " + status.getText()+" "+ this.count);
		try {
			
			

			this.bw.write(message+"\n");
			this.bw.flush();

	//	bw.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
    }
	
	}
	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}
	//Method that updates the file we are saving to, so that they don't get massive
	public void updateFileToSave(){
		System.out.println("count is "+this.count);
		Calendar time = Calendar.getInstance();
	        // 	filename=Calendar.YEAR+"-"+Calendar.MONTH+" "+Calendar.DAY_OF_MONTH+" "+Calendar.HOUR_OF_DAY;
	            this.filename=""+time.get(Calendar.YEAR)+"-"+time.get(Calendar.MONTH)+"-"+time.get(Calendar.DATE)+"-"+time.get(Calendar.HOUR_OF_DAY);
	            System.out.println("filename changed to "+this.filename);
	         
	         if(this.filename.equals(this.previousfilename))
	         {
	        	 System.out.println(this.previousfilename+"filename same to "+this.filename);
	         }else{
	        	 if(!this.previousfilename.isEmpty()){
	        		 System.out.println(this.previousfilename+"Moving "+this.filename);
	        		 File filetomove=new File(confv.getTempTweetDir()+this.previousfilename+".txt");		       	        		 
	        		
	        		 try {
					this.bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        		System.out.println(this.filename+this.previousfilename+" movi!! "+filetomove.length());
	        	 filetomove.renameTo(new File(confv.getFinalTweetDir()+this.previousfilename+".txt"));
	   
	        	 }
	        	 System.out.println(this.filename+this.previousfilename);
	        	 this.previousfilename=this.filename;
	         }
	         System.out.println(confv.getTempTweetDir()+this.filename);
	         this.file = new File(confv.getTempTweetDir()+this.filename+".txt");
	}
}

