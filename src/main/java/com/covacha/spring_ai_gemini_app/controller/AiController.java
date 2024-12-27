package com.covacha.spring_ai_gemini_app.controller;

import com.covacha.spring_ai_gemini_app.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller // Cambiado a @Controller
public class AiController {

    @Autowired
    private AiService aiService;

    @GetMapping("/") // Mapeado al endpoint /
    public String index() {
        return "index"; // Nombre de la plantilla html
    }

    @GetMapping("/ask")
    @ResponseBody // Añadido @ResponseBody
    public String ask(@RequestParam String question) throws IOException {
        return aiService.generateResponse(question);
    }
}