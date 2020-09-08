package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class StopCovidUserModelTest {
    @Test
    void dummyTestInt() {
        // Given
        Integer i = 0;

        // When
        i++;

        // Then
        assertThat(i, is(1));
    }

    @Test
    void dummyTestIntFailure() {
        // Given
        Integer i = 0;

        // When
        i = i + 42;

        // Then
        // TODO: obviously, this test will fail. Fix it.
        assertThat(i, is(43));
    }
    
}