package fr.univ_lyon1.info.m1.stopcovid_simulator.controller.simulator;

import com.github.hervian.reflection.Delegate;
import com.github.hervian.reflection.Event;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.ClientModel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.SendingStrategy;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ClientControlPanelControllerTest {

    @Mock
    private ClientModel Client;

    private ClientControlPanelController clientControlPanelControllerUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        clientControlPanelControllerUnderTest = new ClientControlPanelController(Client);
    }

    @Test
    void testGetId() {
        // Setup
        when(Client.getId()).thenReturn("id1");

        // Run the test
        final String result = clientControlPanelControllerUnderTest.getId();

        // Verify the results
        assertEquals("id1", result);
    }

}
