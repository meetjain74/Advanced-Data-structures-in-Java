/*

Method 1 -
A simple solution is to consider all subsets of items and 
calculate the total weight and value of all subsets. 
Consider the only subsets whose total weight is smaller than W. 
From all such subsets, pick the maximum value subset.

Optimal Sub-structure: 
To consider all subsets of items, there can be two cases for every item. 
Case 1: The item is included in the optimal subset.
Case 2: The item is not included in the optimal subset.

Therefore, the maximum value that can be obtained from ‘n’ items is the max of the following two values. 

(1) Maximum value obtained by n-1 items and W weight (excluding nth item).
(2) Value of nth item plus maximum value obtained by n-1 items and 
	W minus the weight of the nth item (including nth item).
	
If the weight of ‘nth’ item is greater than ‘W’, 
then the nth item cannot be included and Case 1 is the only possibility.

*/

class Main
{
	public static int max(int a,int b)
	{
		return (a>b)?a:b;
	}

	/*Recursive implementation*/
	public static int knapsack(int wt[],int val[],int knapsackCapacity,int numberOfElements)
	{
		/*Base case*/
		if (numberOfElements==0 || knapsackCapacity==0)
			return 0;

		/*If the weight of ‘nth’ item is greater than ‘W’, 
		then the nth item cannot be included*/
		if (wt[numberOfElements-1]>knapsackCapacity)
			return knapsack(wt,val,knapsackCapacity,numberOfElements-1);

		/*Return the maximum of twp cases-
		(1) nth item included
		(2) nth item not included*/
		return max(knapsack(wt,val,knapsackCapacity,numberOfElements-1), 
			val[numberOfElements-1]+knapsack(wt,val,knapsackCapacity-wt[numberOfElements-1],numberOfElements-1));
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
O(2^n)

Space Complexity -
O(1)

For the given example the code runs as follows -
Wt[]={10,20,30}
val[]={60,100,120}

if K(a,b) refers to 
a = no of elements in subset
b = knapackCapacity
we have

K(3,50) = Max of
		-> 120 + K(2,20) = Max of
						-> 100 + K(1,0)=0
						-> K(1,20) = Max of
								   -> 60 + K(0,10)=0
								   -> K(0,20)=0
		-> K(2,50) = Max of 
				   -> 100 + K(1,30) = Max of 
				   				    -> 60 + K(0,20)=0
				   				    -> K(0,30)=0
				   -> K(1,50) = Max of 
				   			  -> 60 + K(0,40)=0
				   			  -> K(0,50)=0


We can note that for some cases(for large trees) the K(a,b) is calculated
multiple times even if it has been already calculated before 
and hence increases the time complexity.

*/
