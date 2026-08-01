/*
Name		: Muhammad Akmal Bin Ahmad Suhaimi
Group		: 3B
Lecturer	: Puan Nomariani A.Razik
*/

import java.util.Scanner;
import java.util.Arrays;

public class ProgramApp
{
	public static void main(String [] args)
	{
		System.out.println("\n\t\t\tWelcome to Sorting and Searching Program");
		System.out.println("\t\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");

		//instantiate scanner
		Scanner sc = new Scanner(System.in);

		//intantiate array
		int [] arrNum = new int [10];

		//Insert 10 data into an array
		System.out.println("Enter 10 integers number : \n"  );
		for(int i=0 ; i<arrNum.length ; i++)
		{
			System.out.print((i+1) + ")  ");
			arrNum[i] = Integer.parseInt(sc.nextLine());

		}

		//display the array values before sorting
		System.out.println("\nValues before sorting: " + Arrays.toString(arrNum));

		//call method bubble sort
		bubbleSort(arrNum);

		//display the array value after sorting
		System.out.println("\nValues after sorting: " + Arrays.toString(arrNum) + "\n");

		//call method binaryseach
		int index = binarySearch(arrNum);

		if (index != -1)
			System.out.println("The value is found at index " + index + "." );
		else
			System.out.println("Sorry but the value that you are looking for is Not Found !");

		System.out.println("\n\t\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ");
		System.out.print("\t\t\tThank You and See You Again! ");

	}//end of main

	//bubble sort method
	public static void bubbleSort(int[] list)
	{
		for(int x=0 ; x<(list.length-1) ; x++)
		{
			for(int index=0 ; index<list.length-(x+1) ; index++)
			{
				if(list[index]>list[index+1])
				{
					int temp    = list[index];
					list[index] = list[index+1];
					list[index+1] = temp;
				}
			}

		}//end of for

	}//end of method bubble sort


	//binary search method
	public static int binarySearch(int [] list)
	{
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter a value to search: ");
		int search = Integer.parseInt(sc.nextLine());

		int low = 0;
		int high = list.length-1;

		while(high>=low)
		{
			int mid = (low + high)/2;

			if(search < list[mid] )
				high = mid - 1;
			else if(search == list[mid])
				return mid;
			else
				low = mid + 1;
		}

		return -1;
	}//end of binary search method

}