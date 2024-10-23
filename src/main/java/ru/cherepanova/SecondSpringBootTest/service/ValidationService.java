package ru.cherepanova.SecondSpringBootTest.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.cherepanova.SecondSpringBootTest.exception.ValidationFailedException;
import ru.cherepanova.SecondSpringBootTest.model.Request;

@Service
public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;

    void isCodeValid(Request request) throws UnknownError;

}
