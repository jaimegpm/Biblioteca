<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Buscar Libros</title>
<jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="../WEB-INF/menu.jspf" />
		</div>

		<!-- Formulario para buscar libros -->
		<div id="formBusqueda" class="formulariogeneral">
			<form name="frmBuscarLibros" method="post" action="${pageContext.request.contextPath}/controlleradmin">
				<fieldset id="datosBusqueda">
					<legend>
						<img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Búsqueda Sencilla
					</legend>

					<div style="text-align: center">
						<input type="text" id="filtroBusqueda" name="filtroBusqueda" placeholder="Escriba aquí..." required />
						<select name="criterioBusqueda" id="criterioBusqueda" required>
							<option value="">Seleccione...</option>
							<option value="titulo">Título</option>
							<option value="autor">Autor</option>
							<option value="isbn">ISBN</option>
						</select>
						<input type="hidden" name="operacion" value="buscarLibros" />
						<input type="submit" name="Submit" value="Buscar" class="botones" />
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

		<!-- Tabla de resultados -->
		<c:if test="${libros != null && not empty libros}">
			<div class="w-75 ma">
				<table class="table tablaconborde tablacebra tabla-hover">
					<caption>Listado de Libros</caption>
					<thead>
						<tr>
							<th>TITULO DEL LIBRO</th>
							<th>AUTOR</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="libro" items="${libros}">
							<tr>
								<td>${libro.titulo}</td>
								<td>${libro.autor}</td>
								<td class="txtcentrado">
									<!-- Enlace para eliminar ejemplares --> 
									<a href="${pageContext.request.contextPath}/controlleradmin?operacion=eliminarEjemplares&isbn=${libro.isbn}">Eliminar Ejemplares</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>

		<!-- Tabla de ejemplares para eliminar -->
		<c:if test="${not empty ejemplares}">
			<div class="w-75 ma mt-4">
				<form method="post" action="${pageContext.request.contextPath}/controlleradmin">
					<table class="table tablaconborde tablacebra tabla-hover">
						<caption>Ejemplares disponibles para el ISBN: ${isbn}</caption>	
						<thead>
							<tr>
								<th>CODIGO DEL EJEMPLAR</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="ejemplar" items="${ejemplares}">
								<tr>
									<td class="txtcentrado">
										<!-- Checkbox a la izquierda del código --> 
										<input type="checkbox" name="ejemplaresSeleccionados" value="${ejemplar}" /> ${ejemplar}
									</td>
								</tr>
							</c:forEach>
							<!-- Fila con el botón de eliminar -->
							<tr>
								<td class="txtcentrado">
									<input type="hidden" name="operacion" value="procesarEjemplaresSeleccionados" />
									<input type="hidden" name="isbn" value="${isbn}" />
									<input type="submit" value="Eliminar" class="botones" />
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</c:if>

		<!-- Mensaje cuando no hay ejemplares disponibles -->
		<c:if test="${empty ejemplares && not empty isbn}">
			<div class="alert alert-warning mt-4">No hay ejemplares disponibles para el ISBN: ${isbn}.</div>
		</c:if>
	</div>
</body>
</html>
