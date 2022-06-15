import java.util.Random;

class Main
{
	public static void randomised_QuickSort(int array[])
	{
		randomised_QuickSort(array,0,array.length-1);
	}
			
	public static void randomised_QuickSort(int array[],int p,int r)
	{
		if (p<r)
		{
			int q[]=randomised_partition(array,p,r);
			randomised_QuickSort(array,p,q[0]-1);
			randomised_QuickSort(array,q[1]+1,r);	
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
	
	public static int[] randomised_partition(int array[],int p,int r)
	{
		Random rand=new Random();
		int swap_index=p+rand.nextInt(r-p);
		swapAtIndex(array,swap_index,r);
		return partition(array,p,r);
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
		randomised_QuickSort(arr);
		displayArray(arr);
	}
}