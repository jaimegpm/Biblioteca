<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Socio</title>
    <jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>

        <!-- Formulario para editar un socio -->
        <div id="formSocio" class="formulariogeneral">
            <form name="frmSocio" method="post" action="${pageContext.request.contextPath}/controlleradmin">
                <!-- Acción para guardar en el controlleradmin -->
                <input type="hidden" name="operacion" value="guardarSocio" /> <!-- Cambié 'accion' por 'operacion' -->
                <input type="hidden" name="idSocio" value="${socio.idsocio}" /> <!-- ID del socio -->

                <fieldset id="datosSocio">
                    <legend><img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Modifique los datos del socio</legend>

                    <!-- Nombre -->
                    <div class="etiquetas">
                        <label for="nombre">Nombre:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="nombre" name="nombre" value="${socio.nombre}" required />
                    </div>

                    <!-- Dirección -->
                    <div class="etiquetas">
                        <label for="direccion">Dirección:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="direccion" name="direccion" value="${socio.direccion}" required />
                    </div>

                    <!-- Teléfono -->
                    <div class="etiquetas">
                        <label for="telefono">Teléfono:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="telefono" name="telefono" value="${socio.telefono}" required />
                    </div>

                    <!-- Email (solo lectura) -->
                    <div class="etiquetas">
                        <label for="email">Email:</label>
                    </div>
                    <div class="campos">
                        <input type="email" id="email" name="email" value="${socio.email}" readonly />
                    </div>

                    <div class="cb"></div>

                    <!-- Botones -->
                    <div class="botones">
                        <input type="submit" name="Submit" value="Guardar cambios" />
                        <a href="${pageContext.request.contextPath}/listarsocios.jsp" class="cancel-link">Cancelar</a>
                    </div>
                </fieldset>
            </form>
        </div>

        <!-- Mensajes de error o éxito -->
        <c:if test="${not empty error}">
            <div class="diverror">${error}</div>
        </c:if>
        <c:if test="${not empty mensaje}">
            <div class="divconfirmacion">${mensaje}</div>
        </c:if>
    </div>
</body>
</html>
