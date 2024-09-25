

# Pasos para levantar el proyecto por primera vez

## Clonar Proyecto
- Crear directorio según corresponda
### En Windows
- Dentro de C:/Users/TU_USUARIO/repo/encomiendassp

### En MAC/Linux
- Dentro de /Users/TU_USUARIO/repo/encomiendassp


## Preparar VSCode
- Abrir VSCode e Instalar Plugins: 
- 1. Extension Pack for Java de Microsoft, contiene 6 plugins.
- 2. Spring Boot Extension Pack de VMware. contiene 3 plugins.
- 3. REST Client de Huachao Mao.
- Verificar que JAVA_HOME está correctamente en las Variables de Entorno.

### Abrir proyecto con VSCode
- Abrir carpeta de proyecto en VSCode, dejar que se descarguen las librerías, tarda la primera vez. Se crea automáticamente la carpeta .m2 dentro de tu directorio de usuario (~/), verificar si aumenta de tamaño.



## Base de datos
- Se muestra la configuracion de BD en docker y tambien si se tiene instalado directo el postgres.
- ATENCION el docker utiliza el puerto 5433.
- Clientes Recomendados [Beekeeper Studio Community](https://github.com/beekeeper-studio/beekeeper-studio), [DBeaver](https://dbeaver.io/), [DbGate](https://dbgate.org/)

## UTILIZANDO DB EN DOCKER
### Docker: Instalar Docker Desktop (omitir si ya se tiene instalado)
- Decargar de https://www.docker.com/products/docker-desktop/
- Instalar y abrir, esperar que la parte inferior de la pantalla del docker este verdecito
- Otras versiones por si las moscas : https://docs.docker.com/desktop/release-notes/

### Docker: verificar y preparar:
- Abrir terminal para probar si instaló correctamente
> docker --version
- Descargar imagen de postgres
> docker pull postgres:14.3

### Docker: Iniciar DB
- Copiar el archivo de la raiz .env.example como .env en el mismo directorio (la raiz) y completar los parámetros del docker segun corresponda.
- Desde la raiz del proyecto donde está el archivo docker-compose.yaml
- Levantar
> docker-compose up -d
- Comando parar Parar 
> docker-compose down   

### Docker: Conectarse a DB con un cliente
- URL conexión: postgresql://encomiendasspuser:aloha@127.0.0.1:5433/encomiendasspdb


## UTILIZANDO DB INSTALADO LOCALMENTE
- Puede variar de acuerdo a la version y aplicacion de postgres instalada
### Ir a directorio postgres
- Desde powershell en Windows
> cd "C:\Program Files\PostgreSQL\14.6\bin"
- Desde terminal en MAC
> cd /Applications/Postgres.app/Contents/Versions/14/bin

### Conectarse a psql con usuario root postgres
- Windows
> .\psql.exe -U postgres
- Mac
>  ./psql

### Dentro de la consola ya con psql abierto
- Listar usuarios
> \du
- Crear usuario
> CREATE ROLE encomiendasspuser LOGIN PASSWORD 'aloha';
-  Crear BD si no existe
- Listar BD
> \l
- Crear BD
> CREATE DATABASE encomiendasspdb
  WITH OWNER = encomiendasspuser
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       CONNECTION LIMIT = -1;

- Conectarse con el usuario creado 
> \c encomiendasspdb

- Cambiar owner de public al usuario creado
> ALTER SCHEMA public OWNER TO encomiendasspuser;

### Conectarse a DB con un cliente
- URL conexión: postgresql://encomiendasspuser:aloha@127.0.0.1:5432/encomiendasspdb


## Continur en VSCode
### Configuración de application.properties
- Copiar el archivo application.example-(mac o windows).properties como application.properties en el mismo directorio (src/main/resources) y completar los parámetros segun corresponda, reemplazar `TU_USUARIO` por el del equipo.
- Copiar el archivo db.localhost.example.properties como db.localhost.properties en el mismo directorio (src/main/resources/config) y completar los parámetros de la db segun corresponda.

### Configuración de .vscode/settings.json
- Crear directorio `.vscode` en la raiz si no existe.
- Copiar archivo docs/util/settings-example.json al directorio .vscode/settings.json



## Crear estructuras base
- Desde un cliente sql iniciado con usuario `encomiendasspuser`
- Ejecutar script [01-create_ddl.sql](docs/base/postgres/01-create_ddl.sql)
- Ejecutar script [02-insert_inicial.sql](docs/base/postgres/02-insert_inicial.sql)


### Ejecutar proyecto
- Ir al archivo [EncomiendasspApplication.java](src/main/java/com.micodeya.encomiendassp.backend/EncomiendasspApplication.java) y ejecutar el main.
- Probar si el servidor responde en `http://127.0.0.1:8080/micodeya/encomiendassp/server/status`
- Probar login [03-auth.http](docs/base/03-auth.http)



