package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import conexiones.Conexion;
import entidades.Autor;
import entidades.Socio;

public class DaoAutor {
	
	public ArrayList<Autor> ListadoAutores() throws Exception {
        // Inicializamos el ArrayList donde almacenaremos los autores
        ArrayList<Autor> listaAutores = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            // Establecemos la conexión
            con = conexion.getConexion();
            st = con.createStatement();

            // Ejecutamos la consulta SQL
            String ordenSQL = "SELECT * FROM AUTOR ORDER BY NOMBRE";
            rs = st.executeQuery(ordenSQL);

            // Recorremos el ResultSet y creamos objetos Autor
            while (rs.next()) {
                Autor miAutor = new Autor();
                miAutor.setIdAutor(rs.getInt("IDAUTOR"));
                miAutor.setNombre(rs.getString("NOMBRE"));
                miAutor.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));

                // Añadimos cada autor al ArrayList
                listaAutores.add(miAutor);
            }
        } catch (SQLException e) {
            // Lanzamos la excepción SQL para que sea manejada externamente
            throw e;
        } finally {
            // Cerramos los recursos en el bloque finally
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }

        // Retornamos el ArrayList con los autores
        return listaAutores;
    }
	
	public Autor findAutorById(int idautor) throws Exception{
		Autor a =null;
		Conexion conexion = new Conexion(); 
		Connection con = null;	
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			con=conexion.getConexion(); //Creamos un objeto Conexion
			String ordenSQL = "SELECT IDAUTOR,NOMBRE,FECHANACIMIENTO FROM AUTOR" + " WHERE IDAUTOR=?";
			st=con.prepareStatement(ordenSQL); //Creamos un objeto Statement
			st.setInt(1, idautor);
			rs= st.executeQuery();
		    while(rs.next()) {
		    	//Por cada fila obtenida, creamos un objeto autor que añadimos al ArrayList
		    	a = new Autor();
		    	a.setIdAutor(rs.getInt("IDAUTOR"));
		    	a.setNombre(rs.getString("NOMBRE"));
		    	a.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
		   }
		}catch (SQLException e){
			throw e;
		}catch (Exception ex) {
			throw ex;
		}
		finally {
			/* liberamos los recursos en un finally para asegurarnos que se cierra
			 * todo lo abierto
			 */
			if (rs!=null) {
				rs.close();
			}
			if(st!=null) {
				st.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		return a;
	}
	public Autor addAutor(Autor a) throws Exception{
		Conexion conexion = new Conexion(); 
		Connection con = null;	
		PreparedStatement st = null;
		try {
			con=conexion.getConexion(); //Creamos un objeto Conexion
			String ordenSQL = "INSERT INTO AUTOR VALUES(S_AUTOR.NEXTVAL,?,?)";
			st=con.prepareStatement(ordenSQL); //Creamos un objeto Statement
			st.setString(1, a.getNombre());
			st.setDate(2, a.getFechaNacimiento());
			st.executeUpdate();
		}catch (SQLException e){
			throw e;
		}catch (Exception ex) {
			throw ex;
		}
		finally {
			/* liberamos los recursos en un finally para asegurarnos que se cierra
			 * todo lo abierto
			 */
			if(st!=null) {
				st.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		return a;
	}
	
	
}
