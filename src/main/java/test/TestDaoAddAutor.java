package test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import dao.DaoAutor;
import entidades.Autor;

public class TestDaoAddAutor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DaoAutor dao = new DaoAutor();
		Autor autor = new Autor();
		
		String strfecha="1933-10-27";
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		autor.setNombre("Manuel");
		try {
			LocalDate localdate = LocalDate.parse(strfecha,formato);
			Date sqlDateFechaNacimiento = java.sql.Date.valueOf(localdate);
			autor.setFechaNacimiento(sqlDateFechaNacimiento);
			dao.addAutor(autor);		
		}catch(DateTimeParseException pe) {
			System.out.println("Formato invalido");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
