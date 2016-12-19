package tests;

import main.classes.EventCounterImpl;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by stepanovep on 12/18/2016.
 */

public class EventCounterImplTest {

    private EventCounterImpl eventCounter;

    @Test
    public void test1() {
        eventCounter = new EventCounterImpl(System.currentTimeMillis());
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
        eventCounter = new EventCounterImpl(System.currentTimeMillis());
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
        eventCounter = new EventCounterImpl(System.currentTimeMillis());
        final long count = 20_000;
        for (long i = 0L; i < count; i++) {
            eventCounter.eventOccurred();
        }

        assertEquals(count, eventCounter.getEventsCountLastSecond());
    }
}