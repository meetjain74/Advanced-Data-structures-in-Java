import java.lang.Math;

abstract class Heap
{
	int heap[];
	int capacity; /*Maximum possible size of heap*/
	int size; /*Current size of heap*/

	/*To create an empty heap with maximum capacity*/
	/*We consider heap indexes starting from 1*/
	public Heap(int capacity)
	{
		this.capacity=capacity;
		this.size=0;
		heap=new int[capacity+1];
		heap[0]=Integer.MAX_VALUE;
	}

	public Heap(int arr[])
	{
		this.capacity=arr.length;
		this.size=arr.length;
		heap=new int[capacity+1];
		heap[0]=Integer.MAX_VALUE;
		for (int i=0;i<size;i++)
			heap[i+1]=arr[i];
		buildHeap();
	}

	public Heap(int arr[],int capacity)
	{
		this.capacity=capacity;
		this.size=arr.length;
		heap=new int[capacity+1];
		heap[0]=Integer.MAX_VALUE;
		for (int i=0;i<size;i++)
			heap[i+1]=arr[i];
		buildHeap();
	}

	/*Returns parent index of any value*/
	public int parent(int i)
	{
		return i/2;
	}

	/*Returns left child index*/
	public int leftChild(int i)
	{
		return 2*i;
	}

	/*Returns right child index*/
	public int rightChild(int i)
	{
		return 2*i+1;
	}

	/*Checks whether given node is leaf or not
	-Leaf nodes are from floor(size/2)+1 to size*/
	public boolean isLeaf(int i)
	{
		if (i>=(this.size/2)+1 && i<=this.size)
			return true;
		return false;
	}

	/*Check whether heap is empty or not*/
	public boolean isEmpty()
	{
		return size==0;
	}

	private int log2(int x)
	{
		return (int)(Math.log(x)/Math.log(2)); 
	}

	/*To get the height of the heap
	we define the height of a node in a heap to be the 
	number of edges on the longest simple downward path 
	from the node to a leaf, and we define the height of 
	the heap to be the height of its root*/
	public int getHeight()
	{
		return log2(this.size);
	}

	/*To insert an element in heap*/
	public abstract void insert(int element);

	/*To delete element from particular index*/
	public abstract int delete(int index);

	/*To heapify the heap- called so as to maintain the heap property.
	This function assumes that the left and right subtrees are already 
	heapified, we only need to fix the root. */
	/*Time complexity - O(log n) or O(h) where h is height of heap*/
	public abstract void heapify(int index);

	private void printSpaces(int offset)
	{
		for (int i=0;i<offset;i++)
			System.out.print(" ");
	}

	public void display()
	{
		System.out.println("The heap is ->");
		int height=getHeight();
		for (int i=1;i<=size;i++)
		{
			int level=log2(i)+1;
			int offset=(height-level+2)*2;
			printSpaces(offset);
			System.out.print(heap[i]);
			if ((int)Math.pow(2,level)-1==i)
				System.out.println();
		}
		System.out.println();
	} 

	public void buildHeap()
	{
		for (int i=size/2;i>=1;i--)
			heapify(i);
	}

	public int[] toArray()
	{
		int arr[]=new int[this.size];
		for (int i=0;i<this.size;i++)
			arr[i]=heap[i+1];
		return arr;
	}
}

class MaxHeap extends Heap
{
	public MaxHeap(int capacity)
	{
		super(capacity);
	}

	public MaxHeap(int arr[])
	{
		super(arr);
	}

	public MaxHeap(int arr[],int capacity)
	{
		super(arr,capacity);
	}

	public void insert(int element)
	{
		this.size+=1;
		if (size>capacity)
			throw new RuntimeException("More elements can't be inserted");
		heap[this.size]=Integer.MIN_VALUE;
		heapIncreaseKey(this.size,element);
	}

	public int heapMaximum()
	{
		return heap[1];
	}

	public int extractMax()
	{
		if (this.size<1)
			throw new RuntimeException("Heap Underflow");
		int max=heap[1];
		heap[1]=heap[this.size];
		this.size-=1;
		heapify(1);
		return max;
	}

