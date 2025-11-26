# 📘 Sistema de Detección de Mutantes 

Este proyecto implementa un servicio REST que analiza cadenas de ADN para determinar si corresponden a un mutante.
La solución está desarrollada en **Spring Boot**, e incluye componentes de validación, persistencia, manejo de excepciones y estadísticas.

El trabajo original se realizó en conjunto, pero esta es **mi versión personalizada**, con ajustes propios en código, estructura y documentación.

---

## 📌 Funcionalidades principales

### 🧬 1. Análisis de ADN

El servicio expone un endpoint que recibe una secuencia de ADN y determina si es mutante utilizando reglas específicas.
La detección se realiza mediante búsqueda de patrones en:

* Direcciones horizontales
* Verticales
* Diagonales (↘ y ↗)

---

### 📊 2. Generación de estadísticas

El sistema almacena cada verificación realizada y expone un endpoint de estadísticas con:

* Cantidad de ADN mutante almacenado
* Cantidad de ADN humano
* Proporción entre ambos

---

### 💾 3. Persistencia

La aplicación utiliza **H2 Database** (modo en memoria o archivo local según configuración).
Incluye:

* Repositorios Spring Data JPA
* Entidad persistente con ADN normalizado
* Evita duplicados mediante control previo

---

### 🌐 4. Deploy en Render (Producción)

La aplicación puede desplegarse automáticamente en Render utilizando un Dockerfile y variables de entorno.

🔗 https://globalmutant.onrender.com


---

## 🛠️ Tecnologías utilizadas

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* H2 Database
* Docker
* Render.com
* Gradle
* Postman (para pruebas)

---

## 📂 Estructura general del proyecto

```
src/main/java/ar/utn/lauti/meli/
│── controller/
│── service/
│── repository/
│── model/
│── dto/
│── exception/
│── config/
```

> *Los paquetes fueron renombrados en esta versión para que correspondan a mi implementación personal.*

---

## 🚀 Endpoints principales

### 1. Verificar mutante

`POST /mutant`

Cuerpo (JSON):

```json
{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```

Respuestas posibles:

* **200 OK** → ADN mutante
* **403 Forbidden** → ADN humano
* **400 Bad Request** → ADN inválido

---

### 2. Obtener estadísticas

`GET /stats`

Ejemplo de respuesta:

```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

---

## 🐘 Base de datos H2

Mi versión utiliza una base distinta:

```
spring.datasource.url=jdbc:h2:mem:lauti_mutants_db
spring.datasource.username=lauti
spring.datasource.password=1234
```

> Cuando uses modo archivo, reemplazá por:
> `jdbc:h2:file:./data/lauti_mutants_db`

---

## 🐋 Docker y Render

La aplicación se ejecuta con:

```bash
docker build -t mutant-detector-lauti .
docker run -p 8080:8080 mutant-detector-lauti
```

El archivo `render.yaml` fue actualizado con mi nombre de servicio:

```
name: meli-api-lauti
```

🔗 https://dashboard.render.com/web/srv-d4jef8vdiees738mofi0

---

## ⚠️ Manejo de errores

Todos los errores se manejan mediante un `ControllerAdvice`, generando respuestas consistentes:

* ADN inválido
* Formato incorrecto
* Excepciones generales
* Errores de duplicación

El mensaje es devuelto en formato JSON para facilitar debugging o consumo desde Postman.

---

## 📖 Cómo ejecutar en local

### 1. Clonar el repositorio

(este ya sería tu repo personal)

```bash
git clone https://github.com/Lautysosa32/GlobalMutant/edit/main/README.md
```

🔗 https://github.com/Lautysosa32/GlobalMutant

### 2. Compilar y ejecutar

```bash
./gradlew bootRun
```

La API estará disponible en:
➡️ [http://localhost:8080](http://localhost:8080)

