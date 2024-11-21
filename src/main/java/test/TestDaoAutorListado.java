package test;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DaoAutor;
import entidades.Autor;

public class TestDaoAutorListado {

    public static void main(String[] args) {
        // Crear una instancia del DAO
        DaoAutor dao = new DaoAutor();
        ArrayList<Autor> autores;

        try {
            autores = dao.ListadoAutores();

            for (Autor autor : autores) {
                System.out.println("ID: " + autor.getIdAutor() + ", Nombre: " + autor.getNombre());
            }

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}