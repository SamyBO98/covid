package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

public enum SendingStrategyName {
    ALL,
    REPEATED,
    FREQUENT;

    @Override
    public String toString() {
        switch (this) {
            case ALL:
                return "Send all";
            case REPEATED:
                return "Send repeated";
            case FREQUENT:
                return "Send frequent";
            default:
                throw new IllegalArgumentException();
        }
    }
}
