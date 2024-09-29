package quan;

import java.util.Arrays;

class Node {

    int rollno;
    int mark;
    Node left;
    Node right;
    int level;

    public Node(int rollno, int mark) {
        this.rollno = rollno;
        this.mark = mark;
        this.left = null;
        this.right = null;
        this.level = 0;
    }
}

class BinarySearchTree {

    Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(int rollno, int mark) {
        root = insertRec(root, rollno, mark, 0);
    }

    private Node insertRec(Node root, int rollno, int mark, int level) {
        if (root == null) {
            root = new Node(rollno, mark);
            root.level = level;
            return root;
        }

        if (rollno < root.rollno) {
            root.left = insertRec(root.left, rollno, mark, level + 1);
        } else if (rollno > root.rollno) {
            root.right = insertRec(root.right, rollno, mark, level + 1);
        }

        return root;
    }

    public void createTreeFromA(int[][] A) {
        for (int i = 0; i < A.length; i++) {
            insert(A[i][0], A[i][1]);
        }
    }

    public void decreaseMarkOfMinScoreStudents() {
        int minMark = findMinMark(root); // Tìm điểm thấp nhất từ cây
        decreaseMarkOfMinScoreStudents(root, minMark);
    }

    private int findMinMark(Node root) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }
        int leftMin = findMinMark(root.left);
        int rightMin = findMinMark(root.right);
        return Math.min(root.mark, Math.min(leftMin, rightMin));
    }

    private void decreaseMarkOfMinScoreStudents(Node root, int minMark) {
        if (root == null) {
            return;
        }
        if (root.mark == minMark) {
            root.mark /= 2;
        }
        decreaseMarkOfMinScoreStudents(root.left, minMark);
        decreaseMarkOfMinScoreStudents(root.right, minMark);
    }

    public boolean isAVLTree() {
        return isAVLTree(root);
    }

    private boolean isAVLTree(Node root) {
        if (root == null) {
            return true;
        }
        int balanceFactor = getBalanceFactor(root);
        if (Math.abs(balanceFactor) > 1) {
            return false;
        }
        return isAVLTree(root.left) && isAVLTree(root.right);
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return leftHeight - rightHeight;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    public void printPreOrder() {
        System.out.println("Rollno and Mark of pupils stored in T (Pre-order traversal):");
        printPreOrder(root);
    }

    private void printPreOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.println("Rollno: " + root.rollno + ", Mark: " + root.mark + ", Level: " + root.level);
        printPreOrder(root.left);
        printPreOrder(root.right);
    }
}

public class Main {

    // A helper function to find the maximum value in the array based on the mark (second element)
    public static int getMax(int[][] A) {
        int max = A[0][1];
        for (int i = 1; i < A.length; i++) {
            if (A[i][1] > max) {
                max = A[i][1];
            }
        }
        return max;
    }

    // A function to perform counting sort on the array based on a specific digit (mark)
    public static void countSort(int[][] A, int exp) {
        int n = A.length;
        int[][] output = new int[n][2];
        int[] count = new int[10]; // 0 to 9, for base 10 numbers

        // Initialize count array
        Arrays.fill(count, 0);

        // Count occurrences of each digit in exp position
        for (int i = 0; i < n; i++) {
            count[(A[i][1] / exp) % 10]++;
        }

        // Calculate the cumulative count
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            output[count[(A[i][1] / exp) % 10] - 1][0] = A[i][0];
            output[count[(A[i][1] / exp) % 10] - 1][1] = A[i][1];
            count[(A[i][1] / exp) % 10]--;
        }

        // Copy the output array back to A
        for (int i = 0; i < n; i++) {
            A[i][0] = output[i][0];
            A[i][1] = output[i][1];
        }
    }

    // Radix Sort function to sort the array A
    public static void radixSort(int[][] A) {
        int max = getMax(A);

        // Perform counting sort for every digit, starting from the least significant to the most significant
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(A, exp);
        }
    }

    public static void main(String[] args) {
        int[][] A = {{5, 5}, {3, 3}, {2, 2}, {4, 44}, {7, 47}, {6, 100}, {8, 88}, {1, 11}, {9, 99}};
        BinarySearchTree tree = new BinarySearchTree();

        // Tạo cây T từ mảng A
        tree.createTreeFromA(A);

        // In cây T theo thứ tự trước
        tree.printPreOrder();

        // Giảm điểm của tất cả học sinh có điểm thấp nhất đi một nửa
        tree.decreaseMarkOfMinScoreStudents();

        // In lại cây T sau khi giảm điểm
        System.out.println("After decreasing marks of min score students:");
        tree.printPreOrder();

        // Xác định cấp độ cho tất cả các nút

        // Kiểm tra xem T có phải là cây AVL hay không
        boolean isAVL = tree.isAVLTree();
        System.out.println("Is it an AVL tree? " + (isAVL ? "Yes" : "No"));

//        
//
        radixSort(A);

        System.out.println("Sorted Array in Increasing Order of Mark: ");
        for (int i = 0; i < A.length; i++) {
            System.out.println("Age: " + A[i][0] + ", Mark: " + A[i][1]);
        }
    }
}
