import java.util.Objects;

public class LinkedList
{
//attribute
private Node firstNode;
private Node lastNode;
private Node currNode;
private String name;

//constructor
 	//default constructor
	public LinkedList()
	{
		firstNode	= null;
		lastNode	= null;
		currNode	= null;
		name		= "list";		//giving name
	}

 	//normal constructor
	public LinkedList(String a)
	{
		firstNode	= null;
		lastNode	= null;
		currNode	= null;
		name		= a;
	}

//check either list empty or not
public boolean isEmpty()
{
	if(firstNode == null)
	 	return true;
	 else
	 	return false;
}

//Inserting data into list
public void insertAtFront(Object obj)
{
	if (isEmpty())			//firstNode == null
		firstNode = lastNode = new Node(obj);
	else
		firstNode = new Node(obj,firstNode);
}

public void insertAtBack(Object obj)
{
	if (isEmpty())			//firstNode == null
		firstNode = lastNode = new Node(obj);
	else
	{ 	lastNode.next = new Node(obj);
		lastNode = lastNode.next;
	}
}

/* This function is in LinkedList class.
Inserts a new node after the given prev_node. This method is
defined inside LinkedList class shown above */
public void search(Object data)
{
	// Store head node
	Node traverse = firstNode , prev = null;

	while (traverse != null && !Objects.equals(traverse.data, data)) {
			prev = traverse;
			traverse = traverse.next;
	}

	insertAfter(prev,data);
}

public void insertAfter(Node prev_node, Object obj)
{
	/* 1. Check if the given Node is null */
	if (prev_node == null)
	{
		System.out.println("The given previous node cannot be null");
		return;
	}

	/* 2. Allocate the Node &
	3. Put in the data*/
	Node new_node = new Node(obj);

	/* 4. Make next of new Node as next of prev_node */
	new_node.next = prev_node.next;

	/* 5. make next of prev_node as new_node */
	prev_node.next = new_node;
}

//Removing Data
public Object removeFromFront() throws EmptyListException
{
	Object removeItem = null;

	if(isEmpty())			//firstNode == null
		throw new EmptyListException(name);

	removeItem = firstNode.data;

	if(firstNode.equals(lastNode))
	 	firstNode = lastNode = null;
	else
	 	firstNode=firstNode.next;

	 return removeItem;
}

public Object removeFromBack() throws EmptyListException
{
	Object removeItem = null;

	if(isEmpty())			//firstNode == null
		throw new EmptyListException(name);

	removeItem = lastNode.data;

	if(firstNode.equals(lastNode))
	 	firstNode = lastNode = null;
	else
	{
	 	Node current = firstNode; 		//traverse the list to find the second last data
	 	while(current.next != lastNode)
	 	{
			current = current.next;
		}

		lastNode     = current;			//then point the last node to the 2nd last data
		current.next = null;
	}
	 return removeItem;
}

void deleteNode(Object objKey)
{
	// Store head node
	Node traverse = firstNode , prev = null;

	// If head node itself holds the key to be deleted
	if (traverse != null && Objects.equals(traverse.data, objKey)) {
		firstNode = traverse.next; // Changed head
		System.out.println("Data has been removed");
		return;
	}

	// Search for the key to be deleted, keep track of
	// the previous node as we need to change temp.next
	while (traverse != null && !Objects.equals(traverse.data, objKey)) {
		prev = traverse;
		traverse = traverse.next;
	}

	// If key was not present in linked list
	if (traverse == null)
	{
		System.out.println("No data removed");
		return;
	}

	// Unlink the node from linked list
	prev.next = traverse.next;
	System.out.println("Data has been removed");
}
//Handle Exception
public static class EmptyListException extends Exception
{
	public EmptyListException(String name )
	{
		System.out.println("The"+ name + "is empty");
	}
}

//Accessor Method
public Object getFirst()
{
	if(isEmpty())			//firstNode == null
		return null;
	else
	{
		currNode = firstNode;
		return currNode.data;
	}
}

public Object getLast()
{
	if(isEmpty())			//firstNode == null
		return null;
	else
	{
		currNode = lastNode;
		return currNode.data;
	}
}

public Object getNext()
{
	if(currNode != lastNode)			//firstNode == null
	{
		currNode = currNode.next;
		return currNode.data;
	}
	else
		return null;
}


//display @ printer
public void display()
{
	if(isEmpty())			//firstNode == null
		System.out.println("List is empty");
	else
	{
		Node current = firstNode;
		while(current != null)
		{
			System.out.print(current.data + "->");
			current = current.next;
		}
	}

}

}//end of class