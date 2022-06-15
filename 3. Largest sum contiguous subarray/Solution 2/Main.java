class Main
{
	public static int max(int a,int b)
	{
		return (a>b)?a:b;
	}

	public static int getMaximumSubarraySum(int array[])
	{
		int max_sum=array[0];
		int temp_sum=array[0];
		/*Start from 1 because index 0 already included*/
		for (int i=1;i<array.length;i++)
		{
			/*Set temp_sum to maximum of the two - array[i] and temp_sum+array[i]*/
			temp_sum=max(array[i],temp_sum+array[i]);

			/*Set maximum sum to maximum of the two - temp_sum and max_sum*/
			max_sum=max(max_sum,temp_sum);
		}
		return max_sum;
	}

	public static void main(String args[])
	{
		int array[]=new int[] {13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
		System.out.println(getMaximumSubarraySum(array));
	}
}
