package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

public enum Status {
    NO_RISK,
    RISKY,
    INFECTED;

    @Override
    public String toString() {
        switch (this) {
            case NO_RISK:
                return "No risk";
            case RISKY:
                return "Risky";
            case INFECTED:
                return "Infected";
            default:
                throw new IllegalArgumentException();
        }
    }
}
