import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

class Node
{
	String data;
	Node left;
	Node right;

	Node(String data)
	{
		this.data=data;
		this.left=null;
		this.right=null;
	}
}


class ExpressionTree
{
	Node root;

	int status;
	/*
	0 represents not assigned
	1 represents numeric expression
	2 represents character expression
	3 represents mixed expression
	*/ 

	public ExpressionTree()
	{
		this.root=null;
		this.status=0;
	}

	public Node getRoot()
    {
        return root;
    }

    public boolean checkEmpty()  
    {  
        if(root==null)  
            return true;  
        else   
            return false;  
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
        System.out.print(root.data+" "); // D
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
        System.out.print(root.data+" "); // D
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
        System.out.print(root.data+" "); // D
    }

   	private boolean isOperator(char ch)
   	{
   		if (ch=='+' || ch=='-'
   			|| ch=='*' || ch=='/'
   			|| ch=='^' || ch=='('
   			|| ch==')')
   			return true;
   		return false;
   	}

   	public int evaluateExpression()
    {
    	if (root==null)
    		throw new IllegalStateException("Expression to be evaluated is empty");

    	if (status==0)
    		throw new IllegalStateException("Expression to be evaluated is empty");

    	if (status==2)
    	{
    		inorder();
    		return -1;
    	}

    	return evaluate(root);
    }

    private int evaluate(Node root)
    {
    	// Leaf node
    	if (root.left==null && root.right==null)
    		return Integer.parseInt(root.data);

    	// Evaluate left and right subtree
    	int leftResult=evaluate(root.left);
    	int rightResult=evaluate(root.right);

    	return solve(leftResult,rightResult,root.data.charAt(0));
    }

    private int solve(int x,int y,char operator)
    {
    	switch (operator)
    	{
    		case '+' : return x+y;
    		case '-' : return x-y;
    		case '*' : return x*y;
    		case '/' : return x/y;
    		case '^' : return (int) Math.pow(x,y);
    		default : return -1;
    	}
    }

    public String getInfixExpression()
    {
    	if (root==null)
    		throw new NullPointerException("Expression is null");

    	String result=getInfix(root);
    	if (result.length()==1)
    		return result;
    	return result.substring(1,result.length()-1);
    }

    private String getInfix(Node root)
    {
    	if (root.left==null && root.right==null)
    		return root.data;
    	
    	String leftResult=getInfix(root.left);
    	String rightResult=getInfix(root.right);

    	return "("+leftResult+" "+root.data+" "+rightResult+")";
    }

   	/*
	1 for a numeric expression
	2 for a character expression
	3 for invalid expression
   	*/
   	private int expressionContainsSimilarElements(String postfix)
   	{
   		boolean containsDigit=false;
   		boolean containsLetter=false;

   		for (int i=0;i<postfix.length();i++)
   		{
   			char ch=postfix.charAt(i);
   			if (!isOperator(ch))
   			{
   				if (Character.isDigit(ch))
   					containsDigit=true;
   				else if (Character.isLetter(ch))
   					containsLetter=true;
   			}

   			if (containsDigit && containsLetter)
   				return 3;
   		}
   		if (containsDigit)
   			return 1;
   		else 
   			return 2;
   	}

    // Construct Tree from Postfix Expression
    public void constructTreeWithPostfix(String postfix)
    {
    	if (status==0)
    		status=expressionContainsSimilarElements(postfix);

    	if (status==3)
    		throw new IllegalArgumentException("Expression contains dissimilar elements");

    	else if (status==1)
    		constructTreeWithPostfixNumeric(postfix);
    	else //status=2
    		constructTreeWithPostfixCharacter(postfix);
    }

