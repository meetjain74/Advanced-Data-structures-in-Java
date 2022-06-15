class Main
{
	public static void quickSort(int array[])
	{
		quickSort(array,0,array.length-1);
	}
	
	public static void quickSort(int array[],int p,int r)
	{
		if (p<r)
		{
			int q=partition(array,p,r);
			quickSort(array,p,q-1);
			quickSort(array,q+1,r);	
		}
	}
	
	public static void swapAtIndex(int array[],int x,int y)
	{
		int temp=array[x];
		array[x]=array[y];
		array[y]=temp;
	}
	
	public static int partition(int array[],int p,int r)
	{
		int pivot=array[r];
		int i=p-1;
		int temp;
		for (int j=p;j<=r-1;j++)
		{
			if (array[j]<=pivot)
				swapAtIndex(array,++i,j);
		}
		swapAtIndex(array,++i,r);
		return i;
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
		quickSort(arr);
		displayArray(arr);
	}
}

