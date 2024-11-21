package conexiones;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class ConexionCloud {
	public Connection getConexion() throws SQLException{
		final String DB_URL="jdbc:oracle:thin:@vs102024_low?TNS_ADMIN=C:/app/oraclecloudwallet";
    	final String DB_USER = "admin";
    	final String DB_PASSWORD = "Jaimepayaso20O";
        Properties info = new Properties(); 
       
        Connection con=null;
       
		info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
		info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
		info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");
        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);   
        ods.setConnectionProperties(info);
        DatabaseMetaData dbmd;
        OracleConnection oracleconnection=null;
       try {
          oracleconnection= (OracleConnection) ods.getConnection();
          con=(java.sql.Connection)oracleconnection;
          dbmd=con.getMetaData();
	      Logger logger=Logger.getLogger(Conexion.class.getName());
          logger.log(Level.INFO,"Versión de la base de datos: "+dbmd.getDatabaseProductVersion());
         
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return con;
    }

}
