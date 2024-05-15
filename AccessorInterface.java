/**
 * This interface is made for a class that is designed to define a single movie and
 * expose movie properties required by the frontend: title, genre, year, country,
 * and duration.
 */

public interface AccessorInterface {

 // The class's constructor could look like this:
 //
 // private Movie key;
 // private String title;
 // private String genre;
 // private int year;
 // private String country;
 // private int duration;
 //
 // public ClassName(Movie key) {
 //     this.key = key;
 // }

 /**
  * Gets the title of the movie
  */
 public String getTitle();

 /**
  * Gets the genre of the movie
  */
 public String getGenre();

 /**
  * Gets the year of the movie's release
  */
 public int getYear();

 /**
  * Gets the movie's country
  */
 public String getCountry();

 /**
  * Gets the duration of the movie
  */
 public int getDuration();

}