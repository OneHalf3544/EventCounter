package main;

import main.classes.EventCounterImpl;
import java.util.concurrent.TimeUnit;

/**
 * Реализовать объект для учета однотипных событий в системе. Например, отправка фото в сервисе фотографий.
 * События поступают в произвольный момент времени. Возможно как 10к событий в секунду, так и 2 в час.
 *
 * Интерфейс:
 *     1. Учесть событие.
 *     2. Выдать число событий за последнюю минуту (60 секунд).
 *     3. Выдать число событий за последний час (60 минут).
 *     4. Выдать число событий за последние сутки (24 часа).
 *
 *     Created by stepanovep on 12/18/2016
 */

public class Main {

    public static void main(String[] args) throws InterruptedException {

        final long currentTime = System.currentTimeMillis();
        EventCounterImpl eventCounter = new EventCounterImpl(currentTime);

        for(int i = 0; i < 3; ++i) {
            eventCounter.eventOccurred();
        }
        System.out.println(eventCounter.getEventsCountLastSecond());

        TimeUnit.SECONDS.sleep(1);

        for(int i = 0; i < 3; ++i) {
            eventCounter.eventOccurred();
        }
        System.out.println(eventCounter.getEventsCountLastSecond());

        eventCounter.eventOccurred();
        TimeUnit.SECONDS.sleep(2);

        System.out.println(eventCounter.getEventsCountLastSecond());

    }
}
