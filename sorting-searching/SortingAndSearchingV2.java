import java.util.Scanner;
import java.util.Arrays;

public class SortingAndSearchingV2
{
	public static void main(String[] args)
	{
		System.out.println("-------------------------------------------------------------------------");
	 	System.out.println("Welcome to system searching and sorting v1 - string + {accessending");
	 	System.out.println("-------------------------------------------------------------------------" + "\n");

	 	//Instantiate Scanner
	 	Scanner sc = new Scanner(System.in);

		//Input
		String[] qs1 = new String [5];
		String[] qs2 = new String [5];

		for(int a=0 ; a<qs1.length ; a++)
		{
			System.out.print("Enter name " + (a+1) + " : ");
			qs1 [a] = sc.nextLine();

		}

		//Bubble sort
		bubbleSort(qs1);
		System.out.println("\n" + "~Bubble Sort~");
		System.out.println(Arrays.toString(qs1));
 		System.out.println("\n");

		//System.out.println(qs1[3].compareToIgnoreCase(qs1[4]));

 		//Insertion sort
 		for(int c=0 ; c<qs2.length ; c++)
		{
					System.out.print("Enter name v2" + " [" +(c+1) + "] : ");
					qs2[c] = sc.nextLine();
		}

 		insertionSort(qs2);
 		System.out.println("\n" + "~Insertion Sort~");
		System.out.println(Arrays.toString(qs2));
 		System.out.println("\n");

 		//Searching
 		int index = binarySearch(qs1);

 		if (index != -1)
 			System.out.println("\nFound !");
 		else
 			System.out.println("\nNot Found !");



		//Indent
		System.out.println("\n\n\n");

	}//end of main


	//bubble sort method
	public static void bubbleSort(String[] list)
	{
 		for(int x=0 ; x<(list.length-1) ; x++)
 		{
			for(int index=0 ; index<list.length-(x+1) ; index++)
			{
				if(list[index].compareToIgnoreCase(list[index+1])>=0)
				{
					String temp  = list[index];
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

			while(z>=0 && list[z].compareToIgnoreCase(key)>=0) 	     //compare index awal dengan index selepa
			{
				list[z+1] = list[z]; 		 //tukar no yang besar letak depan
				z--; 						 //out of loop z=-1
			}

			list[z+1]= key;       		     // z=-1+1 =0 -> ganti no belakang balik dengan no mula2 tadi

		}//end of for

	}//end of method insertion sort


 	//binary search method
 	public static int binarySearch(String [] list)
 	{
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter a name that you want to search : ");
		String search = sc.nextLine();

		int low = 0;
		int high = list.length-1;

		while(high>=low)
		{
			int mid = (low + high)/2;

			if(search.compareToIgnoreCase(list[mid])<0 )
				high = mid - 1;
			else if(search.equalsIgnoreCase(list[mid]))
				return mid;
			else
			 	low = mid + 1;
		}

		return -1;
	}//end of binary search method

}//end of clas