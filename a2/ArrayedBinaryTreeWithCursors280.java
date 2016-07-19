//Name: Huang Jiaming
//NSID: jih211
//StuID:11207964

package lib280.tree;

import lib280.base.Cursor280;
import lib280.base.CursorPosition280;
import lib280.dictionary.Dict280;
import lib280.exception.AfterTheEnd280Exception;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.ContainerFull280Exception;
import lib280.exception.DuplicateItems280Exception;
import lib280.exception.InvalidArgument280Exception;
import lib280.exception.InvalidState280Exception;
import lib280.exception.ItemNotFound280Exception;
import lib280.exception.NoCurrentItem280Exception;

public class ArrayedBinaryTreeWithCursors280<I> extends
		ArrayedBinaryTree280<I> implements Dict280<I>, Cursor280<I> {

	@Override
	/**	Move the current position to the first (the default) or the next x, if it exists.
	@param x item being sought */
	public void search(I x) {
		// TODO Auto-generated method stub
		if(this.isEmpty())
			return;
		for(int i=1;i<=count;i++){
			if(membershipEquals(this.items[i], x)){
				currentNode = i;
				return;
			}
		}
		currentNode = 1;
	}

	@Override
	/**	
	 * 	Does the container contain the element 'y'?. 
	 *	@param y item whose presence is to be determined 
	 *  @return True if y is in the container, false otherwise.
	 */
	public boolean has(I y) {
		// TODO Auto-generated method stub
		if(this.isEmpty())
			return false;
		for(int i=1;i<=count;i++){
			if(membershipEquals(this.items[i], y))
				return true;
		}
		return false;
	}

	@Override
	/**	Is the current position before the start of the structure?. */
	public boolean before() {
		// TODO Auto-generated method stub
		if(currentNode == 0)
			return true;
		else
			return false;
	}

	@Override
	/**	Is the current position after the end of the structure?. */
	public boolean after() {
		// TODO Auto-generated method stub
		if(currentNode == count+1)
			return true;
		else
			return false;
	}

	@Override
	/**	Advance one item in the data structure. 
	 * @precond  !after()
	 * @throws AfterTheEnd280Exception
     */
	public void goForth() throws AfterTheEnd280Exception {
		// TODO Auto-generated method stub
		if(after())
			throw new AfterTheEnd280Exception("Error, can't goForth at position after()");
		currentNode++;
	}

	@Override
	/**	
	 * Go to the first item in the structure. 
	 * @precond !isEmpty()
	 * @throws ContainerEmpty280Exception
	 */
	public void goFirst() throws ContainerEmpty280Exception {
		// TODO Auto-generated method stub
		if(isEmpty())
			throw new ContainerEmpty280Exception("Error, the tree is empty");
		this.root();
		
	}

	@Override
	/**	Move to the position prior to the first element in the structure. */
	public void goBefore() {
		// TODO Auto-generated method stub
		currentNode = 0;
	}

	@Override
	/**	Move to the position after the last element in the structure. */
	public void goAfter() {
		// TODO Auto-generated method stub
		currentNode = count + 1;
	}

	@Override
	/**	Insert x into the tree.
	 * @precond !isFull() and !has(x)
	 * @param x item to be inserted into the dictionary 
	 */
	public void insert(I x) throws ContainerFull280Exception, DuplicateItems280Exception {
		// TODO Auto-generated method stub
		if(this.isFull())
			throw new ContainerFull280Exception("Error! the tree is full");
		if(this.isEmpty()){
			this.currentNode ++;
			this.items[currentNode] = x;
			this.count ++;
			return;
		}
		if(this.has(x))
			throw new DuplicateItems280Exception("Error, can't insert duplicated Item");
		this.currentNode ++;
		this.items[currentNode] = x;
		this.count ++;
		
	}

	@Override
	/**	Delete the item x.
	 * @precond has(x)
	 * @param x item to be deleted from the dictionary 
	 */
	public void delete(I x) throws ItemNotFound280Exception {
		// TODO Auto-generated method stub
		if(!has(x))
			throw new ItemNotFound280Exception("Error, item not found");
		//save current position
		ArrayedBinaryTreePosition280 save = new ArrayedBinaryTreePosition280(currentNode);
		search(x);
		deleteItem();
		// restore previous position
		goPosition(save);
	}

	@Override
	/**	Delete current item from the data structure. 
	 * @precond	itemExists() 
	 */
	public void deleteItem() throws NoCurrentItem280Exception {
		// TODO Auto-generated method stub
		if(!itemExists())
			throw new NoCurrentItem280Exception("Error, no current item");
		// when current is at last node
		if(currentNode == count){
			currentNode --;
			count --;
			return;
		}
		
		items[currentNode] = items[count];
		count --;
		
	}




	protected boolean searchesRestart;
	
	public ArrayedBinaryTreeWithCursors280(int cap) {
		super(cap);
		searchesRestart = true;
	}

	@Override
	public I obtain(I y) throws ItemNotFound280Exception {
		CursorPosition280 saved = this.currentPosition();
		this.goFirst();
		while(this.itemExists()) {
			if( membershipEquals(this.item(), y)) {
				I found = this.item();
				this.goPosition(saved);
				return found;
			}
			this.goForth();
		}
		this.goPosition(saved);
		throw new ItemNotFound280Exception("The given item could not be found.");
	}




	@Override
	public boolean membershipEquals(I x, I y) {
		if (x.equals(y))
			return true;
		else
			return false;
	}



	@Override
	public void restartSearches() {
		this.searchesRestart = true;		
	}

	@Override
	public void resumeSearches() {
		this.searchesRestart = false;
	}


	
	@Override
	public CursorPosition280 currentPosition() {
		return new ArrayedBinaryTreePosition280(this.currentNode);
	}

	@Override
	public void goPosition(CursorPosition280 c) {
		if (!(c instanceof ArrayedBinaryTreePosition280))
			throw new InvalidArgument280Exception("The cursor position parameter" 
					    + " must be a ArrayedBinaryTreePosition280<I>");		
		
		this.currentNode = ((ArrayedBinaryTreePosition280)c).currentNode;
	}

	/**
	 * Move the cursor to the parent of the current node.
	 * @precond Current node is not the root.
	 * @throws InvalidState280Exception when the cursor is on the root already.
	 */
	public void parent() throws InvalidState280Exception {
		// TODO - Implement this method.
		if(this.currentNode == 1)
			throw new InvalidState280Exception("Invalid! cursor is on the root already");
		this.currentNode >>= 1;
		
	}
	
	/**
	 * Move the cursor to the left child of the current node.
	 * 
	 * @precond The tree must not be empty and the current node must have a left child.
	 * @throws ContainerEmpty280Exception if the tree is empty.
	 * @throws InvalidState280Exception if the current node has no left child.
	 */
	public void goLeftChild()  throws InvalidState280Exception, ContainerEmpty280Exception {
		// TODO - Implement this method.
		if(this.isEmpty())
			throw new ContainerEmpty280Exception("Error! the tree is empty");
		this.currentNode <<= 1;
		if(!this.itemExists()){
			this.currentNode >>= 1;
			throw new InvalidState280Exception("Error! the current node has no left child");
		}
	}	
	
	/**
	 * Move the cursor to the right child of the current node.
	 * 
	 * @precond The tree must not be empty and the current node must have a right child.
	 * @throws ContainerEmpty280Exception if the tree is empty.
	 * @throws InvalidState280Exception if the current item has no right child.
	 */
	public void goRightChild() throws InvalidState280Exception, ContainerEmpty280Exception {
		// TODO - Implement this method.
		if(this.isEmpty())
			throw new ContainerEmpty280Exception("Error! the tree is empty");
		this.currentNode <<= 1;
		this.currentNode ++;
		if(!this.itemExists()){
			this.currentNode --;
			this.currentNode >>= 1;
			throw new InvalidState280Exception("Error! the current node has no left child");
		}
	}	
	
	/**
	 * Move the cursor to the sibling of the current node.
	 * 
	 * @precond The current node must have a sibling.  The tree must not be empty.
	 * @throws ContainerEmpty280Exception if the tree is empty.
	 * @throws InvalidState280Exception if the current item has no sibling.
	 */	
	public void goSibling() throws InvalidState280Exception, ContainerEmpty280Exception {
		// TODO - Implement this method.
		if(this.isEmpty())
			throw new ContainerEmpty280Exception("Error, the tree is empty");
		if(this.currentNode == 1)
			throw new InvalidState280Exception("Error, invalid pisition");
		
	}
	
	/**
	 * Move the cursor to the root of the tree.
	 * 
	 * @precond The tree must not be empty.
	 * @throws ContainerEmpty280Exception if the tree is empty.
	 */
	public void root() throws ContainerEmpty280Exception {
		// TODO - Implement this method.
		if(this.isEmpty())
			throw new ContainerEmpty280Exception("Error! the tree is empty");
		this.currentNode = 1;
		
	}	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayedBinaryTreeWithCursors280<Integer> T = new ArrayedBinaryTreeWithCursors280<Integer>(10);
		
		// IsEmpty on empty tree.
		if(!T.isEmpty()) System.out.println("Test of isEmpty() on empty tree failed.");
		
		// Test root() on empty tree.
		Exception x = null;
		try {
			T.root();
		}
		catch(ContainerEmpty280Exception e) {
			x = e;
		}
		finally {
			if( x == null ) System.out.println("Expected exception moving to root of empty tree.  Got none.");
		}
		
		// test goFirst() on empty tree
		x = null;
		try {
			T.goFirst();
		}
		catch(ContainerEmpty280Exception e) {
			x = e;
		}
		finally {
			if( x == null ) System.out.println("Expected exception moving to first elelement of empty tree.  Got none.");
		}
	
		
		
		// Test goLeftChild() on empty tree.
		x = null;
		try {
			T.goLeftChild();
		}
		catch(ContainerEmpty280Exception e) {
			x = e;
		}
		finally {
			if( x == null ) System.out.println("Expected exception moving to left child in empty tree.  Got none.");
		}
		
		// Test goLeftChild() on empty tree.
		x = null;
		try {
			T.goRightChild();
		}
		catch(ContainerEmpty280Exception e) {
			x = e;
		}
		finally {
			if( x == null ) System.out.println("Expected exception moving to right child in empty tree.  Got none.");
		}
		
		
		// Check itemExists on empty tree
		if(T.itemExists() ) System.out.println("itemExists() returned true on an empty tree.");
		
		// Insert on empty tree.
		T.insert(1);
		
		// Check ItemExists on tree with one element.
		T.root();
		if(!T.itemExists() ) System.out.println("itemExists() returned false on a tree with one element with cursor at the root.");
		
		// isEmpty on tree with 1 element.
		if(T.isEmpty()) System.out.println("Test of isEmpty() on non-empty tree failed.");

		// Insert on tree with 1 element
		T.insert(2);
		
		// Insert some more elements
		for(int i=3; i <= 10; i++) T.insert(i);
		
		if(T.count() != 10  ) System.out.println("Expected tree count to be 10, got "+ T.count());

		
		// Test for isFull on a full tree.
		if(!T.isFull()) System.out.println("Test of isFull() on a full tree failed.");
		
		// Test insert on a full tree
		x = null;
		try {
			T.insert(11);
		}
		catch(ContainerFull280Exception e) {
			x = e;
		}
		finally {
			if( x == null ) System.out.println("Expected exception inserting into a full tree.  Got none.");
		}
		
		

		
		// Test positioning methods
		
		// Test root()
		T.root();
		if( T.item() != 1 ) System.out.println("Expected item at root to be 1, got " + T.item());
		
		T.goLeftChild();
		
		if( T.item() != 2 ) System.out.println("Expected current item to be 2, got " + T.item());
		
		T.goRightChild();
		if( T.item() != 5 ) System.out.println("Expected current item to be 5, got " + T.item());

		
		T.goLeftChild();
		if( T.item() != 10 ) System.out.println("Expected current item to be 10,  got " + T.item());
		
		// Current node now has no children.
		x = null;
		try {
			T.goLeftChild();
		}
		catch( InvalidState280Exception e ) {
			x = e;
		}
		finally {
			if( x == null) System.out.println("Expected exception moving to left child of a leaf.  Got none.");
		}
		
		x = null;
		try {
			T.goRightChild();
		}
		catch( InvalidState280Exception e ) {
			x = e;
		}
		finally {
			if( x == null) System.out.println("Expected exception moving to right child of a leaf.  Got none.");
		}
		
		// Remove the last item ( a leaf)
		T.deleteItem();
		if( T.item() != 9 ) System.out.println("Expected current item to be 9, got " + T.item());

		T.parent();
		
		
		
		// Remove a node with 2 children.  The right child 9 gets promoted.
		T.deleteItem();
		if( T.item() != 9 ) System.out.println("Expected current item to be 9, got " + T.item());
		
		
		// Remove a node with 1 child.  The left child 8 gets promoted.
		T.deleteItem();
		if( T.item() != 8 ) System.out.println("Expected current item to be 8, got " + T.item());
		
		// Remove the root successively.  There are 7 items left.
		T.root();
		T.deleteItem();
		if( T.item() != 7 ) System.out.println("Expected root to be 7, got " + T.item());

		T.deleteItem();
		if( T.item() != 6 ) System.out.println("Expected root to be 6, got " + T.item());

		T.deleteItem();
		if( T.item() != 5 ) System.out.println("Expected root to be 5, got " + T.item());
		
		T.deleteItem();
		if( T.item() != 8 ) System.out.println("Expected root to be 8, got " + T.item());

		T.deleteItem();
		if( T.item() != 3 ) System.out.println("Expected root to be 3, got " + T.item());

		T.deleteItem();
		if( T.item() != 2 ) System.out.println("Expected root to be 2, got " + T.item());

		// Tree has one item.  Try parent() on one item.
		x = null;
		try {
			T.parent();
		}
		catch( InvalidState280Exception e ) {
			x = e;
		}
		finally {
			if( x == null) System.out.println("Expected exception moving to parent of root.  Got none.");
		}
		
		
		// Try to go to the sibling
		x = null;
		try {
			T.goSibling();
		}
		catch(InvalidState280Exception e) {
			x = e;
		}
		finally {
			if( x == null ) System.out.println("Expected exception moving to sibling when at the root.  Got none.");
		}
		
		
		
		T.deleteItem(); 
		
		
		// Tree should now be empty
		if(!T.isEmpty()) System.out.println("Expected empty tree.  isEmpty() returned false.");

		if(T.capacity() != 10) System.out.println("Expected capacity to be 10, got "+ T.capacity());
		
		if(T.count() != 0  ) System.out.println("Expected tree count to be 0, got "+ T.count());
		
		// Remove from empty tree.
		x = null;
		try {
			T.deleteItem();
		}
		catch(NoCurrentItem280Exception e) {
			x = e;
		}
		finally {
			if( x == null ) System.out.println("Expected exception deleting from an empty tree.  Got none.");
		}
		
		
		
		// Try to go to the sibling
		x = null;
		try {
			T.goSibling();
		}
		catch(ContainerEmpty280Exception e) {
			x = e;
		}
		finally {
			if( x == null ) System.out.println("Expected exception moving to sibling in empty tree tree.  Got none.");
		}
		
		
		T.insert(1);
		T.root();
		
		// Try to go to the sibling when there is no child.
		x = null;
		try {
			T.goSibling();
		}
		catch(InvalidState280Exception e) {
			x = e;
		}
		finally {
			if( x == null ) System.out.println("Expected exception moving to sibling of node with no sibling.  Got none.");
		}
		
		T.goBefore();
		if(!T.before()) System.out.println("Error: Should be in 'before' position, but before() reports otherwise.");
		if(T.after()) System.out.println("Error: T.after() reports cursor in the after position when it should not be.");
		
		T.goForth();
		if(T.before()) System.out.println("Error: T.before() reports cursor in the before position when it should not be.");
		if(T.after()) System.out.println("Error: T.after() reports cursor in the after position when it should not be.");

		T.goForth();
		if(!T.after()) System.out.println("Error: Should be in 'after' position, but after() reports otherwise.");
		if(T.before()) System.out.println("Error: T.before() reports cursor in the before position when it should not be.");
		
		x=null;
		try {
			T.goForth();
		}
		catch(AfterTheEnd280Exception e) {
			x = e;
		}
		finally {
			if( x == null ) System.out.println("Expected exception advancing cursor when already after the end.  Got none.");
		}

		
		int y=-1;
		T.goBefore();
		try {
			y =  T.obtain(1); 
		}
		catch( ItemNotFound280Exception e ) {
			System.out.println("Error: Unexpected exception occured when attempting T.obtain(1).");
		}
		finally {
			if(y != 1 ) System.out.println("Obtained item should be 1 but it isn't.");
			if(!T.before()) System.out.println("Error: cursor should still be in the before() position after T.obtain(1), but it isn't.");
		}
		
		if(!T.has(1)) System.out.println("Error: Tree has element 1, but T.has(1) reports that it does not.");
		
		// Test delete()
		T.insert(2);
		T.insert(3);
		T.insert(4);
		
		// Move cursor to item 2
		T.search(2);
		T.delete(4);
		// This should remove 4, but leave the cursor on 2.
		if(T.has(4))
			System.out.println("Error: 4 was deleted, but has() says it's there.");
		if(T.item() != 2)
			System.out.println("delete() did not maintain the pre-existing cursor position.");
		
		// Move cursor to item 3
		T.search(3);
		T.delete(3);
		// This should have deleted 3, and since the cursor was on 3, moved
		// it back to 2.
		if(T.has(3))
			System.out.println("Error: 3 was deleted, but has() says it's there.");
		if(T.currentNode == 3)
			System.out.println("Error: cursor was on 3 when it was deleted, and should have been moved to 2, but it wasn't.");

		
		System.out.println("Regression test complete.");
	}
}
