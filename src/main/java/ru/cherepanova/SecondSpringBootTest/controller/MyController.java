package ru.cherepanova.SecondSpringBootTest.controller;

import com.sun.jdi.request.ExceptionRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.cherepanova.SecondSpringBootTest.exception.ValidationFailedException;
import ru.cherepanova.SecondSpringBootTest.model.*;
import ru.cherepanova.SecondSpringBootTest.service.ModifyResponseService;
import ru.cherepanova.SecondSpringBootTest.service.ValidationService;
import ru.cherepanova.SecondSpringBootTest.util.DateTimeUtil;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService")
                        ModifyResponseService modifyResponseService){
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }


    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

    //    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("response: {}", response);
        try {
            validationService.isValid(bindingResult);
            validationService.isCodeValid(request);
        } catch (ValidationFailedException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.error("validation exception: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (UnknownError ex) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_ERROR);
            response.setErrorMessage(ErrorMessages.UNKNOWN_ERROR);
            log.error("unknown exception: {}", response);
            return new ResponseEntity<>(response, HttpStatus.HTTP_VERSION_NOT_SUPPORTED);

        } catch (Exception e) {

            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.error("unknown exception: {}", response);
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
