/*
Method 2 -
precomputations of same subproblems can be avoided by constructing 
a temporary array K[][] in bottom-up manner.

In the Dynamic programming we will work considering the same cases as 
mentioned in the recursive approach. 
In a K[][] table let’s consider all the possible weights from ‘1’ to ‘W’ 
as the columns and weights that can be kept as the rows. 

The state K[i][j] will denote maximum value of ‘j-weight’ considering 
all values from ‘1 to ith’. 

So if we consider ‘wi’ (weight in ‘ith’ row) we can fill it in 
all columns which have ‘weight values > wi’. Now two possibilities can take place: 

Fill ‘wi’ in the given column.
Do not fill ‘wi’ in the given column.

Now we have to take a maximum of these two possibilities, 
formally if we do not fill ‘ith’ weight in ‘jth’ column 
then K[i][j] state will be same as K[i-1][j] but 
if we fill the weight, K[i][j] will be equal to 
the value of ‘wi’+ value of the column weighing ‘j-wi’ 
in the previous row. 
So we take the maximum of these two possibilities to fill the current state

*/

class Main
{
	public static int max(int a,int b)
	{
		return (a>b)?a:b;
	}

	public static int knapsack(int wt[],int val[],int knapsackCapacity,int numberOfElements)
	{
		int K[][]=new int[numberOfElements+1][knapsackCapacity+1];

		for (int i=0;i<=numberOfElements;i++)
		{
			for (int w=0;w<=knapsackCapacity;w++)
			{
				if (i==0 || w==0)
					K[i][w]=0;

				/*If the weight of ‘ith’ item is greater than ‘W’, 
				then the ith item cannot be included*/
				else if (wt[i-1]>w)
					K[i][w]=K[i-1][w];

				/*Return the maximum of twp cases-
				(1) ith item included
				(2) ith item not included*/
				else
					K[i][w]=max(K[i-1][w],val[i-1]+K[i-1][w-wt[i-1]]);

				//System.out.println(i+" "+w+" "+K[i][w]);
			}
		}
		return K[numberOfElements][knapsackCapacity];
	}

	public static void main(String args[])
	{
		int weights[]=new int[] {10,20,30};
		int values[]=new int[] {60,100,120};
		int capacity=50;
		int numberOfElements=weights.length;
		System.out.println(knapsack(weights,values,capacity,numberOfElements));
	}
}

/*
Time Complexity -
O(N*W)
where ‘N’ is the number of weight elements and ‘W’ is capacity. 
As for every weight element we traverse through all weight capacities 1<=w<=W.

Space Complexity- 
O(N*W). 
The use of 2-D array of size ‘N*W’.
*/


/*

Method 3 -
This method is basically an extension to the recursive approach(Method-1) 
so that we can overcome the problem of calculating redundant cases and 
thus increased complexity. We can solve this problem by simply creating a 2-D array 
that can store a particular state (n, w) if we get it the first time. 

Now if we come across the same state (n, w) again instead of calculating it 
in exponential complexity we can directly return its result stored in the table 
in constant time. This method gives an edge over the recursive approach in this aspect.

Simply first instantiate the whole 2D array with -1.
Check this condition in the recursive implementation discussed in method 1.

if (K[i][W] != -1) 
    return dp[i][W]; 

Time Complexity -
O(N*W)
As redundant calculations of states are avoided.

Space Complexity - 
O(N*W) 
The use of 2D array data structure for storing intermediate states

*/