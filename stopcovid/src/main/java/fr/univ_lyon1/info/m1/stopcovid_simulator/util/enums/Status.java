package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

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

    /**
     * @return the risk indicator represented by a user of `this` status.
     */
    public float getRisk() {
        final var lowRisk = 0.1f;
        final var mediumRisk = 0.2f;
        final var highRisk = 0.5f;

        switch (this) {
            case NO_RISK:
                return lowRisk;
            case RISKY:
                return mediumRisk;
            case INFECTED:
                return highRisk;
            default:
                throw new IllegalArgumentException();
        }
    }
}
