class Main
{
	public static void quickSort_3way(int array[])
	{
		quickSort_3way(array,0,array.length-1);
	}
		
	public static void quickSort_3way(int array[],int p,int r)
	{
		if (p<r)
		{
			int q[]=partition(array,p,r);
			quickSort_3way(array,p,q[0]-1);
			quickSort_3way(array,q[1]+1,r);	
		}
	}
		
	public static void swapAtIndex(int array[],int x,int y)
	{
		int temp=array[x];
		array[x]=array[y];
		array[y]=temp;
	}
		
	public static int[] partition(int array[],int p,int r)
	{
		int pivot=array[r];
		int i=p;
		int k=r;
		for (int j=p;j<k;j++)
		{
			if (array[j]<pivot)
			{
				swapAtIndex(array,i++,j);
			}
			else if (array[j]>pivot)
			{
				swapAtIndex(array,--k,j);
				j--;
			}
		}
		swapAtIndex(array,k,r);
		int indexes[]=new int[] {i,k};
		return indexes;
	}

	public static void displayArray(int array[])
	{
		for (int i=0;i<array.length;i++)
			System.out.print(array[i]+" ");
		System.out.println();
	}

	public static void main(String args[])
	{
		int arr[]=new int[] {13,27,89,4,12,65,78,45,69,12,4,56,78,23};
		quickSort_3way(arr);
		displayArray(arr);
	}
}