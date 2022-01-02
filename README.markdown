# Manual de Operación

Ejemplo de aplicación para registro de clientes en Liferay Portal.

Implementado para **Liferay Community Edition Portal 7.4.3 CE GA4**.


El proyecto incluye un Liferay Workspace con los siguientes módulos:
* customer-data/customer-data-api
* customer-data/customer-data-service
* customer-web

Los módulos bajo `customer-data` se han implementado haciendo uso de la herramienta Service Builder de Liferay, y dan cobertura a la capa de persistencia de datos de la aplicación.
La capa de presentación se implementa en el módulo `customer-web` (MVCPortlet).

Para la construcción y despliegue de la aplicación (**JDK 1.8 requerido**):
1. Descargar código fuente en una carpeta para el proyecto.
```
$> mkdir -p ~/projects/customers
$> cd ~/projects/customers
$> git clone https://github.com/jmrogado/lr-exercises.git
```
2. Situarse en carpeta `customer-data` y construir la capa de persistencia. Para ello, hacer uso del gradle wrapper incluido en el Liferay Workspace.
```
$> cd ~/projects/customers/lr-exercises/modules/customer-data
$> ../../gradlew build
```
3. Situarse en carpeta `customer-web` y construir la capa de presentación.
```
$> cd ~/projects/customers/lr-exercises/modules/customer-web
$> ../../gradlew build
```
4. Copiar/mover los tres artefactos generados en la carpeta `deploy` del servidor de aplicaciones. Se requiere un servidor Liferay CE 7.4 previamente instalado. A modo de ejemplo, se ilustra cómo desplegar en un contenedor Docker:
```
$> mkdir -p ~/projects/customers/lr-exercises/docker/deploy
$> cd ~/projects/customers/lr-exercises
$> docker run -it -p 8080:8080 -p 11311:11311 -v $(pwd)/docker:/mnt/liferay --name lr-7.4 liferay/portal:7.4.3.4-ga4
$> mv ~/projects/customers/lr-exercises/modules/customer-data/customer-data-api/build/libs/com.jmrogado.liferay.customer.api-1.0.0.jar ~/projects/customers/lr-exercises/docker/deploy/
$> mv ~/projects/customers/lr-exercises/modules/customer-data/customer-data-service/build/libs/com.jmrogado.liferay.customer.service-1.0.0.jar ~/projects/customers/lr-exercises/docker/deploy/
$> mv ~/projects/customers/lr-exercises/modules/customer-web/build/libs/com.jmrogado.liferay.customer.web-1.0.0.jar ~/projects/customers/lr-exercises/docker/deploy/
```
Una vez desplegados los artefactos en el servidor, instanciar el portlet customer-web en una página del sitio web.
