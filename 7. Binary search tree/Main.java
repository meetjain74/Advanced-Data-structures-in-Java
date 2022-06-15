import java.util.ArrayList;
import java.util.List;

class BST 
{
	// Root node of BST
	Node root; 

	public BST() 
	{
		root=null;
	}

    public Node getRoot()
    {
        return root;
    }

    public boolean checkEmpty()  
    {  
        if(root == null)  
            return true;  
        else   
            return false;  
    }  

	public void insert(int val) 
	{
		root=insertValue(null,root,val);
	}

	private Node insertValue(Node parent,Node root,int val) 
	{
		// If tree is empty
		if (root==null)
		{
			root=new Node(val);
            root.parent=parent;
			return root;
		}

		// Else recur down the tree
		if (val<root.key)
			root.left = insertValue(root,root.left,val);
		else if (val>root.key)
			root.right = insertValue(root,root.right,val);
        else
            ; // Do nothing when val already exists

        return root;
	}

    public void inorder() 
    {
        inorderTraversal(root);
        System.out.println();
    }


    // Inorder - LDR
    private void inorderTraversal(Node root) 
    {
        if (root==null)
            return;
        inorderTraversal(root.left); // L
        System.out.print(root.key+" "); // D
        inorderTraversal(root.right); // R 
    }

    public void preorder() 
    {
        preorderTraversal(root);
        System.out.println();
    }

    // Preorder - DLR
    private void preorderTraversal(Node root) 
    {
        if (root==null)
            return;
        System.out.print(root.key+" "); // D
        preorderTraversal(root.left); // L
        preorderTraversal(root.right); // R 
    }

    public void postorder()
    {
        postorderTraversal(root);
        System.out.println();
    }

    // Preorder - LRD
    private void postorderTraversal(Node root) 
    {
        if (root==null)
            return;
        postorderTraversal(root.left); // L
        postorderTraversal(root.right); // R 
        System.out.print(root.key+" "); // D
    }

    public Node search(int key)
    {
        // Returns the pointer for the node for the searched key if exists
        // otherwise returns null
        return searchKey(root,key);
    }

    private Node searchKey(Node root,int key)
    {
        if (root==null || root.key==key)
            return root;

        if (root.key>key)
            return searchKey(root.left,key);
        else // root.key<=key
            return searchKey(root.right,key);
    }

    // Minimum value in BST
    public int minimum()
    {
        Node x=root;
        while (x.left!=null)
            x=x.left;
        return x.key;
    }

    // Maximum value in BST
    public int maximum()
    {
        Node x=root;
        while (x.right!=null)
            x=x.right;
        return x.key;
    }

    // returns a pointer to the minimum element in the subtree rooted at a given node x
    public Node treeMinimum(Node x)
    {
        while (x.left!=null)
            x=x.left;
        return x;
    }

    // returns a pointer to the maximum element in the subtree rooted at a given node x
    public Node treeMaximum(Node x)
    {
        while (x.right!=null)
            x=x.right;
        return x;
    }

    // returns the successor of a node x in a binary search tree if it exists, 
    // and NULL if x has the largest key in the tree
    public Node treeSuccessor(Node x)
    {
        if (x.right!=null)
            return treeMinimum(x.right);
        Node y=x.parent;
        while (y!=null && x==y.right)
        {
            x=y;
            y=y.parent;
        }
        return y;
    }

    // returns the predecessor of a node x in a binary search tree if it exists, 
    // and NULL if x has the largest key in the tree
    public Node treePredecessor(Node x)
    {
        if (x.left!=null)
            return treeMaximum(x.left);
        Node y=x.parent;
        while (y!=null && x==y.left)
        {
            x=y;
            y=y.parent;
        }
        return y;
    }

    // TRANSPLANT replaces the subtree rooted at node u with the subtree rooted at node v.
    // node u’s parent becomes node v’s parent, and u’s
    // parent ends up having v as its appropriate child
    public void transplant(Node u,Node v)
    {
        // If u is root of BST
        if (u.parent==null)
            root=v;

        // If u is left child of its parent
        else if (u==u.parent.left)
            u.parent.left=v;

        // If u is right child of its parent
        else // i.e u==u.parent.right
            u.parent.right=v;

        // Update parent of v
        if (v!=null)
            v.parent=u.parent;
    }

    public void delete(Node x)
    {
        // If x has no left child replace x by its right child
        if (x.left==null)
            transplant(x,x.right);

        // If x has no right child replace x by its left child
        else if (x.right==null)
            transplant(x,x.left);

        // If x has both left and right child
        else
        {
            Node y=treeMinimum(x.right); // successor of x

            // If y is x's right child then we replace x by y (Case A)
            // Otherwise y lies in x's right subtree but is not its right child then
            // we first replace y by its own right child, and then we replace x by y. (Case B)

            if (y.parent!=x) // Case B
            {
                transplant(y,y.right);
                y.right=x.right;
                y.right.parent=y;
            }
            transplant(x,y);
            y.left=x.left;
            y.left.parent=y;
        }
    }

	public void printBST()
	{
        PrintTree printer=new PrintTree(root);
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
                    String aa = String.valueOf(n.key); // Key to be printed
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

class Node 
{
    int key;
    Node left;
    Node right;
    Node parent;

    public Node (int key) 
    {
        this.key=key;
        this.left=null;
        this.right=null;
        this.parent=null;
    }
}

public class Main
{
	public static void main(String args[])
	{
		BST tree = new BST();

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
        
        tree.inorder();
        tree.preorder();
        tree.postorder();
        
        tree.printBST();

        if (tree.search(40)!=null)
            System.out.println("YES");

        System.out.println(tree.minimum()+" "+tree.maximum());

        tree.delete(tree.search(40));
        tree.printBST();
        
	}
}