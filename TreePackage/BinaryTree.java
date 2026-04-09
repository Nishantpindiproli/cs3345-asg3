package TreePackage;

import java.util.*;

public class BinaryTree<T> implements BinaryTreeInterface<T>
{
    private BinaryNode<T> root;

    public BinaryTree() {
        root = null;
    } // end default constructor

    public BinaryTree(T rootData) {
        root = new BinaryNode<T>(rootData);
    } // end constructor

    public BinaryTree(T rootData, BinaryTree<T> leftTree,
            BinaryTree<T> rightTree) {
        privateSetTree(rootData, leftTree, rightTree);
    } // end constructor

    public void setTree(T rootData) {
        root = new BinaryNode<T>(rootData);
    } // end setTree

    public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
            BinaryTreeInterface<T> rightTree) {
        privateSetTree(rootData, (BinaryTree<T>) leftTree, (BinaryTree<T>) rightTree);
    } // end setTree

    private void privateSetTree(T rootData,
            BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
        root = new BinaryNode<T>(rootData);

        if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.root);
            root.getLeftChild().setParent(root);
            root.getLeftChild().linkSubtreeThreadOut(root);
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            if (rightTree != leftTree) {
                root.setRightChild(rightTree.root);
            } else {
                root.setRightChild(rightTree.root.copy());
            }

            root.getRightChild().setParent(root);
            root.setThread(root.getRightChild().getLeftmostInSubtree());
        } else {
            root.setThread(null);
        }

        if ((leftTree != null) && (this != leftTree)) {
            leftTree.clear();
        }

        if ((rightTree != null) && (this != rightTree)) {
            rightTree.clear();
        }

    } // end privateSetTree

    public T getRootData() {
        if (isEmpty()) {
            throw new EmptyTreeException("Empty tree for operation getRootData");
        } else {
            return root.getData();
        }
    } // end getRootData

    public boolean isEmpty() {
        return root == null;
    } // end isEmpty

    public void clear() {
        root = null;
    } // end clear

    protected void setRootData(T rootData) {
        root.setData(rootData);
    } // end setRootData

    protected void setRootNode(BinaryNode<T> rootNode) {
        root = rootNode;
        if (root != null) {
            root.setParent(null);
        }
    } // end setRootNode

    protected BinaryNode<T> getRootNode() {
        return root;
    } // end getRootNode

    public int getHeight() {
        if (root == null) {
            return 0;
        } else {
            return root.getHeight();
        }
    } // end getHeight

    public int getNumberOfNodes() {
        if (root == null) {
            return 0;
        } else {
            return root.getNumberOfNodes();
        }
    } // end getNumberOfNodes

    public void inorderTraverse() {
        inorderTraverse(root);
    } // end inorderTraverse

    private void inorderTraverse(BinaryNode<T> node) {
        if (node != null) {
            inorderTraverse(node.getLeftChild());
            System.out.println(node.getData());
            inorderTraverse(node.getRightChild());
        }
    } // end inorderTraverse

    private class InorderIterator implements Iterator<T> {

        private BinaryNode<T> currentNode;

        public InorderIterator() {
            currentNode = root;
            if (currentNode != null) {
                moveToFirstInorderNode();
            }
        }

        private void moveToFirstInorderNode() {
            while (currentNode.getLeftChild() != null) {
                currentNode = currentNode.getLeftChild();
            }
        }

        public boolean hasNext() {
            return currentNode != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            BinaryNode<T> nextNode = currentNode;
            T result = nextNode.getData();

            if (currentNode.hasThread()) {
                currentNode = currentNode.getThread();
            } else if (currentNode.getRightChild() != null) {
                currentNode = currentNode.getRightChild();
                while (currentNode.getLeftChild() != null) {
                    currentNode = currentNode.getLeftChild();
                }
            } else {
                currentNode = null;
            }

            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // end InorderIterator

    public Iterator<T> getInorderIterator() {
        return new InorderIterator();
    }

    public Iterator<T> getPreorderIterator() {
        throw new RuntimeException("Pre order iterators not yet supported by this class");
    }

    public Iterator<T> getPostorderIterator() {
        throw new RuntimeException("Post order iterators not yet supported by this class");
    }

    public Iterator<T> getLevelOrderIterator() {
        throw new RuntimeException("Level order iterators not yet supported by this class");
    }

    public BinaryNode<T> getRoot() {
        return root;
    }
}