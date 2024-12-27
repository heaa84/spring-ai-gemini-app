¡Entendido! Quieres usar la API de Gemini directamente usando el curl que proporcionaste, sin habilitar la API de Vertex AI ni usar la integración de Spring AI para Vertex AI. Es decir, quieres usar la API de Gemini directamente con tu clave API.

Esto es totalmente posible y válido. En este caso, no necesitarías las dependencias de Vertex AI en Spring AI.

Cómo puedes hacerlo en tu proyecto Spring Boot:

Elimina la dependencia de Spring AI Vertex AI:

En tu archivo pom.xml (si usas Maven) o build.gradle (si usas Gradle), elimina la dependencia que añadimos para Vertex AI:

Maven:

<dependency>
      <groupId>org.springframework.ai</groupId>
      <artifactId>spring-ai-vertex-ai-gemini</artifactId>
    </dependency>
content_copy
download
Use code with caution.
Xml

Asegúrate de que tu proyecto se reconstruya después de eliminar la dependencia.

Mantén la dependencia de Spring Web: Necesitas la dependencia de spring-web para construir tu API REST.

Crea un nuevo servicio o modifica el existente (AiService.java en nuestro ejemplo anterior), para ejecutar el curl que proporcionaste:

package com.example.springaigeminiapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Service
public class AiService {

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    public String generateResponse(String userPrompt) throws IOException {

        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + geminiApiKey;
        String requestBody = String.format("{\n" +
                "  \"contents\": [{\n" +
                "    \"parts\":[{\"text\": \"%s\"}]\n" +
                "    }]\n" +
                "   }", userPrompt);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                return extractTextFromResponse(response.toString());
            }
        } else {
            throw new IOException("Error en la respuesta HTTP: " + responseCode);
        }
    }

    private String extractTextFromResponse(String jsonResponse) {
        // Implementar la lógica para extraer el texto del JSON de respuesta de Gemini
        // Esto depende de la estructura del JSON de respuesta
        // Este es un ejemplo, puede necesitar ajustes
        int startIndex = jsonResponse.indexOf("\"text\": \"") + 9;
        int endIndex = jsonResponse.indexOf("\"", startIndex);

        if (startIndex > 8 && endIndex > startIndex) {
            return jsonResponse.substring(startIndex, endIndex);
        }

        return jsonResponse; // Devolver el JSON completo si no se encuentra el texto
    }
}
content_copy
download
Use code with caution.
Java

Actualiza el application.properties (o application.yml):

Añade tu clave API de Gemini en application.properties o application.yml.

gemini.api-key=<TU_API_KEY>
content_copy
download
Use code with caution.
Properties

O si prefieres YAML:

gemini:
api-key: <TU_API_KEY>
content_copy
download
Use code with caution.
Yaml

Reemplaza <TU_API_KEY> con tu clave API real.

Mantén el controlador AiController igual (o ajusta el llamado al nuevo servicio si lo has renombrado).

package com.example.springaigeminiapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AiController {

    @Autowired
    private AiService aiService;

    @GetMapping("/ask")
    public String ask(@RequestParam String question) throws IOException {
        return aiService.generateResponse(question);
    }
}
content_copy
download
Use code with caution.
Java

Explicación:

Hemos eliminado la dependencia de Spring AI Vertex AI.

El servicio AiService ahora ejecuta directamente el comando curl a través de código Java.

Hemos añadido la clave API en application.properties para no tenerla directamente en el código.

Hemos añadido la lógica necesaria para parsear la respuesta JSON de Gemini y extraer el texto generado.

¿Qué hemos conseguido?

Estamos usando directamente la API de Gemini sin Spring AI Vertex AI.

Tenemos el control total de la solicitud a la API.

No dependemos de la configuración y las abstracciones de Spring AI.

Podemos personalizar completamente la interacción con la API de Gemini.

Consideraciones:

Este enfoque requiere más código manual para construir la solicitud, manejar la respuesta y gestionar la autenticación.

Es menos "elegante" que la abstracción de Spring AI pero es funcional.

En caso de que cambies de proveedor de modelos, tendrías que cambiar el código de nuevo.

Prueba la API:

Ejecuta tu aplicación Spring Boot y prueba la API /ask con tu pregunta, como antes. Debería funcionar.

En Resumen:

Hemos ajustado el proyecto para usar directamente la API de Gemini con un curl en Java, sin usar la librería de Spring AI para Vertex AI. Este enfoque es válido si quieres tener más control sobre la interacción con la API o si no quieres usar la integración de Spring AI.

¿Hay algo más que quieras saber o modificar en el código? ¡Estoy listo para ayudarte!