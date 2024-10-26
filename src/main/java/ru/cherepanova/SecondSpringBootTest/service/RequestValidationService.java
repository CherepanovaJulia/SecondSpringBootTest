package ru.cherepanova.SecondSpringBootTest.service;

import org.springframework.aot.generate.UnsupportedTypeValueCodeGenerationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.cherepanova.SecondSpringBootTest.exception.ValidationFailedException;
import ru.cherepanova.SecondSpringBootTest.model.Request;

import java.util.Objects;

@Service
public class RequestValidationService implements ValidationService {
    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            throw new
                    ValidationFailedException(bindingResult.getFieldError().toString());
        }
    }
    @Override
    public void isCodeValid(Request request) throws UnknownError {
        if (!request.isUidValid()) {
            throw new UnknownError("uid не может быть равен 123");
        }
    }
}


        /*if (Objects.equals(bindingResult.getFieldValue("uid"), "123"));
        throw new
                UnsupportedTypeValueCodeGenerationException("uid не должен состоять из цифр 123");*/



