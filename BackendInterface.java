import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this interface exposes (makes available?) these functionalities to the frontend:
 * 1) read data from a file
 * 2) get a list of movies with the minimum duration 
 * 3) get a list of movies with a duration between two specified threshold
 */

public interface BackendInterface {

    /**
     * Reads data from a given file
     * @param File f - when the program is run, the given movies.csv file; when tests are run,
     * my makeshift movie.csv file
     * @return movie tree of movies gathered from file f
     * @throws FileNotFoundException 
     * @throws IOException 
     */
    public IterableMultiKeyRBT<Movie> readsDataFromFile(File f) throws FileNotFoundException, IOException;

    /**
     * Gets a list of movies with the minimum duration
     * Make sure edge cases cover duplicates of the same minimum time (use <=, add min to array)
     * @param duration- given duration of movies
     * @return array of the list of movie objects with the minimum duration
     */
    public ArrayList<Movie> getMinimumDuration(IterableMultiKeyRBT<Movie> movieTree);

    /**
     * Gets a list of movies with a duration between two specific thresholds
     * Make sure edge cases cover movies that are exactly 120 min and 125 min (use >/<= not
     *     >/< in if statements that are adding correct-length movies to the array)
     * @param threshold1- minimun duration of movies
     * @param threshold2 - maximum duration of movies 
     * @return array of the list of movie objects within the threshold
     */
    public ArrayList<Movie> getSpecifiedDuration (int minThreshold, int maxThreshold, 
        IterableMultiKeyRBT<Movie> movieTree);

}