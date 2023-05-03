package com.studybuddy;

import java.util.ArrayList;
import java.util.List;

public class RBTree {
    public Node root;

    public RBTree() {
        // Initialise empty RBTree
        this.root = null;
    }

    /**
     * Insert a new user into the tree
     *
     * @param user the new user to be inserted
     */
    public void insert(User user) {
        Node newNode = new Node(user);
        // new node is always red
        newNode.setColour(true);
        Node root = this.root;
        Node parentNode = null;

        while (root != null && root.user != null) {
            parentNode = root;

            if (compareNodes(newNode.getUser(), root.getUser()) < 0) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        if (parentNode != null) {
            newNode.parent = parentNode;
        }

        // empty tree
        if (parentNode == null) {
            this.root = newNode;
        } else if (compareNodes(newNode.getUser(), parentNode.getUser()) < 0) {
            parentNode.left = newNode;
        } else {
            parentNode.right = newNode;
        }
        // fix tree
        reconstructTree(newNode);
        // after reconstruction once, if the grandparent of the inserted node and its parent are both red,
        // we need to reconstruct the tree again by considering the grandparent as the inserted node
        if (newNode.parent != null && newNode.parent.parent != null && newNode.parent.parent.parent != null &&
                newNode.parent.parent.isRed && newNode.parent.parent.parent.isRed) {
            reconstructTree(newNode.parent.parent);
        }
    }

    /**
     * Reconstruct the tree after insertion so that it satisfies the properties of a red-black tree
     *
     * @param node the inserted node
     */
    private void reconstructTree(Node node) {
        if (node.parent != null && node.parent.isRed) {
            // if node's parent is the left child of grandparent
            if (node.parent == node.parent.parent.left) {
                // uncle is the right child of grandparent
                Node uncle = node.parent.parent.right;
                // if uncle is red
                if (uncle.isRed) {
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                }
                // if uncle is black
                else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rightRotate(node.parent.parent);
                }
            }
            // if node's parent is the right child of grandparent
            else {
                Node uncle = node.parent.parent.left;

                if (uncle.isRed) {
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    leftRotate(node.parent.parent);
                }
            }
        }
        this.root.isRed = false;
    }

    public void leftRotate(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;

        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;

        // if node is root
        if (node.parent == null) {
            this.root = rightChild;
        }
        // if node is left child
        else if (node == node.parent.left) {
            node.parent.left = rightChild;
        }
        // if node is right child
        else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    public void rightRotate(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;

        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;

        if (node.parent == null) {
            this.root = leftChild;
        } else if (node == node.parent.left) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }

    /**
     * Search for users by name
     *
     * @param name the user's name
     * @return a list of users with the same name
     */
    public List<Node> searchByName(String name) {
        List<Node> results = new ArrayList<>();
        Node result = findFirstOccurrence(this.root, name);
        // since users with same name are inserted to the right of the first occurrence
        // we can only traverse right children
        while (result != null && result.getUser() != null && result.getUser().getName().equals(name)) {
            results.add(result);
            result = result.right;
        }
        return results;
    }

    /**
     * Search for a user by name
     *
     * @param root the root of the tree
     * @param name the user's name
     * @return the first user with the given name
     */
    private Node findFirstOccurrence(Node root, String name) {
        Node currentNode = root;
        Node result = null;

        while (currentNode != null && currentNode.user != null) {
            int comparison = compareNames(name, currentNode.getUser().getName());

            if (comparison == 0) {
                return currentNode;
            } else if (comparison < 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
        return result;
    }

    private int compareNodes(User a, User b) {
        // remove the space in names and convert to lowercase
        String aName = a.getName().replace(" ", "").toLowerCase();
        String bName = b.getName().replace(" ", "").toLowerCase();
        return aName.compareTo(bName);
    }

    private int compareNames(String a, String b) {
        // remove the space in names and convert to lowercase
        String aName = a.replace(" ", "").toLowerCase();
        String bName = b.replace(" ", "").toLowerCase();
        return aName.compareTo(bName);
    }


    // in-order traversal
    public List<Node> inOrderTraverse() {
        return inOrderTraverseHelper(this.root);
    }

    // don't record the leaf nodes
    private List<Node> inOrderTraverseHelper(Node node) {
        List<Node> nodes = new ArrayList<>();
        if (node.left != null && node.left.user != null) {
            nodes.addAll(inOrderTraverseHelper(node.left));
        }
        if (node.user != null) {
            nodes.add(node);
        }
        if (node.right != null && node.right.user != null) {
            nodes.addAll(inOrderTraverseHelper(node.right));
        }
        return nodes;
    }

    // post-order traversal
    public List<Node> postOrderTraverse() {
        return postOrderTraverseHelper(this.root);
    }

    // don't record the leaf nodes
    private List<Node> postOrderTraverseHelper(Node node) {
        List<Node> nodes = new ArrayList<>();
        if (node.left != null && node.left.user != null) {
            nodes.addAll(postOrderTraverseHelper(node.left));
        }
        if (node.right != null && node.right.user != null) {
            nodes.addAll(postOrderTraverseHelper(node.right));
        }
        if (node.user != null) {
            nodes.add(node);
        }
        return nodes;
    }

    public static class Node {

        boolean isRed; // Node colour
        private User user; // Node value
        Node parent; // Parent node
        Node left, right; // Children nodes

        public Node(User user) {
            this.user = user;
            this.isRed = false;
            this.parent = null;

            // Initialise children leaf nodes
            this.left = new Node();
            this.right = new Node();
            this.left.parent = this;
            this.right.parent = this;
        }

        // Leaf node
        public Node() {
            this.user = null;
            this.isRed = false;
        }

        public void setColour(boolean isRed) {
            this.isRed = isRed;
        }

        public User getUser() {
            return this.user;
        }

        public String toString() {
            if (this.user != null) {
                if (isRed) {
                    return "Red: " + this.user;
                }
                return "Black: " + this.user;
            } else {
                return "Leaf";
            }
        }

        // Return a list of all leaves this node leads to
        public List<Node> childrenLeaves() {
            List<Node> leaves = new ArrayList<>();

            if (this.user == null) {
                leaves.add(this);
            } else {
                leaves.addAll(this.left.childrenLeaves());
                leaves.addAll(this.right.childrenLeaves());
            }
            return leaves;
        }

        // Return the number of black nodes between this node and root (inclusive)
        public int blackParentCount() {
            return this.parent == null ? 1 : ((this.isRed ? 0 : 1) + this.parent.blackParentCount());
        }
    }
}
