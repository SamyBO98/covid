package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import com.github.hervian.reflection.Event;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientModelTest {

    private ClientModel clientModelUnderTest;

    @BeforeEach
    void setUp() {
        clientModelUnderTest = new ClientModel();
    }

    @Test
    void testGetStatus() {
        // Setup

        // Run the test
        final Status result = clientModelUnderTest.getStatus();

        // Verify the results
        assertEquals(Status.NO_RISK, result);
    }

}
