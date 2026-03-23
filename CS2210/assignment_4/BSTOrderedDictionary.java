import java.util.ArrayList;

public class BSTOrderedDictionary implements BSTOrderedDictionaryADT {
    // Instance Variables.
    private BSTNode root;
    private int numInternalNodes;

    // Constructor.

    /**
     * Initializes a new BSTOrderedDictionary object.
     */
    public BSTOrderedDictionary() {
        this.root = new BSTNode();
        this.numInternalNodes = 0;
    }

    // Public Methods.

    /**
     * Returns the root of this tree.
     * 
     * @return the root of the tree.
     */
    public BSTNode getRoot() {
        return this.root;
    }

    /**
     * Returns the number of internal nodes in this tree.
     * 
     * @return the number of internal nodes in the tree.
     */
    public int getNumInternalNodes() {
        return this.numInternalNodes;
    }

    /**
     * Finds in the tree with root r the BSTNode object with the given key
     * attribute and returns the list of MultimediaItem objects.
     * Returns null if no node found in the tree.
     * 
     * @param r   a BSTNode object.
     * @param key the name of the data.
     * 
     * @return a list of MultimediaItem objects if in tree; otherwise null.
     */
    public ArrayList<MultimediaItem> get(BSTNode r, String key) {
        if (r.isLeaf()) {
            return null;
        }

        int comparison = key.compareTo(r.getData().getName());

        if (comparison == 0) {
            return r.getData().getMedia();
        } else if (comparison < 0) {
            return get(r.getLeftChild(), key);
        } else {
            return get(r.getRightChild(), key);
        }
    }

    /**
     * Creates a new BSTNode object and allocates it in its proper location.
     * If the node already exists with the given key, the content is stored inside
     * the node.
     * 
     * @param r       a BSTNode object.
     * @param key     the name of the data.
     * @param content the name of the content.
     * @param type    the type of the content.
     */
    public void put(BSTNode r, String key, String content, int type) {
        BSTNode node = findNode(r, key);

        if (!node.isLeaf()) {
            // Node with this key already exists.
            MultimediaItem item = new MultimediaItem(content, type);
            node.getData().add(item);
        } else {
            // Node is a leaf, need to create new internal node.
            Data newData = new Data(key);
            MultimediaItem item = new MultimediaItem(content, type);
            newData.add(item);

            node.setData(newData);
            node.setLeftChild(new BSTNode());
            node.getLeftChild().setParent(node);
            node.setRightChild(new BSTNode());
            node.getRightChild().setParent(node);

            numInternalNodes++;
        }
    }

    /**
     * Removes a BSTNode in the tree with the same key.
     * 
     * @param r   a BSTNode object.
     * @param key the name of the data.
     * 
     * @throws DictionaryException If the BSTNode object r is a leaf node.
     */
    public void remove(BSTNode r, String key) throws DictionaryException {
        BSTNode node = findNode(r, key);

        if (node.isLeaf()) {
            throw new DictionaryException("Key not found");
        }

        // Removes the node.
        removeNode(node);
        numInternalNodes--;
    }

    /**
     * Removes the MultimediaItem objects inside the list
     * 
     * @param r    a BSTNode object.
     * @param key  the name of the data.
     * @param type the type of content.
     * 
     * @throws DictionaryException If the BSTNode object r is a leaf node.
     */
    public void remove(BSTNode r, String key, int type) throws DictionaryException {
        BSTNode node = findNode(r, key);

        if (node.isLeaf()) {
            throw new DictionaryException("Key not found");
        }

        node.getData().getMedia().removeIf(item -> item.getType() == type);

        if (node.getData().getMedia().isEmpty()) {
            // Removes the node.
            removeNode(node);
            numInternalNodes--;
        }
    }

    /**
     * Returns the smallest node in the tree with the given root.
     * 
     * @param r a BSTNode object.
     * 
     * @return a BSTNode object with the smallest key value from the root. Null if
     *         leaf node.
     */
    public Data smallest(BSTNode r) {
        if (r.isLeaf()) {
            return null;
        }

        BSTNode current = r;
        while (!current.getLeftChild().isLeaf()) {
            current = current.getLeftChild();
        }

        return current.getData();
    }

