// Class Stack definition
public class Stack extends LinkedList
{
	public Stack() { }
	// constructor
	public void push (Object elem)
	{	insertAtFront(elem) ; }

	public Object pop ()throws EmptyListException
	{	return removeFromFront();	}

	public Object peek()
	{ 	return getFirst();	}

} // end Stack