    private void constructTreeWithPostfixNumeric(String postfix)
    {
    	Stack<Node> stack=new Stack<Node>();
    	Node x,y,z;

    	int number=0;
    	boolean numberExists=false;
    	for (int i=0;i<postfix.length();i++)
    	{
    		char ch=postfix.charAt(i);
    		if (Character.isWhitespace(ch))
    		{
    			if (numberExists)
    			{
    				numberExists=false;
    				z=new Node(Integer.toString(number));
    				number=0;
    				stack.push(z);
    			}
    			else
    				continue;
    		}
    		else if (Character.isDigit(ch))
    		{
    			numberExists=true;
    			number=(number*10)+(ch-'0');
    		}
    		else if (isOperator(ch))
    		{
    			if (numberExists)
    			{
    				numberExists=false;
    				z=new Node(Integer.toString(number));
    				number=0;
    				stack.push(z);
    			}
    			z=new Node(Character.toString(ch));

    			// If stack doesn't contains two elements-invalid postfix expression
    			if (stack.isEmpty())
    				throw new IllegalStateException("Invalid Postfix expression");
    			x=stack.pop();
    			if (stack.isEmpty())
    				throw new IllegalStateException("Invalid Postfix expression");
    			y=stack.pop();

    			z.right=x;
    			z.left=y;

    			stack.push(z);
    		}
    	}
    	if (numberExists)
    		stack.push(new Node(Integer.toString(number)));
    	if (stack.isEmpty())
    		throw new IllegalStateException("Invalid Postfix expression");
    	this.root=stack.pop();
    	if (!stack.isEmpty())
    		throw new IllegalStateException("Invalid Postfix expression");
    }

    private void constructTreeWithPostfixCharacter(String postfix)
    {
    	Stack<Node> stack=new Stack<Node>();
    	Node x,y,z;

    	for (int i=0;i<postfix.length();i++)
    	{
    		char ch=postfix.charAt(i);
    		if (Character.isWhitespace(ch))
    			continue;
    		if (!isOperator(ch))
    		{
    			z=new Node(Character.toString(ch));
    			stack.push(z);
    		}
    		else
    		{
    			z=new Node(Character.toString(ch));

    			// If stack doesn't contains two elements-invalid postfix expression
    			if (stack.isEmpty())
    				throw new IllegalStateException("Invalid Postfix expression");
    			x=stack.pop();
    			if (stack.isEmpty())
    				throw new IllegalStateException("Invalid Postfix expression");
    			y=stack.pop();

    			z.right=x;
    			z.left=y;

    			stack.push(z);
    		}
    	}

    	if (stack.isEmpty())
    		throw new IllegalStateException("Invalid Postfix expression");
    	this.root=stack.pop();
    	if (!stack.isEmpty())
    		throw new IllegalStateException("Invalid Postfix expression");
    }

   	// Construct Tree from Infix Expression
    public void constructTreeWithInfix(String infix)
    {
    	if (status==0)
    		status=expressionContainsSimilarElements(infix);

    	if (status==3)
    		throw new IllegalArgumentException("Expression contains dissimilar elements");

    	else if (status==1)
    		constructTreeWithInfixNumeric(infix);
    	else //status=2
    		constructTreeWithInfixCharacter(infix);
    }

    private int getPriority(char ch)
    {
    	if (ch=='+' || ch=='-')
    		return 2;
    	else if (ch=='*' || ch=='/')
    		return 4;
    	else if (ch=='^')
    		return 5;
    	else if (ch=='(')
    		return 1;
    	else
    		return 0;
    }

