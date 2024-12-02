package com.bho.catchtrippingbackend.travelrecommend.service;

import com.bho.catchtrippingbackend.travelrecommend.dto.reponse.TravelRecommendationResponse;
import com.bho.catchtrippingbackend.travelrecommend.dto.request.TravelRecommendationRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class ChatGptService {
    @Value("${openai.chatgpt.api-key}")
    private String openaiApiKey;

    public TravelRecommendationResponse getTravelRecommendations(TravelRecommendationRequest request) throws Exception {
        String prompt = createPrompt(request);

        String apiUrl = "https://api.openai.com/v1/chat/completions";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", Collections.singletonList(message));
        requestBody.put("max_tokens", 500);
        requestBody.put("temperature", 0.7);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl, HttpMethod.POST, httpEntity, String.class);

        String responseStr = responseEntity.getBody();

        // 응답에서 여행지 추출

        return parseResponse(responseStr);
    }

    private String createPrompt(TravelRecommendationRequest request) {
        return String.format(
                "여행 유형은 %s이고, 예산 범위는 %s입니다. 관심 있는 활동은 %s이며, 여행 기간은 %s입니다. 이 조건에 맞는 세부 여행지를 5곳 추천해 주세요. 각 여행지의 이름과 간단한 설명을 제공해주세요. 각 여행지는 다음과 같은 형식으로 출력해주세요:\\n번호. 여행지 이름:설명",
                request.getSelectedTravelType(),
                request.getBudgetRange(),
                String.join(", ", request.getActivities()),
                request.getTravelDuration()
        );
    }

    private TravelRecommendationResponse parseResponse(String responseStr) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseStr);
        JsonNode choicesNode = rootNode.path("choices");

        if (choicesNode.isArray() && !choicesNode.isEmpty()) {
            String content = choicesNode.get(0).path("message").path("content").asText();

            // 응답 내용 출력
            log.info("ChatGPT 응답 내용: {}\n", content);

            // 응답에서 여행지 리스트 추출
            List<TravelRecommendationResponse.Destination> destinations = extractDestinations(content);

            TravelRecommendationResponse response = new TravelRecommendationResponse();
            response.setDestinations(destinations);

            return response;
        } else {
            throw new Exception("OpenAI API로부터 유효한 응답을 받지 못했습니다.");
        }
    }

    private List<TravelRecommendationResponse.Destination> extractDestinations(String content) {
        List<TravelRecommendationResponse.Destination> destinations = new ArrayList<>();

        String[] lines = content.split("\\n");
        for (String line : lines) {
            if (line.matches("^\\d+\\.\\s.*")) {
                String[] parts = line.split("\\.\\s", 2);
                if (parts.length == 2) {
                    String nameAndDescription = parts[1];
                    String[] nameDescSplit = nameAndDescription.split(":", 2);
                    String name = nameDescSplit[0].trim();
                    String description = nameDescSplit.length > 1 ? nameDescSplit[1].trim() : "";

                    TravelRecommendationResponse.Destination destination = new TravelRecommendationResponse.Destination();
                    destination.setName(name);
                    destination.setDescription(description);

                    destinations.add(destination);
                }
            }
        }

        return destinations;
    }
}
