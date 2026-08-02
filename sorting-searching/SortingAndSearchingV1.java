import java.util.Scanner;
import java.util.Arrays;

public class SortingAndSearchingV1
{
	public static void main(String[] args)
	{
		System.out.println("-------------------------------------------------------------------------");
	 	System.out.println("Welcome to system searching and sorting v1 - int + {accessending");
	 	System.out.println("-------------------------------------------------------------------------" + "\n");

	 	//Instantiate Scanner
	 	Scanner sc = new Scanner(System.in);

		//Input
		int[] num = new int [5];
		int[] num2 = new int [5];

		for(int a=0 ; a<num.length ; a++)
		{
			System.out.print("Enter number" + (a+1) + " : ");
			num[a] = Integer.parseInt(sc.nextLine());

		}

		//Bubble sort
		bubbleSort(num);
		System.out.println("\n" + "~Bubble Sort~");
		System.out.println(Arrays.toString(num));
 		System.out.println("\n");

 		//Insertion sort
 		for(int c=0 ; c<num2.length ; c++)
		{
					System.out.print("Enter number v2" + " [" +(c+1) + "] : ");
					num2[c] = Integer.parseInt(sc.nextLine());
		}

 		insertionSort(num2);
 		System.out.println("\n" + "~Insertion Sort~");
		System.out.println(Arrays.toString(num2));
 		System.out.println("\n");

 		//Searching
 		int index = binarySearch(num, sc);

 		if (index != -1)
 			System.out.println("\nFound !");
 		else
 			System.out.println("\nNot Found !");

		//Indent
		System.out.println("\n\n\n");

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


	//insertion sort method
	public static void insertionSort(int[] list)
	{
 		for(int y=1 ; y<list.length ; y++)   //start dgn 1 sebab kita assume element dlm index 0 betul dah tu
 		{
			int key = list[y];
			int z = y-1;		             //to make it start with index 0

			while(z>=0 && list[z]>key) 	     //compare index awal dengan index selepa
			{
				list[z+1] = list[z]; 		 //tukar no yang besar letak depan
				z--; 						 //out of loop z=-1
			}

			list[z+1]= key;       		     // z=-1+1 =0 -> ganti no belakang balik dengan no mula2 tadi

		}//end of for

	}//end of method insertion sort


 	//binary search method — reuses main's Scanner (a second Scanner(System.in)
 	//hits EOF under redirected stdin because the first one buffers the stream)
 	public static int binarySearch(int [] list, Scanner sc)
 	{
		System.out.print("Enter a number that you want to search : ");
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

}//end of clas