import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class runs the Frontend for the Movie App.
 * 
 * @author jackh
 *
 */
public class Frontend implements FrontendInterface {
    // fields
    BackendInterface backend;
    private Scanner input;
    IterableMultiKeyRBT<Movie> treeFromFile;
    Scanner inputScanner;

    /**
     * constructor for Frontend class
     * @param backend 
     */
    public Frontend(BackendInterface backend) {
        this.backend = backend;
        input = new Scanner(System.in);
    }

    @Override
    /**
     * This method runs the entire app and uses a do while loop and only
     * stops when the exitmethod is called. 
     */
    public void startMainLoop() {
        System.out.println("Welcome to the Movie App!");
        System.out.println("-------------------------");
        inputScanner = new Scanner(System.in);
        boolean dataLoaded = false;
        boolean continueRunning = true;

        do {
            System.out.println("Type:");
            if (!dataLoaded) {
                System.out.println("L to load in the data file");
            }

            if (dataLoaded) {
                System.out.println("S to list movies with the shortest duration");
                System.out.println("M to list movies by duration range");
            }
            System.out.println("E to exit the app");

            String input = "";
            if (inputScanner.hasNextLine()) {
                input = inputScanner.nextLine().toLowerCase();
            }

            switch (input) {
                case "l":
                    dataLoaded = loadDataFile();

                    break;
                case "s":
                    if (dataLoaded) {
                        listShortestDurationMovies();
                    } else {
                        System.out.println("Please load the data file before using this option.");
                    }
                    break;
                case "m":
                    if (dataLoaded) {
                        int minDuration;
                        int maxDuration;

                        while (true) {
                            try {
                                System.out.print("Enter minimum duration in minutes: ");
                                minDuration = Integer.parseInt(inputScanner.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                displayInvalidInputFeedback();
                            }
                        }

                        while (true) {
                            try {
                                System.out.print("Enter maximum duration in minutes: ");
                                maxDuration = Integer.parseInt(inputScanner.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                displayInvalidInputFeedback();
                            }
                        }

                        listMoviesByDurationRange(minDuration, maxDuration);
                    } else {
                        System.out.println(
                            "Please load the data file (Option 1) before using this option.");
                    }
                    break;
                case "e":
                    continueRunning = exitApp();
                    break;
                default:
                    displayInvalidInputFeedback();
            }
        } while (continueRunning);

        inputScanner.close();
    }

    @Override
    /**
     * This method prompts the user for a data file and the calls the backend
     * to see if they match and if they do match then it loads the csv file
     * into the app. 
     */
    public boolean loadDataFile() {

        System.out.print("Enter the path to the data file: ");
        String filePath = inputScanner.nextLine();
        File dataFile = new File(filePath);

        try {

            treeFromFile = backend.readsDataFromFile(dataFile);
            System.out.println("Data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check the file path.");
            return false;
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            return false;
        }
        return true;
    }


    @Override
    /**
     * This method calls the backend to display the movies by shortest duration.
     */
    public void listShortestDurationMovies() {

        ArrayList<Movie> shortestDurationMovies = backend.getMinimumDuration(treeFromFile);

        if (shortestDurationMovies.isEmpty()) {
            System.out.println("No movies with the minimum duration found.");
        } else {
            System.out.println("Movies with the shortest duration:");
            for (Movie movie : shortestDurationMovies) {
                System.out.println(movie.getTitle() + " - " + movie.getDuration() + " minutes");
            }
        }
    }

    @Override
    /**
     * This method calls the backend to display the movies in a specific
     * duration range. 
     */
    public void listMoviesByDurationRange(int minDuration, int maxDuration) {
        ArrayList<Movie> moviesInDurationRange =

            backend.getSpecifiedDuration(minDuration, maxDuration, treeFromFile);

        if (moviesInDurationRange.isEmpty()) {
            System.out.println("No movies found within the specified duration range.");
        } else {
            System.out.println("Movies within the specified duration range:");
            for (Movie movie : moviesInDurationRange) {
                System.out.println(movie.getTitle() + " - " + movie.getDuration() + " minutes");
            }
        }
    }

    @Override
    /**
     * When called this method exits the app. 
     */
    public boolean exitApp() {
        System.out.println("Successfully Exited App");
        return false;
    }

    @Override
    /**
     * When the user enters an invalid input then this method is called
     */
    public void displayInvalidInputFeedback() {
        System.out.println("Invalid Input, please follow the directions");
    }

    /**
     * This method sets the backend instance. 
     * @param backend
     */
    public void setBackendInstance(BackendInterface backend) {
        this.backend = backend;
    }

    /**
     * This method runs the entire app. 
     * @param args
     */
    public static void main(String[] args) {
        BackendDeveloperClass backend = new BackendDeveloperClass(null);
        Frontend frontend = new Frontend(backend);
        backend.setFrontendInstance(frontend);
        frontend.startMainLoop();
    }
}