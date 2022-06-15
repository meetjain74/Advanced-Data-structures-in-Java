import java.util.Scanner;

/*Merge sort
if (right>left)
	1. Find the middle point to divide the array into two halves:  
	    middle mid = (left+right)/2
	2. Call mergeSort for first half of array:   
	    Call mergeSort(arr, left, mid)
	3. Call mergeSort for second half of array:
	    Call mergeSort(arr, mid+1, right)
	4. Merge the two halves sorted in step 2 and 3:
	    Call merge(arr, left, mid, right)
*/


class Main
{
	public static void merge(int arr[],int left,int right,int mid)
	{
		/*Calculate size of the two subarrays-left and right*/
		int n1=mid-left+1;
		int n2=right-mid;

		/*Create temporary left and right arrays*/
		int leftArray[]=new int[n1];
		int rightArray[]=new int[n2];

		/*Copy data from array to temporary arrays*/
		for (int i=0;i<n1;i++)
			leftArray[i]=arr[left+i];
		for (int i=0;i<n2;i++)
			rightArray[i]=arr[mid+1+i];

		/*Initial index of both subarrays is zero*/
		int i=0,j=0;

		/*Initial index of merged subarray*/
		int k=left;

		while (i<n1 && j<n2)
		{
			if (leftArray[i]<rightArray[j])
			{
				arr[k]=leftArray[i];
				i++;
			}
			else
			{
				arr[k]=rightArray[j];
				j++;
			}
			k++;
		}

		/*Add remaining elements of leftArray(if remaining)*/
		while (i<n1)
		{
			arr[k]=leftArray[i];
			i++;
			k++;
		} 

		/*Add remaining elements of rightArray(if remaining)*/
		while (j<n2)
		{
			arr[k]=rightArray[j];
			j++;
			k++;
		} 

	}

	public static void mergeSort(int arr[],int left,int right)
	{
		/*Base case*/
		if (left>=right)
			return;

		int mid=(left+right)/2;
		mergeSort(arr,left,mid);
		mergeSort(arr,mid+1,right);
		merge(arr,left,right,mid);
	}

	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int arr[]=new int[n];
		for (int i=0;i<n;i++)
			arr[i]=sc.nextInt();

		mergeSort(arr,0,n-1);
		for (int i=0;i<n;i++)
			System.out.print(arr[i]+" ");
	}
}