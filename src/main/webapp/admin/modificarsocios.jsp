<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>insert taitel</title>
    <jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>

      <div id="formSocio" class="formulariogeneral">
			<form name="frmSocio" method="post"
				action="${pageContext.request.contextPath}/controlleradmin">
				<fieldset id="datosSocio">
					<legend><img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Modifique los datos</legend>
					<div class="etiquetas">
						<label for="nombre">Nombre:</label>
						<label for="direccion">Direcci�n:</label>
						<label for="version">Versi�n:</label>
					</div>
					<div class="campos">
						<input type="text" 
						       id="nombre" 
						       name="nombre"
						       value="${nuevoautor.nombre}" />
						       <input name="operacion" 
						       type="hidden" 
						       id="operacion" 
						       value="listadoSocio">
						       
					</div>
					<div class="cb"></div>
					
						
					<div class="cb"></div>
					<div class="botones">	
							<input type="submit" name="Submit" value="Guardar">
					</div>
				</fieldset>
			</form>
		</div>

        <c:if test="${listadoSocios!=null && not empty listadoSocios}">
            <div class="w-75 ma"> 
                
                <table class="table tablaconborde tablacebra tabla-hover">
                    <caption>Listado de socios:</caption>
                    <thead>
                        <tr>
                            <th scope="col">IdSocio</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Direcci�n</th>
                            <th scope="col">Editar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="Socio" items="${listadoSocios}">
                            <tr>
                                <td class= "txtcentrado">${Socio.idsocio}</td>
                                <td>${Socio.nombre}</td>
                                <td class="txtcentrado">${Socio.direccion}</td>
                                <td class="txtcentrado"><a href="${pageContext.request.contextPath}/controlleradmin?operacion=editarSocios&idSocio=${Socio.idsocio}">Editar</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

    </div>
</body>
</html>
