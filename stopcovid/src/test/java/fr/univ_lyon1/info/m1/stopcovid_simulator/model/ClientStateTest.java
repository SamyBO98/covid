package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientStateTest {
    private ClientState clientState;

    @BeforeEach
    public void setUp(){
        clientState = new ClientState();
    }
    @Test
    public void testGetSetId(){
        clientState.setId("testGetSetID");
        assertEquals(clientState.getId(), "testGetSetID");
    }

    @Test
    public void testGetSetStatus(){
        Status status = Status.NO_RISK;
        clientState.setStatus(status);
        assertEquals(clientState.getStatus(), status);
    }

}