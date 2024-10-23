package ru.cherepanova.SecondSpringBootTest.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Request {

    @NotBlank
    private String uid;

    @NotBlank
    private String operationUid;
    private String systemName;
//@Pattern (regexp = "^[0-9],{0,4}-[0-9],{0,2}-[0-9],{0,2} [0-9],{0,2}:[0-9],{0,2}:[0-9],{0,2}$")
    //yyyy-MM-dd''HH:mm:ss
    @NotBlank
    private String systemTime;
    private String source;

    @Min(1)
    @Max(100000)
    private int communicationId;
    private int templateId;
    private int productCode;
    private int smsCode;

    public boolean setUid() {
        return !uid.equals("123");
    }
}
