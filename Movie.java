public class Movie implements AccessorInterface, Comparable<Movie> {

  private Movie key;
  private String title;
  private String genre;
  private int year;
  private String country;
  private int duration;

  public Movie(String title, int year, String genre, int duration, String country) {
    this.title = title;
    this.genre = genre;
    this.year = year;
    this.country = country;
    this.duration = duration;
  }

  // TODO i added, not overridden
  public Movie getKey() {
    return this.key;
  }

  @Override //TODO add for all?
  public String getTitle() {
    return this.title;
  }

  @Override
  public String getGenre() {
    return this.genre;
  }

  @Override
  public int getYear() {
    return this.year;
  }

  @Override
  public String getCountry() {
    return this.country;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }

  // TODO because theyre sorted by length right
  // TODO and this is the right format right
  public int compareTo(Movie o) {
    if (this.duration < o.duration) {
      return -1;
    } else if (this.duration > o.duration) {
      return 1;
    } else {
      return 0;
    }
  }
  
  // TODO need?
  @Override
  // TODO toString
  public String toString() {
    String string = this.key + " " + this.title + " " + this.genre + " " 
        + this.year + " " + this.country + " " + this.duration;
    
    return string;
  }

}