<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ page language="java" 
         contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro de Nuevo Socio</title>

<jsp:directive.include file="includes/includefile.jspf" />

<script src="https://www.google.com/recaptcha/api.js?render=6Le9BcEUAAAAAHayURW2eOss5lmTGX5vA3sBslx1"></script>
<script>
grecaptcha.ready(function() {
    grecaptcha.execute('6Le9BcEUAAAAAHayURW2eOss5lmTGX5vA3sBslx1', {action: 'register'})
    .then(function(token) {
        var recaptchaResponse = document.getElementById('g-recaptcha-response');
        recaptchaResponse.value = token;
    });
});
</script> 

</head>

<body>

	<div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="/WEB-INF/menu.jspf" />
        </div>
    <div class="container">
        
        
        <div id="formRegistro" class="formulariogeneral">		
            <form action="registrar_socio" method="post">
                <fieldset id="datosRegistro">
                    <legend><img src="resources/img/azarquiel.gif">&nbsp;Formulario de Registro de Socio</legend>
                    
                    <div class="etiquetas">
                        <label for="nombre">Nombre Completo:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="nombre" name="nombre" required />
                    </div>

                    <div class="etiquetas">
                        <label for="email">Email:</label>
                    </div>
                    <div class="campos">
                        <input type="email" id="email" name="email" required />
                    </div>
                    
                    <div class="etiquetas">
                        <label for="direccion">Dirección:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="direccion" name="direccion" required />
                    </div>
                    
                    <div class="etiquetas">
                        <label for="telefono">Teléfono:</label>
                    </div>
                    <div class="campos">
                        <input type="tel" id="telefono" name="telefono" required pattern="[0-9]{9}" />
                    </div>

                    <div class="etiquetas">
                        <label for="clave">Clave:</label>
                    </div>
                    <div class="campos">
                        <input type="password" id="clave" name="clave" required minlength="8" 
                               placeholder="Mínimo 8 caracteres" />
                    </div>
                    
                    <div class="etiquetas">
                        <label for="confirmar_clave">Confirmar Clave:</label>
                    </div>
                    <div class="campos">
                        <input type="password" id="confirmar_clave" name="confirmar_clave" required minlength="8" />
                    </div>
                    
                    <div class="cb"></div>
                    <div class="botones">	
                        <input type="submit" name="Submit" value="Registrar">
                    </div>	
                </fieldset>		
                <input type="hidden" id="g-recaptcha-response" name="g-recaptcha-response">	
            </form>
        </div>
    </div>
</body>
</html>