    private void constructTreeWithInfixNumeric(String infix)
    {
    	Stack<Node> stackNodes=new Stack<Node>(); // Stack for holding nodes
    	Stack<Character> stackOperators=new Stack<Character>(); // Stack for holding operators
    	Node z;
    	stackOperators.push('#'); //priority-0

    	int number=0;
    	boolean numberExists=false;
    	for (int i=0;i<infix.length();i++)
    	{
    		char ch=infix.charAt(i);
    		if (Character.isWhitespace(ch))
    		{
    			if (numberExists)
    			{
    				numberExists=false;
    				z=new Node(Integer.toString(number));
    				number=0;
    				stackNodes.push(z);
    			}
    			else
    				continue;
    		}

    		// Add '(' as encountered
    		if (ch=='(')
    		{
    			if (numberExists)
    			{
    				numberExists=false;
    				z=new Node(Integer.toString(number));
    				number=0;
    				stackNodes.push(z);
    			}
    			stackOperators.add('(');
    		}

    		// Form numbers as encountered to add to nodes stack 
    		else if (Character.isDigit(ch))
    		{
    			numberExists=true;
    			number=(number*10)+(ch-'0');
    		}

    		else
    		{
    			if (numberExists)
    			{
    				numberExists=false;
    				z=new Node(Integer.toString(number));
    				number=0;
    				stackNodes.push(z);
    			}

    			// When ')' is encountered and pop until '(' encountered
	    		if (ch==')')
	    		{
	    			while (stackOperators.peek()!='(')
	    			{
	    				if (stackOperators.peek()=='#')
	    					throw new IllegalStateException("Invalid Infix expression");
	    				updateStackNodes(stackNodes,stackOperators);
	    			}
	    			stackOperators.pop();
	    		}

	    		// ^ case
	    		// We pop elements until priority of top element greater than priority of operand
	    		else if (ch=='^')
	    		{
	    			while (getPriority(stackOperators.peek())>getPriority(ch))
	    			{
	    				if (stackOperators.peek()=='#')
	    					throw new IllegalStateException("Invalid Infix expression");
	    				updateStackNodes(stackNodes,stackOperators);
	    			}
	    			stackOperators.push(ch);
	    		}

	    		// + - * / case
	    		// We pop elements until priority of top element greater than equal to priority of operator
	    		else if (isOperator(ch))
	    		{
	    			while (getPriority(stackOperators.peek())>=getPriority(ch))
	    			{
	    				if (stackOperators.peek()=='#')
	    					throw new IllegalStateException("Invalid Infix expression");
	    				updateStackNodes(stackNodes,stackOperators);
	    			}
	    			stackOperators.push(ch);
	    		}
    		}	
    	}

    	// After complete expression iteration if stack contains operators
    	// perform update stack nodes operation
    	while (stackOperators.peek()!='#')
    	{
    		updateStackNodes(stackNodes,stackOperators);
    	}

		if (stackNodes.isEmpty())
    		throw new IllegalStateException("Invalid Infix expression");
    	this.root=stackNodes.pop();
    	if (!stackNodes.isEmpty())
    		throw new IllegalStateException("Invalid Infix expression");  
    }

    private void constructTreeWithInfixCharacter(String infix)
    {
    	Stack<Node> stackNodes=new Stack<Node>(); // Stack for holding nodes
    	Stack<Character> stackOperators=new Stack<Character>(); // Stack for holding operators
    	Node z;
    	stackOperators.push('#'); //priority-0

    	for (int i=0;i<infix.length();i++)
    	{
    		char ch=infix.charAt(i);
    		if (Character.isWhitespace(ch)) //ignore spaces
    			continue;

    		// Add '(' as encountered
    		if (ch=='(')
    			stackOperators.add('(');

    		// Add character to nodes stack as encountered
    		else if (Character.isLetter(ch))
    		{
    			z=new Node(Character.toString(ch));
    			stackNodes.push(z);
    		}

    		// When ')' is encountered and pop until '(' encountered
    		else if (ch==')')
    		{
    			while (stackOperators.peek()!='(')
    			{
    				if (stackOperators.peek()=='#')
    					throw new IllegalStateException("Invalid Infix expression");
    				updateStackNodes(stackNodes,stackOperators);
    			}
    			stackOperators.pop();
    		}

    		// ^ case
    		// We pop elements until priority of top element greater than priority of operand
    		else if (ch=='^')
    		{
    			while (getPriority(stackOperators.peek())>getPriority(ch))
    			{
    				if (stackOperators.peek()=='#')
    					throw new IllegalStateException("Invalid Infix expression");
    				updateStackNodes(stackNodes,stackOperators);
    			}
    			stackOperators.push(ch);
    		}

    		// + - * / case
    		// We pop elements until priority of top element greater than equal to priority of operator
    		else if (isOperator(ch))
    		{
    			while (getPriority(stackOperators.peek())>=getPriority(ch))
    			{
    				if (stackOperators.peek()=='#')
    					throw new IllegalStateException("Invalid Infix expression");
    				updateStackNodes(stackNodes,stackOperators);
    			}
    			stackOperators.push(ch);
    		}
    	}

    	// After complete expression iteration if stack contains operators
    	// perform update stack nodes operation
    	while (stackOperators.peek()!='#')
    	{
    		updateStackNodes(stackNodes,stackOperators);
    	}

		if (stackNodes.isEmpty())
    		throw new IllegalStateException("Invalid Infix expression");
    	this.root=stackNodes.pop();
    	if (!stackNodes.isEmpty())
    		throw new IllegalStateException("Invalid Infix expression");    	
    }

