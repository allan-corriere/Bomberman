package sql;


import java.sql.*;
import com.mysql.cj.*;

public class WriteTab {

			public void Connexion() {
				
					
					String url = "jdbc:mysql://localhost/test";
					String login = "toto";
					String password = "toto";
					Connection cn = null;
					Statement st = null; 
				     
					try {
						
						Class.forName("com.mysql.cj.jdbc.Driver");

					     String strClassName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";

					     cn = DriverManager.getConnection(url,login,password);
				            // Code here.
					     st = cn.createStatement();
					     
				        
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
	}
			
			

