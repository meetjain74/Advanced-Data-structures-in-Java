/*Kadane's Algorithm*/

/*
Initialize:
    max_sum = 0
    temp_sum = 0

Loop for each element of the array
  (a) temp_sum = temp_sum + a[i]
  (b) if(max_sum < temp_sum)
            max_sum = temp_sum
  (c) if(temp_sum < 0)
            temp_sum = 0
return max_sum

*/



class Main
{
	public static int getMaximumSubarraySum(int array[])
	{
		int max_sum=0;
		int temp_sum=0;
		for (int i=0;i<array.length;i++)
		{
			temp_sum=temp_sum+array[i];
			if (temp_sum<0)
				temp_sum=0;
			else if (max_sum<temp_sum)
				max_sum=temp_sum;
		}
		return max_sum;
	}

	public static void main(String args[])
	{
		int array[]=new int[] {13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
		System.out.println(getMaximumSubarraySum(array));
	}
}

/*This algorithm does not works for all negative elements in the array*/