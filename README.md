# **Gastos Viajes API - Prueba Técnica**

## Descripción
Este proyecto es una solución para la prueba técnica dirigida a candidatos al rol de Analista de Desarrollo de Tecnología en Formación en Suramericana.
La aplicación implementa una API REST en Java 17 + Spring Boot que permite consultar la lista de gastos de viaje de los empleados, con:
- Total gastos por todos los empleados.
- Total por empleado y mes.
- Indicación de qué gastos asume la empresa y cuáles el empleado, según regla de negocio.
- Orden alfabético de empleados por nombre.

## Objetivo de la prueba
- Evaluar el diseño detallado y la separación de responsabilidades.
- Aplicar un enfoque orientado a objetos y uso adecuado del sistema de tipos en Java.
- Implementar una solución funcional en Java 8 o superior.
- Entregar una API funcional sin interfaz gráfica (UI).
- Desarrollar pruebas unitarias que validen la lógica del negocio.
- Documentar cómo ejecutar el proyecto.

## Tecnologías usadas
- Java 17
- Spring Boot 3.5.3
- Maven para construcción y gestión de dependencias
- JUnit 5 para pruebas unitarias
- CSV como fuente de datos (archivo plano)

## Funcionalidades
- Lectura de gastos desde un archivo CSV (`/persistencia/gastos.csv`)
- Agrupación de gastos por empleado y mes
- Cálculo de totales con y sin IVA (19%)
- Clasificación de gastos según un límite asumido por SURA (1.000.000)
- Exposición de los datos procesados vía API REST

## Consideraciones de diseño
- Se tomó la decisión de que SURA asume los gastos siempre que el total mensual por empleado con IVA no supere $1.000.000. En caso contrario, el gasto es asumido por el empleado.
- Se calcula el total de los gastos tanto con IVA como sin él, a nivel global y por empleado.
- La lógica de negocio está encapsulada en GastoService, separada del controlador REST (GastoController) y del acceso a datos (GastoCSVRepository).
- Los datos se cargan desde un archivo plano CSV (gastos.csv) ubicado en src/main/resources/persistencia, siguiendo una estructura simple con encabezados: id,nombre,fecha,monto.
- El resultado de la API devuelve una estructura jerárquica clara con totales globales, por empleado, y por mes, lo cual facilita su consumo por otros servicios o visualizaciones posteriores.
- El ordenamiento alfabético de los empleados se aplica antes de construir la respuesta, garantizando una salida predecible y alineada con los requisitos del negocio.
- Las pruebas unitarias cubren todos los componentes clave del sistema, incluyendo lógica de negocio, controladores y lectura de archivos CSV.

## Como ejecutar el proyecto
### Requisitos previos
- Java 17+ instalado y configurado (java -version)
- Maven instalado y en el PATH (mvn -v)

Clonar el repositorio
```bash
git clone https://github.com/pilicorredor/pt-gastos-sura.git
```
Una vez clonado, entra al directorio del proyecto:
```bash
cd gastos_viajes_api
```
Ejecutar con Maven
```bash
mvn spring-boot:run
```
Ejecutar tests
```bash
mvn test
```

## Uso de la API
Endpoint para consultar gastos
```bash
GET http://localhost:8080/api/obtener-gastos
```
Devuelve un JSON con:
- totalGeneral: suma total de gastos de todos los empleado sin IVA
- totalGeneralConIva: suma total de gastos de todos los empleado con IVA aplicado
- empleados: lista ordenada alfabéticamente con gastos por empleado y mes
  
  <img width="738" alt="{01FC5F28-C23E-4359-B04F-A3380C88E417}" src="https://github.com/user-attachments/assets/bb9a3b75-b51e-4923-9afd-55209902950f" />


## Futuras mejoras
- Interfaz gráfica (UI)
- Ejecutables en el repositorio (jar, exe, dll)
- Persistencia en base de datos (se usa CSV plano)

## Autora
**Pilar Andrea Corredor Corredor**
