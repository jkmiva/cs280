/**
 * Name:  Huang Jiaming
 * NSID:  jih211
 * StuID: 11207964
 */
package lib280.dispenser;

import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.ContainerFull280Exception;
import lib280.tree.ArrayedBinaryTreeIterator280;
import lib280.tree.IterableArrayedHeap280;

@SuppressWarnings("unused")
public class PriorityQueue280<I extends Comparable<? super I>> {

	// This is the heap that we are restricting.
	// Items in the priority queue get stored in the heap.
	protected IterableArrayedHeap280<I> items;

	/**
	 * Create a new priorty queue with a given capacity.
	 * 
	 * @param cap
	 *            The maximum number of items that can be in the queue.
	 */
	public PriorityQueue280(int cap) {
		items = new IterableArrayedHeap280<I>(cap);
	}

	public String toString() {
		return items.toString();
	}

	// TODO
	// Add Priority Queue ADT methods (from the specification) here.
	/**
	 * insert an item into the priority queue
	 * 
	 * @param in
	 *            item to be inserted
	 */
	public void insert(I in) {
		this.items.insert(in);
	}

	/**
	 * Determine if the queue is full.
	 * 
	 * @return true if the queue is full. false otherwise.
	 */
	public boolean isFull() {
		return this.items.isFull();
	}

	/**
	 * Determine if the queue is empty.
	 * 
	 * @return true if the queue is empty. false otherwise.
	 */
	public boolean isEmpty() {
		return this.items.isEmpty();
	}

	/**
	 * Get the number of items in the queue.
	 * 
	 * @return The number of items in the queue.
	 * 
	 */
	public int count() {
		return this.items.count();
	}

	/**
	 * return the largest(highest priority) item in the queue
	 * 
	 * @return the largest(highest priority) item in the queue
	 */
	public I maxItem() {
		ArrayedBinaryTreeIterator280<I> iter = this.items.iterator();
		iter.goFirst();
		return iter.item();
	}

	/**
	 * return the smallest(lowest priority) item in the queue
	 * 
	 * @return the smallest(lowest priority) item in the queue
	 */
	public I minItem() {

		ArrayedBinaryTreeIterator280<I> iter = this.items.iterator();
		int num = this.items.count() / 2;
		iter.goBefore();
		for (int i = 0; i <= num; i++) {
			iter.goForth();
		}
		int j = 0;
		I min = iter.item();
		for (int i = 1; i < this.items.count() - num; i++) {
			iter.goForth();
			if (iter.item().compareTo(min) < 0){
				j = i;
				min = iter.item();
			}
		}
		// find the first occurrence of the smallest items, strange requirement...
		int index = num + 1 + j;
		iter.goFirst();
		for (int i = 1; i < index; i++) {
			if (iter.item().compareTo(min) == 0){
				min = iter.item();
				break;
			}
			iter.goForth();
		}
		return min;
	}

	// delete the largest(highest priority) item in the queue
	public void deleteMax() {
		this.items.deleteItem();
	}

	// delete the smallest(lowest priority) item in the queue
	public void deleteMin() {
		ArrayedBinaryTreeIterator280<I> iter = this.items.iterator();
		int num = this.items.count() / 2;
		iter.goBefore();
		for (int i = 0; i <= num; i++) {
			iter.goForth();
		}

		int j = 0;
		I min = iter.item();
		for (int i = 1; i < this.items.count() - num; i++) {
			iter.goForth();
			if (iter.item().compareTo(min) < 0) {
				j = i;
				min = iter.item();
			}
		}
		int index = num + 1 + j;
		iter.goBefore();
		for (int i = 1; i <= index; i++) {
			iter.goForth();
		}
		items.deleteAtPosition(iter);

	}

	// delete all occurrences of the highest priority items in the queue
	public void deleteAllMax() {
		I max = this.maxItem();
		while (!this.items.isEmpty() && this.maxItem().compareTo(max) == 0) {
			this.deleteMax();
		}
	}

