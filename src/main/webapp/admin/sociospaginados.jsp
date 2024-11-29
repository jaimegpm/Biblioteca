<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Socios Paginados</title>
    <jsp:directive.include file="../includes/includefile.jspf" />
    <style>
        /* Clase adicional para botones pequeños */
        .boton-pequeno {
            font-size: 0.8rem;
            padding: 0.2rem 0.5rem;
            background-color: #41a0ff;
            color: white;
            border: none;
            border-radius: 3px;
            text-decoration: none;
        }
        .boton-pequeno:hover {
            background-color: #2a7db8;
        }
        .txtpequeno {
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>

        <!-- Mostrar mensaje de error -->
        <c:if test="${not empty error}">
            <div class="diverror">
                <p>${error}</p>
            </div>
        </c:if>

        <!-- Mostrar mensaje de confirmación -->
        <c:if test="${not empty mensaje}">
            <div class="divconfirmacion">
                <p>${mensaje}</p>
            </div>
        </c:if>

        <div class="w-75 ma">     
            <table class="table tablaconborde tablacebra tabla-hover mb-1">
                <caption>Listado de socios registrados:</caption>
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Email</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Dirección</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="socio" items="${listadoSocios}">
                        <tr>
                            <td class="txtcentrado">${socio.idsocio}</td>
                            <td>${socio.email}</td>
                            <td>${socio.nombre}</td>
                            <td>${socio.direccion}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Información de registros y navegación -->
            <div class="txtpequeno txtcentrado mt-2">
                Mostrando desde ${limiteInferior} hasta ${limiteSuperior} de ${totalRegistros} registros.&nbsp;
                <c:if test="${paginaActual > 0}">
                    <a href="controlleradmin?operacion=listarSocios&pag=${paginaActual - 1}&nrp=${registrosPorPagina}" class="boton-pequeno">Ant</a>
                </c:if>
                <c:if test="${limiteSuperior < totalRegistros}">
                    &nbsp;<a href="controlleradmin?operacion=listarSocios&pag=${paginaActual + 1}&nrp=${registrosPorPagina}" class="boton-pequeno">Sig</a>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
