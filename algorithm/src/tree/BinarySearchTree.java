package tree;

/**
 * @author 尉涛
 * @date 2020-02-04 17:20
 * 二叉搜索树
 **/
public class BinarySearchTree {

    public static void main(String[] args) {
        Node root = generateTree();

        preOrder(root);
        System.out.println();
        inOrder(root);
        System.out.println();
        postOrder(root);
        System.out.println();

        Node node = find(root, 1);
        if (node != null) {
            System.out.println("Find node: " + node.data);
        }

        // 每个数字都试一下
        root = delete(root, 15);
        inOrder(root);
        System.out.println();
        root = delete(root, 8);
        inOrder(root);
        System.out.println();
        root = delete(root, 7);
        inOrder(root);
        System.out.println();
        root = delete(root, 12);
        inOrder(root);
        System.out.println();
        root = delete(root, 0);
        inOrder(root);
        System.out.println();

    }

    static Node generateTree() {
        Node root = new Node(7);
        insert(root, 3);
        insert(root, 11);
        insert(root, 1);
        insert(root, 5);
        insert(root, 9);
        insert(root, 13);
        insert(root, 0);
        insert(root, 2);
        insert(root, 4);
        insert(root, 6);
        insert(root, 8);
        insert(root, 10);
        insert(root, 12);
        insert(root, 14);
        insert(root, 15);

        //                 7
        //         3               11
        //      1     5         9      13
        //     0 2   4 6       8 10  12   14
        //                                   15
        return root;
    }

    static class Node {
        Node(int data) {
            this.data = data;
        }

        int data;
        Node left;
        Node right;
    }

    /**
     * insert root(no same value root)
     *
     * @param root the root
     * @param key  node value
     * @return insert node
     */
    static Node insert(Node root, int key) {
        Node node = new Node(key);

        if (root == null) {
            return node;
        } else {
            Node current = root;
            while (true) {
                if (key > current.data) {
                    if (current.right == null) {
                        current.right = node;
                        return node;
                    } else {
                        current = current.right;
                    }
                } else if (key < current.data) {
                    if (current.left == null) {
                        current.left = node;
                        return node;
                    } else {
                        current = current.left;
                    }
                } else {
                    return current;
                }
            }
        }
    }


    /**
     * 前序遍历,"中左右"
     *
     * @param root
     */
    static void preOrder(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    /**
     * 中序遍历,"左中右"
     *
     * @param root
     */
    static void inOrder(Node root) {
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.data + " ");
            inOrder(root.right);
        }
    }

    /**
     * 后序遍历,"左右中"
     *
     * @param root
     */
    static void postOrder(Node root) {
        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.data + " ");
        }
    }

    static Node find(Node root, int key) {
        if (root != null) {
            Node current = root;
            while (current.data != key) {
                if (key > current.data) {
                    current = current.right;
                } else {
                    current = current.left;
                }
                if (current == null) {
                    System.out.println("not find key");
                    return null;
                }
            }
            return current;
        }
        return null;
    }


    /**
     * 参考：https://blog.csdn.net/isea533/article/details/80345507
     *
     * @param root
     * @param key
     */
    static Node delete(Node root, int key) {
        if (root == null) {
            return null;
        }
        if (root.data > key) {
            deleteChild(root, root.left, key, true);
        } else if (root.data < key) {
            deleteChild(root, root.right, key, false);
        } else {
            return deleteRoot(root);
        }
        return root;
    }

    /**
     * 删除顶点
     */
    private static Node deleteRoot(Node root) {
        Node newRoot = null;
        if (root.left != null && root.right != null) {
            newRoot = getSuccessor(root);
            //复制连接
            newRoot.left = root.left;
            newRoot.right = root.right;
        } else if (root.left != null) {
            newRoot = root.left;
        } else if (root.right != null) {
            newRoot = root.right;
        }
        // 断开root连接
        root.left = null;
        root.right = null;
        return newRoot;
    }

    /**
     * 删除子节点
     */
    private static void deleteChild(Node parent, Node target, int key, boolean isLeftChild) {
        if (parent == null || target == null) {
            System.out.println("not find key");
            return;
        }

        if (target.data > key) {
            deleteChild(target, target.left, key, true);
        } else if (target.data < key) {
            deleteChild(target, target.right, key, false);
        } else {
            // 左右都有子节点、只有左子节点、只有右子节点、没有子节点
            if (target.left != null && target.right != null) {
                Node successor = getSuccessor(target);
                //接入
                successor.left = target.left;
                successor.right = target.right;
                if (isLeftChild) {
                    parent.left = successor;
                } else {
                    parent.right = successor;
                }
            } else if (target.left != null) {
                if (isLeftChild) {
                    parent.left = target.left;
                } else {
                    parent.right = target.left;
                }
                target.left = null;
            } else if (target.right != null) {
                if (isLeftChild) {
                    parent.left = target.right;
                } else {
                    parent.right = target.right;
                }
                target.right = null;
            } else {
                if (isLeftChild) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
        }
    }

    /**
     * 获取替代节点（获取的节点已和树断开）
     * 获取右边最小的节点
     *
     * @param node
     * @return
     */
    private static Node getSuccessor(Node node) {
        Node successor;
        if (node.right.left != null) {
            // 右子节点的左子节点不为空，取-> 右左左....
            node = node.right;
            while (node.left.left != null) {
                node = node.left;
            }
            successor = node.left;
            // 记得断开父节点的引用
            node.left = null;
        } else {
            // 右子节点的左子节点为空，取-> 右
            successor = node.right;
            // 记得断开父节点的引用
            node.right = null;
        }

        // successor是没有左节点的，如果有右节点，就给successor的父节点接上
        if (successor.right != null) {
            // 右子给父左
            node.left = successor.right;
            // 断开右节点
            successor.right = null;
        }
        return successor;
    }
}
