package com.studybuddy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.studybuddy.bathtub.Course;
import com.studybuddy.search.RBTree;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a test class for RB Tree.
 * @author Yanghe (u7533843)
 */
public class RBTreeTest {
    private final Course[] courses = {new Course("COMP2300"), new Course("COMP3600"),
            new Course("COMP2100"), new Course("COMP2100"), new Course("COMP4600"),
            new Course("COMP3620"), new Course("COMP2420"), new Course("COMP3310"),
            new Course("COMP4528"), new Course("COMP1110"), new Course("COMP2620"),
            new Course("COMP4691"), new Course("COMP3630"), new Course("COMP3425")};

    // check if two lists contain the same elements
    private boolean compareLists(List<RBTree.Node> expected, List<RBTree.Node> actual) {
        if (expected.size() != actual.size()) {
            return false;
        }
        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).getCourse().equals(actual.get(i).getCourse())) {
                return false;
            }
        }
        return true;
    }

    // check if root and every leaf is black
    public boolean testProp1(RBTree rbTree) {
        if (rbTree.root.isRed) return false;
        List<RBTree.Node> leaf = findLeaf(rbTree.root);
        for (RBTree.Node leave : leaf) {
            if (leave.isRed) return false;
        }
        return true;
    }

    // find all leaves of the given node
    public List<RBTree.Node> findLeaf(RBTree.Node node) {
        List<RBTree.Node> leaf = new ArrayList<>();
        if (node == null) return leaf;
        if (node.left == null && node.right == null && node.getCourse() == null) {
            leaf.add(node);
        }
        leaf.addAll(findLeaf(node.left));
        leaf.addAll(findLeaf(node.right));
        return leaf;
    }

    // check if every red node has two black children
    public boolean testProp2(RBTree rbTree) {
        List<RBTree.Node> reds = findRed(rbTree.root);
        for (RBTree.Node red : reds) {
            if (red.left.isRed || red.right.isRed) return false;
        }
        return true;
    }

    // find all red nodes
    public List<RBTree.Node> findRed(RBTree.Node node) {
        List<RBTree.Node> reds = new ArrayList<>();
        if (node == null) return reds;
        if (node.isRed) {
            reds.add(node);
        }
        reds.addAll(findRed(node.left));
        reds.addAll(findRed(node.right));
        return reds;
    }

    // check if every path from node to its leaf has the same number of black nodes
    public boolean testProp3(RBTree rbTree) {
        List<RBTree.Node> notLeaf = rbTree.postOrderTraverse();
        for (RBTree.Node node : notLeaf) {
            List<RBTree.Node> leaves = node.childrenLeaves();
            RBTree.Node parent = node.parent;
            node.parent = null;
            List<Integer> blacks = new ArrayList<>();
            for (RBTree.Node leaf : leaves) {
                int black = leaf.blackParentCount();
                blacks.add(black);
            }
            node.parent = parent;
            int first = blacks.get(0);
            for (Integer n : blacks) {
                if (n != first) return false;
            }
        }
        return true;
    }

    private RBTree tree;

    @Before
    public void initialise() {
        this.tree = new RBTree();
    }

    @After
    public void destroy() {
        this.tree = null;
    }


    @Test(timeout = 1000)
    public void testEmptyTree() {
        assertNull(this.tree.root);
    }

    @Test(timeout = 1000)
    public void testSimpleInsertWithoutReconstruction() {
        List<RBTree.Node> expected = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.tree.insert(courses[i]);
        }
        List<RBTree.Node> actual = this.tree.inOrderTraverse();
        expected.add(new RBTree.Node(courses[2]));
        expected.add(new RBTree.Node(courses[0]));
        expected.add(new RBTree.Node(courses[1]));

        assertTrue(testProp1(this.tree) && testProp2(this.tree) && testProp3(this.tree));
        assertTrue(compareLists(expected, actual));
    }

    @Test(timeout = 1000)
    public void testDuplicateInsert() {
        List<RBTree.Node> expected = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.tree.insert(courses[i]);
        }
        List<RBTree.Node> actual = this.tree.inOrderTraverse();
        expected.add(new RBTree.Node(courses[2]));
        expected.add(new RBTree.Node(courses[0]));
        expected.add(new RBTree.Node(courses[1]));

        assertTrue(testProp1(this.tree) && testProp2(this.tree) && testProp3(this.tree));
        assertTrue(compareLists(expected, actual));
    }

    @Test(timeout = 1000)
    public void testMediumInsert() {
        List<RBTree.Node> expected = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            this.tree.insert(courses[i]);
        }
        List<RBTree.Node> actual = this.tree.inOrderTraverse();
        expected.add(new RBTree.Node(courses[2]));
        expected.add(new RBTree.Node(courses[0]));
        expected.add(new RBTree.Node(courses[6]));
        expected.add(new RBTree.Node(courses[7]));
        expected.add(new RBTree.Node(courses[1]));
        expected.add(new RBTree.Node(courses[5]));
        expected.add(new RBTree.Node(courses[4]));

        assertTrue(testProp1(this.tree) && testProp2(this.tree) && testProp3(this.tree));
        assertTrue(compareLists(expected, actual));
    }

    @Test(timeout = 1000)
    public void testComplexInsert() {
        List<RBTree.Node> expected = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            this.tree.insert(courses[i]);
        }
        List<RBTree.Node> actual = this.tree.inOrderTraverse();
        expected.add(new RBTree.Node(courses[9]));
        expected.add(new RBTree.Node(courses[2]));
        expected.add(new RBTree.Node(courses[0]));
        expected.add(new RBTree.Node(courses[6]));
        expected.add(new RBTree.Node(courses[10]));
        expected.add(new RBTree.Node(courses[7]));
        expected.add(new RBTree.Node(courses[13]));
        expected.add(new RBTree.Node(courses[1]));
        expected.add(new RBTree.Node(courses[5]));
        expected.add(new RBTree.Node(courses[12]));
        expected.add(new RBTree.Node(courses[8]));
        expected.add(new RBTree.Node(courses[4]));
        expected.add(new RBTree.Node(courses[11]));

        assertTrue(testProp1(this.tree) && testProp2(this.tree) && testProp3(this.tree));
        assertTrue(compareLists(expected, actual));
    }

    @Test(timeout = 1000)
    public void testSearchNonExist() {
        for (int i = 0; i < 14; i++) {
            this.tree.insert(courses[i]);
        }
        assertNull(this.tree.searchByCourseCode(this.tree.root,"COMP8600"));
    }

    @Test(timeout = 1000)
    public void testSearchByCourseCode() {
        for (int i = 0; i < 14; i++) {
            this.tree.insert(courses[i]);
        }
        assertEquals(this.tree.searchByCourseCode(this.tree.root,"COMP4600").getCourse(), courses[4]);
    }
}
