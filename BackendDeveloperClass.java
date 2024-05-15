import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class BackendDeveloperClass implements BackendInterface {
  
  //CONSTRUCTOR & FIELDS (where Movie is its own class that defines a single movie object 
  // that can be inserted into a Red-Black tree later (for now, into a makeshift ArrayList
  // of type Movie)
  public IterableMultiKeyRBT<Movie> movieTree;
  private Frontend frontendInstance;

  public BackendDeveloperClass(Frontend frontendInstance){
       this.frontendInstance = frontendInstance;
       this.movieTree = new IterableMultiKeyRBT<Movie>();
  }

  /**
   * Reads in movie data from a CSV file, inserts it into a
   * Red-Black Tree, and accesses it based on commands from the frontend
   * @param File f - will eventually take in the given csv file, but for now takes in a shorter
   *                 version of the movies.csv file for more efficient testing
   * @throws FileNotFoundException, IO Exception
   */
  @Override
  public IterableMultiKeyRBT<Movie> readsDataFromFile(File f) throws FileNotFoundException, IOException {

    // creates a scanner that holds the movie strings read from file f
    try (Scanner scnr = new Scanner(f)) {
      // defines the parameters that are required for the creation of each Movie object that will
      // be added to the makeshift ArrayList
      String title;
      String genre;
      int year;
      String country;
      int duration;
      
      // skips first line?
      scnr.nextLine();

      // while there is another movie line to be read
      while (scnr.hasNextLine()) {

        // makes sure the scanner reads only one line of the file at a time
        // TODO and advances to the next line
        String movieLine = scnr.nextLine();
        int quotationCounter = 0;
        int startIndex = 0;
        ArrayList<String> movieData = new ArrayList<String>();
        
        // reads through each line and splits each piece of information by commas, unless a given
        // comma is integrated into a phrase that is in between double quotes. it does this by
        // adding a counter to see how many quotes have been read so far -- if  the counter is at
        // an even number, split; if odd, do not
        for (int currentIndex = 0; currentIndex < movieLine.length(); currentIndex++) {
          
          // reads through each character in each movie line
          if (movieLine.charAt(currentIndex) == '\"') {
            // number of double quotes read
            quotationCounter++;
          }
          
          // if the quotationCounter is at an even number, the String "splits" at the comma
          if (movieLine.charAt(currentIndex) == ',' && quotationCounter % 2 == 0) {
            // adds each new String (piece of Movie data) to an ArrayList
            movieData.add(movieLine.substring(startIndex + 1, currentIndex));
            // essentially "resets" 
            startIndex = currentIndex;
          }
          
        }
        
        // initializes each String from the ArrayList to its corresponding piece of Movie data
        title = movieData.get(1);
        year = Integer.parseInt(movieData.get(2));
        genre = movieData.get(3);
        duration = Integer.parseInt(movieData.get(4));
        country = movieData.get(5);
        
        // creates a new Movie object from the data that was just gathered from the makeshift
        // file and inserts it into our makeshift "tree" (ArrayList)
        Movie eachMovie = new Movie(title, year, genre, duration, country);
        this.movieTree.insertSingleKey(eachMovie);
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    return this.movieTree;
    
  }

@Override
  /**
   * Gets a list of movies with the minimum duration
   * Make sure edge cases cover duplicates of the same minimum time (use <=, add min to array)
   * @return minimum - ArrayList of Movie objects that run for the minimum duration
   */
  // with this format, i am assuming that an ordered rbt has already been made
  public ArrayList<Movie> getMinimumDuration(IterableMultiKeyRBT<Movie> movieTree) {
    
    // creates an empty ArrayList to hold each Movie object that has the minimum duration
    ArrayList<Movie> minimum = new ArrayList<Movie>();
    int minDuration = Integer.MAX_VALUE;
    // initializes an iterator that takes type Movie
    Iterator<Movie> iterator = movieTree.iterator();
    
    // if there is another Movie to iterate to...
    while (iterator.hasNext()) {
      int current = iterator.next().getDuration();
      //...and if the duration of the current Movie is less than the current minimum duration,
      // then the current Movie's duration is the new minimum duration
      if (current < minDuration) {
        minDuration = current;
      }

    }
    
    // "resets" iterator
    iterator = movieTree.iterator();
    
    // movieTree.numKeys()
    for(int i = 0; i < movieTree.size(); i++) {
      // adds to the minimum ArrayList the Movie objects which have the minimum duration
      Movie current = iterator.next();
      if (current.getDuration() == minDuration) {
        minimum.add(current);
      }
    }
    
    // returns the ArrayList that contains the Movies with the minimum duration
    return minimum;
  }

  /**
   * Gets a list of movies with a duration between two specific thresholds
   * Make sure edge cases cover movies that are exactly 120 min and 125 min (use >/<= not
   *     >/< in if statements that are adding correct-length movies to the array)
   * @param threshold1- minimun duration of movies
   * @param threshold2 - maximum duration of movies 
   * @return threshold - ArrayList of Movie objects that run within the designated duration
   *                     threshold
   */
  public ArrayList<Movie> getSpecifiedDuration (int threshold1, 
      int threshold2, IterableMultiKeyRBT<Movie> movieTree) {
    
    // creates an empty ArrayList to hold each Movie object that is within the duration threshold
    ArrayList<Movie> threshold = new ArrayList<Movie>();
    // initializes an iterator that takes type Movie
    Iterator<Movie> iterator = movieTree.iterator();
    
    // if there is another Movie to iterate to...
    while (iterator.hasNext()) {
      Movie current = iterator.next();
      //...and if the duration of the current Movie's duration is within the duration threshold,
      // then the current Movie's duration is added to the threshold ArrayList
      if (current.getDuration() >= threshold1 && 
          current.getDuration() <= threshold2) {
        threshold.add(current);
      }
    }
    
    // returns the ArrayList that contains the Movies within the duration threshold
    return threshold;
  }
  
  public void setFrontendInstance(Frontend frontend) {
    this.frontendInstance = frontend; 
  }

  public static void main(String[] args) {
    Frontend frontend = new Frontend(null);
    BackendDeveloperClass backend = new BackendDeveloperClass(frontend);
    frontend.setBackendInstance(backend);
    frontend.startMainLoop();
  }
}