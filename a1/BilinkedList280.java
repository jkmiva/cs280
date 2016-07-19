//Name: Huang Jiaming
//NSID: jih211
//StuID:11207964
package lib280.list;

import lib280.base.*;
import lib280.exception.*;

/**
 * This list class incorporates the functions of an iterated dictionary such as
 * has, obtain, search, goFirst, goForth, deleteItem, etc. It also has the
 * capabilities to iterate backwards in the list, goLast and goBack.
 */
/**
 * @author Huang Jiaming<br>NSID: jih211			
 *
 */
public class BilinkedList280<I> extends LinkedList280<I> implements BilinearIterator280<I> {
	/*
	 * Note that because firstRemainder() and remainder() should not cut links
	 * of the original list, the previous node reference of firstNode is not
	 * always correct. Also, the instance variable prev is generally kept up to
	 * date, but may not always be correct. Use previousNode() instead!
	 */

	/**
	 * Construct an empty list. Analysis: Time = O(1)
	 */
	public BilinkedList280() {
		super();
	}

	/**
	 * Create a BilinkedNode280 this Bilinked list. This routine should be
	 * overridden for classes that extend this class that need a specialized
	 * node.
	 * 
	 * @param item
	 *            - element to store in the new node
	 * @return a new node containing item
	 */
	protected BilinkedNode280<I> createNewNode(I item) {
		// TODO
		return new BilinkedNode280<I>(item);
		// NOTE: Remove the next line before you write this method. It is only
		// here to prevent a compiler error
		// return null;
	}

	/**
	 * Insert element at the beginning of the list
	 * 
	 * @param x
	 *            item to be inserted at the beginning of the list
	 */
	public void insertFirst(I x) {
		// TODO
		BilinkedNode280<I> newItem = this.createNewNode(x);
		newItem.setNextNode(this.head);
		if(head != null){
			((BilinkedNode280<I>) head).setPreviousNode(newItem);
		}
		// If the cursor is at the first node, cursor predecessor becomes the
		// new node.
		if (this.position == this.head)
			this.prevPosition = newItem;

		// Special case: if the list is empty, the new item also becomes the
		// tail.
		if (this.isEmpty())
			this.tail = newItem;
		this.head = newItem;

	}

	/**
	 * Insert element at the beginning of the list
	 * 
	 * @param x
	 *            item to be inserted at the beginning of the list
	 */
	public void insert(I x) {
		this.insertFirst(x);
	}

	/**
	 * Insert an item before the current position.
	 * 
	 * @param x
	 *            - The item to be inserted.
	 */
	public void insertBefore(I x) throws InvalidState280Exception {
		if (this.before())
			throw new InvalidState280Exception(
					"Cannot insertBefore() when the cursor is already before the first element.");

		// If the item goes at the beginning or the end, handle those special
		// cases.
		if (this.head == position) {
			insertFirst(x); // special case - inserting before first element
		} else if (this.after()) {
			insertLast(x); // special case - inserting at the end
		} else {
			// Otherwise, insert the node between the current position and the
			// previous position.
			BilinkedNode280<I> newNode = createNewNode(x);
			newNode.setNextNode(position);
			newNode.setPreviousNode((BilinkedNode280<I>) this.prevPosition);
			prevPosition.setNextNode(newNode);
			((BilinkedNode280<I>) this.position).setPreviousNode(newNode);

			// since position didn't change, but we changed it's predecessor,
			// prevPosition needs to be updated to be the new previous node.
			prevPosition = newNode;
		}
	}

	/**
	 * Insert x before the current position and make it current item. <br>
	 * Analysis: Time = O(1)
	 * 
	 * @param x
	 *            item to be inserted before the current position
	 */
	public void insertPriorGo(I x) {
		this.insertBefore(x);
		this.goBack();
	}

