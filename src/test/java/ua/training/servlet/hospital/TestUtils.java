package ua.training.servlet.hospital;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class TestUtils {
    public static void assertTimeIsBetween(LocalDateTime time,LocalDateTime before, LocalDateTime after){
        assertTrue(time.compareTo(before) >= 0);
        assertTrue(time.compareTo(after) <= 0);
    }
}
