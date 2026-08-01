public class Job
{
	//attribute
	char name ;
	int cpuTime;
	int arrivalTime;
	int executeTime;
	int waitingTime;

	//constructor
	public Job()
	{
		name = 0;
		cpuTime = 0;
		arrivalTime = 0;
		executeTime = 0;
		waitingTime = 0;

	}

	public Job(char v, int w, int x)
	{
		name = v;
		cpuTime = w;
		arrivalTime = x;
		executeTime = 0;
		waitingTime = 0;

	}

	//setter
	public void setCpuTime    (int v){ cpuTime = v; }
	public void setArrivalTime(int w){ arrivalTime = w;}
	public void setExecuteTime(int x){ executeTime = x; }
	public void setWaitingTime(int y){ waitingTime = y; }
	public void setName   	  (char z){ name  = z; }

	//getter
	public int getCpuTime    (){ return cpuTime; }
	public int getArrivalTime(){ return arrivalTime; }
	public int getExecuteTime(){ return executeTime; }
	public int getWaitingTime(){ return waitingTime; }
	public char getName   	 (){ return name; }

	public void print()
	{
		System.out.println(" Name " + name);
		System.out.println(" Cpu time " + cpuTime);
		System.out.println(" Arrival time " + arrivalTime);

	}

}