package tree;

/**
 * @author 尉涛
 * @date 2020-02-03 23:06
 * 二叉搜索树转成排序的双向列表，方法3最好
 **/
public class BinarySearchTreeToBiDirList {


    public static void main(String[] args) {
        BinarySearchTree.Node root = BinarySearchTree.generateTree();
        Link result = convert(root);

        while (result != null) {
            System.out.print(result.value);
            result = result.back;
        }
        System.out.println();

        //方法二
        root = BinarySearchTree.generateTree();
        BinarySearchTree.Node result2 = convert2(root);
        while (result2 != null) {
            System.out.print(result2.data);
            result2 = result2.right;
        }
        System.out.println();

        //方法三
        root = BinarySearchTree.generateTree();
        BinarySearchTree.Node result3 = convert3(root);
        while (result3 != null) {
            System.out.print(result3.data);
            result3 = result3.right;
        }

    }

    //方法一 创建新的链表对象 ********************************************************

    private static Link temp;

    private static Link convert(BinarySearchTree.Node root) {
        temp = new Link(0);
        Link tempHead = temp;
        realConvert(root);

        if (tempHead.back != null) {
            temp = tempHead.back;
            while (temp.back != null) {
                temp.back.front = temp;
                temp = temp.back;
            }
        }
        return tempHead.back;
    }

    private static void realConvert(BinarySearchTree.Node root) {
        if (root == null) {
            return;
        }
        realConvert(root.left);
        temp.back = new Link(root.data);
        temp = temp.back;
        realConvert(root.right);
    }

    private static class Link {
        Link front;
        Link back;
        int value;

        Link(int value) {
            this.value = value;
        }
    }

    //方法二 不创建新对象，只调整节点的指向 *******************************************

    private static BinarySearchTree.Node temp2, head;

    private static BinarySearchTree.Node convert2(BinarySearchTree.Node root) {
        if (root == null) {
            return null;
        }

        realConvert2(root);
        BinarySearchTree.Node tempHead = head;
        if (tempHead != null) {
            while (tempHead.right != null) {
                tempHead.right.left = tempHead;
                tempHead = tempHead.right;
            }
        }
        return head;
    }

    private static void realConvert2(BinarySearchTree.Node node) {
        if (node == null) {
            return;
        }

        realConvert2(node.left);
        BinarySearchTree.Node right = node.right;
        if (temp2 == null) {
            head = node;
            temp2 = node;
        } else {
            temp2.right = node;
            temp2 = temp2.right;
        }
        realConvert2(right);
    }


    //方法3 *********************************************************************

    private static BinarySearchTree.Node convert3(BinarySearchTree.Node root) {
        BinarySearchTree.Node lastNode = null;
        lastNode = realConvert3(root, lastNode);
        BinarySearchTree.Node headNode = lastNode;

        while (headNode.left != null){
            headNode = headNode.left;
        }

        return headNode;
    }

    private static BinarySearchTree.Node realConvert3(BinarySearchTree.Node node, BinarySearchTree.Node lastNode) {
        if (node == null) {
            return lastNode;
        }

        lastNode = realConvert3(node.left, lastNode);

        node.left = lastNode;
        if (lastNode != null) {
            lastNode.right = node;
        }
        lastNode = node;

        lastNode = realConvert3(node.right, lastNode);

        return lastNode;
    }

}
