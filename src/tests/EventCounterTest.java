package tests;

import main.classes.EventCounterImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by stepanovep on 12/18/2016.
 */

public class EventCounterTest {

    private EventCounterImpl eventCounter;

    @Before
    public void init() {
        eventCounter = new EventCounterImpl(System.currentTimeMillis());
    }

    @Test
    public void test1() {
        eventCounter.eventOccurred();
        eventCounter.eventOccurred();
        eventCounter.eventOccurred();

        assertEquals(3L, eventCounter.getEventsCountLastSecond());
        assertEquals(3L, eventCounter.getEventsCountLastMinute());
        assertEquals(3L, eventCounter.getEventsCountLastHour());
        assertEquals(3L, eventCounter.getEventsCountLastDay());
    }

    @Test
    public void test2() throws InterruptedException {
        eventCounter.eventOccurred();
        eventCounter.eventOccurred();
        assertEquals(2L, eventCounter.getEventsCountLastMinute());

        TimeUnit.SECONDS.sleep(1);
        eventCounter.eventOccurred();
        eventCounter.eventOccurred();
        eventCounter.eventOccurred();
        assertEquals(3L, eventCounter.getEventsCountLastSecond());
        assertEquals(5L, eventCounter.getEventsCountLastMinute());
        assertEquals(5L, eventCounter.getEventsCountLastHour());
    }

    @Test
    public void test3() {
        final long count = 100_000;
        for (long i = 0L; i < count; i++) {
            eventCounter.eventOccurred();
        }

        assertEquals(count, eventCounter.getEventsCountLastSecond());
    }
}