/*

AVLTree operations provided-
-> Initialise -> AVLTree<T> tree=new AVLTree<T>()
              -> AVLTree<T> tree=new AVLTree<T>(T[] array)

-> boolean checkEmpty() // To check whether AVL tree is empty or not
-> int size() // Returns size of AVL Tree
-> void insert(T element) // To insert
-> T lower() // Returns minimum of AVL Tree
-> T upper() // Returns maximum of AVL Tree
-> void delete(T element) // To delete
-> boolean search(T element) // To search
-> void inorder()
-> void preorder()
-> void postorder()

*/

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class Node<T extends Comparable<T>>
{
    public T value;
    public int height;
    public Node left;
    public Node right;

    Node(T d) {
        this.value=d;
        this.height=1;
    }
}

class AVLTree<T extends Comparable<T>> {

    private Node<T> root;

    public AVLTree() {}

    public AVLTree(T[] array){
        for(T element : array){
            insert(element);
        }
    }

    public boolean checkEmpty()  
    {  
        if(root == null)  
            return true;  
        else   
            return false;  
    }  

    private int sizeRecursive(Node<T> node){
        if(node==null){
            return 0;
        } else {
            return 1+sizeRecursive(node.left)+sizeRecursive(node.right);
        }
    }

    public int size(){
        return sizeRecursive(root);
    }

    private int height(Node<T> x) {
        if (x == null)
            return 0;

        return x.height;
    }

    private void updateHeight(Node<T> node){
        node.height=Math.max(height(node.left), height(node.right)) + 1;
    }

    private Node<T> rightRotate(Node<T> y) {
        Node<T> x = y.left;
        Node<T> T2 = x.right;

        // Perform rotation 
        x.right=y;
        y.left=T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        // Return new root 
        return x;
    }

    private Node<T> leftRotate(Node<T> x) {
        Node<T> y = x.right;
        Node<T> T2 = y.left;

        // Perform rotation 
        y.left=x;
        x.right=T2;

        //  Update heights 
        x.height=Math.max(height(x.left),height(x.right))+1;
        y.height=Math.max(height(y.left),height(y.right))+1;

        // Return new root 
        return y;
    }

    // Get Balance factor of node N 
    private int getBalance(Node<T> node) {
        if (node == null)
            return 0;

        return height(node.left) - height(node.right);
    }

    private boolean isAvlBalancedRecursive(Node<T> node){
        if(node!=null){
            if(Math.abs(getBalance(node))>1){
                return false;
            } else {
                return isAvlBalancedRecursive(node.left)
                        && isAvlBalancedRecursive(node.right);
            }
        } else {
            return true;
        }
    }

    boolean isAvlBalanced(){
        return isAvlBalancedRecursive(root);
    }