    private void updateStackNodes(Stack<Node> stackNodes,Stack<Character> stackOperators)
    {
    	Node x,y,z;
    	z=new Node(Character.toString(stackOperators.pop()));

		// If stack nodes doesn't contains two elements-invalid infix expression
		if (stackNodes.isEmpty())
			throw new IllegalStateException("Invalid Infix expression");
		x=stackNodes.pop();
		if (stackNodes.isEmpty())
			throw new IllegalStateException("Invalid Infix expression");
		y=stackNodes.pop();

		z.right=x;
		z.left=y;

		stackNodes.push(z);
    }

    // Construct Tree from Prefix Expression
    public void constructTreeWithPrefix(String prefix)
    {
    	if (status==0)
    		status=expressionContainsSimilarElements(prefix);

    	if (status==3)
    		throw new IllegalArgumentException("Expression contains dissimilar elements");

    	else if (status==1)
    		constructTreeWithPrefixNumeric(prefix);
    	else //status=2
    		constructTreeWithPrefixCharacter(prefix);
    }

    private void constructTreeWithPrefixNumeric(String prefix)
    {
    	Stack<Node> stack=new Stack<Node>();
    	Node x,y,z;

    	String number="";
    	boolean numberExists=false;
    	for (int i=prefix.length()-1;i>=0;i--)
    	{
    		char ch=prefix.charAt(i);
    		if (Character.isWhitespace(ch))
    		{
    			if (numberExists)
    			{
    				numberExists=false;
    				StringBuilder reverseNumber=new StringBuilder(number);
    				reverseNumber.reverse();
    				z=new Node(reverseNumber.toString());
    				number="";
    				stack.push(z);
    			}
    			else
    				continue;
    		}
    		else if (Character.isDigit(ch))
    		{
    			numberExists=true;
    			number=number+ch;
    		}
    		else if (isOperator(ch))
    		{
    			if (numberExists)
    			{
    				numberExists=false;
    				StringBuilder reverseNumber=new StringBuilder(number);
    				reverseNumber.reverse();
    				z=new Node(reverseNumber.toString());
    				number="";
    				stack.push(z);
    			}
    			z=new Node(Character.toString(ch));

    			// If stack doesn't contains two elements-invalid prefix expression
    			if (stack.isEmpty())
    				throw new IllegalStateException("Invalid Prefix expression");
    			x=stack.pop();
    			if (stack.isEmpty())
    				throw new IllegalStateException("Invalid Prefix expression");
    			y=stack.pop();

    			z.left=x;
    			z.right=y;

    			stack.push(z);
    		}
    	}
    	if (numberExists)
    	{
    		numberExists=false;
			StringBuilder reverseNumber=new StringBuilder(number);
			reverseNumber.reverse();
			z=new Node(reverseNumber.toString());
			number="";
			stack.push(z);
    	}
    	if (stack.isEmpty())
    		throw new IllegalStateException("Invalid Postfix expression");
    	this.root=stack.pop();
    	if (!stack.isEmpty())
    		throw new IllegalStateException("Invalid Postfix expression");
    }

    private void constructTreeWithPrefixCharacter(String prefix)
    {
    	Stack<Node> stack=new Stack<Node>();
    	Node x,y,z;

    	for (int i=prefix.length()-1;i>=0;i--)
    	{
    		char ch=prefix.charAt(i);
    		if (Character.isWhitespace(ch))
    			continue;
    		if (!isOperator(ch))
    		{
    			z=new Node(Character.toString(ch));
    			stack.push(z);
    		}
    		else
    		{
    			z=new Node(Character.toString(ch));

    			// If stack doesn't contains two elements-invalid prefix expression
    			if (stack.isEmpty())
    				throw new IllegalStateException("Invalid Prefix expression");
    			x=stack.pop();
    			if (stack.isEmpty())
    				throw new IllegalStateException("Invalid Prefix expression");
    			y=stack.pop();

    			z.left=x;
    			z.right=y;

    			stack.push(z);
    		}
    	}
    	if (stack.isEmpty())
    		throw new IllegalStateException("Invalid Prefix expression");
    	this.root=stack.pop();
    	if (!stack.isEmpty())
    		throw new IllegalStateException("Invalid Prefix expression");
    }

    public void print()
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
                    String aa = String.valueOf(n.data); // Key to be printed
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
		ExpressionTree tree = new ExpressionTree();
		tree.constructTreeWithPostfix("abcd/+*");
		tree.print();
		tree.inorder();
		System.out.println(tree.evaluateExpression());
		System.out.println(tree.getInfixExpression());
		tree.postorder();
	}
}
