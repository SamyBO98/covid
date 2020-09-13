package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

public enum CautionLevel {
    UNWARY,
    BASIC,
    WARY;

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
        final float highTolerance = 5f;
        final float mediumTolerance = 1f;
        final float lowTolerance = 0.5f;

        switch (this) {
            case UNWARY:
                return highTolerance;
            case BASIC:
                return mediumTolerance;
            case WARY:
                return lowTolerance;
            default:
                throw new IllegalArgumentException();
        }
    }
}
