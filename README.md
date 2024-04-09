¡Claro! Aquí está el archivo **README.md** formateado para el proyecto **Kusta**:

# Kusta: Documentación del Microservicio

Este README proporciona una descripción general del proyecto, incluyendo sus características, tecnologías utilizadas, instrucciones de instalación y ejecución, detalles sobre contribuciones y licencia. Puedes personalizar este archivo según las necesidades específicas del proyecto.

## Requisitos previos
Antes de ejecutar Kusta, asegúrate de tener instalado lo siguiente:
- **Lenguaje de programación**: Java 17
- **Herramientas**: (opcional) Docker
- **Base de datos**: MongoDB

## Configuración del entorno
1. Clona el repositorio:
   ```bash
   git clone https://github.com/epacoricona/kusta.git
   cd kusta
   ```

2. **Configuración de la Base de Datos:**
   - Configura una base de datos PostgreSQL.
   - Actualiza las configuraciones de la base de datos en el archivo `application.yaml` ubicado en `src/main/resources`.

3. **Ejecutar el Microservicio:**
   - Navega al directorio raíz del proyecto.
   - Ejecuta el siguiente comando para iniciar el microservicio:
     ```bash
     ./mvnw spring-boot:run
     ```
   - Nota: Si utilizas un IDE, también puedes ejecutar el proyecto desde el Boot Dashboard.

4. **Ejecutar el proyecto mediante Docker (opcionalmente):**
   - Se ha creado y configurado un archivo `Dockerfile` para la ejecución. Encuéntralo en la raíz del proyecto.
   - Ejecuta los siguientes comandos para crear e iniciar la imagen:
     ```bash
     docker build -t kusta_img .
     docker run kusta_img
     ```

5. Puedes realizar pruebas del microservicio utilizando herramientas como Postman. Aquí hay algunos ejemplos de llamadas REST:

### ENTIDAD ANIMAL

1. **Obtener Todos los Animales:**
   - Método: GET
   - URL: `http://localhost:8080/api/animal`

2. **Obtener un Animal por ID:**
   - Método: GET
   - URL: `http://localhost:8080/api/animal/{id}`
   - Sustituye `{id}` con el ID del animal específico que deseas obtener.

3. **Crear un Nuevo Animal:**
   - Método: POST
   - URL: `http://localhost:8080/api/animal`
   - Encabezado: `Content-Type: application/json`
   - Cuerpo de la solicitud (JSON):
     ```json
     {
         "name": "Fido",
         "especie": "Dog"
     }
     ```

4. **Actualizar un Animal Existente:**
   - Método: PUT
   - URL: `http://localhost:8080/api/animal/{id}`
   - Encabezado: `Content-Type: application/json`
   - Cuerpo de la solicitud (JSON):
     ```json
     {
         "name": "Fido",
         "species": "Dog",
         "age": 4
     }
     ```
   - Sustituye `{id}` con el ID del animal que deseas actualizar.

5. **Eliminar un Animal por ID:**
   - Método: DELETE
   - URL: `http://localhost:8080/api/animal/{id}`
   - Sustituye `{id}` con el ID del animal que deseas eliminar.

### ENTIDAD PERSONA

1. **Obtener Todas las Personas:**
   - Método: GET
   - URL: `http://localhost:8080/api/person`

2. **Obtener una Persona por ID:**
   - Método: GET
   - URL: `http://localhost:8080/api/person/{id}`
   - Sustituye `{id}` con el ID de la persona específica que deseas obtener.

3. **Crear una Nueva Persona:**
   - Método: POST
   - URL: `http://localhost:8080/api/person`
   - Encabezado: `Content-Type: application/json`
   - Cuerpo de la solicitud (JSON):
     ```json
     {
         "name": "Esteban",
         "lasName": "Pacoricona"
     }
     ```

4. **Actualizar una Persona Existente:**
   - Método: PUT
   - URL: `http://localhost:8080/api/person/{id}`
   - Encabezado: `Content-Type: application/json`
   - Cuerpo de la solicitud (JSON):
     ```json
      {
         "id":"123"
         "name": "Esteban",
         "lasName": "Taquila"
     }
     ```
   - Sustituye `{id}` con el ID de la persona que deseas actualizar.

5. **Eliminar una Persona por ID:**
   - Método: DELETE
   - URL: `http://localhost:8080/api/person/{id}`
   - Sustituye `{id}` con el ID de la persona que deseas eliminar.

  
### Lógica de Negocio con Drools

- Se creó un archivo `rules.drl` con una regla básica, la cual recibe un parámetro de entrada, ejecuta una lógica de negocio y retorna un valor.

1. **Elección de fuente de datos:**
    - GET `http://localhost:8080/api/decision/{source}`  ---  SOURCE = ANIMAL O PERSONA
