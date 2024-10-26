package ru.cherepanova.SecondSpringBootTest.model;
import com.fasterxml.jackson.annotation.JsonValue;
public enum ErrorCodes {
    EMPTY(""),
    VALIDATION_EXCEPTION ("ValidationException"),
    UNKNOWN_EXCEPTION("UnknownException"),
    UNKNOWN_ERROR("UnknownError");

    private final String name;

    ErrorCodes(String name){
        this.name = name;
    }
     @JsonValue
    public String getName(){
        return name;
     }

     @Override
    public String toString(){
        return name;
     }

}
