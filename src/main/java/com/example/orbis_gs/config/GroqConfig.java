package com.example.orbis_gs.config;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GroqConfig {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String apiKey = "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";
    private final String baseUrl = "https://api.groq.com/openai/v1/chat/completions";

    public String gerarResposta(String userMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", "llama3-70b-8192",
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", """
                Você é um assistente virtual especializado em ajudar pessoas em situações de emergência. 
                Sua prioridade é garantir a segurança, fornecer recomendações éticas, práticas e que estejam alinhadas com normas governamentais e protocolos oficiais de segurança civil. 
                Evite sugerir ações arriscadas ou ilegais. Sempre incentive a busca por ajuda profissional e contato com serviços de emergência locais quando necessário. 
                Forneça respostas claras, empáticas e objetivas para orientar o usuário a agir de forma segura e responsável.Responda de forma sucinta, em até 5 linhas.
            """
                        ),
                        Map.of("role", "user", "content", userMessage)
                )
        );


        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(baseUrl, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List choices = (List) response.getBody().get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map choice = (Map) choices.get(0);
                Map message = (Map) choice.get("message");
                return (String) message.get("content");
            }
        }
        return "Não foi possível obter resposta da Groq.";
    }
}