import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>>
    implements IterableMultiKeySortedCollectionInterface<T> {
    private Comparable<T> iterationStartPoint; // Field to store the iteration start point
    private int numberOfKeys; // Field to store the number of keys

    /**
     * Inserts a value into the tree that can store multiple objects per key by keeping
     * lists of objects in each node of the tree.
     * @param key object to insert
     * @return true if a new node was inserted, false if the key was added into an existing node
     */
    public boolean insertSingleKey(T key) {
        // Create a KeyList
        KeyList<T> KeyList = new KeyList<>(key);
        // Find the node that might contain duplicates using findNode method
        // of the KeyList class
        Node<KeyListInterface<T>> duplicateNode = findNode(KeyList);
        // always increment the number of keys when method is called
        numberOfKeys++;

        if (duplicateNode != null) {
            // If a node with the duplicate exists, then add the new key to
            // the KeyList in the node
            KeyListInterface<T> keyListInNode = duplicateNode.data;
            keyListInNode.addKey(key);
            return false;
        } else {
            // If no node with duplicates found, then create a new node and
            // insert the KeyList
            insert(KeyList);
            return true;
        }
    }

    /**
     * @return the number of values in the tree.
     */
    public int numKeys() {
        return numberOfKeys;
    }


    /**
     * Returns a stack containing nodes to start the iterator from.
     * Helper method for the Iterator method 
     * @return Stack containing nodes based on the iteration start point
     */
    protected Stack<Node<KeyListInterface<T>>> getStartStack() {
        // create new stack
        Stack<Node<KeyListInterface<T>>> stack = new Stack<>();

        if (iterationStartPoint == null) {
            // if there is no starting point, then start from the leftmost node
            // and push nodes onto the stack.
            Node<KeyListInterface<T>> currentNode = root;
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.down[0];
            }
        } else {
            // If a start point is set, find the node with the closest or equal
            // key and push nodes accordingly.
            Node<KeyListInterface<T>> currentNode = root;
            while (currentNode != null) {
                int compare = iterationStartPoint
                    .compareTo(((KeyListInterface<T>) 
                        currentNode.data).iterator().next());
                if (compare <= 0) {
                    // If the current node's key is less than or equal to the
                    // start point, push it and move left.
                    stack.push(currentNode);
                    currentNode = currentNode.down[0];
                } else {
                    // if not, move it right
                    currentNode = currentNode.down[1];
                }
            }
        }
        return stack;
    }

    /**
     * Returns an iterator that does an in-order iteration over the tree.
     *
     * @return Iterator for in-order iteration
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Stack<Node<KeyListInterface<T>>> stack = getStartStack();
            private Iterator<T> keyIterator = null;


            // This method checks if there are more elements in the iteration.
            public boolean hasNext() {
                return !stack.isEmpty() || (keyIterator != null && keyIterator.hasNext());
            }

            // This method checks if there is a next element and then returns
            // that element
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                while (keyIterator == null || !keyIterator.hasNext()) {
                    // Iterate through nodes until a key with more values is
                    // found.
                    if (!stack.isEmpty()) {
                        Node<KeyListInterface<T>> current = stack.pop();
                        keyIterator = current.data.iterator();


                        if (current.down[1] != null) {
                            // If there is a right child, push it onto the
                            // stack.
                            stack.push(current.down[1]);
                        }
                    }
                }

                return keyIterator.next();
            }
        };
    }



    /**
     * Sets the starting point for iterations. Future iterations will start at the
     * starting point or the key closest to it in the tree. This setting is remembered
     * until it is reset. Passing in null disables the starting point.
     * @param startPoint the start point to set for iterations
     */
    public void setIterationStartPoint(Comparable<T> startPoint) {
        this.iterationStartPoint = startPoint;
    }

    /**
     * sets the number of keys to zero
     */
    @Override
    public void clear() {
        super.clear();
        numberOfKeys = 0;
    }



    /**
     * Inserts three keys into an empty tree, then checks if an iterator for the tree
     * returns these three keys in the expected order. Also checks if both numKeys()
     * and size() return 3 for the tree.
     */

}