package test;

import java.sql.SQLException;

import dao.DaoEjemplar;

public class TestDaoEliminarEjemplar {
	public static void main (String[] args) {
		String[] ejemplaresaborrar = {"36","4"};
		DaoEjemplar dao = new DaoEjemplar();
		try {
			dao.eliminarEjemplares(ejemplaresaborrar);
			System.out.println("Ejemplares eliminados...");
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
