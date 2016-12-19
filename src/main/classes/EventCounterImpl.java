package main.classes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * This is the main class
 * It counts how many events occurred over the given period, period could be last_[second, minute, hour and day]
 */

public class EventCounterImpl implements EventCounter {

    private final long startTimeStamp;
    volatile private LinkedList<Photo> photos;
    /* LinkedList or ArrayList ? The first one is good for adding new items at the end of the collection and
    * the second one is good for getting an item by index, thus we could implement countEvents by binarySearch.
    * I assume that adding a new item will be much more often than the query countEvents method, so my choice is LinkedList
    * Maybe I should consider hashed Collections ?*/
    private long totalPhotoCount;


    public EventCounterImpl(long startTimeStamp) {
        this.photos = new LinkedList<>();
        this.startTimeStamp = startTimeStamp;
        this.totalPhotoCount = 0;
    }


    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR   = 60 * MINUTE;
    private static final long DAY    = 24 * HOUR;


    @Override
    public void eventOccurred() {
        long currentTime = System.currentTimeMillis();
        final Photo currentPhoto = new Photo(totalPhotoCount, currentTime - startTimeStamp);
        totalPhotoCount++;
        photos.add(currentPhoto);
    }

    // this one is just for testing long durations, HOUR and DAY for instance
    public void eventOccurred(long givenTime) {
        photos.add(new Photo(totalPhotoCount, givenTime));
        totalPhotoCount++;
    }


    private long getEventsCountOverPeriod(TimePeriod timePeriod) {
        long timePeriodDuration;

        switch (timePeriod) {
            case LAST_SECOND:
                timePeriodDuration = SECOND;
                break;
            case LAST_MINUTE:
                timePeriodDuration = MINUTE;
                break;
            case LAST_HOUR:
                timePeriodDuration = HOUR;
                break;
            case LAST_DAY:
                timePeriodDuration = DAY;
                break;
            default:
                throw new IllegalArgumentException("Unexpected timePeriod value");
        }

        long eventsCountOverPeriod = 0;
        final long countEndsAtTime = Math.max(0, System.currentTimeMillis() - startTimeStamp - timePeriodDuration);

        Iterator<Photo> iterator = photos.descendingIterator();
        while(iterator.hasNext()) {
            Photo currentPhoto = iterator.next();
            if (currentPhoto.getTime() >= countEndsAtTime) {
                eventsCountOverPeriod++;
            } else {
                break;
            }
        }

        return eventsCountOverPeriod;
    }


    @Override
    public long getEventsCountLastSecond() {
        return getEventsCountOverPeriod(TimePeriod.LAST_SECOND);
    }

    @Override
    public long getEventsCountLastMinute() {
        return getEventsCountOverPeriod(TimePeriod.LAST_MINUTE);
    }

    @Override
    public long getEventsCountLastHour() {
        return getEventsCountOverPeriod(TimePeriod.LAST_HOUR);
    }

    @Override
    public long getEventsCountLastDay() {
        return getEventsCountOverPeriod(TimePeriod.LAST_DAY);
    }
}
