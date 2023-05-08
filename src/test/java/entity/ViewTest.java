package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ViewTest {

    private View viewUnderTest;

    @BeforeEach
    void setUp() {
        viewUnderTest = new View(Timestamp.from(Instant.now()), 1, 1, 1);
    }

    @Test
    void testEquals() {
        assertFalse(viewUnderTest.equals("o"));
    }
}
