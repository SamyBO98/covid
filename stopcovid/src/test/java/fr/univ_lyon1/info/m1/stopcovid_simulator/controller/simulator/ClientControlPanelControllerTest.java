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
    private ClientModel mockClient;

    private ClientControlPanelController clientControlPanelControllerUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        clientControlPanelControllerUnderTest = new ClientControlPanelController(mockClient);
    }

    @Test
    void testGetId() {
        // Setup
        when(mockClient.getId()).thenReturn("result");

        // Run the test
        final String result = clientControlPanelControllerUnderTest.getId();

        // Verify the results
        assertEquals("result", result);
    }

}
