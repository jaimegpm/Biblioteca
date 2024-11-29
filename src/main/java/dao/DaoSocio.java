package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexiones.Conexion;
import entidades.Libro;
import entidades.Socio;
import tools.Sha256;


  // Asumimos que tienes una clase para encriptar la contraseña

public class DaoSocio {
	
	public Socio obtenerSocioPorId(int idSocio) throws SQLException {
	    Conexion conexion = new Conexion();
	    Connection con = null;
	    ResultSet rs = null;
	    PreparedStatement stmt = null;
	    Socio socio = null;

	    try {
	        con = conexion.getConexion();
	        
	        if (con == null) {
	            System.out.println("Error: La conexión a la base de datos falló.");
	            return null;
	        }

	        String sql = "SELECT s.IDSOCIO, s.EMAIL, s.NOMBRE, s.DIRECCION, t.TELEFONO " +
	                     "FROM SOCIO s " +
	                     "JOIN TOKEN t ON s.EMAIL = t.EMAIL " +
	                     "WHERE s.IDSOCIO = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setInt(1, idSocio);
	        
	        rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            socio = new Socio();
	            socio.setIdsocio(rs.getInt("IDSOCIO"));
	            socio.setEmail(rs.getString("EMAIL"));
	            socio.setNombre(rs.getString("NOMBRE"));
	            socio.setDireccion(rs.getString("DIRECCION")); 
	            socio.setTelefono(rs.getString("TELEFONO")); // Asignar el teléfono
	        } else {
	            System.out.println("No se encontró el socio con ID: " + idSocio);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al obtener el socio: " + e.getMessage());
	        throw e;
	    } finally {
	        if (rs != null) try { rs.close(); } catch (SQLException e) { /* Ignorar */ }
	        if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* Ignorar */ }
	        if (con != null) try { con.close(); } catch (SQLException e) { /* Ignorar */ }
	    }

