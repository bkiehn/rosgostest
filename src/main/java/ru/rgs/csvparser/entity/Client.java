package ru.rgs.csvparser.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
    String clientName;
    String contractDate;

    public Client() {

    }

    public Client(String clientName, String contractDate) {
        this.clientName = clientName;
        this.contractDate = contractDate;
    }

    @Override
    public String toString() {
        return ("clietnName : " + clientName +
                "\ncontractDate : " + contractDate);
    }
}
