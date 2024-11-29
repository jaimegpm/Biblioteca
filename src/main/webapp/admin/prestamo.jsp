<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Realizar Pr�stamo</title>
    <jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>

        <!-- Formulario para el pr�stamo -->
        <div id="formPrestamo" class="formulariogeneral">
            <form name="frmPrestamo" method="post" action="${pageContext.request.contextPath}/controlleradmin">
                <fieldset id="datosRegistro">
                    <legend>
                        <img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Nuevo Pr�stamo
                    </legend>

                    <div style="text-align: center">

                        <!-- Campo para el c�digo del socio -->
                        <div class="etiquetas">
                            <label for="codigoSocio">C�digo de Socio:</label>
                        </div>
                        <div class="etiquetas">
                            <input type="text" id="codigoSocio" name="codigoSocio" required />
                        </div>

                        <!-- Campo para el c�digo del ejemplar -->
                        <div class="etiquetas">
                            <label for="codigoEjemplar">C�digo de Ejemplar:</label>
                        </div>
                        <div class="etiquetas">
                            <input type="text" id="codigoEjemplar" name="codigoEjemplar" required />
                        </div>

                       
                        

                    </div>
                    <div class="cb"></div>
                    <div class="botones">
                        	<input type="submit" name="Submit" value="Guardar" class="botones" />
                        	<input type="hidden" name="operacion" value="realizarPrestamo" />
                        </div>
                </fieldset>
            </form>
        </div>

        <!-- Mensajes de error o �xito -->
        <c:if test="${not empty error}">
            <div class="diverror">${error}</div>
        </c:if>
        <c:if test="${not empty mensaje}">
            <div class="divconfirmacion">${mensaje}</div>
        </c:if>
    </div>
</body>
</html>
