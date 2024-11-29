package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexiones.Conexion;
import entidades.Libro;

public class DaoEjemplar {

    // Método existente para eliminar ejemplares
	public void eliminarEjemplares(String[] ejemplaresaborrar) throws SQLException, Exception {
	    PreparedStatement ps = null;
	    Connection con = null;
	    try {
	        Conexion miconex = new Conexion();
	        con = miconex.getConexion();
	        con.setAutoCommit(false);
	        String ordensql = "UPDATE EJEMPLAR SET BAJA='S' WHERE IDEJEMPLAR=?";
	        ps = con.prepareStatement(ordensql);
	        for (String s : ejemplaresaborrar) {
	            int ejemplar = Integer.parseInt(s);
	            ps.setInt(1, ejemplar);
	            ps.executeUpdate();
	        }
	        con.commit();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (ps != null) ps.close();
	        if (con != null) con.close();
	    }
	}



    // Método modificado para buscar libros
    public ArrayList<Libro> buscarLibros(String autor, String titulo, String isbn) throws SQLException, Exception {
        ArrayList<Libro> libros = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            // Obtener la conexión
            Conexion miconex = new Conexion();
            con = miconex.getConexion();

            // Consulta SQL con parámetros
            String query = """
                    SELECT L.ISBN, L.TITULO, A.NOMBRE AUTOR,
                           EJEMPLARESTOTALES, EJEMPLARESENPRESTAMO,
                           (EJEMPLARESTOTALES - EJEMPLARESENPRESTAMO) AS EJEMPLARESDISPONIBLES
                    FROM LIBRO L, AUTOR A,
                         (SELECT A.ISBN, EJEMPLARESTOTALES, NVL(EJEMPLARESENPRESTAMO, 0) AS EJEMPLARESENPRESTAMO
                          FROM (SELECT L.ISBN, COUNT(*) AS EJEMPLARESTOTALES
                                FROM LIBRO L, EJEMPLAR E
                                WHERE L.ISBN = E.ISBN
                                  AND E.BAJA = 'N'
                                GROUP BY L.ISBN) A
                          LEFT JOIN (SELECT ISBN, COUNT(*) AS EJEMPLARESENPRESTAMO
                                     FROM PRESTAMO P, EJEMPLAR E
                                     WHERE P.IDEJEMPLAR = E.IDEJEMPLAR
                                     GROUP BY ISBN) B
                          ON A.ISBN = B.ISBN) B
                    WHERE L.ISBN = B.ISBN
                      AND L.IDAUTOR = A.IDAUTOR
                      AND TRANSLATE(UPPER(A.NOMBRE), 'ÁÉÍÓÚ', 'AEIOU') LIKE TRANSLATE(UPPER(?), 'ÁÉÍÓÚ', 'AEIOU')
                      AND TRANSLATE(UPPER(L.TITULO), 'ÁÉÍÓÚ', 'AEIOU') LIKE TRANSLATE(UPPER(?), 'ÁÉÍÓÚ', 'AEIOU')
                      AND L.ISBN LIKE ?
                    ORDER BY AUTOR, TITULO
                    """;

            // Verificar la consulta antes de ejecutarla
            System.out.println("Ejecutando consulta: " + query);
            System.out.println("Parametros: autor = " + autor + ", titulo = " + titulo + ", isbn = " + isbn);

            // Crear el PreparedStatement
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + (autor != null && !autor.trim().isEmpty() ? autor : "") + "%");
            ps.setString(2, "%" + (titulo != null && !titulo.trim().isEmpty() ? titulo : "") + "%");
            ps.setString(3, "%" + (isbn != null && !isbn.trim().isEmpty() ? isbn : "") + "%");

            // Ejecutar la consulta
            rs = ps.executeQuery();