	/**
	 * Insert x after the current item. <br>
	 * Analysis: Time = O(1)
	 * 
	 * @param x
	 *            item to be inserted after the current position
	 */
	public void insertNext(I x) {
		if (isEmpty() || before())
			insertFirst(x);
		else if (this.position == lastNode())
			insertLast(x);
		else if (after()) // if after then have to deal with previous node
		{
			insertLast(x);
			this.position = this.prevPosition.nextNode();
		} else // in the list, so create a node and set the pointers to the new
				// node
		{
			BilinkedNode280<I> temp = createNewNode(x);
			temp.setNextNode(this.position.nextNode());
			temp.setPreviousNode((BilinkedNode280<I>) this.position);
			((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode(temp);
			this.position.setNextNode(temp);
		}
	}

	/**
	 * Insert a new element at the end of the list
	 * 
	 * @param x
	 *            item to be inserted at the end of the list
	 */
	public void insertLast(I x) {
		// TODO
		BilinkedNode280<I> newNode = new BilinkedNode280<>(x);
		newNode.setNextNode(null);

		// If the cursor is after(), then cursor predecessor becomes the new
		// node.
		if (this.after())
			this.prevPosition = newNode;

		// If list is empty, handle special case
		if (this.isEmpty()) {
			this.head = newNode;
			this.tail = newNode;
		} else {
			this.tail.setNextNode(newNode);
			newNode.setPreviousNode((BilinkedNode280<I>) tail);
			this.tail = newNode;
		}

	}

	/**
	 * Delete the item at which the cursor is positioned
	 * 
	 * @precond itemExists() must be true (the cursor must be positioned at some
	 *          element)
	 */
	public void deleteItem() throws NoCurrentItem280Exception {
		// TODO
		// Hint: if you delete the item at which the cursor is positioned, where
		// do you then position the cursor?
		if (!this.itemExists())
			throw new NoCurrentItem280Exception("There is no item at the cursor to delete.");

		// If we are deleting the first item...
		if (this.position == this.head) {
			// Handle the special case...
			this.deleteFirst();
			this.position = this.head;
		} else {
			// Set the previous node to point to the successor node. Set the
			// successor point to the previous.
			this.prevPosition.setNextNode(this.position.nextNode());
			if(this.position.nextNode != null){
				((BilinkedNode280<I>) this.position.nextNode).setPreviousNode(((BilinkedNode280<I>) this.prevPosition));
			}
			// Reset the tail reference if we deleted the last node.
			if (this.position == this.tail) {
				this.tail = this.prevPosition;
			}
			this.position = this.position.nextNode();
		}

	}

	@Override
	public void delete(I x) throws ItemNotFound280Exception {
		if (this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot delete from an empty list.");

		// Save cursor position
		LinkedIterator280<I> savePos = (LinkedIterator280<I>) this.currentPosition();

		// Find the item to be deleted.
		search(x);
		if (!this.itemExists())
			throw new ItemNotFound280Exception("Item to be deleted wasn't in the list.");

		// If we are about to delete the item that the cursor was pointing at,
		// advance the cursor in the saved position, but leave the predecessor
		// where
		// it is because it will remain the predecessor.
		if (this.position == savePos.cur)
			savePos.cur = savePos.cur.nextNode();

		// If we are about to delete the predecessor to the cursor, the
		// predecessor
		// must be moved back one item.
		if (this.position == savePos.prev) {

			// If savePos.prev is the first node, then the first node is being
			// deleted
			// and savePos.prev has to be null.
			if (savePos.prev == this.head)
				savePos.prev = null;
			else {
				// Otherwise, Find the node preceding savePos.prev
				LinkedNode280<I> tmp = this.head;
				while (tmp.nextNode() != savePos.prev)
					tmp = tmp.nextNode();

				// Update the cursor position to be restored.
				savePos.prev = tmp;
			}
		}

		// Unlink the node to be deleted.
		if (this.prevPosition != null)
			// Set previous node to point to next node.
			// Only do this if the node we are deleting is not the first one.
			this.prevPosition.setNextNode(this.position.nextNode());

		if (this.position.nextNode() != null)
			// Set next node to point to previous node
			// But only do this if we are not deleting the last node.
			((BilinkedNode280<I>) this.position.nextNode())
					.setPreviousNode(((BilinkedNode280<I>) this.position).previousNode());

		// If we deleted the first or last node (or both, in the case
		// that the list only contained one element), update head/tail.
		if (this.position == this.head)
			this.head = this.head.nextNode();
		if (this.position == this.tail)
			this.tail = this.prevPosition;

		// Clean up references in the node being deleted.
		this.position.setNextNode(null);
		((BilinkedNode280<I>) this.position).setPreviousNode(null);

		// Restore the old, possibly modified cursor.
		this.goPosition(savePos);

	}

	/**
	 * Remove the first item from the list.
	 * 
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteFirst() throws ContainerEmpty280Exception {
		// TODO
		if (this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot delete an item from an empty list.");

		// If the cursor is on the second node, set the prev pointer to null.
		if (this.prevPosition == this.head)
			this.prevPosition = null;
		// Otherwise, if the cursor is on the first node, set the cursor to the
		// next node.
		else if (this.position == this.head)
			this.position = this.position.nextNode();

		// If we're deleting the last item, set the tail to null.
		// Setting the head to null gets handled automatically in the following
		// unlinking.
		if (this.head == this.tail)
			this.tail = null;

		// Unlink the first node.
		LinkedNode280<I> oldhead = this.head;
		this.head = this.head.nextNode();
		if(this.head != null)
			((BilinkedNode280<I>) this.head).setPreviousNode(null);
		oldhead.setNextNode(null);
	}

	/**
	 * Remove the last item from the list.
	 * 
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteLast() throws ContainerEmpty280Exception {
		// TODO
		// Special cases if there are 0 or 1 nodes.
		if (this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot delete an item from an empty list.");
		else if (this.head != null && this.head == this.tail)
			this.deleteFirst();
		else {
			// There are at least two nodes.

			// If the cursor is on the last node, we need to update the cursor.
			if (this.position == this.tail) {
				this.position = this.prevPosition;
				if(this.prevPosition == head){
					this.prevPosition = null;
				}
				else{
					this.prevPosition = ((BilinkedNode280<I>)this.prevPosition).previousNode;
				}
//				// Find the node prior to this.position
//				LinkedNode280<I> newPrev = this.head;
//				while (newPrev.nextNode() != this.prevPosition)
//					newPrev = newPrev.nextNode();
//				this.position = this.prevPosition;
//				this.prevPosition = newPrev;
			}

			// Find the second-last node -- note this makes the deleteLast()
			// algorithm O(n)
			LinkedNode280<I> penultimate = this.head;
			while (penultimate.nextNode() != this.tail)
				penultimate = penultimate.nextNode();

			// If the cursor is in the after() position, then prevPosition
			// has to become the second last node.
			if (this.after()) {
				this.prevPosition = penultimate;
			}

			// Unlink the last node.
			penultimate.setNextNode(null);
			((BilinkedNode280<I>) this.tail).setPreviousNode(null);
			this.tail = penultimate;
		}
	}

	/**
	 * Move the cursor to the last item in the list.
	 * 
	 * @precond The list is not empty.
	 */
	public void goLast() throws ContainerEmpty280Exception {
		// TODO
		if (this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot position cursor at last element of an empty list.");
		this.position = this.tail;
		// If there is only one node
		if (this.head == this.tail) {
			this.prevPosition = null;
		} else {
			LinkedNode280<I> penultimate = this.head;
			while (penultimate.nextNode() != this.tail) {
				penultimate = penultimate.nextNode();
			} 
			this.prevPosition = penultimate;
		}
	}

	/**
	 * Move back one item in the list. Analysis: Time = O(1)
	 * 
	 * @precond !before()
	 */
	public void goBack() throws BeforeTheStart280Exception {
		// TODO
		if (before()) {
			throw new BeforeTheStart280Exception("Cannot go back to previous when already before the head");
		}
		if (after()){
			goLast();
			return;
		}
		if (this.position == head) {
			this.goBefore();
		}
		else
		{
			this.position = this.prevPosition;
			this.prevPosition = ((BilinkedNode280<I>)this.prevPosition).previousNode;
		}		
	}

	/**
	 * Iterator for list initialized to first item. Analysis: Time = O(1)
	 */
	public BilinkedIterator280<I> iterator() {
		return new BilinkedIterator280<I>(this);
	}

	/**
	 * Go to the position in the list specified by c. <br>
	 * Analysis: Time = O(1)
	 * 
	 * @param c
	 *            position to which to go
	 */
	@SuppressWarnings("unchecked")
	public void goPosition(CursorPosition280 c) {
		if (!(c instanceof BilinkedIterator280))
			throw new InvalidArgument280Exception(
					"The cursor position parameter" + " must be a BilinkedIterator280<I>");
		BilinkedIterator280<I> lc = (BilinkedIterator280<I>) c;
		this.position = (BilinkedNode280<I>) lc.cur;
		this.prevPosition = (BilinkedNode280<I>) lc.prev;
	}

	/**
	 * The current position in this list. Analysis: Time = O(1)
	 */
	public BilinkedIterator280<I> currentPosition() {
		return new BilinkedIterator280<I>(this, this.prevPosition, this.position);
	}

	/**
	 * A shallow clone of this object. Analysis: Time = O(1)
	 */
	public BilinkedList280<I> clone() throws CloneNotSupportedException {
		return (BilinkedList280<I>) super.clone();
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws RuntimeException {
		// TODO
		// Write a regression test for the BilinkedList280<I> class.
		
		// Test insertFirst, insertLast, deleteItem, deleteFirst, deleteLast, goLast, goBack
		BilinkedList280<Integer> L = new BilinkedList280<>();
		System.out.println(L);
		
		System.out.print("List should be empty...");
		if( L.isEmpty() ) System.out.println("and it is.");
		else System.out.println("ERROR: and it is *NOT*.");
		
		// Test insertFirst
		System.out.println("//Test insertFirst:");
			// Test insertFirst on an empty list
		L.insertFirst(2);
		if(L.tail == L.head)
			System.out.println("  Inner test succeed: insertFirst when list is empty, head equals tail");
		else {
			throw new RuntimeException("test failed");
		}
		if((L.prevPosition.toString()).equals("2")){
			System.out.println("  Inner test succeed: prePosition set correctly when the cursor is at the first node");
		}
		else {
			throw new RuntimeException("test failed");
		}
		L.insertFirst(3);
		System.out.println("List should be: 3, 2,");
		System.out.print(  "     and it is: ");
		System.out.println(L);
		
		// Test insertLast
		System.out.println("\n//Test insertLast:");
		L.insertLast(5);
		L.insertLast(6);
		System.out.println("List should be: 3, 2, 5, 6,");
		System.out.print(  "     and it is: ");
		System.out.println(L);
		L.clear();
		System.out.print("List should be empty...");
		if( L.isEmpty() ) System.out.println("and it is.");
		else System.out.println("ERROR: and it is *NOT*.");
			// Test insertLast on an empty list
		L.insertLast(5);
		if(L.tail == L.head)
			System.out.println("  Inner test succeed: insertLast when list is empty, head equals tail");
		else {
			throw new RuntimeException("test failed");
		}
			// Test insertLast when the cursor is after()
		L.goAfter();
		L.insertLast(6);
		if(L.prevPosition.toString().equals("6"))
			System.out.println("  Inner test succeed: insertLast when the cursor is after(), then cursor predecessor becomes the new node");
		else {
			throw new RuntimeException("test failed");
		}
		System.out.println("List should be: 5, 6,");
		System.out.print(  "     and it is: ");
		System.out.println(L);
		
		// Test goLast
		System.out.println("\n//Test goLast");
			// Test goLast on an empty list
		L.clear();
		System.out.println("goLast on an empty list");
		try {
			L.goLast();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");
		} catch (ContainerEmpty280Exception e) {
			// TODO: handle exception
			System.out.println("  Inner test succeed: Caught exception.  OK!");
		}
			// Test goLast when there is only one node
		L.insert(5);
		L.goLast();
		if (L.prevPosition == null && L.position.item == 5) {
			System.out.println("  Inner test succeed: goLast when there is only one node, prePosition set null");
		}
		else {
			throw new RuntimeException("test failed");
		}
			// Test goLast when there is at least two nodes
		L.insert(6);
		L.goLast();
		if (L.prevPosition.item == 6 && L.position.item == 5) {
			System.out.println("  Inner test succeed: goLast when there is at least two nodes, prePosition set penultimate");
		}
		else {
			throw new RuntimeException("test failed");
		}
		
		// Test goBack
		System.out.println("\n//Test goBack");
		L.clear();
			// Test goBack at before()
		L.insert(5);
		L.goBefore();
		try {
			L.goBack();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");
		} catch ( BeforeTheStart280Exception e) {
			// TODO: handle exception
			System.out.println("  Inner test succeed: Caught exception.  OK!");
		}
			// Test goBack at after()
		L.goAfter();
		L.goBack();
		if (L.position.item == 5 && L.prevPosition == null){
			System.out.println("  Inner test succeed: goBack at after(), called goLast");
		}
		else {
			throw new RuntimeException("test failed");
		}
			// Test goBack at head
		L.goFirst();
		L.goBack();
		if (L.before()){
			System.out.println("  Inner test succeed: goBack at head, enter before() state");
		}
		else{
			throw new RuntimeException("test failed");
		}
			// Test goBack at middle
		L.insert(6);
		L.insert(7);
		L.goFirst();
		L.goForth();
		L.goForth();
		L.goBack();
		if (L.position.item == 6 && L.prevPosition.item == 7){
			System.out.println("  Inner test succeed: goBack at middle, move back one item in list");
		}
		else {
			throw new RuntimeException("test failed");
		}
		
		// Test deleteFirst
		System.out.println("\n//Test deleteFirst");
		L.clear();
		System.out.print("List should be empty...");
		if( L.isEmpty() ) System.out.println("and it is.");
		else System.out.println("ERROR: and it is *NOT*.");
			// Test delete from an empty item
		System.out.println("Deleting from an empty list.");
		try {
			L.deleteFirst();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");			
		}
		catch( ContainerEmpty280Exception e ) {
			System.out.println("  Inner test succeed: Caught exception.  OK!");
		}
			// Test deleteFirst when cursor is on the second node
		L.insert(5);
		L.goFirst();
		L.insert(6);
		L.deleteFirst();
		if(L.prevPosition == null) {
			System.out.println("  Inner test succeed: deleteFirst when cursor is on second node, prevPosition set null");
		}
		else {
			throw new RuntimeException("test failed");
		}
			// Test deleteFirst when cursor is on the first node
		L.clear();
		L.insert(5);
		L.insert(6);
		L.insert(7);
		L.goFirst();
		L.deleteFirst();
		if(L.position.item == 6) {
			System.out.println("  Inner test succeed: deleteFirst when cursor is on first node, position set the succeeding node");
		}
		else {
			throw new RuntimeException("test failed");
		}
			// Test deleteFirst when there is only one node
		L.clear();
		L.insert(5);
		L.deleteFirst();
		if(L.tail == null) {
			System.out.println("  Inner test succeed: deleteFirst when there is only one node, tail set null");
		}
		else {
			throw new RuntimeException("test failed");
		}
			// Test other situation
		L.clear();
		L.insert(5);
		L.insert(6);
		L.insert(7);
		L.goAfter();
		L.deleteFirst();
		System.out.println("List should be: 6, 5,");
		System.out.print(  "     and it is: ");
		System.out.println(L);
		
		// Test deleteItem
		System.out.println("\n//Test deleteItem");
		L.clear();
		System.out.print("List should be empty...");
		if( L.isEmpty() ) System.out.println("and it is.");
		else System.out.println("ERROR: and it is *NOT*.");
			// Test delete none exist item
		System.out.println("Deleting noCurrentItem from list.");
		try {
			L.deleteItem();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");			
		}
		catch( NoCurrentItem280Exception e ) {
			System.out.println("  Inner test succeed: Caught exception.  OK!");
		}
			// Test delete first item
		L.insert(5);
		L.insert(6);
		L.insert(7);
		L.goFirst();
		L.deleteItem();
		System.out.println("List should be: 6, 5,");
		System.out.print(  "     and it is: ");
		System.out.println(L);
			// Test delete last item
		L.insert(7);
		L.goForth();
		L.deleteItem();
		System.out.println("List should be: 7, 6,");
		System.out.print(  "     and it is: ");
		System.out.println(L);
			// Test delete middle item
		L.insert(8);
		L.goFirst();
		L.goForth();
		L.deleteItem();
		System.out.println("List should be: 8, 6,");
		System.out.print(  "     and it is: ");
		System.out.println(L);
		
		// Test deleteLast
		System.out.println("\n//Test deleteLast");
		L.clear();
		System.out.print("List should be empty...");
		if( L.isEmpty() ) System.out.println("and it is.");
		else System.out.println("ERROR: and it is *NOT*.");
			// Test deleteLast on an empty list
		System.out.println("Deleting last from empty list.");
		try {
			L.deleteLast();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");			
		}
		catch( ContainerEmpty280Exception e ) {
			System.out.println("  Inner test succeed: Caught exception.  OK!");
		}
			// Test deleteLast when there is only one node in list
		L.insert(5);
		L.deleteLast();
		System.out.println("Deleting last from list with single node");
		System.out.print("List should be empty...");
		if( L.isEmpty() ) System.out.println("and it is.");
			// Test deleteLast when cursor is at last node
		L.insert(5);
		L.insert(6);
		L.goLast();
		L.deleteLast();
		if (L.position.item == 6 && L.prevPosition == null){
			System.out.println("  Inner test succeed: Test deleteLast when cursor is at last node, cursor updated");
		}
		else {
			throw new RuntimeException("test failed");
		}
			// Test deleteLast at after()
		L.insert(7);
		L.goAfter();
		L.deleteLast();
		if (L.position == null && L.prevPosition.item == 7){
			System.out.println("  Inner test succeed: Test deleteLast at after(), cursor updated");
		}
		else {
			throw new RuntimeException("test failed");
		}
			// Test deleteLast at normal cases
		L.clear();
		L.insert(5);
		L.insert(6);
		L.insert(7);
		L.deleteLast();
		System.out.println("List should be: 7, 6,");
		System.out.print(  "     and it is: ");
		System.out.println(L);

	}
}
