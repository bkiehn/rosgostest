package ru.rgs.csvparser.feign;

import com.github.tomakehurst.wiremock.admin.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 500) {
            return new NotFoundException("ошибка");
        }
        return new Exception("Zdarova");
    }
}