            // Recorrer los resultados y agregarlos a la lista de libros
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIsbn(rs.getString("ISBN"));
                libro.setTitulo(rs.getString("TITULO"));
                libro.setAutor(rs.getString("AUTOR"));
                libro.setEjemplaresTotales(rs.getInt("EJEMPLARESTOTALES"));
                libro.setEjemplaresEnPrestamo(rs.getInt("EJEMPLARESENPRESTAMO"));
                libro.setEjemplaresDisponibles(rs.getInt("EJEMPLARESDISPONIBLES"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Cerrar los recursos
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        // Retornar la lista de libros encontrados
        return libros;
    }
       
    public ArrayList<Integer> obtenerEjemplaresParaEliminar(String isbn) throws SQLException, Exception {
        ArrayList<Integer> ejemplares = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            Conexion miconex = new Conexion();
            con = miconex.getConexion();

            // Consulta SQL para obtener ejemplares no en préstamo
            String query = """
                    SELECT E.IDEJEMPLAR
                    FROM EJEMPLAR E
                    LEFT JOIN PRESTAMO P ON E.IDEJEMPLAR = P.IDEJEMPLAR
                    WHERE E.ISBN = ? 
                      AND E.BAJA = 'N'
                      AND P.IDEJEMPLAR IS NULL
                    """;

            ps = con.prepareStatement(query);
            ps.setString(1, isbn);

            rs = ps.executeQuery();

            while (rs.next()) {
                ejemplares.add(rs.getInt("IDEJEMPLAR"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return ejemplares;
    }

    
    public ArrayList<Libro> filtrarLibros(String autor, String titulo, String isbn) throws SQLException, Exception {
        ArrayList<Libro> libros = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            // Obtener la conexión
            Conexion miconex = new Conexion();
            con = miconex.getConexion();

            // Consulta SQL con parámetros para filtrar libros
            String query = """
                    SELECT L.ISBN, L.TITULO, A.NOMBRE AUTOR,
                           EJEMPLARESTOTALES, EJEMPLARESENPRESTAMO,
                           (EJEMPLARESTOTALES - EJEMPLARESENPRESTAMO) AS EJEMPLARESDISPONIBLES
                    FROM LIBRO L, AUTOR A,
                         (SELECT A.ISBN, EJEMPLARESTOTALES, NVL(EJEMPLARESENPRESTAMO, 0) AS EJEMPLARESENPRESTAMO
                          FROM (SELECT L.ISBN, COUNT(*) AS EJEMPLARESTOTALES
                                FROM LIBRO L, EJEMPLAR E
                                WHERE L.ISBN = E.ISBN
                                  AND E.BAJA = 'N'
                                GROUP BY L.ISBN) A
                          LEFT JOIN (SELECT ISBN, COUNT(*) AS EJEMPLARESENPRESTAMO
                                     FROM PRESTAMO P, EJEMPLAR E
                                     WHERE P.IDEJEMPLAR = E.IDEJEMPLAR
                                     GROUP BY ISBN) B
                          ON A.ISBN = B.ISBN) B
                    WHERE L.ISBN = B.ISBN
                      AND L.IDAUTOR = A.IDAUTOR
                      AND TRANSLATE(UPPER(A.NOMBRE), 'ÁÉÍÓÚ', 'AEIOU') LIKE TRANSLATE(UPPER(?), 'ÁÉÍÓÚ', 'AEIOU')
                      AND TRANSLATE(UPPER(L.TITULO), 'ÁÉÍÓÚ', 'AEIOU') LIKE TRANSLATE(UPPER(?), 'ÁÉÍÓÚ', 'AEIOU')
                      AND L.ISBN LIKE ?
                    ORDER BY AUTOR, TITULO
                    """;

            // Crear el PreparedStatement
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + (autor != null && !autor.trim().isEmpty() ? autor : "") + "%");
            ps.setString(2, "%" + (titulo != null && !titulo.trim().isEmpty() ? titulo : "") + "%");
            ps.setString(3, "%" + (isbn != null && !isbn.trim().isEmpty() ? isbn : "") + "%");

            // Ejecutar la consulta
            rs = ps.executeQuery();

            // Recorrer los resultados y agregarlos a la lista de libros
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIsbn(rs.getString("ISBN"));
                libro.setTitulo(rs.getString("TITULO"));
                libro.setAutor(rs.getString("AUTOR"));
                libro.setEjemplaresTotales(rs.getInt("EJEMPLARESTOTALES"));
                libro.setEjemplaresEnPrestamo(rs.getInt("EJEMPLARESENPRESTAMO"));
                libro.setEjemplaresDisponibles(rs.getInt("EJEMPLARESDISPONIBLES"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Cerrar los recursos
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        // Retornar la lista de libros encontrados
        return libros;
    }

    public boolean esEjemplarDisponible(int codigoEjemplar) throws SQLException {
        boolean estaDisponible = false;
        Conexion conexion = new Conexion();
        String sql = "SELECT COUNT(*) FROM PRESTAMO " +
                     "WHERE IDEJEMPLAR = ? " +
                     "AND FECHALIMITEDEVOLUCION IS NULL";  // Si no tiene fecha de devolución, está prestado

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, codigoEjemplar);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    estaDisponible = rs.getInt(1) == 0;  // No hay préstamos activos para este ejemplar
                }
            }
        }
        
        return estaDisponible;
    }



}
