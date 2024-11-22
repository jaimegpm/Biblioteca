package test;

import java.sql.SQLException;

import dao.DaoAutor;
import entidades.Autor;

public class TestDaoAutorById {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DaoAutor dao = new DaoAutor();
		Autor autor;
		try {
			autor = dao.findAutorById(1);
			if (autor != null) {
				System.out.println("Hay autor");
				System.out.println("Nombre: "+autor.getNombre());
			}
			else {
				System.out.println("No hay Autor");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
