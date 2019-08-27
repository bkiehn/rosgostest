package ru.rgs.csvparser.MultiThread;

import org.springframework.stereotype.Component;
import ru.rgs.csvparser.entity.Client;
import ru.rgs.csvparser.entity.Scoring;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Store {

    public CopyOnWriteArrayList<Scoring> scorings;

    Store() {
        scorings = new CopyOnWriteArrayList<>();
    }

    public void push(Scoring scoring) {
        scorings.add(scoring);
    }

    public Scoring get(int number) {
        return scorings.get(number);
    }

    public void sortById() {
        Collections.sort(scorings);
    }

}
