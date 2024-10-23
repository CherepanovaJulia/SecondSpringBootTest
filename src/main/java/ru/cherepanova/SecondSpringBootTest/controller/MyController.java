package ru.cherepanova.SecondSpringBootTest.controller;

import com.sun.jdi.request.ExceptionRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.cherepanova.SecondSpringBootTest.exception.ValidationFailedException;
import ru.cherepanova.SecondSpringBootTest.model.Request;
import ru.cherepanova.SecondSpringBootTest.model.Response;
import ru.cherepanova.SecondSpringBootTest.service.ValidationService;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.IllegalFormatCodePointException;

@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }


    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime())
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            validationService.isValid(bindingResult);
            validationService.isCodeValid(request);
        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedOperationException ex) {
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage("Не поддерживаемая ошибка");
            return new ResponseEntity<>(response, HttpStatus.HTTP_VERSION_NOT_SUPPORTED);

        } catch (Exception e) {

            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    }


/*
            response.setCode("failed");
            response.setErrorCode("IllegalArgumentException");
            response.setErrorMessage("UID = 123");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);*/
