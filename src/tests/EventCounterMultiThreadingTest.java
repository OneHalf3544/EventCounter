package tests;

import main.java.EventCounterImpl;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * Created by stepanovep on 12/19/2016.
 */

public class EventCounterMultiThreadingTest {

    private EventCounterImpl eventCounter;

    @Before
    public void init() {
        eventCounter = new EventCounterImpl(System.currentTimeMillis());
    }

    @Test
    public void testTwoThreads() throws InterruptedException {

        final int count1 = 40_000;
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < count1; i++) {
                eventCounter.eventOccurred();
            }
        });

        final int count2 = 10_000;
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < count2; i++) {
                eventCounter.eventOccurred();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(count1 + count2, eventCounter.getEventsCountLastSecond());
    }

    @Test
    public void testFourThreads() throws InterruptedException {

        final int count1 = 40_000;
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < count1; i++) {
                eventCounter.eventOccurred();
            }
        });

        final int count2 = 40_000;
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < count2; i++) {
                eventCounter.eventOccurred();
            }
        });

        final int count3 = 100_000;
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < count3; i++) {
                eventCounter.eventOccurred();
            }
        });

        final int count4 = 100_000;
        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < count4; i++) {
                eventCounter.eventOccurred();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();


        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        assertEquals(count1 + count2 + count3 + count4, eventCounter.getEventsCountLastSecond());
    }
}