public class Student
{
//attributes declaration
 	private String name;
 	private int number;

 //constructor
	public Student()
	{
		name = null;
		number = 0;
	}

	public Student(String nm , int num)
	{
		name = nm;
		number = num;
	}

//accessor method
	public String getName() {return name;}
	public int getNumber()	{return number;}

//mutator method
 	public void setNumber(int n ) {number = n;}
 	public void setName(String n ) {name = n;}
}