	    return socio;
	}



	public void actualizarSocio(Socio socio) throws SQLException {
	    Conexion conexion = new Conexion();
	    Connection con = null;
	    PreparedStatement stmtSocio = null;
	    PreparedStatement stmtToken = null;

	    try {
	        // Obtener la conexión
	        con = conexion.getConexion();
	        con.setAutoCommit(false); // Iniciar una transacción

	        // Actualizar datos en la tabla SOCIO
	        String sqlSocio = "UPDATE SOCIO SET NOMBRE = ?, DIRECCION = ?, VERSION = VERSION + 1 WHERE IDSOCIO = ?";
	        stmtSocio = con.prepareStatement(sqlSocio);
	        stmtSocio.setString(1, socio.getNombre());
	        stmtSocio.setString(2, socio.getDireccion());
	        stmtSocio.setInt(3, socio.getIdsocio());
	        stmtSocio.executeUpdate();

	        // Actualizar el teléfono en la tabla TOKEN
	        String sqlToken = "UPDATE TOKEN SET TELEFONO = ? WHERE EMAIL = ?";
	        stmtToken = con.prepareStatement(sqlToken);
	        stmtToken.setString(1, socio.getTelefono());  // El teléfono a actualizar
	        stmtToken.setString(2, socio.getEmail());     // El email como clave
	        stmtToken.executeUpdate();

	        // Confirmar la transacción
	        con.commit(); 
	    } catch (SQLException e) {
	        if (con != null) {
	            con.rollback(); // Revertir cambios si ocurre un error
	        }
	        System.out.println("Error al actualizar el socio: " + e.getMessage());
	        throw e;
	    } finally {
	        if (stmtSocio != null) stmtSocio.close();
	        if (stmtToken != null) stmtToken.close();
	        if (con != null) con.close();
	    }
	}




    public void registrarSocio(Socio socio, String token) throws SQLException {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement stmtSocio = null;
        PreparedStatement stmtToken = null;

        try {
            con = conexion.getConexion();
            con.setAutoCommit(false); // Iniciar una transacción

            // Encriptar la clave del socio antes de guardarla
            String claveEncriptada = Sha256.getSha256(socio.getClave());

            // Insertar el socio en la tabla 'SOCIO'
            String sqlSocio = "INSERT INTO SOCIO (NOMBRE, EMAIL, DIRECCION, TELEFONO, CLAVE) VALUES (?, ?, ?, ?, ?)";
            stmtSocio = con.prepareStatement(sqlSocio);
            stmtSocio.setString(1, socio.getNombre());
            stmtSocio.setString(2, socio.getEmail());
            stmtSocio.setString(3, socio.getDireccion());
            stmtSocio.setString(4, socio.getTelefono());
            stmtSocio.setString(5, claveEncriptada); // Guardar la clave encriptada
            stmtSocio.executeUpdate();

            // Insertar el token en la tabla 'TOKEN'
            String sqlToken = "INSERT INTO TOKEN (EMAIL, CLAVE, VALUE, FECHA_INICIO) VALUES (?, ?, ?, NOW())";
            stmtToken = con.prepareStatement(sqlToken);
            stmtToken.setString(1, socio.getEmail());
            stmtToken.setString(2, claveEncriptada);  // Almacenar la clave encriptada
            stmtToken.setString(3, token);  // Guardar el token generado
            stmtToken.executeUpdate();

            // Confirmar la transacción
            con.commit();
        } catch (Exception e) {
            // Si algo falla, hacer rollback en la transacción
            if (con != null) {
                con.rollback();
            }
        } finally {
            if (stmtSocio != null) stmtSocio.close();
            if (stmtToken != null) stmtToken.close();
            if (con != null) con.close();
        }
    }

    public ArrayList<Socio> ListadoSociosMorosos() throws Exception {
        ArrayList<Socio> listaSociosMorosos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = conexion.getConexion();
            st = con.createStatement();
            String ordenSQL = "SELECT SOCIO.IDSOCIO, SOCIO.NOMBRE " +
                    "FROM PRESTAMO " +
                    "INNER JOIN SOCIO ON PRESTAMO.IDSOCIO = SOCIO.IDSOCIO " +
                    "WHERE PRESTAMO.FECHALIMITEDEVOLUCION < SYSDATE " +
                    "GROUP BY SOCIO.IDSOCIO, SOCIO.NOMBRE " +
                    "ORDER BY SOCIO.IDSOCIO";
            rs = st.executeQuery(ordenSQL);

            while (rs.next()) {
                Socio miSocio = new Socio();
                miSocio.setIdsocio(rs.getInt("IDSOCIO"));
                miSocio.setNombre(rs.getString("NOMBRE"));
                listaSociosMorosos.add(miSocio);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return listaSociosMorosos;
    }

    public ArrayList<Libro> ListadoLibrosNoDevueltosPorSocio(int idsocio) throws Exception {
        ArrayList<Libro> listaLibrosNoDevueltos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = conexion.getConexion();
            st = con.createStatement();

            String ordenSQL = "SELECT LIBRO.TITULO, PRESTAMO.FECHAPRESTAMO, " +
                    "DATEDIFF(SYSDATE, PRESTAMO.FECHALIMITEDEVOLUCION) AS DIAS_DEMORA " +
                    "FROM PRESTAMO " +
                    "INNER JOIN LIBRO ON PRESTAMO.IDLIBRO = LIBRO.IDLIBRO " +
                    "WHERE PRESTAMO.IDSOCIO = " + idsocio + " AND PRESTAMO.FECHALIMITEDEVOLUCION < SYSDATE";

            rs = st.executeQuery(ordenSQL);

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setTitulo(rs.getString("TITULO"));
                libro.setFechaPrestamo(rs.getDate("FECHAPRESTAMO"));
                libro.setDiademora(rs.getString("DIAS_DEMORA"));

                listaLibrosNoDevueltos.add(libro);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return listaLibrosNoDevueltos;
    }
    
    public ArrayList<Socio> ListadoSocios(String nombre) throws Exception {
        ArrayList<Socio> listaSocios = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            con = conexion.getConexion();

            String ordenSQL = "SELECT IDSOCIO, NOMBRE, DIRECCION, VERSION " +
                    "FROM SOCIO " +
                    "WHERE UPPER(NOMBRE) LIKE UPPER(?)";
            st = con.prepareStatement(ordenSQL);
            st.setString(1, "%" + nombre + "%");
            rs = st.executeQuery();

            while (rs.next()) {
                Socio socio = new Socio();
                socio.setIdsocio(rs.getInt("IDSOCIO"));
                socio.setNombre(rs.getString("NOMBRE"));
                socio.setDireccion(rs.getString("DIRECCION"));
                socio.setVersion(rs.getString("VERSION"));

                listaSocios.add(socio);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return listaSocios;
    }
    
    public boolean esSocioMoroso(int codigoSocio) throws SQLException {
        boolean esMoroso = false;
        Conexion conexion = new Conexion();
        String sql = "SELECT COUNT(*) FROM PRESTAMO " +
                     "WHERE IDSOCIO = ? " +
                     "AND FECHALIMITEDEVOLUCION < SYSDATE";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, codigoSocio);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    esMoroso = rs.getInt(1) > 0;  // Si hay préstamos vencidos
                }
            }
        }
        
        return esMoroso;
    }


}