    /**
     * Returns the largest node in the tree with the given root.
     * 
     * @param r a BSTNode object.
     * 
     * @return a BSTNode object with the largest key value from the root. Null if
     *         leaf node.
     */
    public Data largest(BSTNode r) {
        if (r.isLeaf()) {
            return null;
        }

        BSTNode current = r;
        while (!current.getRightChild().isLeaf()) {
            current = current.getRightChild();
        }

        return current.getData();
    }

    /**
     * Returns the successor of the given BSTNode object.
     * 
     * @param r a BSTNode object.
     * 
     * @return the successor node. Null if leaf node.
     */
    public Data successor(BSTNode r, String key) {
        BSTNode node = findNode(r, key);

        if (node.isLeaf()) {
            return null;
        }

        // If right subtree exists, successor is smallest in right subtree
        if (!node.getRightChild().isLeaf()) {
            return smallest(node.getRightChild());
        }

        // Otherwise, go up the tree
        BSTNode current = node;
        BSTNode parent = current.getParent();

        while (parent != null && current == parent.getRightChild()) {
            current = parent;
            parent = current.getParent();
        }

        if (parent == null) {
            return null;
        }

        return parent.getData();
    }

    /**
     * Returns the predecessor of the given BSTNode object.
     * 
     * @param r a BSTNode object.
     * 
     * @return the predecessor node. Null if leaf node.
     */
    public Data predecessor(BSTNode r, String key) {
        BSTNode node = findNode(r, key);

        if (node.isLeaf()) {
            return null;
        }

        // If left subtree exists, predecessor is largest in left subtree
        if (!node.getLeftChild().isLeaf()) {
            return largest(node.getLeftChild());
        }

        // Otherwise, go up the tree
        BSTNode current = node;
        BSTNode parent = current.getParent();

        while (parent != null && current == parent.getLeftChild()) {
            current = parent;
            parent = current.getParent();
        }

        if (parent == null) {
            return null;
        }

        return parent.getData();
    }

    // Private Methods.

    /**
     * Recursive helper method that searches the tree for a BSTNode object with
     * similar keys.
     * 
     * @param r   a BSTNode object.
     * @param key a key value for the node.
     * @return the matching BSTNode object if in tree; otherwise the first leaf
     *         node.
     */
    private BSTNode findNode(BSTNode r, String key) {
        if (r.isLeaf()) {
            return r;
        }

        int comparison = key.compareTo(r.getData().getName());

        if (comparison == 0) {
            return r;
        } else if (comparison < 0) {
            return findNode(r.getLeftChild(), key);
        } else {
            return findNode(r.getRightChild(), key);
        }
    }

    /**
     * Recursive helper method that removes the given BSTNode object from the tree.
     * 
     * @param node a BSTNode object
     */
    private void removeNode(BSTNode node) {
        BSTNode parent = node.getParent();

        // Case 1: Node has two leaf children
        if (node.getLeftChild().isLeaf() && node.getRightChild().isLeaf()) {
            if (parent == null) {
                // Node is root
                root = new BSTNode();
            } else {
                BSTNode leaf = new BSTNode();
                leaf.setParent(parent);

                if (parent.getLeftChild() == node) {
                    parent.setLeftChild(leaf);
                } else {
                    parent.setRightChild(leaf);
                }
            }
        }
        // Case 2: Node has one non-leaf child
        else if (node.getLeftChild().isLeaf() || node.getRightChild().isLeaf()) {
            BSTNode child = node.getLeftChild().isLeaf() ? node.getRightChild() : node.getLeftChild();

            if (parent == null) {
                // Node is root
                root = child;
                child.setParent(null);
            } else {
                child.setParent(parent);

                if (parent.getLeftChild() == node) {
                    parent.setLeftChild(child);
                } else {
                    parent.setRightChild(child);
                }
            }
        }
        // Case 3: Node has two non-leaf children
        else {
            BSTNode successor = findNode(root, smallest(node.getRightChild()).getName());
            node.setData(successor.getData());
            removeNode(successor);
            numInternalNodes++; // Compensate because we didn't actually remove the original node
        }
    }
}