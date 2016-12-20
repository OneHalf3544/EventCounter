package main.classes;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * This is the main class
 * It counts how many events occurred over the given period, period could be last_[second, minute, hour and day]
 */

public class EventCounterImpl implements EventCounter {

    private final long startTimeStamp;
    private ConcurrentLinkedDeque<Photo> photos;

    private long currentPhotoId;


    public EventCounterImpl(long startTimeStamp) {
        this.photos = new ConcurrentLinkedDeque<>();
        this.startTimeStamp = startTimeStamp;
        this.currentPhotoId = 0;
    }


    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR   = 60 * MINUTE;
    private static final long DAY    = 24 * HOUR;


    @Override
    public void eventOccurred() {
        long currentTime = System.currentTimeMillis();
        final Photo currentPhoto = new Photo(currentPhotoId, currentTime - startTimeStamp);
        currentPhotoId++;
        photos.add(currentPhoto);
    }

    // this one is just for testing long durations, HOUR and DAY for instance
    public void eventOccurred(long givenTime) {
        photos.add(new Photo(currentPhotoId, givenTime));
        currentPhotoId++;
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

        /* Почему Deque, а не Queue ? Итерироваться по Queue раза в полтора - два быстрее,
         * чем поддерживать два порядка обхода для Deque, но т.к. нам интересны именно последние события,
         * то лучше начать перебирать события с конца,
         * нежели начинать с начала и перебрать (потеницально) почти всю коллекцию. */

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