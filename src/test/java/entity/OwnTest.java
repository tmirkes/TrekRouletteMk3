package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class OwnTest {

    private Own ownUnderTest;

    @BeforeEach
    void setUp() {
        ownUnderTest = new Own(1, 1);
    }

    @Test
    void testEquals() {
        assertFalse(ownUnderTest.equals("o"));
    }

    @Test
    void testHashCode() {
        assertEquals(29823, ownUnderTest.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Own{id=0, userId=1, seasonId=1}", ownUnderTest.toString());
    }
}
