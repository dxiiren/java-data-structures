// Driver / demo for the adts/ family (LinkedList, Node, Stack, Queue).
// Exercises LinkedList insert/search/delete/traversal, Stack push/pop/peek,
// and Queue enqueue/dequeue -- including empty-pop/empty-dequeue edge cases.
//
// The String and Integer values used with search()/deleteNode() are built at
// runtime (new String(...), or int autoboxed above 127) so they are never the
// same object as what is already stored in the list, even though they are
// .equals() to it. This deliberately exercises the reference-identity vs.
// value-equality comparison in LinkedList.search()/deleteNode().
public class AdtsDemo
{
	public static void main(String[] args) throws LinkedList.EmptyListException
	{
		linkedListStringDemo();
		linkedListIntegerDemo();
		stackDemo();
		queueDemo();
	}

	// ---- helpers -------------------------------------------------------

	private static void show(String label, LinkedList list)
	{
		System.out.print(label + ": ");
		list.display();
		System.out.println();
	}

	private static void heading(String title)
	{
		System.out.println();
		System.out.println("=== " + title + " ===");
	}

	// ---- LinkedList with String data -----------------------------------

	private static void linkedListStringDemo() throws LinkedList.EmptyListException
	{
		heading("LinkedList Demo (String data)");

		LinkedList fruits = new LinkedList("fruits");
		fruits.insertAtBack("apple");
		fruits.insertAtBack("banana");
		fruits.insertAtBack("cherry");
		fruits.insertAtBack("date");
		fruits.insertAtBack("elderberry");
		fruits.insertAtFront("fig");

		show("Initial list", fruits);
		System.out.println("getFirst(): " + fruits.getFirst());
		System.out.println("getLast(): " + fruits.getLast());

		// search() for a PRESENT, mid-list element, using a String object that
		// is .equals() but NOT == to the one stored in the list.
		String presentKey = new String("cherry");
		System.out.println("search(present, mid-list \"cherry\", non-identical object)");
		fruits.search(presentKey);
		show("After search(\"cherry\")", fruits);

		// deleteNode() the duplicate cherry that search() just inserted --
		// again with a non-identical String, targeting a middle element.
		System.out.println("deleteNode(middle element \"cherry\", non-identical object)");
		fruits.deleteNode(new String("cherry"));
		show("After deleteNode(\"cherry\")", fruits);

		// removeFromBack() -- real tail-removal API, unrelated to the
		// identity-comparison bug.
		Object removedBack = fruits.removeFromBack();
		System.out.println("removeFromBack() returned: " + removedBack);
		show("After removeFromBack()", fruits);

		// deleteNode() the head element, with a non-identical String --
		// exercises the head-shortcut branch of deleteNode().
		System.out.println("deleteNode(head element \"fig\", non-identical object)");
		fruits.deleteNode(new String("fig"));
		show("After deleteNode(\"fig\")", fruits);

		// deleteNode() the (now unique) tail element, with a non-identical
		// String -- exercises the tail-position branch of deleteNode().
		System.out.println("deleteNode(tail element \"date\", non-identical object)");
		fruits.deleteNode(new String("date"));
		show("After deleteNode(\"date\")", fruits);

		// search() for an ABSENT element -- not found, so it is appended.
		System.out.println("search(absent \"mango\", non-identical object)");
		fruits.search(new String("mango"));
		show("After search(\"mango\")", fruits);

		// deleteNode() for a genuinely absent key -- confirms the "not
		// found" branch still correctly reports nothing removed.
		System.out.println("deleteNode(absent \"kiwi\", non-identical object)");
		fruits.deleteNode(new String("kiwi"));
		show("After deleteNode(\"kiwi\")", fruits);
	}

	// ---- LinkedList with Integer data (values > 127, escape the cache) --

	private static void linkedListIntegerDemo() throws LinkedList.EmptyListException
	{
		heading("LinkedList Demo (Integer data > 127)");

		LinkedList ids = new LinkedList("ids");
		int a = 200, b = 300, c = 400, d = 500;
		ids.insertAtBack(a);
		ids.insertAtBack(b);
		ids.insertAtBack(c);
		ids.insertAtBack(d);

		show("Initial list", ids);

		// Each autobox of a value outside [-128,127] allocates a fresh
		// Integer object, so searchVal is never == to the 300 already
		// stored, only .equals() to it.
		int searchVal = 300;
		System.out.println("search(present, mid-list 300, non-identical Integer)");
		ids.search(searchVal);
		show("After search(300)", ids);

		int deleteVal = 300;
		System.out.println("deleteNode(middle element 300, non-identical Integer)");
		ids.deleteNode(deleteVal);
		show("After deleteNode(300)", ids);

		Object removedBack = ids.removeFromBack();
		System.out.println("removeFromBack() returned: " + removedBack);
		show("After removeFromBack()", ids);

		int deleteHeadVal = 200;
		System.out.println("deleteNode(head element 200, non-identical Integer)");
		ids.deleteNode(deleteHeadVal);
		show("After deleteNode(200)", ids);

		int deleteTailVal = 400;
		System.out.println("deleteNode(tail element 400, non-identical Integer)");
		ids.deleteNode(deleteTailVal);
		show("After deleteNode(400)", ids);

		int absentSearchVal = 999;
		System.out.println("search(absent 999, non-identical Integer)");
		ids.search(absentSearchVal);
		show("After search(999)", ids);

		int absentDeleteVal = 111;
		System.out.println("deleteNode(absent 111, non-identical Integer)");
		ids.deleteNode(absentDeleteVal);
		show("After deleteNode(111)", ids);
	}

	// ---- Stack ------------------------------------------------------------

	private static void stackDemo() throws LinkedList.EmptyListException
	{
		heading("Stack Demo");

		Stack stack = new Stack();
		stack.push(10);
		stack.push(20);
		stack.push(30);

		System.out.println("peek(): " + stack.peek());
		System.out.println("pop(): " + stack.pop());
		System.out.println("pop(): " + stack.pop());
		System.out.println("peek(): " + stack.peek());
		System.out.println("pop(): " + stack.pop());

		// empty-peek edge case: getFirst() returns null when the list is
		// empty, no exception involved.
		System.out.println("peek() on empty stack: " + stack.peek());

		// empty-pop edge case: removeFromFront() throws EmptyListException.
		try
		{
			stack.pop();
		}
		catch (LinkedList.EmptyListException e)
		{
			System.out.println("pop() on empty stack threw EmptyListException, as expected");
		}
	}

	// ---- Queue --------------------------------------------------------

	private static void queueDemo() throws LinkedList.EmptyListException
	{
		heading("Queue Demo");

		Queue queue = new Queue();
		queue.enqueue("x");
		queue.enqueue("y");
		queue.enqueue("z");

		System.out.println("getFront(): " + queue.getFront());
		System.out.println("getEnd(): " + queue.getEnd());
		System.out.println("dequeue(): " + queue.dequeue());
		System.out.println("dequeue(): " + queue.dequeue());
		System.out.println("getFront(): " + queue.getFront());
		System.out.println("dequeue(): " + queue.dequeue());

		// empty-getFront edge case: getFirst() returns null when empty.
		System.out.println("getFront() on empty queue: " + queue.getFront());

		// empty-dequeue edge case: removeFromFront() throws EmptyListException.
		try
		{
			queue.dequeue();
		}
		catch (LinkedList.EmptyListException e)
		{
			System.out.println("dequeue() on empty queue threw EmptyListException, as expected");
		}
	}
}
