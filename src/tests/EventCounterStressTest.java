package tests;

import main.java.EventCounterImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * Created by Captain-Nemo on 12/20/2016.
 */

public class EventCounterStressTest {

    private EventCounterImpl eventCounter;

    @Before
    public void init() {
        eventCounter = new EventCounterImpl(System.currentTimeMillis());
    }


    @Test
    public void test30M() {
        final long bigCount = 30_000_000;
        for (long i = 0L; i < bigCount; i++) {
            eventCounter.eventOccurred();
        }

        assertEquals(bigCount, eventCounter.getEventsCountLastHour());
    }

    @Test
    public void test30M_TwoThreads() throws InterruptedException {
        long count1 = 15_000_000;
        Thread thread1 = new Thread(() -> {
            for (long i = 0L; i < count1; i++) {
                eventCounter.eventOccurred();
            }
        });

        long count2 = 15_000_000;
        Thread thread2 = new Thread(() -> {
            for (long i = 0L; i < count2; i++) {
                eventCounter.eventOccurred();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(count1+ count2, eventCounter.getEventsCountLastMinute());
        // as expected approximately two times faster
    }

    @Ignore
    @Test
    public void TooMuchForMyLaptop() {
        // Java out of memory Heap space
        final long bigCount = 50_000_000;
        for (long i = 0L; i < bigCount; i++) {
            eventCounter.eventOccurred();
        }

        assertEquals(bigCount, eventCounter.getEventsCountLastHour());
    }

}
