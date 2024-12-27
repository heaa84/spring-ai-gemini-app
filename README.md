# Spring Boot AI Chat

Este proyecto es una aplicación web simple construida con Spring Boot que utiliza la API de Gemini para responder preguntas a través de una interfaz de usuario sencilla.

## Características

*   Interfaz de usuario web creada con HTML, CSS (Bootstrap 5) y JavaScript.
*   Interacción con la API de Gemini para generar respuestas a las preguntas del usuario.
*   Uso de un backend con Spring Boot.
*   Configuración de la clave API de Gemini a través de variables de entorno.

## Pre-requisitos

Antes de ejecutar la aplicación, asegúrate de tener instalado lo siguiente:

*   Java Development Kit (JDK) 17 o superior.
*   Maven.
*   Un editor de código o IDE (IntelliJ IDEA, VS Code, Eclipse, etc.).
*   Una clave API de Gemini de Google.

## Configuración

1.  **Clona el repositorio:**
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd <NOMBRE_DEL_REPOSITORIO>
    ```

2.  **Configura la clave API de Gemini:**

    *   Crea un archivo `application.properties` (o `application.yml`) en `src/main/resources`.
    *   Añade la clave API en el archivo:

        ```properties
        gemini.api-key=<TU_API_KEY>
        ```
        o en caso de yml
        ```yaml
        gemini:
          api-key: <TU_API_KEY>
        ```

        Reemplaza `<TU_API_KEY>` con tu clave API real.

3.  **Construye la aplicación:**
    ```bash
    mvn clean install
    ```

## Ejecución

1.  **Ejecuta la aplicación:**

    ```bash
    mvn spring-boot:run
    ```
    O si usas un IDE, puedes ejecutar directamente la clase principal `SpringAiGeminiAppApplication.java`

2.  **Abre la interfaz en el navegador:**

    Abre tu navegador web y ve a `http://localhost:8080/`.

3.  **Escribe tu pregunta:**

    Introduce tu pregunta en el campo de texto y haz clic en el botón "Preguntar".

4.  **Visualiza la respuesta:**

    La respuesta generada por Gemini aparecerá en la pantalla.

## Dependencias

*   **Spring Boot:** Para la creación de la aplicación.
*   **Spring Web:** Para la creación de APIs REST.
*   **Thymeleaf (opcional):** Para la generación de páginas web con plantillas.
*   **Bootstrap 5:** Para los estilos de la interfaz de usuario.

## Estructura del Proyectoificar en el código? ¡Estoy listo para ayudarte!