package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

public enum Status {
    NO_RISK,
    RISKY,
    INFECTED;

    private static final float LOW_RISK = 0.1f;
    private static final float MEDIUM_RISK = 0.2f;
    private static final float HIGH_RISK = 0.5f;

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
        switch (this) {
            case NO_RISK:
                return LOW_RISK;
            case RISKY:
                return MEDIUM_RISK;
            case INFECTED:
                return HIGH_RISK;
            default:
                throw new IllegalArgumentException();
        }
    }
}