	public void heapIncreaseKey(int index,int key)
	{
		if (key<heap[index])
			throw new RuntimeException("New Key is smaller than current key");
		while (index>1 && heap[parent(index)]<key)
		{
			heap[index]=heap[parent(index)];
			index=parent(index);
		}
		heap[index]=key;
	}

	private void swapAtIndex(int i,int largest)
	{
		int temp=heap[i];
		heap[i]=heap[largest];
		heap[largest]=temp;
	}

	private int getMaxIndexAmongFamily(int index)
	{
		int left=leftChild(index);
		int right=rightChild(index);
		int largest=index;
		if (left<=size && heap[left]>heap[index])
			largest=left;
		if (right<=size && heap[right]>heap[largest])
			largest=right;
		return largest;
	}

	public void heapify(int index)
	{
		if (isLeaf(index))
			return;
		int largest;
		/*Largest is the index of maximum of the three-
		parent,its left child and its right child*/
		largest=getMaxIndexAmongFamily(index);
		if (largest!=index)
		{
			swapAtIndex(index,largest);
			heapify(largest);
		}
	}

	public int delete(int index)
	{
		if (index>this.size)
			throw new RuntimeException("Index out of range");
		int value=heap[index];
		if (heap[index]>heap[this.size])
		{
			heap[index]=heap[this.size];
			heapify(index);
		}
		else
			heapIncreaseKey(index,heap[this.size]);
		this.size-=1;
		return value;
	}
}

class MinHeap extends Heap
{
	public MinHeap(int capacity)
	{
		super(capacity);
	}

	public MinHeap(int arr[])
	{
		super(arr);
	}

	public MinHeap(int arr[],int capacity)
	{
		super(arr,capacity);
	}

	public void insert(int element)
	{
		this.size+=1;
		if (size>capacity)
			throw new RuntimeException("More elements can't be inserted");
		heap[this.size]=Integer.MAX_VALUE;
		heapDecreaseKey(this.size,element);
	}

	public int heapMinimum()
	{
		return heap[1];
	}

	public int extractMin()
	{
		if (this.size<1)
			throw new RuntimeException("Heap Underflow");
		int min=heap[1];
		heap[1]=heap[this.size];
		this.size-=1;
		heapify(1);
		return min;
	}

	public void heapDecreaseKey(int index,int key)
	{
		if (key>heap[index])
			throw new RuntimeException("New Key is larger than current key");
		while (index>1 && heap[parent(index)]>key)
		{
			heap[index]=heap[parent(index)];
			index=parent(index);
		}	
		heap[index]=key;
	}

	private void swapAtIndex(int i,int largest)
	{
		int temp=heap[i];
		heap[i]=heap[largest];
		heap[largest]=temp;
	}

	private int getMinIndexAmongFamily(int index)
	{
		int left=leftChild(index);
		int right=rightChild(index);
		int smallest=index;
		if (left<=size && heap[left]<heap[index])
			smallest=left;
		if (right<=size && heap[right]<heap[smallest])
			smallest=right;
		return smallest;
	}

	public void heapify(int index)
	{
		if (isLeaf(index))
			return;
		int smallest;
		/*Smallest is the index of minimum of the three-
		parent,its left child and its right child*/
		smallest=getMinIndexAmongFamily(index);
		if (smallest!=index)
		{
			swapAtIndex(index,smallest);
			heapify(smallest);
		}
	}

	public int delete(int index)
	{
		if (index>this.size)
			throw new RuntimeException("Index out of range");
		int value=heap[index];
		if (heap[index]<heap[this.size])
		{
			heap[index]=heap[this.size];
			heapify(index);
		}
		else
			heapDecreaseKey(index,heap[this.size]);
		this.size-=1;
		return value;
	}
}

class Main
{
	public static void main(String args[])
	{
		int arr[]=new int[] {15,13,9,5,12,8,7,4,0,6,2,1};	
		MinHeap mh=new MinHeap(arr,20);
		mh.display();
		mh.insert(10);
		mh.insert(26);
		mh.display();
		System.out.println(mh.delete(5));
		//System.out.println(mh.extractMax());
		mh.display();	
	}
}