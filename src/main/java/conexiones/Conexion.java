package conexiones;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexion {
	public Connection getConexion() throws SQLException {
		   
	    String JNDI="jdbc/bibliotecacloud";
	   
	    Connection con = null;
	       try {
	           InitialContext initialContext = new InitialContext();
	           DataSource ds = (DataSource) initialContext.lookup(JNDI);
	           con = ds.getConnection();
	       } catch (SQLException se) {
	           throw se;
	       } catch (NamingException ne) {
	           ne.printStackTrace();
	       }
	       return con;
	   }
    
}