import java.util.Scanner;
import java.util.Arrays;

public class SortingAndSearchingV3
{
	public static void main(String[] args)
	{
		System.out.println("--------------------------------------------------------------------------------");
	 	System.out.println("Welcome to system searching and sorting v3 - adt ( object ) + {accessending");
	 	System.out.println("--------------------------------------------------------------------------------" + "\n");

	 	//Instantiate Scanner
	 	Scanner sc = new Scanner(System.in);

		//Instantiate Object
	 	Student [] stu = new Student [5];

		for(int a=0 ; a<stu.length ; a++)
		{

			System.out.print("Enter name" + " : ");
			String name = sc.nextLine();

			System.out.print("Enter number" + " : ");
			int num = Integer.parseInt(sc.nextLine());

			stu[a] = new Student(name,num);
		}

		//Bubble sort
		for(int x=0 ; x<(stu.length-1) ; x++)
		{
			for(int index=0 ; index<stu.length-(x+1) ; index++)
			{
					if(stu[index].getNumber() > stu[index+1].getNumber())
					{
							int temp                 = stu[index].getNumber();

							stu[index].setNumber(stu[index+1].getNumber()) ;
							stu[index+1].setNumber(temp) ;
					}
			}

		}//end of for

		System.out.println("\n" + "~Bubble Sort for int~");
		System.out.print("[");
		for(int a = 0 ; a<stu.length ; a++)
		{
			if(a<stu.length-1)
				System.out.print(stu[a].getNumber() + ",");
			else
			 	System.out.print(stu[a].getNumber() + "]");
		}
 		System.out.println("\n");



 		//Insertion sort
		for(int y=1 ; y<stu.length ; y++)   //start dgn 1 sebab kita assume element dlm index 0 betul dah tu
 		{
			String key = stu[y].getName();
			int z = y-1;		             //to make it start with index 0

			while(z>=0 && stu[z].getName().compareToIgnoreCase(key)>=0) 	     //compare index awal dengan index selepas
			{
				stu[z+1].setName(stu[z].getName()) ;		 					//tukar no yang besar letak depan
				z--; 						 									//out of loop z=-1
			}

			stu[z+1].setName(key);       		     // z=-1+1 =0 -> ganti no belakang balik dengan no mula2 tadi

		}//end of for

 		System.out.println("\n" + "~Insertion Sort for string ~");
		System.out.print("[");
		for(int a = 0 ; a<stu.length ; a++)
		{
				if(a<stu.length-1)
					System.out.print(stu[a].getName() + ",");
				else
			 		System.out.print(stu[a].getName() + "]");
		}
 		System.out.println("\n");


 		//Searching
		System.out.print("Enter a number that you want to search : ");
		int search = Integer.parseInt(sc.nextLine());

		int low = 0;
		int high = stu.length-1;
		int found = -1 ;

		while(high>=low)
		{
			int mid = (low + high)/2;

			if(search < stu[mid].getNumber() )
				high = mid - 1;
			else if(search == stu[mid].getNumber())
			{
				found = mid;
				break;
			}
			else
				low = mid + 1;
		}

		if (found != -1)
		 	System.out.println("\nFound !");
		 else
 			System.out.println("\nNot Found !");

		//Indent
		System.out.println("\n\n\n");

	}//end of main
}//end of class