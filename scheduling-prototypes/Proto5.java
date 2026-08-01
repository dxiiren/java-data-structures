import java.util.Scanner;
import java.util.ArrayList;

public class Proto5
{
    public static void main(String [] args)
    {
		//Scanner
		Scanner sc = new Scanner(System.in);

		//input|

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

		int clock    =1;
		int stopper  =0;
		int complete =1;
		int count = 1;
		int trigger =0;

		ArrayList <Job> queue = new ArrayList <Job> ();
		ArrayList <Job> work = new ArrayList <Job> ();
		int cpu=0 ;
		char name='a';

		queue.add(input.get(0));

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

					work.add(xyz);
					queue.remove(0);
					complete=0;
				}

				if(cpu==0)
					complete=1;
				else
					System.out.println("\nTime : " + clock);

				if(cpu!=0)
				{
					System.out.println("Job " + name + " is executing ...");
					cpu--;
					trigger=1;
				}

				if(trigger==1)
				{
						clock++;
						trigger=0;
				}

				//check arrival time and put into queue
				for(int i=count ; i<input.size() ; i++)
				{
					Job klm = input.get(i);
					if(klm.getArrivalTime() == clock)
					{
						System.out.println("Job " + klm.getName() + " has arrived" );
						queue.add(klm);
				        //queue.get(i).print();
						//sort(queue)
					}
				}//end of for

				stopper=1;
			}//while stopper

			if(complete==1)
			{
					input.remove(0);
					System.out.println("SIZE =" + input.size());
			}

			stopper=0;
		}
			System.out.println("\nTime : " + clock);
			System.out.println("End\n\n");

    }//main

}