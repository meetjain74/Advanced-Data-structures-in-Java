class Main2
{
	public static int min(int a,int b,int c)
	{
		return min(a,min(b,c));
	}

	public static int max(int a,int b,int c)
	{
		return max(a,max(b,c));
	}

	public static int min(int a,int b)
	{
		return (a<b)?a:b;
	}

	public static int max(int a,int b)
	{
		return (a>b)?a:b;
	}

	/*
	As the maximum product can also be achieved by the previous minimum(-ve) product
	hence we calculate both maxProduct and minProduct possible
	Set initial maxProduct and minProduct to array[0]
	and also set result to array[0]
	We can have two posible values a and b which is
	a=minProduct*array[i] and b=maxProduct*array[i]
	Now we again need to calculate our minProduct and maxProuct as
	minProduct=min(array[i],a,b)
	maxProduct=max(array[i],a,b)
	and finally result is the maximum of the two maxProduct and result
	*/
	public static int getMaximumSubarrayProduct(int array[])
	{
		int minProduct,maxProduct,result;
		minProduct=array[0];
		maxProduct=array[0];
		result=array[0];
		int a,b;

		/*Start from index 1 as index 0 already considered*/
		for (int i=1;i<array.length;i++)
		{
			a=minProduct*array[i];
			b=maxProduct*array[i];

			/*Also consider array[i] because it can be smaller or larger
			than both a and b and we need to consider that*/
			minProduct=min(array[i],a,b);
			maxProduct=max(array[i],a,b);

			result=max(maxProduct,result);
		}
		return result;
	}

	public static void main(String args[])
	{
		int array[]=new int[] {6, -3, -10, 0, 2};
		System.out.println(getMaximumSubarrayProduct(array));
	}
}


/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
