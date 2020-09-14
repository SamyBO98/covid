package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

public enum SendingStrategy {
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

    /**
     * @return the sending threshold of `this` sending strategy.
     */
    public int getSendingThreshold() {
        final var allThreshold = 1;
        final var repeatedThreshold = 2;
        final var frequentThreshold = 5;

        switch (this) {
            case ALL:
                return allThreshold;
            case REPEATED:
                return repeatedThreshold;
            case FREQUENT:
                return frequentThreshold;
            default:
                throw new IllegalArgumentException();
        }
    }
}
