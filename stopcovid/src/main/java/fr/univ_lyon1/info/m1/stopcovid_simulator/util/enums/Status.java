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
        final float lowRisk = 0.1f;
        final float mediumRisk = 0.5f;
        final float highRisk = 1f;

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
