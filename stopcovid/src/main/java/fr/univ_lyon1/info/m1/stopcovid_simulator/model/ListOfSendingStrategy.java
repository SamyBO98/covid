package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import java.util.ArrayList;
import java.util.Arrays;

public final class ListOfSendingStrategy {
    public static final ArrayList<SendingStrategy> LIST = new ArrayList<>(Arrays.asList(
            new SendingStrategy.SendAll(),
            new SendingStrategy.SendRepeated(),
            new SendingStrategy.SendFrequent()
    ));

    private ListOfSendingStrategy() {
    }
}
