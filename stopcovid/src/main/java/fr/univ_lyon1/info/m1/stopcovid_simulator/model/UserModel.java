package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import java.util.UUID;

public class UserModel {
    private String id;
    private Status status;

    /**
     * `id` : Random UUID.
     * `status` : NO_RISK.
     */
    public UserModel() {
        id = UUID.randomUUID().toString();
        status = Status.NO_RISK;
    }

    public String getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }
}
