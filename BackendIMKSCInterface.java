import java.util.ArrayList;
import java.util.Iterator;

// TODO functioning as arraylist right now, not a tree
public class BackendIMKSCInterface<T extends Comparable<Movie>> implements 
    IterableMultiKeySortedCollectionInterface<Movie> {

  private int insertCount = 0;
  private ArrayList<Movie> moviesList;
  
  // this constructor takes in an ArrayList right now so that the iteraotr method can run even
  // though this class does not yet create a tree to insert our Movie objects into
  public BackendIMKSCInterface(ArrayList<Movie> moviesList) {
    this.moviesList = moviesList;
  }
  
  @Override
  public boolean insert(KeyListInterface<Movie> data)
      throws NullPointerException, IllegalArgumentException {
    // TODO Auto-generated method stub
    return false;
  }

  // TODO probably different in trees, can keep for ArrayList testing though
  @Override
  public int size() {
    return this.numKeys();
  }

  @Override
  public boolean isEmpty() {
    
    if(this.insertCount == 0) {
      return true;
    }
    
    return false;
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean contains(Comparable<KeyListInterface<Movie>> data) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean insertSingleKey(Movie key) {
    // TODO Auto-generated method stub
    
    // insert into the placeholder ArrayList
    moviesList.add(key);
    
    insertCount++;
    
    return true;
  }

  // TODO number of movie objects, or number of nodes? (right now, the former)
  @Override
  public int numKeys() {
    return this.insertCount;
  }

  @Override
  public Iterator<Movie> iterator() {
    // uses the placeholder ArrayList moviesList to instatiate an iterator of type Movie
    Iterator<Movie> iterator = moviesList.iterator();
    
    return iterator;
  }

  @Override
  public void setIterationStartPoint(Comparable<Movie> startPoint) {
    // TODO Auto-generated method stub
    
  }
  
}