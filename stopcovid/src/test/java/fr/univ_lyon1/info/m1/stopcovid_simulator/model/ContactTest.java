package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

class ContactTest {

    @Mock
    private Runnable mockLifeTimerHandler;

    private Contact contactUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        contactUnderTest = new Contact(mockLifeTimerHandler);
    }


    @Test
    void testGetValue() {
        // Setup

        // Run the test
        final int result = contactUnderTest.getValue();

        // Verify the results
        assertEquals(0, result);
    }


}
