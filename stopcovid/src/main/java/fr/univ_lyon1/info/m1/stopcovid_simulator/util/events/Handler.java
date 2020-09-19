package fr.univ_lyon1.info.m1.stopcovid_simulator.util.events;

import java.io.Serializable;

public interface Handler extends Serializable {
    interface With0Params extends Handler {
        void handle();
    }

    interface With1Params<T1> extends Handler {
        void handle(T1 arg1);
    }

    interface With2Params<T1, T2> extends Handler {
        void handle(T1 arg1, T2 arg2);
    }
}
