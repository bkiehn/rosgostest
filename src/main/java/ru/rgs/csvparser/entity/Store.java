package ru.rgs.csvparser.entity;

import org.springframework.stereotype.Component;

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

    public void clean() {
        scorings.removeAll(scorings);
    }

}
