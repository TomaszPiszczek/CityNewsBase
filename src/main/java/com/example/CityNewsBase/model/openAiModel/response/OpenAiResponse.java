package com.example.CityNewsBase.model.openAiModel.response;

import java.util.List;

public class OpenAiResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
