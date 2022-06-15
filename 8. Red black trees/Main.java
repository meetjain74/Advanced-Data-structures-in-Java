/*

Red Black Trees Operations provided -

-> Initialise -> RedBlackBST<Key,Value> tree = new RedBlackBST<Key,Value>();
    // Key and Value are any data types

-> int size() // Returns no of key-value pairs in tree

-> boolean isEmpty() // Check whether tree is empty

-> Value get(Key key) // Returns the value associated with the given key.

-> boolean contains(Key key) // Searches key in tree

-> void put(Key key, Value val) 
    // Inserts the specified key-value pair into the symbol table, overwriting the old 
           value with the new value if the symbol table already contains the specified key.
          Deletes the specified key (and its associated value) from this symbol table
           if the specified value is {null}

-> void deleteMin() 
    // Removes the smallest key and associated value from the tree
    // throws NoSuchElementException if tree is empty

-> void deleteMax() 
    // Removes the largest key and associated value from the tree
    // throws NoSuchElementException if tree is empty

-> void delete(Key key) 
    // Removes the specified key and its associated value from this symbol table     
        (if the key is in this symbol table)  

-> int height() // Returns height of tree  

-> Key min() // Returns the smallest key in the tree

-> Key max() // Returns the largest key in the tree

-> Key floor(Key key) 
    // Returns the largest key in the tree less than or equal to {key}

-> Key ceiling(Key key) 
    // Returns the smallest key in the tree greater than or equal to {key}

-> Key select(int rank)
    // Return the key in the symbol table of a given {rank}.
        This key has the property that there are {rank} keys in
        the symbol table that are smaller. In other words, this key is the
        ({rank}+1)st smallest key in the tree

-> int rank(Key key) 
    // Return the number of keys in the tree strictly less than {key} 

-> Iterable<Key> keys() // Returns all keys in the tree as an {Iterable}

-> Iterable<Key> keys(Key lo, Key hi)
    // Returns all keys in the tree in the given range
        between {lo} (inclusive) and {hi} (inclusive),
        as an {Iterable}

-> int size(Key lo, Key hi)
    // Returns the number of keys in the tree in the given range
        between {lo} (inclusive) and {hi} (inclusive)

-> void inorder() // Inorder Traversal

-> void preorder() // Preorder Traversal

-> void postorder() // Postorder Traversal

-> void print() // To print Red Black Tree

*/

import java.util.*;

class RedBlackBST<Key extends Comparable<Key>, Value> {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;     // root of the BST

    // BST helper node data type
    class Node {
        private Key key;           // key
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int size;          // subtree count

        public Node(Key key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }

    public RedBlackBST() {
    }

