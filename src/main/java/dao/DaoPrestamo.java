package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexiones.Conexion;
import entidades.Libro;

public class DaoPrestamo {
	 public ArrayList<Libro>listadoPrestamo(int socio) throws SQLException {
		 ArrayList<Libro>listadoPrestamo=new ArrayList<>();
		 Conexion conexion=new Conexion(); // Creamos un objeto Conexion.
	       Connection con=null; // Objeto para conectar a la bbdd.
	       ResultSet rs = null; // Donde recojo los resultados de la consulta
	       PreparedStatement pst = null;
	       
	       try {
			con =conexion.getConexion();
		
			
			String ordenSQL="SELECT S.NOMBRE,L.TITULO, P.FECHAPRESTAMO, TRUNC (SYSDATE-FECHALIMITEDEVOLUCION) DIAS_DEMORA "
					+ " FROM PRESTAMO P,EJEMPLAR E, LIBRO L,SOCIO S"
					+ " WHERE P.IDEJEMPLAR = E.IDEJEMPLAR"
					+ " AND E.ISBN = L.ISBN"
					+ " AND P.IDSOCIO = S.IDSOCIO"
					+ " AND P.IDSOCIO=?";
			pst =con.prepareStatement(ordenSQL);
			pst.setInt(1,socio);
			rs=pst.executeQuery();
			
			while(rs.next()) {
				Libro p =new Libro();
				p.setNombre(rs.getString("NOMBRE"));
				p.setTitulo(rs.getString("TITULO"));
				p.setDiademora(rs.getString("DIAS_DEMORA"));
				p.setFechaPrestamo(rs.getDate("FECHAPRESTAMO"));
			listadoPrestamo.add(p);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       finally {
	           // liberamos los recursos en un finally para asegurarnos que se cierra
	           // todo lo abierto
	           if(rs!=null)
	               rs.close();
	           if(pst!=null)
	               pst.close();
	           if(con!=null)
	               con.close();
	       }
		return listadoPrestamo;
		 
	 }
	 
	 public boolean realizarPrestamo(int codigoSocio, int codigoEjemplar) throws SQLException {
		    String sql = "INSERT INTO PRESTAMO (IDSOCIO, IDEJEMPLAR) VALUES (?, ?)";
		    Conexion conexion = new Conexion();
		    try (Connection con = conexion.getConexion();
		         PreparedStatement ps = con.prepareStatement(sql)) {
		        
		        ps.setInt(1, codigoSocio);
		        ps.setInt(2, codigoEjemplar);
		        
		        int rowsAffected = ps.executeUpdate();
		        return rowsAffected > 0;  // Si se insertó un registro correctamente
		    }
		}

}