package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



/*     Exemple d'utilisation 
 * ConnectSql dBCon = new ConnectSql("test","toto","toto");
		dBCon.WriteTab("wesh");
		ArrayList<String> test = dBCon.getTab();
		for (String obj : test) {
			System.out.println(obj);
		}
 */

public class ConnectSql {

	private String url = "jdbc:mysql://";
	private String login;
	private String password;
	private String select =  "SELECT * from highscore order by n_victory desc limit 5";
	private Connection cn = null;
	private Statement st = null; 
	
	public ConnectSql(String ipDB,String dbName,String login, String password){
		
		this.login = login;
		this.password = password ;
		this.url = this.url+ipDB+"/"+dbName ;
	}
		
	public ArrayList<String> getTab ()
	{
		
		ArrayList<String> liste = new ArrayList<String>();
		
		try {
		
		//D�claration des drivers 
		Class.forName("com.mysql.cj.jdbc.Driver");
	     cn = DriverManager.getConnection(url,login,password);
	     st = cn.createStatement();
	     ResultSet rs = st.executeQuery(select);	
	    	    	
	    	 
    	   while (rs.next()) {
    		   liste.add(rs.getString(1));
    		   liste.add(String.valueOf(rs.getInt(2)));

    	   }
    	   rs.close();
    	  
    	}
	
	
	catch (ClassNotFoundException | SQLException e) {
			
	}
    // Handle any errors that may have occurred.
	
	finally {
		try 
		{
			cn.close();
			st.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
		return liste;
}
}
