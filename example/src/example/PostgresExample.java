/** 
 * ConnectPostgre.java 
 */  
package example;
  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.Statement;  
  
/** 
 * @author www.javaworkspace.com 
 *  
 */  
public class PostgresExample {  
    public static void main(String[] args) {  
        Connection connection = null;  
        ResultSet resultSet = null;  
        Statement statement = null;  
  
        try {  
            Class.forName("org.postgresql.Driver");  
            connection = DriverManager.getConnection(  
                    "jdbc:postgresql://localhost:5432/twitter_collector", "postgres",  
                    "postgres");  
            statement = connection.createStatement();  
            resultSet = statement  
                    .executeQuery("SELECT * FROM \"Tweets\"");  
            while (resultSet.next()) {  
                System.out.println("EMPLOYEE NAME:"  
                        + resultSet.getString("Tweet_id"));  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                resultSet.close();  
                statement.close();  
                connection.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
} 