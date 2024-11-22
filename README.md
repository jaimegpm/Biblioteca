## Biblioteca
Proyecto hecho para simplificar conflictos exportando el DWP

## Comandos útiles

```bash
# Ruta en el main  
cd eclipse-workspace/Biblioteca/
git pull origin main --allow-unrelated-histories
```

## Contenido del .gitignore
```bash
# Ignorar configuraciones locales y archivos de construcción
.settings/
build/
.classpath
.project

# Incluir todo en 'src/main/java'
!src/main/java/

# Incluir todo en 'src/main/webapp', excepto 'WEB-INF'
!src/main/webapp/
src/main/webapp/WEB-INF/

# Excluir todos los archivos en 'WEB-INF' y sus subcarpetas
src/main/webapp/WEB-INF/*

# Ignorar archivos comunes no deseados
*.log
*.tmp
*.bak
*.swp

```







