package com.studybuddy.search;

import com.studybuddy.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * This class is a RB Tree to store course data.
 *
 * @author Yanghe Dong
 */
public class RBTree {
    public Node root;

    public RBTree() {
        // Initialise empty RBTree
        this.root = null;
    }

    /**
     * Insert a new course into the tree
     *
     * @param course the new course to be inserted
     */
    public void insert(Course course) {
        Node newNode = new Node(course);
        // new node is always red
        newNode.setColour(true);
        Node root = this.root;
        Node parentNode = null;

        while (root != null && root.course != null) {
            parentNode = root;
            int compareResult = compareNodes(newNode.getCourse(), root.getCourse());
            if (compareResult < 0) {
                root = root.left;
            } else if (compareResult > 0) {
                root = root.right;
            }
            else return;
        }
        if (parentNode != null) {
            newNode.parent = parentNode;
        }

        // empty tree
        if (parentNode == null) {
            this.root = newNode;
        } else if (compareNodes(newNode.getCourse(), parentNode.getCourse()) < 0) {
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
     * Search for a course by course code (e.g. COMP2100)
     *
     * @param root the root of the tree
     * @param courseCode the code of the course
     * @return the node containing the course with the given code
     */
    public Node searchByCourseCode(Node root, String courseCode) {
        Node currentNode = root;
        Node result = null;

        while (currentNode != null && currentNode.course != null) {
            int comparison = compareCourseCode(courseCode, currentNode.getCourse().getCourseCode());
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

    private int compareNodes(Course a, Course b) {
        String courseCode1 = a.getCourseCode();
        String courseCode2 = b.getCourseCode();
        return compareCourseCode(courseCode1, courseCode2);
    }

    private int compareCourseCode(String courseCode1, String courseCode2) {
        int code1 = Integer.parseInt(courseCode1.substring(4));
        int code2 = Integer.parseInt(courseCode2.substring(4));
        return code1 - code2;
    }


    // in-order traversal
    public List<Node> inOrderTraverse() {
        return inOrderTraverseHelper(this.root);
    }

    // don't record the leaf nodes
    private List<Node> inOrderTraverseHelper(Node node) {
        List<Node> nodes = new ArrayList<>();
        if (node.left != null && node.left.course != null) {
            nodes.addAll(inOrderTraverseHelper(node.left));
        }
        if (node.course != null) {
            nodes.add(node);
        }
        if (node.right != null && node.right.course != null) {
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
        if (node.left != null && node.left.course != null) {
            nodes.addAll(postOrderTraverseHelper(node.left));
        }
        if (node.right != null && node.right.course != null) {
            nodes.addAll(postOrderTraverseHelper(node.right));
        }
        if (node.course != null) {
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * This class represents a node in the RB tree.
     */
    public static class Node {

        public boolean isRed;
        private Course course;
        public Node parent;
        public Node left;
        public Node right;

        public Node(Course course) {
            this.course = course;
            this.isRed = false;
            this.parent = null;

            // Initialise children leaf nodes
            this.left = new Node();
            this.right = new Node();
            this.left.parent = this;
            this.right.parent = this;
        }

        // Leaf node constructor
        public Node() {
            this.course = null;
            this.isRed = false;
        }

        public void setColour(boolean isRed) {
            this.isRed = isRed;
        }

        public Course getCourse() {
            return this.course;
        }

        public String toString() {
            if (this.course != null) {
                if (isRed) {
                    return "Red: " + this.course;
                }
                return "Black: " + this.course;
            } else {
                return "Leaf";
            }
        }

        // Return a list of all leaves this node leads to
        // This method is from previous exam
        public List<RBTree.Node> childrenLeaves() {
            List<RBTree.Node> leaves = new ArrayList<>();

            if (this.course == null) {
                leaves.add(this);
            } else {
                leaves.addAll(this.left.childrenLeaves());
                leaves.addAll(this.right.childrenLeaves());
            }
            return leaves;
        }

        // Return the number of black nodes between this node and root (inclusive)
        // This method is from previous exam
        public int blackParentCount() {
            return this.parent == null ? 1 : ((this.isRed ? 0 : 1) + this.parent.blackParentCount());
        }
    }
}