    private Node<T> fixAfterInsertion(Node<T> node, T key){
        int balance = getBalance(node);

        Node<T> templeft = node.left;
        Node<T> tempRight = node.right;

        // left-left case
        if (balance > 1 && key.compareTo(templeft.value)<0)
            return rightRotate(node);

        // right-right case
        if (balance < -1 && key.compareTo(tempRight.value)>0)
            return leftRotate(node);

        // left-right case
        if (balance > 1 && key.compareTo(templeft.value)>0) {
            node.left=leftRotate(node.left);
            return rightRotate(node);
        }

        // right-left case
        if (balance < -1 && key.compareTo(tempRight.value)<0) {
            node.right=rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node<T> insert(Node<T> node, T key) {
        if (node == null)
            return new Node<T>(key);

        if (key.compareTo(node.value)<0) {
            node.left=insert(node.left,key);
        } else if (key.compareTo(node.value)>0) {
            node.right=insert(node.right,key);
        } else {
            // Replace already existing node
            Node<T> newNode = new Node(key);
            newNode.right=node.right;
            newNode.left=node.left;
            node=newNode;
        }

        updateHeight(node);
        return fixAfterInsertion(node, key);
    }

    public void insert(T element){
        root=insert(root, element);
    }

    private Node<T> lowerNode(Node<T> node)
    {
        Node<T> current = node;

        // find element the furthest left element in the tree
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    public T lower(){
        return lowerNode(root).value;
    }

    private Node<T> upperNode(Node<T> node)
    {
        Node current = node;

        // find element the furthest right element in the tree
        while (current.right != null) {
            current = current.right;
        }

        return current;
    }

    public T upper(){
        return upperNode(root).value;
    }

    private Node<T> fixAfterDeletion(Node<T> node){
        int balance = getBalance(node);

        // left-left case
        if (balance > 1 && getBalance(node.left) >= 0)
            return rightRotate(node);

        // left-right case
        if (balance > 1 && getBalance(node.left) < 0)
        {
            node.left=leftRotate(node.left);
            return rightRotate(node);
        }

        // right-right case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        // right-left case
        if (balance < -1 && getBalance(node.right) > 0)
        {
            node.right=rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node<T> deleteNode(Node<T> node, T key)
    {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.value)<0) {
            // search left subtree
            node.left=deleteNode(node.left,key);
        } else if (key.compareTo(node.value)>0) {
            // search right subtree
            node.right=deleteNode(node.right,key);
        } else {
            // node with only one child or no child
            if ((node.left == null) || (node.right == null))
            {
                Node<T> temp;
                if (node.left !=null) {
                    temp = node.left;
                } else {
                    temp = node.right;
                }

                // the node has no children
                if (temp == null)
                {
                    return null;
                }
                else {
                    // replace the node with its only child
                    node = temp;
                }
            }
            else
            {
                // get the lowest node in the right subtree
                Node<T> lowest = lowerNode(node.right);
                // replace node value with the lowest value
                node.value=lowest.value;
                // delete the lowest in right subtree using this function recursively
                node.right=deleteNode(node.right,lowest.value);
            }
        }

        updateHeight(node);
        return fixAfterDeletion(node);
    }

    public void delete(T element) {
        root = deleteNode(root, element);
    }

    private boolean containsRecursive(Node<T> node, T element){
        if(node==null){
            return false;
        } else if(node.value.equals(element)){
            return true;
        } else {
            if (node.value.compareTo(element)>0)
                return containsRecursive(node.left,element);
            else
                return containsRecursive(node.right,element);

            // return containsRecursive(node.left, element)
            //         || containsRecursive(node.right, element);
        }
    }

    public boolean search(T element){
        return containsRecursive(root, element);
    }

    int elementDepthRecursive(Node<T> node, T element){
        if(node==null){
            return -1;
        }
        int onLeft=elementDepthRecursive(node.left, element);
        if(onLeft!=-1){
            return onLeft;
        } else {
            return elementDepthRecursive(node.right, element);
        }
    }

    int elementDepth(T element){
        return elementDepthRecursive(root, element);
    }

    public void inorder() 
    {
        inorderTraversal(root);
        System.out.println();
    }

    // Inorder - LDR
    private void inorderTraversal(Node<T> node) {
        if (node==null)
            return;
        inorderTraversal(node.left); // L
        System.out.print(node.value+" "); // D
        inorderTraversal(node.right); // R  
    }

    public void preorder() 
    {
        preorderTraversal(root);
        System.out.println();
    }

    // Preorder - DLR
    private void preorderTraversal(Node<T> node) 
    {
        if (node==null)
            return;
        System.out.print(node.value+" "); // D
        preorderTraversal(node.left); // L
        preorderTraversal(node.right); // R 
    }

    public void postorder()
    {
        postorderTraversal(root);
        System.out.println();
    }

    // Preorder - LRD
    private void postorderTraversal(Node<T> node) 
    {
        if (node==null)
            return;
        postorderTraversal(node.left); // L
        postorderTraversal(node.right); // R 
        System.out.print(node.value+" "); // D
    }

    public void print()
    {
        PrintTree printer = new PrintTree(root);
        printer.print();
    }
}

class PrintTree
{
    Node treeRoot;

    PrintTree(Node treeRoot)
    {
        this.treeRoot=treeRoot;
    }

    /*To be compiled using javac -encoding UTF-8 file_name.java*/
    public void print()
    {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<Node> level = new ArrayList<Node>();
        List<Node> next = new ArrayList<Node>();

        level.add(treeRoot);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (Node n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = String.valueOf(n.value); // Key to be printed
                    line.add(aa);
                    if (aa.length() > widest) 
                        widest = aa.length();

                    next.add(n.left);
                    next.add(n.right);

                    if (n.left != null) 
                        nn++;
                    if (n.right != null) 
                        nn++;
                }
            }

            if (widest % 2 == 1) 
                widest++;

            lines.add(line);

            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            perpiece /= 2;
        }
    }

}



public class Main
{
    public static void main(String args[])
    {
        AVLTree<Integer> tree=new AVLTree<Integer>();

        tree.insert(25);
        tree.insert(15);
        tree.insert(40);
        tree.insert(10);
        tree.insert(18);
        tree.insert(35);
        tree.insert(45);
        tree.insert(5);
        tree.insert(19);
        tree.insert(20);
        tree.insert(44);
        tree.insert(49);
        tree.insert(3);

        tree.print();

        tree.inorder();
        tree.preorder();
        tree.postorder();

        if (tree.search(52))
            System.out.println("hregf");

        tree.delete(18);
        tree.delete(19);
        tree.delete(20);
        tree.print();
    }
}
