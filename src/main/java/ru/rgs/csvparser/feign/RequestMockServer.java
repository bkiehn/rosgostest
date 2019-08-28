package ru.rgs.csvparser.feign;


import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.rgs.csvparser.configuration.ClientConfiguration;
import ru.rgs.csvparser.entity.Client;
import ru.rgs.csvparser.entity.Scoring;

@FeignClient(name = "external", configuration = ClientConfiguration.class)
public interface RequestMockServer {

    @RequestMapping(method = RequestMethod.POST, path = "/score", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Contetnt-Type: application/json")
    public Scoring getScore(@RequestBody Client client);

}