class DisjointSet
{
	int n;
	int parent[];
	int rank[];

	DisjointSet(int n)
	{
		this.n=n;
		parent=new int[n];
		rank=new int[n];
		makeSet();
	}

	public void makeSet(int x)
	{
		if (x>=n)
			throw new IllegalArgumentException("makeSet takes parameter value which is not available");
		parent[x]=-1; // -1 represents elements are in their own set
		rank[x]=0;
	}

	private void makeSet()
	{
		for (int i=0;i<n;i++)
			makeSet(i);
	}

	// Find absolute parent of x
	public int find(int x)
	{
		if (parent[x]==-1)
			return x;
		return parent[x]=find(parent[x]);
	}

	// Union to make absolute parent of both same
	public void union(int x,int y)
	{
		int xParent=find(x);
		int yParent=find(y);
		int xRank=rank[xParent];
		int yRank=rank[yParent];

		// If elements are in same set - no need for union
		if (xParent==yParent)
			return;

		// If x's rank is greater than y's rank
		if (xRank>yRank)
			parent[yParent]=xParent;

		// Else If y's rank is greater than x's rank 
		else if (yRank>xRank)
			parent[xParent]=yParent;

		// If both ranks are equal
		else
		{
			// Move y under x 
			parent[yParent]=xParent;
			// Increment rank by 1 for resultant tree
			rank[xParent]++;
		}
	}

	public void display()
	{
		System.out.print("Parent array : ");
		for (int i=0;i<n;i++)
			System.out.print(parent[i]+" ");
		System.out.print("\nRank array : ");
		for (int i=0;i<n;i++)
			System.out.print(rank[i]+" ");
		System.out.println();
	}
}


public class Main
{
	public static void main(String args[])
	{
		DisjointSet set = new DisjointSet(10);
		set.union(0,1);
		set.union(1,7);
		set.union(7,6);
		set.union(3,8);
		set.union(8,3);
		set.union(3,4);
		set.union(4,9);
		set.union(5,2);
		set.union(2,8);
		set.display();
	}
}