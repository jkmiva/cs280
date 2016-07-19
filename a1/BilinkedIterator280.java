//Name: Huang Jiaming
//NSID: jih211
//StuID:11207964
package lib280.list;

 


import lib280.exception.*;
import lib280.base.*;

/**	A LinkedIterator which has functions to move forward and back, 
	and to the first and last items of the list.  It keeps track of 
	the current item, and also has functions to determine if it is 
	before the start or after the end of the list. */
/**
 * @author Huang Jiaming<br>NSID: jih211			
 *
 */
public class BilinkedIterator280<I> extends LinkedIterator280<I> implements BilinearIterator280<I>
{

	/**	Constructor creates a new iterator for list 'list'. <br>
		Analysis : Time = O(1) 
		@param list list to be iterated */
	public BilinkedIterator280(BilinkedList280<I> list)
	{
		super(list);
	}

	/**	Create a new iterator at a specific position in the newList. <br>
		Analysis : Time = O(1)
		@param newList list to be iterated
		@param initialPrev the previous node for the initial position
		@param initialCur the current node for the initial position */
	public BilinkedIterator280(BilinkedList280<I> newList, 
			LinkedNode280<I> initialPrev, LinkedNode280<I> initialCur)
	{
		super(newList, initialPrev, initialCur);
	}
    

	/**
	 * Move the cursor to the last element in the list.
	 * @precond The list is not empty.
	 */
	public void  goLast() throws ContainerEmpty280Exception
	{
		// TODO
		// Hint: this should be very similar to goLast() in BiLinkedList280<I>
		if(this.list.isEmpty()) throw new ContainerEmpty280Exception("Cannot move to last element of an empty list.");
		cur = list.lastNode();
		if (this.list.head == this.list.tail){
			prev = null;
		}
		else {
			prev = ((BilinkedNode280<I>)list.lastNode()).previousNode;
		}

		
	}

	
	/**
	 * Move the cursor one element closer to the beginning of the list
	 * @precond !before() - the cursor cannot already be before the first element.
	 */
	public void goBack() throws BeforeTheStart280Exception
	{
		// TODO
		if (before()) {
			throw new BeforeTheStart280Exception("Cannot go back to previous when already before the head");
		}
		if (after()){
			goLast();
			return;
		}
		if (cur == list.head) {
			this.goBefore();
		}
		else
		{
			this.cur = this.prev;
			this.prev = ((BilinkedNode280<I>)this.prev).previousNode;
		}		
		// Hint: this should be very similar to goBack() in BiLinkedList280<I>
		
	 }

	/**	A shallow clone of this object. <br> 
	Analysis: Time = O(1) */
	public BilinkedIterator280<I> clone()
	{
		return (BilinkedIterator280<I>) super.clone();
	}

	public static void main(String[] args){
		BilinkedList280<Integer> L = new BilinkedList280<>();
		L.insert(5);
		L.insert(6);
		L.insert(7);
		BilinkedIterator280<Integer> i = new BilinkedIterator280<>(L);
		i.goLast();
		System.out.println(i.cur.item);
		i.goBack();
		System.out.println(i.cur.item);
		i.goBack();
		System.out.println(i.cur.item);
		i.goBack();
		System.out.println(i.cur);
		try {
			i.goBack();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");	
		} catch (BeforeTheStart280Exception e) {
			// TODO: handle exception
			System.out.println("Exception caught!  OK!");
			
		}
		
	}

} 
