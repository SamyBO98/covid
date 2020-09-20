package fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SendingStrategyNameTest {

    @Test
    void testToString() {
        // Run the test
        final String allResult = SendingStrategyName.ALL.toString();
        final String repeatedResult = SendingStrategyName.REPEATED.toString();
        final String frequentResult = SendingStrategyName.FREQUENT.toString();

        // Verify the results
        assertEquals("Send all", allResult);
        assertEquals("Send repeated", repeatedResult);
        assertEquals("Send frequent", frequentResult);
    }

}
