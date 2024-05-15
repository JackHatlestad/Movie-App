

/**
*This class mirrors a Red Black Tree 
*/
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {
protected static class RBTNode<T> extends Node<T> {
    public int blackHeight = 0;
    public RBTNode(T data) { super(data); }
    public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
    public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
    public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
}
/**
 * Enforces the Red-Black Tree properties after inserting a new node.
 * This method ensures that the Red-Black Tree properties are maintained
 * after inserting a new node into the tree.
 *
 * @param newNode The newly inserted node that may have caused property violations.
 */
protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newNode) {
    // Initialize the current node to the newly inserted node.
    RBTNode<T> currentNode = newNode;

    // while loop that continues method call when RBT isn't correct
    while (currentNode != null && currentNode != root && currentNode.getUp().blackHeight == 0) {
        // creates parent and grandparent nodes that I can use 
        RBTNode<T> parent = currentNode.getUp();
        RBTNode<T> grandparent = parent.getUp();

        // creates uncle node that I can use 
        RBTNode<T> uncle = (parent == grandparent.getDownLeft()) ? grandparent.getDownRight()
            : grandparent.getDownLeft();

        if (uncle != null && uncle.blackHeight == 0) {
            // Case 1: Uncle is red.
            // Adjust node colors and continue to check higher levels.
            parent.blackHeight = 1;
            uncle.blackHeight = 1;
            grandparent.blackHeight = 0;
            currentNode = grandparent;
        } else {
            // Cases 2, 3, 4: Uncle is black or null (null nodes are considered black).

            if (currentNode == parent.getDownRight() && parent == grandparent.getDownLeft()) {
                // Case 2: Left-Right (LR) rotation needed.
                rotate(currentNode, parent);
                currentNode = parent;
                parent = currentNode.getUp();
            } else if (currentNode == parent.getDownLeft()
                && parent == grandparent.getDownRight()) {
                // Case 3: Right-Left (RL) rotation needed.
                rotate(currentNode, parent);
                currentNode = parent;
                parent = currentNode.getUp();
            }

            // Case 4: Left-Left or Right-Right  rotation needed.
            parent.blackHeight = 1;
            grandparent.blackHeight = 0;

            if (currentNode == parent.getDownLeft()) {
                // Perform a right rotation.
                rotate(parent, grandparent);
            } else {
                // Perform a left rotation.
                rotate(parent, grandparent);
            }
        }
    }

    // Ensure that the root is black.
    if (root instanceof RBTNode) {
        ((RBTNode<T>) root).blackHeight = 1;
    }
}

/**
*Insert node into the RBT
* @param data data type
* @return inserted node 
*/
public boolean insert(T data) throws NullPointerException {
if (data == null)
            throw new NullPointerException();
        
        RBTNode<T> newNode = new RBTNode<>(data);

        boolean inserted = super.insertHelper(newNode);

        if (inserted) {
            enforceRBTreePropertiesAfterInsert(newNode);
            if (root instanceof RBTNode) {
                ((RBTNode<T>) root).blackHeight = 1;
            }
        }

        return inserted;




}


}