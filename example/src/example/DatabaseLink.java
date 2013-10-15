package example;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.postgresql.util.PSQLException;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.User;

public class DatabaseLink {
	Integer BatchUserCounter=0;
	Integer BatchStatusCounter=0;
	Integer TotalStatusCounter=0;
	Connection connection = null;
	PreparedStatement psUserCm,psStatusCm,psUser,psStatus;
	public Integer collectionMethod=null;
	public Logging logger=new Logging();
	ConfigurationVariables confvar=new ConfigurationVariables();
	
	
	public DatabaseLink(Integer colMet) {
		this.collectionMethod=colMet;
		try {
			Class.forName("org.postgresql.Driver");

			this.connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/twittercollector",
					"postgres", "postgres");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		try {
			this.psUserCm = this.connection
					.prepareStatement("INSERT INTO user_collections("
	          +  "collection_method_id, twitter_user_id)"
	   + "VALUES (?, ?)");
			this.psUser = this.connection
					.prepareStatement("INSERT INTO twitter_users("
							+ "twitter_user_id, created_at, description, screen_name, name, "
							+ "location, statuses_count)"
							+ "VALUES (?, ?, ?, ?, ?, " + "?, ?);");
			 this.psStatus = this.connection
					.prepareStatement("INSERT INTO tweets("+
            "coordinates_lat, coordinates_long, created_at, place, truncated,"+ 
            "tweet_id, text, twitter_user_id)"+
    "VALUES (?, ?, ?, ?, ?,"+
            "?, ?, ?);");
	
		this.psStatusCm = this.connection
					.prepareStatement("INSERT INTO tweet_collections("
	          +  "collection_method_id,tweet_id)"
	   + "VALUES (?, ?)");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}

	public void insertuser(User user,Boolean encode) {

		ResultSet resultSet = null;
		Statement statement = null;

		try {


			@SuppressWarnings("deprecation")
			java.sql.Date sqlDate = new java.sql.Date(user.getCreatedAt()
					.getTime());
			if(!encode)
			{
				this.psUser.setLong(1, user.getId());
				this.psUser.setDate(2, sqlDate);
				this.psUser.setString(3, user.getDescription());
				this.psUser.setString(4, user.getScreenName());
				this.psUser.setString(5, user.getName());
				this.psUser.setString(6, user.getLocation());
				this.psUser.setInt(7, user.getStatusesCount());
			
			}else{
				this.psUser.setLong(1, user.getId());
				this.psUser.setDate(2, sqlDate);
			if(user.getDescription()==null){this.psUser.setNull(3,Types.VARCHAR);}else this.psUser.setString(3, user.getDescription().getBytes("UTF-8").toString());
			if(user.getScreenName()==null){this.psUser.setNull(4,Types.VARCHAR);}else this.psUser.setString(4, user.getScreenName().getBytes("UTF-8").toString());
			if(user.getName()==null){this.psUser.setNull(5,Types.VARCHAR);}else this.psUser.setString(5, user.getName().getBytes("UTF-8").toString());
			if(user.getLocation()==null){this.psUser.setNull(6,Types.VARCHAR);}else this.psUser.setString(6, user.getLocation().getBytes("UTF-8").toString());
			this.psUser.setInt(7, user.getStatusesCount());
		
	}	
			this.psUserCm.setInt(1,this.collectionMethod);
			this.psUserCm.setLong(2,user.getId());

			try {
				
				this.psUser.addBatch();
				this.psUserCm.addBatch();
				if(this.BatchUserCounter>=confvar.getBatchSize()){
					this.psUser.executeBatch();
					this.psUserCm.executeBatch();
					this.BatchUserCounter=0;
				}else this.BatchUserCounter++;
			} catch (BatchUpdateException esql_1) {
				PSQLException esql=(PSQLException) ((java.sql.BatchUpdateException) esql_1).getNextException();
				
				
				//esql.printStackTrace();
				if (((PSQLException) esql).getServerErrorMessage()
						.getMessage()
						.equals("duplicate key value violates unique constraint \"pk_twitter_users\"")||esql.getServerErrorMessage()
						.getMessage()
						.startsWith("duplicate key value violates unique constraint")||
						((PSQLException) esql).getServerErrorMessage()
								.getMessage()
								.equals("duplicate key value violates unique constraint \"usercollectionspk\"")) {
					System.out.println("llave duplicada");
				} else {
					if (((PSQLException) esql).getServerErrorMessage()
							.getMessage().contains("UTF-8")){
						if(!encode){
						this.insertuser(user, true);
						}else{
							System.out.println("Ni se que paso!!");
							logger.logException(esql.getMessage(), 0);
						}
					}
				else {
					System.out.println("Ni se que paso!!");
					System.out.println(user);
					System.out.println(((SQLException) esql).getNextException().getMessage());
					System.out.println();
					esql.printStackTrace();
					
				}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	public void insertTweet(Status status,Boolean forceflush) {

		ResultSet resultSet = null;
		Statement statement = null;
		Double latitude=null;
		Double longitude=null;

		try {
			if(!forceflush)
			{
			@SuppressWarnings("deprecation")
			
			
			java.sql.Timestamp timestamp = new java.sql.Timestamp(status.getCreatedAt().getTime());

			
			
			
			if(status.getPlace()==null){this.psStatus.setNull(1,Types.VARCHAR);}else this.psStatus.setString(1, status.getPlace().getName());
			if(status.getGeoLocation()==null){
				this.psStatus.setNull(1,Types.NUMERIC);
				this.psStatus.setNull(2,Types.NUMERIC);
				}else {
					this.psStatus.setDouble(1,status.getGeoLocation().getLatitude());
					this.psStatus.setDouble(2,status.getGeoLocation().getLongitude());
				}
			if(status.getCreatedAt()==null){this.psStatus.setNull(3,Types.DATE);}else this.psStatus.setTimestamp(3, timestamp);
			if(status.getPlace()==null){this.psStatus.setNull(4,Types.VARCHAR);}else this.psStatus.setString(4, status.getPlace().getName());
			
			this.psStatus.setBoolean(5,status.isTruncated());
			this.psStatus.setLong(6, status.getId());
			this.psStatus.setString(7, status.getText());
			this.psStatus.setLong(8, status.getUser().getId());
	//		insertuser(status.getUser(),false);
			
			this.psStatusCm.setInt(1,this.collectionMethod);
			this.psStatusCm.setLong(2,status.getId());	
			
		//	System.out.println(ps.toString());
			

			}
			
			


			try {
				if(!forceflush){
//				System.out.println(" "+this.TotalStatusCounter+ " "+status.getId());
				this.psStatus.addBatch();
				this.psStatusCm.addBatch();
				}
				if((this.BatchStatusCounter>=this.confvar.getBatchSize()||forceflush)&&this.BatchStatusCounter>0){
					java.sql.Timestamp now = new java.sql.Timestamp(0);
					System.out.println(now+" "+this.BatchStatusCounter+" "+this.TotalStatusCounter);
					this.psUser.executeBatch();
//					System.out.println("users added"+this.psUser.getUpdateCount());
					this.psUserCm.executeBatch();
//					System.out.println("users cm added"+this.psUserCm.getUpdateCount());
					this.psStatus.executeBatch();
//					System.out.println("tweets added"+this.psStatus.getUpdateCount());
					this.psStatusCm.executeBatch();
//					System.out.println("tweets cm added"+this.psStatusCm.getUpdateCount());
					

					this.TotalStatusCounter++;

					this.BatchStatusCounter=0;
					this.BatchUserCounter=0;
				}else {
					this.BatchStatusCounter++;
					this.TotalStatusCounter++;
				}
			} catch (BatchUpdateException esql_1) {
			
	
				
			//	System.out.println(" updated "+uc.length);
//				System.exit(0);


				PSQLException esql=(PSQLException) esql_1.getNextException();
//				System.out.println(esql.getServerErrorMessage()+" "+status.getId()+" "+status.getUser().getId());
				 esql=(PSQLException) esql_1.getNextException();
//				System.out.println(esql.getServerErrorMessage());
				if (esql.getServerErrorMessage()
						.getMessage()
						.equals("duplicate key value violates unique constraint \"pk_twitter_users\"")||esql.getServerErrorMessage()
						.getMessage()
						.startsWith("duplicate key value violates unique constraint")||
						esql.getServerErrorMessage()
								.getMessage()
								.equals("duplicate key value violates unique constraint \"pk_user_collections\"")) {
			//		System.out.println("llave duplicada"+this.TotalStatusCounter);
			 
			
					this.BatchStatusCounter=0;
					this.BatchUserCounter=0;
				} else {
					esql.printStackTrace();
					System.exit(0);
					
				}
				 
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	protected void finalize()  
	{  Status status=null;
	this.insertTweet(status, true);
	    try { this.connection.close(); 
	    super.finalize();  } 
	    catch (Throwable e) { 
	        e.printStackTrace();
	    }
	    
	}
}
