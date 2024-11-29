<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Filtrar Libros</title>
<jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="../WEB-INF/menu.jspf" />
		</div>

		<!-- Formulario de búsqueda -->
		<div id="formBusqueda" class="formulariogeneral">
			<form name="frmFiltrarLibros" method="post"
				action="${pageContext.request.contextPath}/controlleradmin">
				<fieldset id="datosBusqueda">
					<legend>
						<img
							src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Filtrar
						Libros
					</legend>

					<div style="text-align: center">
						<input type="text" id="filtroBusqueda" name="filtroBusqueda"
							placeholder="Escriba aquí..." required /> <select
							name="criterioBusqueda" id="criterioBusqueda" required>
							<option value="">Seleccione...</option>
							<option value="titulo">Título</option>
							<option value="autor">Autor</option>
							<option value="isbn">ISBN</option>
						</select> <input type="hidden" name="operacion" value="filtrarLibros" /> <input
							type="submit" name="Submit" value="Buscar" class="botones" />
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
		<c:if test="${not empty libros}">
			<div class="w-75 ma mt-4">
				<table class="table tablaconborde tablacebra tabla-hover">
					<caption>Resultados de la búsqueda</caption>
					<thead>
						<tr>
							<th>Título</th>
							<th>Autor</th>
							<th>Totales</th>
							<th>Prestados</th>
							<th>Disponibles</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="libro" items="${libros}">
							<tr>
								<td>${libro.titulo}</td>
								<td>${libro.autor}</td>
								<td>${libro.ejemplaresTotales}</td>
								<td>${libro.ejemplaresEnPrestamo}</td>
								<td>${libro.ejemplaresDisponibles}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>

	</div>
</body>
</html>
