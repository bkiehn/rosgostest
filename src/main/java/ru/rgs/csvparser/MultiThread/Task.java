package ru.rgs.csvparser.MultiThread;

import com.github.tomakehurst.wiremock.admin.NotFoundException;
import ru.rgs.csvparser.feign.RequestMockServer;
import ru.rgs.csvparser.entity.Client;
import ru.rgs.csvparser.entity.Scoring;

import static ru.rgs.csvparser.service.CsvParserServiceImpl.statusFailed;

public class Task implements  Runnable{

    private Client              client;
    private RequestMockServer   requestMockServer;
    private Store               store;
    private Integer             id;
    private String              contractDate;

    public Task(Client client, RequestMockServer requestMockServer, Store store,
                Integer id, String contractDate) {
        this.client = client;
        this.requestMockServer = requestMockServer;
        this.store = store;
        this.id = id;
        this.contractDate = contractDate;
    }

    @Override
    public void run() {
        System.out.println("Start " + Thread.currentThread().getName());

        Scoring scoring = new Scoring();
        try {
            scoring = requestMockServer.getScore(client);
        } catch (NotFoundException e) {
            scoring = new Scoring();
            scoring.setStatus(statusFailed);
            scoring.setDescription("ошибка обработки");
        }

        scoring.setId(id);
        scoring.setNameClient(client.getClientName());
        scoring.setContractDate(contractDate);
        store.push(scoring);
        System.out.println("\n---------\n" + scoring.toString() + "\n---------\n");
    }

}
