public class BSTNode {
    // Instance Variables.
    private BSTNode parent;
    private BSTNode leftChild;
    private BSTNode rightChild;
    private Data data;

    // Constructors.

    /**
     * Initializes a new leaf node.
     */
    public BSTNode() {
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
        this.data = null;
    }

    /**
     * Initializes a new internal node.
     * 
     * @param newParent     Parent of the node.
     * @param newLeftChild  Left child of the node.
     * @param newRightChild Right child of the node.
     * @param newData       data of the node.
     */
    public BSTNode(BSTNode newParent, BSTNode newLeftChild, BSTNode newRightChild, Data newData) {
        this.parent = newParent;
        this.leftChild = newLeftChild;
        this.rightChild = newRightChild;
        this.data = newData;
    }

    // Public Methods.

    /**
     * Returns the parent of this node.
     * 
     * @return a BSTNode object.
     */
    public BSTNode getParent() {
        return this.parent;
    }

    /**
     * Returns left child of this node.
     * 
     * @return a BSTNode object.
     */
    public BSTNode getLeftChild() {
        return this.leftChild;
    }

    /**
     * Returns the right child of this node.
     * 
     * @return a BSTNode object.
     */
    public BSTNode getRightChild() {
        return this.rightChild;
    }

    /**
     * Returns the data of this node.
     * 
     * @return a Data object.
     */
    public Data getData() {
        return this.data;
    }

    /**
     * Sets the parent of this node to the specified value.
     * 
     * @param newParent the BSTNode to replace the current parent.
     */
    public void setParent(BSTNode newParent) {
        this.parent = newParent;
    }

    /**
     * Sets the left child of this node to the specified value.
     * 
     * @param newLeftChild the BSTNode to replace the current left child.
     */
    public void setLeftChild(BSTNode newLeftChild) {
        this.leftChild = newLeftChild;
    }

    /**
     * Sets the right child of this node to the specified value.
     * 
     * @param newRightChild the BSTNode to replace the current right child.
     */
    public void setRightChild(BSTNode newRightChild) {
        this.rightChild = newRightChild;
    }

    /**
     * Sets the data of this node to the specified value.
     * 
     * @param newData the Data object to replace the current data.
     */
    public void setData(Data newData) {
        this.data = newData;
    }

    /**
     * Checks if the node is a leaf (No children and empty data).
     * 
     * @return true if the node is a leaf; false otherwise.
     */
    public boolean isLeaf() {
        return (this.data == null && this.leftChild == null && this.rightChild == null);
    }
}