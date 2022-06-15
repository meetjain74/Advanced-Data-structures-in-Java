/*To get the maximum subarray too i.e also display the maximum subarray*/

class Main
{
	public static int max(int a,int b)
	{
		return (a>b)?a:b;
	}

	public static int getMaximumSubarraySum(int array[])
	{
		int start_index=0;
		int end_index=0;
		int temp_start=0;
		int max_sum=array[0];
		int temp_sum=array[0];
		for (int i=1;i<array.length;i++)
		{
			/*Set initial temp_sum as temp_sum+array[i]*/
			temp_sum=temp_sum+array[i];	

			/*If temp_sum becomes negative then set temp_sum to current
			array index value*/
			if (temp_sum<0)
			{
				temp_start=i;
				temp_sum=array[i];
			}

			/*If temp_sum exceeds the max_sum we assign max_sum equal to
			temp_sum and also specify the start_index and end_i*/
			if (max_sum<temp_sum)
			{
				start_index=temp_start;
				end_index=i;
				max_sum=temp_sum;
			}
		}
		displayArray(array,start_index,end_index);
		return max_sum;
	}

	public static void displayArray(int array[],int start,int end)
	{
		for (int i=start;i<=end;i++)
			System.out.print(array[i]+" ");
		System.out.println();
	}

	public static void main(String args[])
	{
		int array[]=new int[] {13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
		System.out.println(getMaximumSubarraySum(array));
	}
}
