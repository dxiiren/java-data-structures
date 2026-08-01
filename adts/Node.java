public class Node
{
//attribute
Object data;
Node next;

//constructor
	public Node(Object item)
	{
		data = item;
		next = null;
	}

	public Node(Object item, Node nextNode)
	{
		data = item;
		next = nextNode;
	}

//accessor method
	public Object getData(){return data;}
	public Node getNext(){return next;}
}