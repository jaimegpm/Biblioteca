<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@taglib uri="jakarta.tags.fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<jsp:directive.include file="../includes/includefile.jspf" /> <%--para no tener que poner rutas de estilos  --%>
</head>
<body>
	<div class="container">
		<div class="header"></div>
			<div class="menu">
				<jsp:directive.include file="../WEB-INF/menu.jspf"/>
			</div>
		</div>
	</div>
</body>
</html>