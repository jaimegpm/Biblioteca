package entidades;

import java.sql.Date;

public class Autor {

	private int idAutor;
	private String Nombre;
	private Date fechaNacimiento;

	public Autor() {
		
	}

	public Autor(int idAutor, String nombre, Date fechaNacimiento) {
		super();
		this.idAutor = idAutor;
		Nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
}