    // is node x red; false if x is null
    private boolean isRed(Node x) {
        if (x == null) 
            return false;
        return x.color == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) 
            return 0;
        return x.size;
    } 

    
       // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return size(root);
    }

    // Is this symbol table empty?
    public boolean isEmpty() {
        return root == null;
    }

    // Returns the value associated with the given key.
    public Value get(Key key) {
        if (key == null) 
            throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp < 0) 
                x = x.left;
            else if (cmp > 0) 
                x = x.right;
            else              
                return x.val;
        }
        return null;
    }

    // Does this symbol table contain the given key?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    
    /* 
    Inserts the specified key-value pair into the symbol table, overwriting the old 
       value with the new value if the symbol table already contains the specified key.
      Deletes the specified key (and its associated value) from this symbol table
       if the specified value is {null} 
       */
    public void put(Key key, Value val) {
        if (key == null) 
            throw new IllegalArgumentException("first argument to put() is null");
        
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);
        root.color = BLACK;
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, Key key, Value val) { 
        if (h == null) 
            return new Node(key, val, RED, 1);

        int cmp = key.compareTo(h.key);
        if (cmp < 0) 
            h.left  = put(h.left,  key, val); 
        else if (cmp > 0) 
            h.right = put(h.right, key, val); 
        else              
            h.val = val;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      
            h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) 
            h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     
            flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }

    /*
    Removes the smallest key and associated value from the symbol table.
    throws NoSuchElementException if the symbol table is empty
    */
    public void deleteMin() {
        if (isEmpty()) 
            throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) 
            root.color = BLACK;
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    /*
    Removes the largest key and associated value from the symbol table.
    throws NoSuchElementException if the symbol table is empty
    */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) 
            root.color = BLACK;
    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) { 
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    // Removes the specified key and its associated value from this symbol table     
    // (if the key is in this symbol table)    
    public void delete(Key key) { 
        if (key == null) 
            throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) 
            return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) 
            root.color = BLACK;
    }

    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, Key key) { 
        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        assert (h != null) && isRed(h.left);

        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        assert (h != null) && isRed(h.right);

        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);

        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        if (isRed(h.right) && !isRed(h.left))    h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    // Returns the height of the BST
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    // Returns the smallest key in the symbol table
    public Key min() {
        if (isEmpty()) 
            throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    } 

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) { 
        if (x.left == null) 
            return x; 
        else                
            return min(x.left); 
    } 

    // Returns the largest key in the symbol table
    public Key max() {
        if (isEmpty()) 
            throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    } 

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) { 
        if (x.right == null) 
            return x; 
        else                 
            return max(x.right); 
    }

       // Returns the largest key in the symbol table less than or equal to {key}
    public Key floor(Key key) {
        if (key == null) 
            throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) 
            throw new NoSuchElementException("calls floor() with empty symbol table");

        Node x = floor(root, key);
        if (x == null) 
            throw new NoSuchElementException("argument to floor() is too small");
        else           
            return x.key;
    }    

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node floor(Node x, Key key) {
        if (x == null) 
            return null;

        int cmp = key.compareTo(x.key);
        if (cmp == 0) 
            return x;
        if (cmp < 0)  
            return floor(x.left, key);

        Node t = floor(x.right, key);
        if (t != null) 
            return t; 
        else           
            return x;
    }

    // Returns the smallest key in the symbol table greater than or equal to {key}
    public Key ceiling(Key key) {
        if (key == null) 
            throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) 
            throw new NoSuchElementException("calls ceiling() with empty symbol table");

        Node x = ceiling(root, key);
        if (x == null) 
            throw new NoSuchElementException("argument to ceiling() is too small");
        else           
            return x.key;  
    }

    // the smallest key in the subtree rooted at x greater than or equal to the given key
    private Node ceiling(Node x, Key key) {  
        if (x == null) 
            return null;

        int cmp = key.compareTo(x.key);
        if (cmp == 0) 
            return x;
        if (cmp > 0)  
            return ceiling(x.right, key);

        Node t = ceiling(x.left, key);
        if (t != null) 
            return t; 
        else           
            return x;
    }

    /*
    Return the key in the symbol table of a given {rank}.
    This key has the property that there are {rank} keys in
    the symbol table that are smaller. In other words, this key is the
    ({rank}+1)st smallest key in the symbol table
    */
    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private Key select(Node x, int rank) {
        if (x == null) 
            return null;

        int leftSize = size(x.left);
        if (leftSize > rank) 
            return select(x.left,  rank);
        else if (leftSize < rank) 
            return select(x.right, rank - leftSize - 1); 
        else                   
            return x.key;
    }

    // Return the number of keys in the symbol table strictly less than {key} 
    public int rank(Key key) {
        if (key == null) 
            throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    } 

    // number of keys less than key in the subtree rooted at x
    private int rank(Key key, Node x) {
        if (x == null) 
            return 0; 

        int cmp = key.compareTo(x.key); 
        if (cmp < 0) 
            return rank(key, x.left); 
        else if (cmp > 0) 
            return 1 + size(x.left) + rank(key, x.right); 
        else              
            return size(x.left); 
    } 

    // Returns all keys in the symbol table as an {Iterable}
    public Iterable<Key> keys() {
        if (isEmpty()) 
            return new LinkedList<Key>();
        return keys(min(), max());
    }

    /*
    Returns all keys in the tree in the given range
    between {lo} (inclusive) and {hi} (inclusive),
    as an {Iterable}
    */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) 
            throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) 
            throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new LinkedList<Key>();
        keys(root, queue, lo, hi);
        return queue;
    } 

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) { 
        if (x == null) 
            return; 

        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key);

        if (cmplo < 0) 
            keys(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) 
            queue.add(x.key); 
        if (cmphi > 0) 
            keys(x.right, queue, lo, hi); 
    } 

    /*
    Returns the number of keys in the tree in the given range
    between {lo} (inclusive) and {hi} (inclusive)
    */
    public int size(Key lo, Key hi) {
        if (lo == null) 
            throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) 
            throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) 
            return 0;
        if (contains(hi)) 
            return rank(hi) - rank(lo) + 1;
        else              
            return rank(hi) - rank(lo);
    }

    private boolean check() {
        if (!isBST())            
            System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) 
            System.out.println("Subtree counts not consistent");
        if (!isRankConsistent()) 
            System.out.println("Ranks not consistent");
        if (!is23())             
            System.out.println("Not a 2-3 tree");
        if (!isBalanced())       
            System.out.println("Not balanced");

        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) 
            return true;
        if (min != null && x.key.compareTo(min) <= 0) 
            return false;
        if (max != null && x.key.compareTo(max) >= 0) 
            return false;

        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    } 

    // are the size fields correct?
    private boolean isSizeConsistent() { 
        return isSizeConsistent(root); 
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) 
            return true;
        if (x.size != size(x.left) + size(x.right) + 1) 
            return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    } 

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) 
                return false;

        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) 
                return false;

        return true;
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private boolean is23() { 
        return is23(root); 
    }

    private boolean is23(Node x) {
        if (x == null) 
            return true;
        if (isRed(x.right)) 
            return false;
        if (x != root && isRed(x) && isRed(x.left))
            return false;
        return is23(x.left) && is23(x.right);
    } 

    // do all paths from root to leaf have same number of black edges?
    private boolean isBalanced() { 
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) 
                black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    // does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        if (x == null) 
            return black == 0;
        if (!isRed(x)) 
            black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    public void inorder() {
        inorderTraversal(root);
        System.out.println();
    }

    private void inorderTraversal(Node root) {
        if (root==null)
            return;
        inorderTraversal(root.left);
        System.out.print(root.key+":"+root.val+" ");
        inorderTraversal(root.right);
    }

    public void preorder() {
        preorderTraversal(root);
        System.out.println();
    }

    private void preorderTraversal(Node root) {
        if (root==null)
            return;
        System.out.print(root.key+":"+root.val+" ");
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }

    public void postorder() {
        postorderTraversal(root);
        System.out.println();
    }

    private void postorderTraversal(Node root) {
        if (root==null)
            return;
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.print(root.key+":"+root.val+" ");
    }

    public void print()
    {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<Node> level = new ArrayList<Node>();
        List<Node> next = new ArrayList<Node>();

        level.add(root);
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
                    String aa = String.valueOf(n.key+":"+n.val); // Key to be printed
                    String temp=n.color ? ":Red" : ":Black";
                    aa=aa+temp;
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
        RedBlackBST<Integer, Integer> tree = new RedBlackBST<Integer, Integer>();
        
        tree.put(2,6);
        tree.put(5,7);
        tree.put(2,7);
        tree.put(3,5);
        tree.put(4,7);
        tree.put(1,5);
        tree.put(13,7);
        tree.put(10,7);
        tree.put(8,45);

        tree.inorder();
        tree.preorder();
        tree.postorder();
        tree.print();

        tree.delete(5);

        tree.print();

        System.out.println(tree.min()+" "+tree.max());

        if (tree.contains(2))
            System.out.println("YES");
    }
}