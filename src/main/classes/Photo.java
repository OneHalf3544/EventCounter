package main.classes;

/**
 * A class for simple object
 * Let it be class Photo, like in the example (Photo service)
 * the variable @time means how many milliseconds pass since the initiate EventCounter class
 */

public class Photo {

    private final long id;
    private final long time;

    public Photo(long id, long time) {
        this.id = id;
        this.time = time;
    }

    public long getId() {
        return this.id;
    }

    public long getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return "Photo" + this.id + ": " + this.time;
    }
}
