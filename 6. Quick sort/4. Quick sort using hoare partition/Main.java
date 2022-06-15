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
			int q=hoare_partition(array,p,r);
			quickSort(array,p,q);
			quickSort(array,q+1,r);	
		}
	}
	
	public static void swapAtIndex(int array[],int x,int y)
	{
		int temp=array[x];
		array[x]=array[y];
		array[y]=temp;
	}
	
	public static int hoare_partition(int array[],int p,int r)
	{
		int pivot=array[p];
		int i=p-1;
		int j=r+1;
		while(true)
		{
			do{
				i++;
			}while(array[i]<pivot);

			do{
				j--;
			}while(array[j]>pivot);

			if (i>=j)
				return j;

			swapAtIndex(array,i,j);
		}
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