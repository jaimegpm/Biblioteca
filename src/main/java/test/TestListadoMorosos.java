package test;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoSocio;
import entidades.Socio;

public class TestListadoMorosos {
	public static void main(String[] args) {
		DaoSocio dao = new DaoSocio();
    	ArrayList<Socio> socios;

    	try {
        	socios = dao.ListadoSociosMorosos();

        	for (Socio socio : socios) {
        		System.out.println("ID: " + socio.getIdsocio() + ", Nombre: " + socio.getNombre());
        	}

    	} catch (SQLException e) {
        	System.out.println("Error SQL: " + e.getMessage());
    	} catch (Exception ex) {
        	System.out.println("Error: " + ex.getMessage());
    	}
	}
}
