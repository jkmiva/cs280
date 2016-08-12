/**
 * Name:  Huang Jiaming
 * NSID:  jih211
 * StuID: 11207964
 */
package lib280.tree;

import lib280.base.LinearIterator280;
import lib280.exception.AfterTheEnd280Exception;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.NoCurrentItem280Exception;

public class ArrayedBinaryTreeIterator280<I> extends ArrayedBinaryTreePosition280 implements LinearIterator280<I> {

	// This is a reference to the tree that created this iterator object.
	ArrayedBinaryTree280<I> tree;
	
	// An integer that represents the cursor position is inherited from
	// ArrayedBinaryTreePosition280.
	
	/**
	 * Create a new iterator from a given heap.
	 * @param t The heap for which to create a new iterator.
	 */
	public ArrayedBinaryTreeIterator280(ArrayedBinaryTree280<I> t) {
		super(t.currentNode);
		this.tree = t;
	}

	// Add methods from LinearIterator280 here.

	/**	The current item. 
	 * @precond The cursor is at a valid item.
	 * @return Returns the item at the cursor if there is one.
	 * @throws NoCurrentItem280Exception
	 */
	@Override
	public I item() throws NoCurrentItem280Exception {
		return this.tree.item();
	}

	/**	Is there a current item?. 
	 * @return return true if there exists an item, false otherwise
	 */
	@Override
	public boolean itemExists() {
		return this.tree.itemExists();
	}

	/**	Is the current position before the start of the structure?. */
	@Override
	public boolean before() {
		return this.currentNode == 0;
	}

	/**	Is the current position after the end of the structure?. */
	@Override
	public boolean after() {
		return this.currentNode > this.tree.count;
	}

	/**	Advance one item in the data structure. 
	 * @precond  !after()
	 * @throws AfterTheEnd280Exception
     */
	@Override
	public void goForth() throws AfterTheEnd280Exception {
		if(this.after()) throw new AfterTheEnd280Exception("Cannot advance cursor in the after position.");
		this.tree.currentNode++;
		this.currentNode++;	
	}

	/**	
	 * Go to the first item in the structure. 
	 * @precond !isEmpty()
	 * @throws ContainerEmpty280Exception
	 */
	@Override
	public void goFirst() throws ContainerEmpty280Exception {
		if( this.tree.isEmpty() ) throw new ContainerEmpty280Exception("Cannot move to first item of an empty tree.");
		this.tree.currentNode = 1;
		this.currentNode = 1;
	}

	/**	Move to the position prior to the first element in the structure. */
	@Override
	public void goBefore() {
		this.tree.currentNode = 0;
		this.currentNode = 0;
	}

	/**	Move to the position after the last element in the structure. */
	@Override
	public void goAfter() {
		this.tree.currentNode = this.tree.count + 1;
		this.currentNode = this.tree.count + 1;
	}
	
}
