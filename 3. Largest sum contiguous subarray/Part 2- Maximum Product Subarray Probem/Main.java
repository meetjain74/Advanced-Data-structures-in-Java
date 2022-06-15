class Main
{
	public static int max(int a,int b)
	{
		return (a>b)?a:b;
	}

	/*We consider all the possible subarrays and calculate its product and
	find the maximum product*/
	public static int getMaximumSubarrayProduct(int array[])
	{
		int temp_product=1;
		int max_product=1;
		for (int i=0;i<array.length;i++)
		{
			temp_product=array[i];
			for (int j=i+1;j<array.length;j++)
			{
				max_product=max(max_product,temp_product);
				temp_product=temp_product*array[j];
			}
			
			/*for the last index*/
			max_product=max(max_product,temp_product);
		}
		return max_product;
	}

	public static void main(String args[])
	{
		int array[]=new int[] {6, -3, -10, 0, 2};
		System.out.println(getMaximumSubarrayProduct(array));
	}
}


/*
Time Complexity - O(n^2)
Space Complexity - O(1)
*/