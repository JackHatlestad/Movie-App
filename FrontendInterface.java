import java.util.Scanner;

public interface FrontendInterface {

       /**
         * Constructor for the FrontendInterface class.
         *
         * @param movie The reference to the back end object.
         * @param scanner The Scanner instance for user input.
         */
     //   public FrontendInterface(BackendInterface movie, Scanner scanner); 
    

    /**
     * Starts the main command loop for the user.
     * This method should display the main menu and handle user input.
     */
    public void startMainLoop();

    /**
     * Command to specify and load a data file.
     * This method should prompt the user for the file path and
     * request the backend to load the data from the file.
     */
    public boolean loadDataFile();

    /**
     * Command to list movies with the shortest duration in the data set.
     * This method should call the backend to retrieve and display
     * a list of movies with the shortest duration.
     */
    public void listShortestDurationMovies();

    /**
     * Command to list movies with a duration between two specified thresholds.
     * This method should prompt the user for the minimum and maximum duration,
     * and then request the backend to retrieve and display a list of movies
     * within the specified duration range.
     * @param minDuration the minimum threshold of movie duration  
     * @param maxDuration the maximum threshold of movie duration 
     */
    public void listMoviesByDurationRange(int minDuration, int maxDuration);

    /**
     * Command to exit the application.
     * This method should exit the application.
     */
    public boolean exitApp();

    /**
     * Displays instructive feedback for invalid user input.
     * This method should be called when the user enters invalid input
     * and provide feedback on what the valid input should be.
     */
    public void displayInvalidInputFeedback();


}