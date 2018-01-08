public class BinarySearchTree<E extends Comparable<E>> {
    class Node {
        E value;
        Node leftChild = null;
        Node rightChild = null;

        Node(E value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if ((obj instanceof BinarySearchTree.Node) == false)
                return false;
            @SuppressWarnings("unchecked")
            Node other = (BinarySearchTree<E>.Node) obj;
            return other.value.compareTo(value) == 0 &&
                    other.leftChild == leftChild && other.rightChild == rightChild;
        }
    }

    protected Node root = null;

    protected void visit(Node n) {
        System.out.println(n.value);
    }

    public boolean contains(E val) {
        return contains(root, val);
    }

    protected boolean contains(Node n, E val) {
        if (n == null) return false;

        if (n.value.equals(val)) {
            return true;
        } else if (n.value.compareTo(val) > 0) {
            return contains(n.leftChild, val);
        } else {
            return contains(n.rightChild, val);
        }
    }

    public boolean add(E val) {
        if (root == null) {
            root = new Node(val);
            return true;
        }
        return add(root, val);
    }

    protected boolean add(Node n, E val) {
        if (n == null) {
            return false;
        }
        int cmp = val.compareTo(n.value);
        if (cmp == 0) {
            return false; // this ensures that the same value does not appear more than once
        } else if (cmp < 0) {
            if (n.leftChild == null) {
                n.leftChild = new Node(val);
                return true;
            } else {
                return add(n.leftChild, val);
            }
        } else {
            if (n.rightChild == null) {
                n.rightChild = new Node(val);
                return true;
            } else {
                return add(n.rightChild, val);
            }
        }
    }

    public boolean remove(E val) {
        return remove(root, null, val);
    }

    protected boolean remove(Node n, Node parent, E val) {
        if (n == null) return false;

        if (val.compareTo(n.value) == -1) {
            return remove(n.leftChild, n, val);
        } else if (val.compareTo(n.value) == 1) {
            return remove(n.rightChild, n, val);
        } else {
            if (n.leftChild != null && n.rightChild != null) {
                n.value = maxValue(n.leftChild);
                remove(n.leftChild, n, n.value);
            } else if (parent == null) {
                root = n.leftChild != null ? n.leftChild : n.rightChild;
            } else if (parent.leftChild == n) {
                parent.leftChild = n.leftChild != null ? n.leftChild : n.rightChild;
            } else {
                parent.rightChild = n.leftChild != null ? n.leftChild : n.rightChild;
            }
            return true;
        }
    }

    protected E maxValue(Node n) {
        if (n.rightChild == null) {
            return n.value;
        } else {
            return maxValue(n.rightChild);
        }
    }


    /*********************************************
     *
     * IMPLEMENT THE METHODS BELOW!
     *
     *********************************************/


    /**
     * Given a value that is stored in the BST, it returns the corresponding Node that holds it. If the value does not
     * exist in this BST, this method should return null.
     *
     * @param val
     * @return
     */
    public Node findNode(E val) {
        int compareResult;
        Node current = root;
        if (root != null && val != null && this.contains(val)) {
            Node result = null;
            while (result == null) {
                compareResult = val.compareTo(current.value);
                if (compareResult < 0) {
                    current = current.leftChild;
                } else if (compareResult > 0) {
                    current = current.rightChild;
                } else {
                    result = current;
                }
            }
            return result;
        }
        return null;

    }

    /**
     * Given a value, this method should return the “depth” of its Node, which is the number of ancestors between that
     * node and the root, including the root but not the node itself. The depth of the root is defined to be 0; the
     * depth of its two children (if any) is defined to be 1; the depth of the root’s grandchildren (if any) is defined
     * to be 2; and so on. If the value is null or does not exist in this BST, this method should return -1.
     *
     * @param val
     * @return
     */
    protected int depth(E val) {
        if (root != null && val != null && this.contains(val)) {
            Node current = root;
            int compareResult;
            int depth = 0;
            while (true) {
                compareResult = val.compareTo(current.value);
                if (compareResult < 0) {
                    current = current.leftChild;
                    depth++;
                } else if (compareResult > 0) {
                    current = current.rightChild;
                    depth++;
                } else {
                    break;
                }
            }
            return depth;
        }
        return -1;
    }

    /**
     * Given a value, this method should return the “height” of its Node, which is the greatest number of nodes between
     * that node and any descendant node that is a leaf, including the leaf but not the node itself. The height of a
     * leaf node (i.e., one which has no children) is defined to be 0. If the input value is null or does not exist in
     * this BST, this method should return -1.
     *
     * @param val
     * @return
     */
    protected int height(E val) {
        if (root != null && val != null && this.contains(val)) {
            Node start = findNode(val);
            return getHeight(start, 0);
        }
        return -1;
    }

    private int getHeight(Node n, int height) {
        int leftHeight = 0, rightHeight = 0;
        if (n.leftChild == null && n.rightChild == null) {
            return height;
        }
        if (n.leftChild != null) {
            leftHeight = getHeight(n.leftChild, height + 1);
        }
        if (n.rightChild != null) {
            rightHeight = getHeight(n.rightChild, height + 1);
        }

        return leftHeight < rightHeight ? rightHeight : leftHeight;
    }


    /**
     * Given a Node, return true if the absolute value of the difference in heights of its left and right children is
     * 0 or 1, and return false otherwise. If the Node is null or does not exist in this BST, this method should return
     * false.
     *
     * @param n
     * @return
     */
    protected boolean isBalanced(Node n) {
        int absoluteVal = 0;
        if (n != null) {
            Node node = findNode(n.value);
            if (node != null) {
                if (height(node.value) != 0) {
                    int leftHeight = -1, rightHeight = -1;
                    if (node.leftChild != null)
                        leftHeight = height(node.leftChild.value);
                    if (node.rightChild != null)
                        rightHeight = height(node.rightChild.value);
                    absoluteVal = Math.abs(leftHeight - rightHeight);
                }
                return absoluteVal == 1 || absoluteVal == 0;
            }
        }
        return false;
    }

    /**
     * Returns true if isBalanced(Node) returns true for all Nodes in the tree
     * @return
     */
    public boolean isBalanced() {
        return isTreeBalanced(root);
    }

    private boolean isTreeBalanced(Node n) {
        if (root != null) {
            boolean balanced = isBalanced(n);
            if (balanced) {
                if (n.leftChild != null) {
                    balanced = isBalanced(n.leftChild);
                }

                if (balanced && n.rightChild != null) {
                    balanced = isBalanced(n.rightChild);
                }
            }
            return balanced;
        }
        return true;
    }
}
