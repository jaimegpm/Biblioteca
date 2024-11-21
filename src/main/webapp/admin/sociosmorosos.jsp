<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Socios Morosos</title>
    <jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>

        <div class="w-75 ma">
            <table class="table tablaconborde tablacebra tabla-hover">
                <caption>Lista de Socios Morosos</caption>
                <thead>
                    <tr>
                        <th>ID Socio</th>
                        <th>Nombre</th>
                        <th>Libros</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="socio" items="${sociosMorosos}">
                        <tr>
                            <td class="txtcentrado">${socio.idsocio}</td>
                            <td>${socio.nombre}</td>
                            <td class="txtcentrado">
                                <a href="${pageContext.request.contextPath}/controlleradmin?operacion=verLibrosMorosos&idsocio=${socio.idsocio}">Ver Libros</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${listadoLibrosFueraPlazo!=null && not empty listadoLibrosFueraPlazo}">
            <div class="w-75 ma"> 
                
                <table class="table tablaconborde tablacebra tabla-hover">
                    <caption>Prestamos no devueltos del socio:</caption>
                    <thead>
                        <tr>
                            <th scope="col">Título</th>
                            <th scope="col">Fecha Préstamo</th>
                            <th scope="col">Días de Demora</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="libro" items="${listadoLibrosFueraPlazo}">
                            <tr>
                                <td class= "txtcentrado">${libro.titulo}</td>
                                <td>${libro.fechaPrestamo}</td>
                                <td class="txtcentrado">${libro.diademora}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

    </div>
</body>
</html>
