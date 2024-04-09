# kusta
desafio

Este README proporciona una descripción general del proyecto, incluyendo sus características, tecnologías utilizadas, instrucciones de instalación y ejecución, detalles sobre contribuciones y licencia. Puedes personalizar este archivo según las necesidades específicas del proyecto.

Requisitos previos
Antes de ejecutar Kusta, asegúrate de tener instalado lo siguiente:
Lenguaje de programación: (Java 17)
Herramientas: (opcional Docker)
Base de datos: (MongoDB)

1. Clona el repositorio:
- git clone https://github.com/epacoricona/kusta.git

2. **Configuración de la Base de Datos:**
- Configura una base de datos PostgreSQL.
- Actualiza las configuraciones de la base de datos en el archivo `application.yaml` ubicado en `src/main/resources`.

3. **Ejecutar el Microservicio:**
- Navega al directorio raíz del proyecto.
- Ejecuta el siguiente comando para iniciar el microservicio:
    ./mvnw spring-boot:run
- nota: si utilza un IDE este paso se podria ejecutando el proyecto median el Boot Dashboard.

3. **Ejecutar proyecto mediante Docker (opcionalmente):**
- Se creo y configuro un archivo dockerFile para la ejecucion el cual se encuentra en la raiz del proyecto.
- Ejecuta los siguientes comando para crear e iniciar la imagen:
    - docker build -t kusta_img .
    - docker run kusta_img

4. Puedes realizar pruebas del microservicio utilizando herramientas como Postman. Aquí hay algunos ejemplos de llamadas REST:
    - ENTIDAD ANIMAL 
    1. **Obtener Todos los Animales:**
    GET http://localhost:8080/api/animal
    
    3. **Obtener un Animal por ID:**
    GET http://localhost:8080/api/animal/{id}
    
    3. **Crear un Nuevo Animal:**
    POST http://localhost:8080/api/animal
    Content-Type: application/json
    
    {
    "name": "Fido",
    "especie": "Dog",
    }
    
    4. **Actualizar un Animal Existente:**
    PUT http://localhost:8080/api/animal/{id}
    Content-Type: application/json
    
    {
    "name": "Fido",
    "species": "Dog",
    "age": 4
    }
  
    5. **Eliminar un Animal por ID:**
    DELETE http://localhost:8080/api/animal/{id}
  
  
    - ENTIDAD PERSONA 
  
    1. **Obtener Todas las Personas:**
    GET http://localhost:8080/api/person
    
    3. **Obtener una Persona por ID:**
    GET http://localhost:8080/api/person/{id}
    
    3. **Crear una Nueva Persona:**
    POST http://localhost:8080/api/person
    Content-Type: application/json
    
    {
    "name": "Fido",
    "especie": "Dog",
    }
    
    4. **Actualizar una Persona Existente:**
    PUT http://localhost:8080/api/person/{id}
    Content-Type: application/json
    
    {
    "name": "Fido",
    "species": "Dog",
    "age": 4
    }

  5. **Eliminar una Persona por ID:**
  DELETE http://localhost:8080/api/person/{id}


  - Lógica de Negocio con Drools:
    - se creo un archivo rules.drl con una regla basica, la cual recibe un parametro de entrada, ejecuta una logica de negocio y retorna un valor:
    
    1. **Eleccion de fuente de datos:**
       GET http://localhost:8080/api/decision/{source}  ---  SOURCE = ANIMAL O PERSONA

       
