package ru.rgs.csvparser.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import ru.rgs.csvparser.Exception.NotFoundException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 500) {
            return new NotFoundException();
        }
        return new Exception("Zdarova");
    }
}
