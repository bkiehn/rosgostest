package ru.rgs.csvparser.controller;

import org.springframework.stereotype.Component;
import ru.rgs.csvparser.entity.Client;
import ru.rgs.csvparser.entity.Scoring;

@Component
public class ControllerScoreFallback implements ControllerScore {

    @Override
    public Scoring getScore(Client client){
        Scoring zapaska = new Scoring();
        zapaska.setStatus("FAILED");
        zapaska.setDescription("ошибка обработки");
        return zapaska;
    }
}
