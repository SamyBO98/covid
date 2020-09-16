package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

public enum SendingStrategy {
    ALL,
    REPEATED,
    FREQUENT;

    private static final int ALL_THRESHOLD = 1;
    private static final int REPEATED_THRESHOLD = 2;
    private static final int FREQUENT_THRESHOLD = 5;

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

    /**
     * @return the sending threshold of `this` sending strategy.
     */
    public int getSendingThreshold() {
        switch (this) {
            case ALL:
                return ALL_THRESHOLD;
            case REPEATED:
                return REPEATED_THRESHOLD;
            case FREQUENT:
                return FREQUENT_THRESHOLD;
            default:
                throw new IllegalArgumentException();
        }
    }
}
