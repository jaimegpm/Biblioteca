package entidades;

import java.sql.Date;

public class Libro {
    private String nombre; // Puede ser el autor, cambiar si no es necesario.
    private String titulo;
    private String diademora;
    private Date fechaPrestamo;

    // Nuevas propiedades para búsqueda de libros
    private String isbn;
    private String autor;
    private int ejemplaresTotales;
    private int ejemplaresEnPrestamo;
    private int ejemplaresDisponibles;

    // Constructor vacío
    public Libro() {
        super();
    }

    // Getters y Setters de las propiedades originales
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getDiademora() {
        return diademora;
    }

    public void setDiademora(String diademora) {
        this.diademora = diademora;
    }

    // Getters y Setters para las nuevas propiedades
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getEjemplaresTotales() {
        return ejemplaresTotales;
    }

    public void setEjemplaresTotales(int ejemplaresTotales) {
        this.ejemplaresTotales = ejemplaresTotales;
    }

    public int getEjemplaresEnPrestamo() {
        return ejemplaresEnPrestamo;
    }

    public void setEjemplaresEnPrestamo(int ejemplaresEnPrestamo) {
        this.ejemplaresEnPrestamo = ejemplaresEnPrestamo;
    }

    public int getEjemplaresDisponibles() {
        return ejemplaresDisponibles;
    }

    public void setEjemplaresDisponibles(int ejemplaresDisponibles) {
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }
}
