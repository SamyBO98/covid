package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SendingStrategyTest {

    @Test
    void testToString() {
        // Run the test
        final String allResult = SendingStrategy.ALL.toString();
        final String repeatedResult = SendingStrategy.REPEATED.toString();
        final String frequentResult = SendingStrategy.FREQUENT.toString();

        // Verify the results
        assertEquals("Send all", allResult);
        assertEquals("Send repeated", repeatedResult);
        assertEquals("Send frequent", frequentResult);
    }

    @Test
    void testGetSendingThreshold() {
        // Run the test
        final int allResult = SendingStrategy.ALL.getSendingThreshold();
        final int repeatedResult = SendingStrategy.REPEATED.getSendingThreshold();
        final int frequentResult = SendingStrategy.FREQUENT.getSendingThreshold();

        // Verify the results
        assertEquals(1, allResult);
        assertEquals(2, repeatedResult);
        assertEquals(5, frequentResult);
    }
}
