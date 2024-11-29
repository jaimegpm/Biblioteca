package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import dao.DaoAutor;
import dao.DaoEjemplar;
import dao.DaoPrestamo;
import dao.DaoSocio;
import entidades.Autor;
import entidades.Libro;
import entidades.Socio;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class controlleradmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public controlleradmin() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("operacion"); 
        DaoAutor daoAutor = new DaoAutor();
        DaoSocio daoSocio = new DaoSocio();
        DaoEjemplar daoEjemplar = new DaoEjemplar();

        switch (accion) {
            case "insertaautor":
            	String nombre = request.getParameter("nombre");
                String fecha = request.getParameter("fechaNacimiento");
                Autor autor = new Autor();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                autor.setNombre(nombre);

                try {
                    LocalDate localdate = LocalDate.parse(fecha, formato);
                    java.sql.Date sqlDateFechaNacimiento = java.sql.Date.valueOf(localdate);
                    autor.setFechaNacimiento(sqlDateFechaNacimiento);
                    daoAutor.addAutor(autor); 
                    request.setAttribute("confirmaroperacion", "Autor creado correctamente");
                } catch (DateTimeParseException pe) {
                    System.out.println("Formato inválido");
                    request.setAttribute("error", "Formato de fecha inválido");
                } catch (SQLException e) {
                    request.setAttribute("error", "Error: " + e.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                request.getRequestDispatcher("admin/altaautor.jsp").forward(request, response);
                break;

            case "socioslibrosfueraplazo":
                try {
                	ArrayList<Socio> listaSociosMorosos;
                    listaSociosMorosos = daoSocio.ListadoSociosMorosos();
                    request.setAttribute("sociosMorosos", listaSociosMorosos);
                } catch (Exception e) {
                    request.setAttribute("error", "Error al listar socios morosos: " + e.getMessage());
                }
                request.getRequestDispatcher("admin/sociosmorosos.jsp").forward(request, response);
                break;
               
            case "verLibrosMorosos":
                try {
                    ArrayList<Socio> listaSociosMorosos;
                    listaSociosMorosos = daoSocio.ListadoSociosMorosos();
                    request.setAttribute("sociosMorosos", listaSociosMorosos);
                    
                   
                } catch (Exception e) {
                    request.setAttribute("error", "Error al listar los libros no devueltos: " + e.getMessage());
                }
                
                ArrayList<Libro> listadoprestamos = new ArrayList<>();
                String idSocioParam = request.getParameter("idsocio");
                DaoPrestamo dao= new DaoPrestamo();
                if (idSocioParam !=null) {
                	int socio = Integer.parseInt(idSocioParam);
                	try {
                		listadoprestamos = dao.listadoPrestamo(socio);
                		request.setAttribute("listadoLibrosFueraPlazo", listadoprestamos);
                	} catch (SQLException e){
                		request.setAttribute("error", "Error al listar los prestamos: "+ e.getMessage());
                	}
                }else {
                	request.setAttribute("error", "ID de socio no proporcionado");
                }
                request.getRequestDispatcher("admin/sociosmorosos.jsp").forward(request, response);
                break;
                
            case "listadoSocio":
            	String nombreSocio = request.getParameter("nombre"); 
            	ArrayList<Socio> listadoSocios = new ArrayList<>();
            	try {
            		listadoSocios = daoSocio.ListadoSocios(nombreSocio);
            		request.setAttribute("listadoSocios", listadoSocios);
            	} catch (SQLException e){
            		request.setAttribute("error", "Error al listar los socios: "+ e.getMessage());
            	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	request.getRequestDispatcher("admin/listarsocios.jsp").forward(request, response);
            	break;
            	
            	// En el controlleradmin.java
            case "editarSocios":
                // Obtener el id del socio desde la solicitud (por ejemplo, desde un parámetro en el enlace)
                String idSocioMod = request.getParameter("idSocio");
                if (idSocioMod != null) {
                    try {
                        // Convertir el idSocio de String a int
                        int idSocio = Integer.parseInt(idSocioMod);
                        
                        // Crear una instancia de DaoSocio y obtener el socio por su ID
                        Socio socio = daoSocio.obtenerSocioPorId(idSocio);  // Obtener los datos del socio

                        if (socio != null) {
                            // Pasar el socio al JSP de edición
                            request.setAttribute("socio", socio);
                            request.getRequestDispatcher("/admin/editarSocio.jsp").forward(request, response);  // Redirigir al JSP de edición
                        } else {
                            // Si no se encuentra el socio, redirigir a la lista de socios
                            request.setAttribute("error", "Socio no encontrado.");
                            request.getRequestDispatcher("/admin/listarsocios.jsp").forward(request, response);
                        }
                    } catch (SQLException e) {
                        request.setAttribute("error", "Error al obtener los datos del socio: " + e.getMessage());
                        request.getRequestDispatcher("/admin/listarsocios.jsp").forward(request, response);
                    }
                } else {
                    // Si no se proporciona el ID del socio, redirigir con error
                    request.setAttribute("error", "ID de socio no proporcionado");
                    request.getRequestDispatcher("/admin/listarsocios.jsp").forward(request, response);
                }
                break;

            case "guardarSocio":
                try {
                    // Obtener los datos del formulario
                    String socioIdString = request.getParameter("idSocio");
                    if (socioIdString == null || socioIdString.isEmpty()) {
                        throw new IllegalArgumentException("ID de socio no proporcionado");
                    }
                    
                    int idSocio = Integer.parseInt(socioIdString);  // Parseo de idSocio
                    String nombreSocioActualizado = request.getParameter("nombre");
                    String direccionSocioActualizado = request.getParameter("direccion");
                    String telefonoSocioActualizado = request.getParameter("telefono");  // Capturar el teléfono actualizado
                    String emailSocioActualizado = request.getParameter("email");

                    // Crear un objeto Socio con los datos actualizados
                    Socio socioActualizado = new Socio();
                    socioActualizado.setIdsocio(idSocio);
                    socioActualizado.setNombre(nombreSocioActualizado);
                    socioActualizado.setDireccion(direccionSocioActualizado);
                    socioActualizado.setEmail(emailSocioActualizado);
                    socioActualizado.setTelefono(telefonoSocioActualizado);  // Establecer el teléfono

                    // Llamar al DAO para actualizar tanto el socio como el teléfono
                    daoSocio.actualizarSocio(socioActualizado);
                    
                    // Establecer mensaje de éxito y redirigir a la página listarsocios.jsp
                    request.setAttribute("mensaje", "Socio actualizado con éxito.");
                    request.getRequestDispatcher("/admin/listarsocios.jsp").forward(request, response);

                } catch (SQLException e) {
                    // Manejar errores de base de datos
                    request.setAttribute("error", "Error al actualizar el socio: " + e.getMessage());
                    e.printStackTrace();  // Asegúrate de ver el error en el log del servidor
                    request.getRequestDispatcher("/admin/editarSocio.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    // Manejar errores de conversión de ID
                    request.setAttribute("error", "ID de socio inválido.");
                    e.printStackTrace();  // Asegúrate de ver el error en el log del servidor
                    request.getRequestDispatcher("/admin/editarSocio.jsp").forward(request, response);
                } catch (Exception e) {
                    // Cualquier otro error
                    request.setAttribute("error", "Error desconocido: " + e.getMessage());
                    e.printStackTrace();  // Asegúrate de ver el error en el log del servidor
                    request.getRequestDispatcher("/admin/editarSocio.jsp").forward(request, response);
                }
                break;
                
            case "buscarLibros":
                String filtroBusqueda = request.getParameter("filtroBusqueda");
                String criterioBusqueda = request.getParameter("criterioBusqueda");

                String titulo = null, autorLibro = null, isbn = null;

                // Según el criterio, asignar la búsqueda al campo adecuado
                if ("titulo".equals(criterioBusqueda)) {
                    titulo = filtroBusqueda;
                } else if ("autor".equals(criterioBusqueda)) {
                    autorLibro = filtroBusqueda;
                } else if ("isbn".equals(criterioBusqueda)) {
                    isbn = filtroBusqueda;
                }

                try {
                    // Usar el DAO para buscar los libros según los criterios
                    ArrayList<Libro> libros = daoEjemplar.buscarLibros(autorLibro, titulo, isbn);
                    request.setAttribute("libros", libros); // Pasar los libros al JSP
                } catch (Exception e) {
                    request.setAttribute("error", "Error al buscar libros: " + e.getMessage());
                    e.printStackTrace();
                }

                // Redirigir a la página de resultados
                request.getRequestDispatcher("socios/getlibros.jsp").forward(request, response);
                break;

            case "eliminarEjemplares":
                // Obtener el ISBN del libro seleccionado
                String isbnLibro = request.getParameter("isbn");

                try {
                    if (isbnLibro != null && !isbnLibro.isEmpty()) {
                        // Llamada al DAO para obtener los ejemplares asociados
                        ArrayList<Integer> ejemplares = daoEjemplar.obtenerEjemplaresParaEliminar(isbnLibro);

                        // Guardar los ejemplares y el ISBN en el request
                        request.setAttribute("ejemplares", ejemplares);
                        request.setAttribute("isbn", isbnLibro);
                    } else {
                        request.setAttribute("error", "No se proporcionó un ISBN válido para listar ejemplares.");
                    }
                } catch (Exception e) {
                    request.setAttribute("error", "Error al obtener ejemplares: " + e.getMessage());
                    e.printStackTrace();
                }

                // Redirigir al JSP
                request.getRequestDispatcher("socios/getlibros.jsp").forward(request, response);
                break;

            case "filtrarLibros":
                String filtro = request.getParameter("filtroBusqueda");
                String criterio = request.getParameter("criterioBusqueda");

                String libroTitulo = null, libroAutor = null, libroIsbn = null;

                // Según el criterio, asignar la búsqueda al campo adecuado
                if ("titulo".equals(criterio)) {
                    libroTitulo = filtro;
                } else if ("autor".equals(criterio)) {
                    libroAutor = filtro;
                } else if ("isbn".equals(criterio)) {
                    libroIsbn = filtro;
                }

                try {
                    // Usar el DAO para buscar los libros según los criterios
                    ArrayList<Libro> libros = daoEjemplar.filtrarLibros(libroAutor, libroTitulo, libroIsbn);
                    request.setAttribute("libros", libros); // Pasar los libros al JSP
                } catch (Exception e) {
                    request.setAttribute("error", "Error al buscar libros: " + e.getMessage());
                    e.printStackTrace();
                }

                // Redirigir a la página de resultados
                request.getRequestDispatcher("admin/eliminarEjemplar.jsp").forward(request, response);
                break;
                
            case "realizarPrestamo":
                // Obtener los parámetros enviados desde el formulario
                String codigoSocioParam = request.getParameter("codigoSocio");
                String codigoEjemplarParam = request.getParameter("codigoEjemplar");

                // Validar los parámetros
                if (codigoSocioParam == null || codigoSocioParam.isEmpty() || codigoEjemplarParam == null || codigoEjemplarParam.isEmpty()) {
                    request.setAttribute("error", "Por favor, complete todos los campos.");
                    request.getRequestDispatcher("admin/prestamo.jsp").forward(request, response);
                    return;
                }

                try {
                    // Convertir los códigos de socio y ejemplar a enteros
                    int codigoSocio = Integer.parseInt(codigoSocioParam);
                    int codigoEjemplar = Integer.parseInt(codigoEjemplarParam);

                    // Verificar si el socio tiene préstamos vencidos
                    boolean socioMoroso = daoSocio.esSocioMoroso(codigoSocio);
                    if (socioMoroso) {
                        request.setAttribute("error", "El socio tiene préstamos vencidos y no puede realizar un nuevo préstamo.");
                        request.getRequestDispatcher("admin/prestamo.jsp").forward(request, response);
                        return;
                    }

                    // Verificar si el ejemplar está disponible para préstamo
                    boolean ejemplarDisponible = daoEjemplar.esEjemplarDisponible(codigoEjemplar);
                    if (!ejemplarDisponible) {
                        request.setAttribute("error", "El ejemplar no está disponible para préstamo.");
                        request.getRequestDispatcher("admin/prestamo.jsp").forward(request, response);
                        return;
                    }

                    // Si todo está bien, proceder a crear el préstamo
                    DaoPrestamo daoPrestamo = new DaoPrestamo();
                    boolean prestamoRealizado = daoPrestamo.realizarPrestamo(codigoSocio, codigoEjemplar);

                    if (prestamoRealizado) {
                        request.setAttribute("mensaje", "Préstamo realizado con éxito.");
                    } else {
                        request.setAttribute("error", "Error al realizar el préstamo. Intente nuevamente.");
                    }

                    // Redirigir de vuelta al formulario de préstamo con mensaje de éxito o error
                    request.getRequestDispatcher("admin/prestamo.jsp").forward(request, response);

                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Los códigos deben ser números válidos.");
                    request.getRequestDispatcher("admin/prestamo.jsp").forward(request, response);
                } catch (SQLException e) {
                    request.setAttribute("error", "Error al realizar el préstamo: " + e.getMessage());
                    request.getRequestDispatcher("admin/prestamo.jsp").forward(request, response);
                }
                break;

        
        }
    }
}
