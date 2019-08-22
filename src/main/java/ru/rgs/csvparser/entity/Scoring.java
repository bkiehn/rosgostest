package ru.rgs.csvparser.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Scoring {
    String  status;
    String  scoringValue;
    String  description;

    @Override
    public String toString() {
        return ("status : " + status +
                "\nscoringValue : " + scoringValue +
                "\ndescription : " + description);
    }

}
