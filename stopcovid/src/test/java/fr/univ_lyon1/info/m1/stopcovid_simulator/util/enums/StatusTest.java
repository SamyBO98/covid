package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusTest {

    @Test
    void testToString() {
        // Run the test
        final String no_riskResult = Status.NO_RISK.toString();
        final String riskyResult = Status.RISKY.toString();
        final String infectedResult = Status.INFECTED.toString();

        // Verify the results
        assertEquals("No risk", no_riskResult);
        assertEquals("Risky", riskyResult);
        assertEquals("Infected", infectedResult);
    }

    @Test
    void testGetRisk() {
        // Run the test
        final float no_riskResult = Status.NO_RISK.getRisk();
        final float riskyResult = Status.RISKY.getRisk();
        final float infectedResult = Status.INFECTED.getRisk();

        // Verify the results
        assertEquals(0.1f, no_riskResult, 0.0001);
        assertEquals(0.2f, riskyResult, 0.0001);
        assertEquals(0.5f, infectedResult, 0.0001);
    }
}
