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

	private String url = "jdbc:mysql://localhost/";
	private String login;
	private String password;
	private String insert = "INSERT INTO highscore(nickname, n_victory) VALUES";
	private String selectGen = "SELECT * from highscore";
	private String select =  "SELECT * from highscore order by n_victory desc limit 10";
	private Connection cn = null;
	private Statement st = null; 
	
	public ConnectSql(String dbName,String login, String password){
		
		this.login = login;
		this.password = password ;
		this.url = this.url+dbName ;
	}
	
	public void WriteTab(String playerId) {
	
		try {
			
			//D�claration des drivers 
			Class.forName("com.mysql.cj.jdbc.Driver");	
			
		     cn = DriverManager.getConnection(url,login,password);
		     st = cn.createStatement();
		     ResultSet rs = st.executeQuery(selectGen);	
		    	    	
	    	 //V�rification de l'existence du player dans la liste 

		    	 boolean exist = false;	 
		    	 
		    	   while (rs.next()) {
		    	         if (rs.getString(1).equals(playerId)) {
		    	        	 exist = true;
		    	         }
		    	   }
		    	   rs.close();
		    	   
		    	   if (exist == false) {
		    		  String  insertFirst = insert + "('"+playerId+"', 1)";
		    		   st.executeUpdate(insertFirst);
		    	   }
		    	   else 
		    	   {
		    		   String update = "UPDATE highscore SET n_victory = n_victory+1 WHERE nickname = '"+playerId+"'";
		    		   st.executeUpdate(update);
		    	   }

		    	}
		
		
		catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
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
			e.printStackTrace();
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
