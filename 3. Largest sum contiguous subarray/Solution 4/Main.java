/*Maximum subarray sum using divide and conquer technique*/

/*
We divide the array A[low,high] into two subarrays of equal size-
A[low,mid] and A[mid+1,high] where mid=low+high/2
Any contiguous subarray (that we need to find) A[i,j] of A[low,high]
must lie in one of the following three places- 
1. Entirely in the subarray A[low,mid] 
	so that low<=i<=j<=mid
2. Entirely in the subarray A[mid+1,high] 
	so that mid<i<=j<=high
3. Crossing the midpoint 
	so that low<=i<=mid<=j<=high
i.e take subarray with maximum sum of the three.

We can find maximum subarrays of A[low,mid] and A[mid+1,high] recursively.
*/

/*To store the low index, high index and max sum in one 
object we create the Result class*/
class Result
{
	int low_index;
	int high_index;
	int max_sum;

	Result(int low_index,int high_index,int max_sum)
	{
		this.low_index=low_index;
		this.high_index=high_index;
		this.max_sum=max_sum;
	}

	Result()
	{
		this.low_index=0;
		this.high_index=0;
		this.max_sum=0;
	}

	public int getMaxSum()
	{
		return max_sum;
	}

	public int getLowIndex()
	{
		return low_index;
	}

	public int getHighIndex()
	{
		return high_index;
	}
}


class Main
{
	public static Result maxOnBasisOfSum(Result r1,Result r2)
	{
		int a=r1.getMaxSum();
		int b=r2.getMaxSum();
		return (a>b)?r1:r2;
	}

	public static Result findMaxCrossingSubarray(int array[],int low,int mid,int high)
	{
		int left_sum=array[mid];
		int right_sum=array[mid+1];
		int sum=0;
		int max_left=mid,max_right=mid+1;

		/*Find the maximum subarray for the left part*/
		/*Since A[mid] should exist we start from mid to low*/
		for (int i=mid;i>=low;i--)
		{
			sum=sum+array[i];
			if (sum>left_sum)
			{
				left_sum=sum;
				max_left=i;
			}
		}

		/*Same working for the right half*/
		sum=0;
		for (int i=mid+1;i<=high;i++)
		{
			sum=sum+array[i];
			if (sum>right_sum)
			{
				right_sum=sum;
				max_right=i;
			}
		}

		/*returns the indices max_left and max_right that demarcate 
		a maximum subarray crossing the midpoint, along with
		the sum left_sum+right_sum of the values in the subarray A[max_left,max_right] */
		Result r_cross=new Result(max_left,max_right,left_sum+right_sum);
		return r_cross;
	}

	public static Result findMaximumSubarray(int array[],int low,int high)
	{
		Result r_left=new Result();
		Result r_right=new Result();
		Result r_cross=new Result();
		Result r_answer=new Result();

		/*Base case -
		If array has only one element and hence max_sum is array[low] */
		if (high==low)
			return new Result(low,high,array[low]);

		/*Recursive case */
		else
		{
			int mid=(low+high)/2;

			/*left subarray*/
			r_left=findMaximumSubarray(array,low,mid);

			/*right subarray*/
			r_right=findMaximumSubarray(array,mid+1,high);

			/*Cross subarray*/
			r_cross=findMaxCrossingSubarray(array,low,mid,high);

			r_answer=maxOnBasisOfSum(r_left,maxOnBasisOfSum(r_right,r_cross));
		}

		return r_answer;
	}

	public static int getMaximumSubarraySum(int array[])
	{
		Result r=findMaximumSubarray(array,0,array.length-1);
		displayArray(array,r.getLowIndex(),r.getHighIndex());
		return r.getMaxSum();
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


/*
Time Complexity-O(nlogn)
Space Complexity-O(1)
*/