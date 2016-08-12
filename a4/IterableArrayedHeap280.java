/**
 * Name:  Huang Jiaming
 * NSID:  jih211
 * StuID: 11207964
 */
package lib280.tree;

import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.NoCurrentItem280Exception;

public class IterableArrayedHeap280<I extends Comparable<? super I>> extends ArrayedHeap280<I> {

	/**
	 * Create an iterable heap with a given capacity.
	 * 
	 * @param cap
	 *            The maximum number of elements that can be in the heap.
	 */
	public IterableArrayedHeap280(int cap) {
		super(cap);
	}

	// Add iterator() and deleteAtPosition() methods here.
	/**
	 * Removes the item pointed by iterator.
	 * 
	 * @param iter iteration which points to the item to be deleted
	 * @throws ContainerEmpty280Exception if the heap is empty.
	 * @throws NoCurrentItem280Exception if currentNode position doesn't exist. 
	 * 
	 */
	public void deleteAtPosition(ArrayedBinaryTreeIterator280<I> iter)
			throws ContainerEmpty280Exception, NoCurrentItem280Exception {
		if (iter.currentNode > this.count)
			throw new NoCurrentItem280Exception();
		if (this.count == 0)
			throw new ContainerEmpty280Exception();

		this.items[currentNode] = this.items[count];
		this.count--;

		// If we deleted the last remaining item, make the the current item
		// invalid, and we're done.
		if (this.count == 0) {
			this.currentNode = 0;
			return;
		}

		// Propagate the new root down the tree.
		int n = currentNode;
		
		// While offset n has a left child...
		while (findLeftChild(n) <= count) {
			// Select the left child.
			int child = findLeftChild(n);

			// If the right child exists and is larger, select it instead.
			if (child + 1 <= count && items[child].compareTo(items[child + 1]) < 0)
				child++;

			// If the parent is smaller than the root...
			if (items[n].compareTo(items[child]) < 0) {
				// Swap them.
				I temp = items[n];
				items[n] = items[child];
				items[child] = temp;
				n = child;
			} else
				return;
		}
	}
	/**
	 * 
	 * @return a new ArrayedBinaryTreeIterator280 iterator based on current array heap.
	 */
	public ArrayedBinaryTreeIterator280<I> iterator() {
		return new ArrayedBinaryTreeIterator280<>(this);	
	}

}