	public static void main(String args[]) {
		class PriorityItem<I> implements Comparable<PriorityItem<I>> {
			I item;
			Double priority;

			public PriorityItem(I item, Double priority) {
				super();
				this.item = item;
				this.priority = priority;
			}

			public int compareTo(PriorityItem<I> o) {
				return this.priority.compareTo(o.priority);
			}

			public String toString() {
				return this.item + ":" + this.priority;
			}
		}

		PriorityQueue280<PriorityItem<String>> Q = new PriorityQueue280<PriorityItem<String>>(5);

		// Test isEmpty()
		if (!Q.isEmpty())
			System.out.println("Error: Queue is empty, but isEmpty() says it isn't.");

		// Test insert() and maxItem()
		Q.insert(new PriorityItem<String>("Sing", 5.0));
		if (Q.maxItem().item.compareTo("Sing") != 0) {
			System.out.println("??Error: Front of queue should be 'Sing' but it's not. It is: " + Q.maxItem().item);
		}

		// Test isEmpty() when queue not empty
		if (Q.isEmpty())
			System.out.println("Error: Queue is not empty, but isEmpty() says it is.");

		// test count()
		if (Q.count() != 1) {
			System.out.println("Error: Count should be 1 but it's not.");
		}

		// test minItem() with one element
		if (Q.minItem().item.compareTo("Sing") != 0) {
			System.out.println("Error: min priority item should be 'Sing' but it's not.");
		}

		// insert more items
		Q.insert(new PriorityItem<String>("Fly", 5.0));
		if (Q.maxItem().item.compareTo("Sing") != 0)
			System.out.println("Front of queue should be 'Sing' but it's not.");
		Q.insert(new PriorityItem<String>("Dance", 3.0));
		if (Q.maxItem().item.compareTo("Sing") != 0)
			System.out.println("Front of queue should be 'Sing' but it's not.");
		Q.insert(new PriorityItem<String>("Jump", 7.0));
		if (Q.maxItem().item.compareTo("Jump") != 0)
			System.out.println("Front of queue should be 'Jump' but it's not.");

		if (Q.minItem().item.compareTo("Dance") != 0)
			System.out.println("minItem() should be 'Dance' but it's not.");

		if (Q.count() != 4) {
			System.out.println("Error: Count should be 4 but it's not.");
		}

		// Test isFull() when not full
		if (Q.isFull())
			System.out.println("Error: Queue is not full, but isFull() says it is.");

		Q.insert(new PriorityItem<String>("Eat", 10.0));
		if (Q.maxItem().item.compareTo("Eat") != 0)
			System.out.println("Front of queue should be 'Eat' but it's not.");

		if (!Q.isFull())
			System.out.println("Error: Queue is full, but isFull() says it isn't.");

		// Test insertion on full queue
		try {
			Q.insert(new PriorityItem<String>("Sleep", 15.0));
			System.out.println("Expected ContainerFull280Exception inserting to full queue but got none.");
		} catch (ContainerFull280Exception e) {
			// Expected exception
		} catch (Exception e) {
			System.out.println(
					"Expected ContainerFull280Exception inserting to full queue but got a different exception.");
			e.printStackTrace();
		}

		// test deleteMin
		Q.deleteMin();
		// System.out.println(Q.toString());//mytest
		if (Q.minItem().item.compareTo("Sing") != 0)
			System.out.println("Min item should be 'Sing', but it isn't.");

		Q.insert(new PriorityItem<String>("Dig", 1.0));
		// System.out.println(Q.toString());//mytest
		// System.out.println(Q.minItem().toString());//mytest
		if (Q.minItem().item.compareTo("Dig") != 0)
			System.out.println("minItem() should be 'Dig' but it's not.");

		// Test deleteMax
		Q.deleteMax();
		if (Q.maxItem().item.compareTo("Jump") != 0)
			System.out.println("Front of queue should be 'Jump' but it's not.");

		Q.deleteMax();
		if (Q.maxItem().item.compareTo("Fly") != 0)
			System.out.println("Front of queue should be 'Fly' but it's not.");

		if (Q.minItem().item.compareTo("Dig") != 0)
			System.out.println("minItem() should be 'Dig' but it's not.");

		Q.deleteMin();
		if (Q.maxItem().item.compareTo("Fly") != 0)
			System.out.println("Front of queue should be 'Fly' but it's not.");

		Q.insert(new PriorityItem<String>("Scream", 2.0));
		Q.insert(new PriorityItem<String>("Run", 2.0));

		if (Q.maxItem().item.compareTo("Fly") != 0)
			System.out.println("Front of queue should be 'Fly' but it's not.");

		// test deleteAllMax()
		Q.deleteAllMax();
		if (Q.maxItem().item.compareTo("Scream") != 0)
			System.out.println("Front of queue should be 'Scream' but it's not.");
		if (Q.minItem().item.compareTo("Scream") != 0)
			System.out.println("minItem() should be 'Scream' but it's not.");
		Q.deleteAllMax();

		// Queue should now be empty again.
		if (!Q.isEmpty())
			System.out.println("Error: Queue is empty, but isEmpty() says it isn't.");

		System.out.println("Regression test complete.");
	}
}
