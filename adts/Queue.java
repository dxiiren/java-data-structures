// Class Queue definition
public class Queue extends LinkedList
{
	// constructor
	public Queue() { }

	public void enqueue(Object elem)
	{ insertAtBack(elem); }

	public Object dequeue() throws EmptyListException
	{ return removeFromFront(); }

	public Object getFront()
	{ return getFirst(); }

	public Object getEnd()
	{ return getLast(); }

} // end Queue