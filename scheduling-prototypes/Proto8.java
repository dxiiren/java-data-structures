import java.util.Scanner;
import java.util.ArrayList;

public class Proto8
{
    public static void main(String [] args)
    {
		//Scanner
		Scanner sc = new Scanner(System.in);

		//input
		ArrayList <Job> input = new ArrayList <Job> ();
 		char ch = 'A';
		String answer;

		do{
			char name = ch++;

			System.out.println( "Job "+ (name) + ") ");

			System.out.print("Enter cpu time = ");
			int cpu = Integer.parseInt(sc.nextLine());

			System.out.print("Enter arrival time = ");
			int arr = Integer.parseInt(sc.nextLine());

			System.out.println("\nWant to add more? - (yes/no)");
			answer = sc.nextLine();

			Job abc  = new Job(name,cpu,arr);
			input.add(abc);

			System.out.println();

		} while(answer.equalsIgnoreCase("Yes"));

		int clock    =1;	 // clock time
		int stopper  =0;     // out of processing loop
		int complete =1;	//determine cpu has complete executing a job or not
		int trigger =0;     //trigger clock time so clock +1

		ArrayList <Job> queue = new ArrayList <Job> ();
		int cpu=0 ;			//store cpu time of current running job
		char name='a';		//store name of current running job

		//counting time
		int size= input.size();
		int		[] execTime	= new int[size];
		int		[] waitTime = new int[size];
		char	[] order	= new char[size];	//store the order of joob running in order (which come first)
		int round = 0;							//to count the number of process that have been finished
		int count = 0;							//to count the executing time

		//put the first arrival in queue
		for(int i=0 ; i<input.size() ; i++)
		{
			Job klm = input.get(i);
			if(klm.getArrivalTime() == clock)
			{
				queue.add(klm);
				//sort(queue)
				insertionSort(queue);
			}
		}//end of for

		while(! input.isEmpty() )
		{
			while(stopper != 1)
			{

				//processing
				if(complete==1)
				{
					Job xyz = queue.get(0);
					cpu = xyz.getCpuTime();
					name = xyz.getName();

					queue.remove(0);
					complete=0;
					count = xyz.getWaitingTime() + 1;
					order[round]=xyz.getName();
					execTime[round]= count ;
					waitTime[round]= count-1;		//==getWaitingTime
				}

				if(cpu==0)
				{
					complete=1;
					count=0;
					round++;
				}
				else
					System.out.println("\nTime : " + clock);

				if(cpu!=0)
				{
					System.out.println("Job " + name + " is executing ...");
					cpu--;
					trigger=1;
					execTime[round]= count++;

                    //waiting in queue
					if(! queue.isEmpty())
					{
						for(int a=0 ; a<queue.size() ; a++)
						{
							Job mln = queue.get(a);
							int wait = mln.getWaitingTime() + 1;
							mln.setWaitingTime(wait);

							System.out.println("Job " + mln.getName() + " is in hold for " + mln.getWaitingTime() + " ms");

						}
					}

					//check arrival time and put into queue
					for(int i=0 ; i<input.size() ; i++)
					{
						if(clock>1)
						{
							Job klm = input.get(i);
							if(klm.getArrivalTime() == clock)
							{
								klm.setWaitingTime(1);
								queue.add(klm);
								System.out.println("Job " + klm.getName() + " has arrived... ");
								//sort(queue)
								insertionSort(queue);
							}
						}
					}//end of for

					//trigger clock
					if(trigger==1)
					{
						clock++;
						trigger=0;
					}

				}//end if


				stopper=1;
			}//while stopper

			if(complete==1)
			{
					input.remove(0);
			}

			stopper=0;
		}
			System.out.println("\nTime : " + clock);
			System.out.println("End\n\n");

			//avg execute time
			double total =0.0;
			for(int a : execTime)
			{
				total += a;
				//System.out.println(a);
			}

			double size2 = size;
			double avgExecTime = total/size2;
			//System.out.println("Total" + total);
			System.out.println("Average turn-around time : " + avgExecTime +"ms");

			//avg execute time
			total=0.0;
			for(int b : waitTime)
			{
				total += b;
				//System.out.println(b);
			}
			double avgWaitTime = total/size2;
			System.out.println("Average waiting time : " + avgWaitTime +"ms");

			System.out.println("\n\n");


    }//main

	//Insertion sort
	public static void insertionSort(ArrayList <Job> arr)
	{
		for(int y=1 ; y<arr.size() ; y++)   //start dgn 1 sebab kita assume element dlm index 0 betul dah tu
 		{
			Job key = arr.get(y);
			int z = y;		             //to make it start with index 0

			while(z>0 && arr.get(z-1).getCpuTime()>key.getCpuTime()) 	     //compare index awal dengan index selepas
			{
				arr.set( z,arr.get(z-1)) ; 		 //tukar no yang besar letak depan
				z--; 							 									//out of loop z=-1
			}

			arr.set(z,key)   ;   		     // z=-1+1 =0 -> ganti no belakang balik dengan no mula2 tadi

		}//end of for
	}//end of insertion sort

}