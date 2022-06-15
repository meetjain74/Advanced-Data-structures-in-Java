import java.lang.Math;

abstract class d_ary_Heap
{
	int heap[];
	int capacity; /*Maximum possible size of heap*/
	int size; /*Current size of heap*/
	int d;

	/*To create an empty heap with maximum capacity*/
	/*We consider heap indexes starting from 1*/
	public d_ary_Heap(int capacity,int d)
	{
		this.capacity=capacity;
		this.size=0;
		heap=new int[capacity+1];
		heap[0]=Integer.MAX_VALUE;
		this.d=d;
	}

	public d_ary_Heap(int arr[],int d)
	{
		this.capacity=arr.length;
		this.size=arr.length;
		this.d=d;
		heap=new int[capacity+1];
		heap[0]=Integer.MAX_VALUE;
		for (int i=0;i<size;i++)
			heap[i+1]=arr[i];
		buildHeap();
	}

	public d_ary_Heap(int arr[],int capacity,int d)
	{
		this.capacity=capacity;
		this.size=arr.length;
		this.d=d;
		heap=new int[capacity+1];
		heap[0]=Integer.MAX_VALUE;
		for (int i=0;i<size;i++)
			heap[i+1]=arr[i];
		buildHeap();
	}

	/*Returns parent index of any value*/
	public int parent(int i)
	{
		if (i<=1)
			throw new RuntimeException("Invalid index to calculate parent");
		return ((i-2)/d)+1;
	}

	/*To get jth child for an index*/
	/*hence 1<=j<=d */
	public int getChild(int i,int j)
	{
		return d*(i-1)+j+1;
	}

	private int log(int x,int base)
	{
		return (int)(Math.log(x)/Math.log(base));
	}

	/*To get the height of the d-ary heap*/
	public int getHeight()
	{
		return log(this.size,d);
	}

	/*Checks whether given node is leaf or not
	-Leaf nodes are from parent(this.size)+11 to size*/
	public boolean isLeaf(int i)
	{
		if (i>=parent(this.size)+1 && i<=this.size)
			return true;
		return false;
	}

	/*Check whether heap is empty or not*/
	public boolean isEmpty()
	{
		return size==0;
	}

	public void displayHeap()
	{
		System.out.println("The heap is ->");
		for (int i=1;i<=this.size;i++)
			System.out.print(heap[i]+" ");
		System.out.println();
	}

	public abstract void heapify(int index);

	public void buildHeap()
	{
		for (int i=parent(this.size);i>=1;i--)
			heapify(i);
	}

	public abstract void insert(int element);

	public int[] toArray()
	{
		int arr[]=new int[this.size];
		for (int i=0;i<this.size;i++)
			arr[i]=heap[i+1];
		return arr;
	}

	public abstract int delete(int index);
}

class d_ary_MaxHeap extends d_ary_Heap
{
	public d_ary_MaxHeap(int capacity,int d)
	{
		super(capacity,d);
	}

	public d_ary_MaxHeap(int arr[],int d)
	{
		super(arr,d);
	}

	public d_ary_MaxHeap(int arr[],int capacity,int d)
	{
		super(arr,capacity,d);
	}

	public int heapMaximum()
	{
		return heap[1];
	}

	private void swapAtIndex(int i,int largest)
	{
		int temp=heap[i];
		heap[i]=heap[largest];
		heap[largest]=temp;
	}

	private int getMaxIndexAmongFamily(int index)
	{
		int largest=index;
		int child;
		for (int i=1;i<=d;i++)
		{
			child=getChild(index,i);
			if (child>this.size)
				break;
			if (heap[child]>heap[largest])
				largest=child;
		}
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

	public void insert(int element)
	{
		this.size+=1;
		if (size>capacity)
			throw new RuntimeException("More elements can't be inserted");
		heap[this.size]=Integer.MIN_VALUE;
		heapIncreaseKey(this.size,element);
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

class d_ary_MinHeap extends d_ary_Heap
{
	public d_ary_MinHeap(int capacity,int d)
	{
		super(capacity,d);
	}

	public d_ary_MinHeap(int arr[],int d)
	{
		super(arr,d);
	}

	public d_ary_MinHeap(int arr[],int capacity,int d)
	{
		super(arr,capacity,d);
	}

	public int heapMinimum()
	{
		return heap[1];
	}

	private void swapAtIndex(int i,int largest)
	{
		int temp=heap[i];
		heap[i]=heap[largest];
		heap[largest]=temp;
	}

	private int getMinIndexAmongFamily(int index)
	{
		int smallest=index;
		int child;
		for (int i=1;i<=d;i++)
		{
			child=getChild(index,i);
			if (child>this.size)
				break;
			if (heap[child]<heap[smallest])
				smallest=child;
		}
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

	public void insert(int element)
	{
		this.size+=1;
		if (size>capacity)
			throw new RuntimeException("More elements can't be inserted");
		heap[this.size]=Integer.MAX_VALUE;
		heapDecreaseKey(this.size,element);
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
		d_ary_MaxHeap dmh=new d_ary_MaxHeap(arr,3);
		dmh.displayHeap();
		dmh.delete(3);
		dmh.displayHeap();
		dmh.insert(20);
		dmh.displayHeap();
	}
}

