import java.util.Scanner;
import java.util.Arrays;

public class SortingAndSearchingV4
{
	public static void main(String[] args)
	{
		System.out.println("--------------------------------------------------------------------------------");
	 	System.out.println("Welcome to system searching and sorting v4 - INT & STRING + {descending");
	 	System.out.println("--------------------------------------------------------------------------------" + "\n");

		//Instantiate Scanner
		Scanner sc = new Scanner(System.in);

		//Declare Array
		int[] numArr    = new int [5];
		String[] strArr = new String [5];

		//input for integer
		for(int a=0 ; a<numArr.length ; a++)
		{
			System.out.print("Enter number" + (a+1) + " : ");
			numArr[a] = Integer.parseInt(sc.nextLine());
		}

		System.out.println("\n");

		//input for string
		for(int a=0 ; a<strArr.length ; a++)
		{
			System.out.print("Enter name " + (a+1) + " : ");
			strArr [a] = sc.nextLine();

		}

		//Bubble sort int
		bubbleSort(numArr);
		System.out.println("\n" + "~Bubble Sort~");
		System.out.println(Arrays.toString(numArr));
 		System.out.println("\n");

		//Insertion sort

 		insertionSort(strArr);
 		System.out.println("\n" + "~Insertion Sort~");
		System.out.println(Arrays.toString(strArr));
 		System.out.println("\n");

 		//Searching integer
		int index = binarySearch1(numArr, sc);

		if (index != -1)
			System.out.println("Found !");
		else
			System.out.println("Not Found !");

		System.out.println("\n");

		//Searching string
 		index = binarySearch2(strArr, sc);

 		if (index != -1)
 			System.out.println("Found !");
 		else
 			System.out.println("Not Found !");

		//Indent
		System.out.println("\n\n\n");

	}//main

//bubble sort method
	public static void bubbleSort(int[] list)
	{
 		for(int x=0 ; x<(list.length-1) ; x++)
 		{
			for(int index=0 ; index<list.length-(x+1) ; index++)
			{
				if(list[index]<list[index+1])
				{
					int temp    = list[index];
					list[index] = list[index+1];
					list[index+1] = temp;

				}
			}

		}//end of for

	}//end of method bubble sort

//insertion sort method
	public static void insertionSort(String[] list)
	{
 		for(int y=1 ; y<list.length ; y++)   //start dgn 1 sebab kita assume element dlm index 0 betul dah tu
 		{
			String key = list[y];
			int z = y-1;		             //to make it start with index 0

			while(z>=0 && list[z].compareToIgnoreCase(key)<=0) 	     //compare index awal dengan index selepa
			{
				list[z+1] = list[z]; 		 //tukar no yang besar letak depan
				z--; 						 //out of loop z=-1
			}

			list[z+1]= key;       		     // z=-1+1 =0 -> ganti no belakang balik dengan no mula2 tadi

		}//end of for

	}//end of method insertion sort

	//binary search method for int — reuses main's Scanner (a second
	//Scanner(System.in) hits EOF under redirected stdin)
	public static int binarySearch1(int [] list, Scanner sc)
 	{
		System.out.print("Enter a number that you want to search : ");
		int search = Integer.parseInt(sc.nextLine());

		int low = 0;
		int high = list.length-1;

		while(high>=low)
		{
			int mid = (low + high)/2;

			if(search > list[mid] )
				high = mid - 1;
			else if(search == list[mid])
				return mid;
			else
			 	low = mid + 1;
		}

		return -1;
	}//end of binary search method

	//binary search method for String — reuses main's Scanner (a second
	//Scanner(System.in) hits EOF under redirected stdin)
 	public static int binarySearch2(String [] list, Scanner sc)
 	{
		System.out.print("Enter a name that you want to search : ");
		String search = sc.nextLine();

		int low = 0;
		int high = list.length-1;

		while(high>=low)
		{
			int mid = (low + high)/2;

			if(search.compareToIgnoreCase(list[mid])>0 )
				high = mid - 1;
			else if(search.equalsIgnoreCase(list[mid]))
				return mid;
			else
			 	low = mid + 1;
		}

		return -1;
	}//end of binary search method

}//class