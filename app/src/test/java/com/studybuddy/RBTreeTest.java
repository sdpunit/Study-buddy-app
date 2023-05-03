package com.studybuddy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class RBTreeTest {
    private final User[] users = {new User(6750912, "Emily Thompson"),
            new User(7340286, "Ava Walker"),
            new User(6142853, "James Anderson"),
            new User(6425273, "James Anderson"),
            new User(7533843, "Yanghe Dong"),
            new User(6239587, "Olivia Turner"),
            new User(7431068, "Michael Taylor"),
            new User(7442688, "Michael Tayer"),
            new User(7452667, "Michael Taylor"),
            new User(7648392, "Sophia Lewis"),
            new User(6953424, "William White"),
            new User(6750912, "Benjamin Harris"),
            new User(7565334, "Amelia Martin"),
            new User(7125906, "Samuel Jackson"),
            new User(7486251, "Zihan Li"),
            new User(7546283, "Zihan Li")};

    private boolean compareLists(List<RBTree.Node> expected, List<RBTree.Node> actual) {
        if (expected.size() != actual.size()) {
            return false;
        }
        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).getUser().equals(actual.get(i).getUser())) {
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
        if (node.left == null && node.right == null && node.getUser() == null) {
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
    public void testSimpleInsertWithoutReconstruction() {
        List<RBTree.Node> expected = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.tree.insert(users[i]);
        }
        List<RBTree.Node> actual = this.tree.inOrderTraverse();
        expected.add(new RBTree.Node(users[1]));
        expected.add(new RBTree.Node(users[0]));
        expected.add(new RBTree.Node(users[2]));

        assertTrue(testProp1(this.tree) && testProp2(this.tree) && testProp3(this.tree));
        assertTrue(compareLists(expected, actual));
    }

    @Test(timeout = 1000)
    public void testDuplicateInsert() {
        List<RBTree.Node> expected = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.tree.insert(users[i]);
        }
        List<RBTree.Node> actual = this.tree.inOrderTraverse();
        expected.add(new RBTree.Node(users[1]));
        expected.add(new RBTree.Node(users[0]));
        expected.add(new RBTree.Node(users[2]));
        expected.add(new RBTree.Node(users[3]));

        assertTrue(testProp1(this.tree) && testProp2(this.tree) && testProp3(this.tree));
        assertTrue(compareLists(expected, actual));
    }

    @Test(timeout = 1000)
    public void testMediumInsert() {
        List<RBTree.Node> expected = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            this.tree.insert(users[i]);
        }
        List<RBTree.Node> actual = this.tree.inOrderTraverse();
        expected.add(new RBTree.Node(users[1]));
        expected.add(new RBTree.Node(users[0]));
        expected.add(new RBTree.Node(users[2]));
        expected.add(new RBTree.Node(users[3]));
        expected.add(new RBTree.Node(users[7]));
        expected.add(new RBTree.Node(users[6]));
        expected.add(new RBTree.Node(users[8]));
        expected.add(new RBTree.Node(users[5]));
        expected.add(new RBTree.Node(users[4]));

        assertTrue(testProp1(this.tree) && testProp2(this.tree) && testProp3(this.tree));
        assertTrue(compareLists(expected, actual));
    }

    @Test(timeout = 1000)
    public void testComplexInsert() {
        List<RBTree.Node> expected = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            this.tree.insert(users[i]);
        }
        List<RBTree.Node> actual = this.tree.inOrderTraverse();
        expected.add(new RBTree.Node(users[12]));
        expected.add(new RBTree.Node(users[1]));
        expected.add(new RBTree.Node(users[11]));
        expected.add(new RBTree.Node(users[0]));
        expected.add(new RBTree.Node(users[2]));
        expected.add(new RBTree.Node(users[3]));
        expected.add(new RBTree.Node(users[7]));
        expected.add(new RBTree.Node(users[6]));
        expected.add(new RBTree.Node(users[8]));
        expected.add(new RBTree.Node(users[5]));
        expected.add(new RBTree.Node(users[13]));
        expected.add(new RBTree.Node(users[9]));
        expected.add(new RBTree.Node(users[10]));
        expected.add(new RBTree.Node(users[4]));
        expected.add(new RBTree.Node(users[14]));
        expected.add(new RBTree.Node(users[15]));

        assertTrue(testProp1(this.tree) && testProp2(this.tree) && testProp3(this.tree));
        assertTrue(compareLists(expected, actual));
    }

    @Test(timeout = 1000)
    public void testSearchByUniqueName() {
        for (int i = 0; i < 16; i++) {
            this.tree.insert(users[i]);
        }
        assertEquals(this.tree.searchByName("Yanghe Dong").get(0).getUser(), users[4]);
    }

    @Test(timeout = 1000)
    public void testSearchByDuplicateName() {
        for (int i = 0; i < 16; i++) {
            this.tree.insert(users[i]);
        }
        assertEquals(this.tree.searchByName("Michael Taylor").get(0).getUser(), users[6]);
        assertEquals(this.tree.searchByName("Michael Taylor").get(1).getUser(), users[8]);
    }
}
