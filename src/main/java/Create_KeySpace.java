import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
public class Create_KeySpace {
   public static void main(String args[]){

     String query = "CREATE KEYSPACE test_space3 WITH replication "
         + "= {'class':'SimpleStrategy', 'replication_factor':1};"; //Query
                    
	Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build(); //creating Cluster object
    
	Session session = cluster.connect(); //Creating Session object
	session.execute(query); //Executing the query
	//session.execute("USE myspace"); //using the KeySpace
          session.close();
          cluster.close();
        System.out.println("Keyspace created"); 
   }
}