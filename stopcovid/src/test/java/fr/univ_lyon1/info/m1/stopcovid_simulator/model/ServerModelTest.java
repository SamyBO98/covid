package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.CautionLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServerModelTest {
    ServerModel serverModel;
    CautionLevel cautionLevel;
    ClientState clientState;
    HashMap<Integer, ClientManager> clients;

    @BeforeEach
    public void setUp(){
        serverModel = new ServerModel();
    }
    @Test
    public void testGetterSetterCautionLevel(){
        cautionLevel = CautionLevel.BASIC;
        serverModel.setCautionLevel(cautionLevel);
        assertEquals(serverModel.getCautionLevel(), cautionLevel);
    }


}