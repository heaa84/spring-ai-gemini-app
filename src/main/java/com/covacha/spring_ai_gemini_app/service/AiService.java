package com.covacha.spring_ai_gemini_app.service;

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

    public String generateResponse(String userPrompt) throws IOException{
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
