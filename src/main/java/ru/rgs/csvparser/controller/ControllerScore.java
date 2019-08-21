package ru.rgs.csvparser.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@FeignClient(name = "csv-parse-example", url = "http://0.0.0.0:8081")
public interface ControllerScore {

    @RequestMapping(method = RequestMethod.POST, path = "/score")
    public HashMap score(@RequestBody HashMap clientName);

}
