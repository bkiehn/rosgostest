package ru.rgs.csvparser.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.rgs.csvparser.entity.Client;
import ru.rgs.csvparser.entity.Scoring;

@FeignClient(name = "csv-parse-example", url = "${external.ribbon.listOfServers}",
        fallback = ControllerScoreFallback.class)
public interface ControllerScore {

    @HystrixCommand(ignoreExceptions = com.jayway.awaitility.core.ConditionTimeoutException.class)
    @RequestMapping(method = RequestMethod.POST, path = "/score", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Contetnt-Type: application/json")
    public Scoring getScore(@RequestBody Client client);

}
