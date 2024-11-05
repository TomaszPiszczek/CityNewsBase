package com.example.CityNewsBase.model.openAiModel.request;

import java.util.ArrayList;
import java.util.List;

public class OpenAiRequest {
    private final String model = "gpt-4o-mini"; // Użyj właściwego modelu
    private final List<Message> messages;
    private final double temperature = 0.0; // Zmienna temperatura, ustawiona na 0

    public OpenAiRequest(String userMessage) {
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", userMessage));
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public double getTemperature() {
        return temperature;
    }


    }
