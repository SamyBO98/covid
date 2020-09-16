package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CautionLevelTest {

    @Test
    void testToString() {
        // Run the test
        final String unwaryResult = CautionLevel.UNWARY.toString();
        final String basicResult = CautionLevel.BASIC.toString();
        final String waryResult = CautionLevel.WARY.toString();

        // Verify the results
        assertEquals("Unwary", unwaryResult);
        assertEquals("Basic", basicResult);
        assertEquals("Wary", waryResult);
    }

    @Test
    void testGetTolerance() {
        // Run the test
        final float unwaryResult = CautionLevel.UNWARY.getTolerance();
        final float basicResult = CautionLevel.BASIC.getTolerance();
        final float waryResult = CautionLevel.WARY.getTolerance();

        // Verify the results
        assertEquals(5.0f, unwaryResult, 0.0001);
        assertEquals(1.0f, basicResult, 0.0001);
        assertEquals(0.5f, waryResult, 0.0001);
    }
}
