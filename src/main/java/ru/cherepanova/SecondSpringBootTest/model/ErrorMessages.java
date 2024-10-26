package ru.cherepanova.SecondSpringBootTest.model;
import com.fasterxml.jackson.annotation.JsonValue;
public enum ErrorMessages {
    EMPTY (""),
    VALIDATION("Ошибка валидации"),
    UNSUPPORTED("Произошла непредвиденная ошибка"),
    UNKNOWN("Не поддерживаемая ошибка"),
    UNKNOWN_ERROR("uid не должен быть равен 123");


    private final String description;

    ErrorMessages(String description){
        this.description = description;
    }
    @JsonValue
    public String getName(){
        return description;
    }

}



