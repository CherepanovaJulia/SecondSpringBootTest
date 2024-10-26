package ru.cherepanova.SecondSpringBootTest.service;

import org.springframework.stereotype.Service;
import ru.cherepanova.SecondSpringBootTest.model.Response;

@Service
public interface ModifyResponseService {
    Response modify (Response response);
}
