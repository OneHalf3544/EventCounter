package main.java;


interface EventCounter {
    void eventOccurred();
    long getEventsCountLastSecond();
    long getEventsCountLastMinute();
    long getEventsCountLastHour();
    long getEventsCountLastDay();
}
