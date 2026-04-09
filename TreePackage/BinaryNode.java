package TreePackage;

/**
 * An implementation of the ADT Binary Node.
 */
class BinaryNode<T> {

    private T data;
    private BinaryNode<T> leftChild;
    private BinaryNode<T> rightChild;
    private BinaryNode<T> parent;
    private BinaryNode<T> thread;

    public BinaryNode() {
        this(null);
    } // end default constructor

    public BinaryNode(T dataPortion) {
        this(dataPortion, null, null);
    } // end constructor

    public BinaryNode(T dataPortion, BinaryNode<T> newLeftChild,
            BinaryNode<T> newRightChild) {
        this(dataPortion, newLeftChild, newRightChild, null);
    } // end constructor

    public BinaryNode(T dataPortion, BinaryNode<T> newLeftChild,
            BinaryNode<T> newRightChild, BinaryNode<T> newParent) {
        this(dataPortion, newLeftChild, newRightChild, newParent, null);
    } // end constructor

    public BinaryNode(T dataPortion, BinaryNode<T> newLeftChild,
            BinaryNode<T> newRightChild, BinaryNode<T> newParent,
            BinaryNode<T> newThread) {
        data = dataPortion;
        leftChild = newLeftChild;
        rightChild = newRightChild;
        parent = newParent;
        thread = newThread;
    } // end constructor

    /**
     * Retrieves the data portion of this node.
     *
     * @return The object in the data portion of the node.
     */
    public T getData() {
        return data;
    } // end getData

    /**
     * Sets the data portion of this node.
     *
     * @param newData The data object.
     */
    public void setData(T newData) {
        data = newData;
    } // end setData

    /**
     * Retrieves the left child of this node.
     *
     * @return The node that is this node's left child.
     */
    public BinaryNode<T> getLeftChild() {
        return leftChild;
    } // end getLeftChild

    /**
     * Sets this node's left child to a given node.
     *
     * @param newLeftChild A node that will be the left child.
     */
    public void setLeftChild(BinaryNode<T> newLeftChild) {
        leftChild = newLeftChild;
    } // end setLeftChild

    /**
     * Detects whether this node has a left child.
     *
     * @return True if the node has a left child.
     */
    public boolean hasLeftChild() {
        return leftChild != null;
    } // end hasLeftChild

    /**
     * Retrieves the right child of this node.
     *
     * @return The node that is this node's right child.
     */
    public BinaryNode<T> getRightChild() {
        return rightChild;
    } // end getRightChild

    /**
     * Sets this nodes's right child to a given node.
     *
     * @param newRightChild A node that will be the right child.
     */
    public void setRightChild(BinaryNode<T> newRightChild) {
        rightChild = newRightChild;
    } // end setRightChild

    /**
     * Detects whether this node has a right child.
     *
     * @return True if the node has a right child.
     */
    public boolean hasRightChild() {
        return rightChild != null;
    } // end hasRightChild

    /**
     * Retrieves this node's parent.
     *
     * @return The parent node.
     */
    public BinaryNode<T> getParent() {
        return parent;
    }

    /**
     * Sets this node's parent.
     *
     * @param p The parent node.
     */
    public void setParent(BinaryNode<T> p) {
        parent = p;
    }

    /**
     * Detects whether this node has a parent.
     *
     * @return True if the node has a parent.
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Retrieves this node's thread.
     *
     * @return The threaded successor.
     */
    public BinaryNode<T> getThread() {
        return thread;
    }

    /**
     * Sets this node's thread.
     *
     * @param target The node this thread should point to.
     */
    public void setThread(BinaryNode<T> target) {
        thread = target;
    }

    /**
     * Detects whether this node has a thread.
     *
     * @return True if the node has a thread.
     */
    public boolean hasThread() {
        return thread != null;
    }

    /**
     * Detects whether this node is a leaf.
     *
     * @return True if the node is a leaf.
     */
    public boolean isLeaf() {
        return (leftChild == null) && (rightChild == null);
    } // end isLeaf

    /**
     * Computes the height of the subtree rooted at this node.
     *
     * @return The height of the subtree rooted at this node.
     */
    public int getHeight() {
        return getHeight(this);
    } // end getHeight

    private int getHeight(BinaryNode<T> node) {
        int height = 0;
        if (node != null) {
            height = 1 + Math.max(getHeight(node.getLeftChild()),
                    getHeight(node.getRightChild()));
        }
        return height;
    } // end getHeight

    /**
     * Counts the nodes in the subtree rooted at this node.
     *
     * @return The number of nodes in the subtree rooted at this node.
     */
    public int getNumberOfNodes() {
        int leftNumber = 0;
        int rightNumber = 0;

        if (leftChild != null) {
            leftNumber = leftChild.getNumberOfNodes();
        }

        if (rightChild != null) {
            rightNumber = rightChild.getNumberOfNodes();
        }

        return 1 + leftNumber + rightNumber;
    } // end getNumberOfNodes

    /**
     * Copies the subtree rooted at this node.
     *
     * @return The root of a copy of the subtree rooted at this node.
     */
    public BinaryNode<T> copy() {
        BinaryNode<T> newRoot = new BinaryNode<T>(data, null, null, null, null);

        if (leftChild != null) {
            newRoot.setLeftChild(leftChild.copy(newRoot));
        }
        if (rightChild != null) {
            newRoot.setRightChild(rightChild.copy(newRoot));
        }

        if (newRoot.getLeftChild() != null) {
            newRoot.getLeftChild().linkSubtreeThreadOut(newRoot);
        }

        if (newRoot.getRightChild() != null) {
            newRoot.setThread(newRoot.getRightChild().getLeftmostInSubtree());
        } else {
            newRoot.setThread(null);
        }

        return newRoot;
    } // end copy

    /**
     * Copies the subtree rooted at this node and sets the parent of the copied root.
     *
     * @param p The parent of the copied root.
     * @return The root of the copied subtree.
     */
    public BinaryNode<T> copy(BinaryNode<T> p) {
        BinaryNode<T> newRoot = new BinaryNode<T>(data, null, null, p, null);

        if (leftChild != null) {
            newRoot.setLeftChild(leftChild.copy(newRoot));
        }
        if (rightChild != null) {
            newRoot.setRightChild(rightChild.copy(newRoot));
        }

        if (newRoot.getLeftChild() != null) {
            newRoot.getLeftChild().linkSubtreeThreadOut(newRoot);
        }

        if (newRoot.getRightChild() != null) {
            newRoot.setThread(newRoot.getRightChild().getLeftmostInSubtree());
        } else {
            newRoot.setThread(null);
        }

        return newRoot;
    }

    /**
     * Links the thread that comes out of this subtree to the given node.
     *
     * @param linkTo The node that should follow the rightmost node in this subtree.
     */
    public void linkSubtreeThreadOut(BinaryNode<T> linkTo) {
        if (rightChild != null) {
            rightChild.linkSubtreeThreadOut(linkTo);
        } else {
            thread = linkTo;
        }
    }

    /**
     * Finds the leftmost node in this subtree.
     *
     * @return The leftmost node in the subtree.
     */
    public BinaryNode<T> getLeftmostInSubtree() {
        if (leftChild != null) {
            return leftChild.getLeftmostInSubtree();
        } else {
            return this;
        }
    }

} // end BinaryNode