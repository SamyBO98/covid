package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

public enum CautionLevel {
    UNWARY,
    BASIC,
    WARY;

    private static final float HIGH_TOLERANCE = 5f;
    private static final float MEDIUM_TOLERANCE = 1f;
    private static final float LOW_TOLERANCE = 0.5f;

    @Override
    public String toString() {
        switch (this) {
            case UNWARY:
                return "Unwary";
            case BASIC:
                return "Basic";
            case WARY:
                return "Wary";
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * @return the risk tolerance indicator at `this` caution level.
     */
    public float getTolerance() {
        switch (this) {
            case UNWARY:
                return HIGH_TOLERANCE;
            case BASIC:
                return MEDIUM_TOLERANCE;
            case WARY:
                return LOW_TOLERANCE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
