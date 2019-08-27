package ru.rgs.csvparser.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Scoring implements Comparable<Scoring>{
    String  status;
    String  scoringValue;
    String  description;
    Integer id;
    String  nameClient;
    String  contractDate;

    @Override
    public String toString() {
        return ("status : " + status +
                "\nscoringValue : " + scoringValue +
                "\ndescription : " + description);
    }

    public int compareTo(Scoring scoring) {
        return id - scoring.id;
    }

}
