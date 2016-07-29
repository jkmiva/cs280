/**
 * @author Huang Jiaming
 * @NSID jih211
 * @StuID 11207964
 */
package assignment3;

import lib280.base.Dispenser280;
import lib280.exception.ContainerFull280Exception;
import lib280.exception.DuplicateItems280Exception;
import lib280.exception.NoCurrentItem280Exception;
import lib280.tree.ArrayedBinaryTree280;

public class ArrayedHeap280<I extends Comparable<I>> extends ArrayedBinaryTree280<I> implements Dispenser280<I>{

	
	@SuppressWarnings("unchecked")
	/**
	 * Constructor.
	 * 
	 * @param cap Maximum number of elements that can be in the tree.
	 * initialize items with array of comparable
	 */
	public ArrayedHeap280(int cap) {
		super(cap);
		items = (I[]) new Comparable[cap+1];
	}

	@Override
	/**
	 * insert an element into the heap
	 * 
	 * @param x element to be inserted.
	 * @h.precond there is no duplicate x in array
	 * @h.precond array is not full
	 */
	public void insert(I x) throws ContainerFull280Exception, DuplicateItems280Exception {
		if (count!=0 && has(x)) throw new DuplicateItems280Exception();
		if (currentNode > capacity-1) throw new ContainerFull280Exception();
		currentNode++;
		count++;
		this.items[currentNode] = x;
		maxHeapify(currentNode);
	}

	@Override
	/**
	 * delete root element from the heap
	 * 
	 * @h.precond array is not empty
	 */
	public void deleteItem() throws NoCurrentItem280Exception {
		if (count == 0) throw new NoCurrentItem280Exception();
		items[1] = items[currentNode];
		currentNode--;
		count--;
		rebuild(1);
	}
	
	/**
	 * keep the property of maxHeap after deletion
	 * 
	 * @param p index to start rebuild
	 */
	private void rebuild(int p){
		int l = 2 * p;
		int r = 2 * p + 1;
		int larger;
		if (l <= count && items[l].compareTo(items[p]) > 0){
			larger = l;
		}else{
			larger = p;
		}
		if (r <= count && items[r].compareTo(items[larger]) > 0){
			larger = r;
		}
		if (larger != p){
			swap(p, larger);
			rebuild(larger);
		}
	}

	/**
	 * keep the property of maxHeap after insertion
	 * 
	 * @param c index to start maxHeapify
	 */
	private void maxHeapify(int c) {
		if (c/2 == 0 || items[c].compareTo(items[c/2]) < 0){
			return;
		}
		swap(c, c/2);
		maxHeapify(c/2);
	}
	
	/**
	 * help function to swap two elements in heap
	 * 
	 * @param a index of element in heap
	 * @param b index of element in heap
	 */
	private void swap(int a, int b) {
		I temp;
		temp = items[a];
		items[a] = items[b];
		items[b] = temp;
	}
	
	/**
	 * determine if element a is in heap
	 * 
	 * @param a element to be checked
	 * @return true if has a, false otherwise
	 */
	private boolean has(I a) {
		for (int i = 1; i<= count; i++){
			if (items[i].compareTo(a) == 0)
				return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		ArrayedHeap280<Integer> aryHeap = new ArrayedHeap280<>(8);
		
		try {
			aryHeap.deleteItem();
		} catch (NoCurrentItem280Exception e) {
			// TODO: handle exception
			System.out.println("Test succeed! catch exception when delete from empty heap");
		}
		
		aryHeap.insert(1);
		try {
			aryHeap.insert(1);
		} catch (DuplicateItems280Exception e) {
			System.out.println("Test succeed! catch exception when insert duplicateitems.");
		}
		
		aryHeap.insert(2);
		aryHeap.insert(4);
		aryHeap.insert(7);
		aryHeap.insert(12);
		aryHeap.insert(5);
		aryHeap.insert(6);
		aryHeap.insert(9);
		
		if (aryHeap.toString().equals("12, 9, 6, 7, 4, 2, 5, 1, \nCursor is on item with array index 8 (item 1)")){
			System.out.println("Test succeed! after insertion got expected heap.");
		}
		else{
			System.out.println("Test failed!");
		}
		
		try {
			aryHeap.insert(99);
		} catch (ContainerFull280Exception e) {
			System.out.println("Test succeed! catch exception when insert into full heap");
		}
		
		aryHeap.deleteItem();
		aryHeap.deleteItem();
		if (aryHeap.toString().equals("7, 5, 6, 1, 4, 2, \nCursor is on item with array index 6 (item 2)")){
			System.out.println("Test succeed! after deletion got expected heap.");
		}
		else{
			System.out.println("Test failed!");
		}
	}
}
