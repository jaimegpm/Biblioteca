package test;

import java.sql.Connection;
import java.sql.SQLException;

import conexiones.Conexion;

public class Prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Conexion conexion = new Conexion();
		Connection con = null;

		try {
			con=conexion.getConexion();
			con.close();
		} catch (SQLException e) {
			System.out.println("Tratamiento del error");
			System.out.println(e.toString());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
