/**
 * Name : Huang Jiaming
 * NSID : jih211
 * StuID: 11207964
 */
package lib280.base;

import java.util.ArrayList;

public class KDTree280 {
	KDNode280 rootNode;
	int dim;
	ArrayList<NDPoint280> searchResult;
	
	public KDTree280(int dim){
		this.dim = dim;
		rootNode = null;
		searchResult = new ArrayList<>();
	}
	
	public void buildKDTree(NDPoint280[] arr, int left, int right, int depth){
		rootNode = kdtree(arr, left, right, depth);
	}
	
	public KDNode280 kdtree (NDPoint280[] arr, int left, int right, int depth){
		if (left > right) {
			return null;
		}
		else{
			int d = depth % this.dim;
			int medianOffset = (left + right) / 2;
			jSmallest(arr, left, right, medianOffset, d);
			KDNode280 node = new KDNode280();
			node.data = arr[medianOffset];
			node.leftNode = kdtree(arr, left, medianOffset-1, depth+1);
			node.rightNode = kdtree(arr, medianOffset+1, right, depth+1);
			return node;
		}
	}
	
	/**
	 * 
	 * @param arr array of KDNode280(Comparable NDPoint280 inside)
	 * @param left offset of start of subArray for which we want the median element
	 * @param right offset of end of subArray for which we want the median element
	 * @param j we want to find the element that belongs at array index j, to find the median, pass in j=(left+right)/2
	 * @param dim dimension of the points to be used to compare points
	 */
	public void jSmallest(NDPoint280[] arr, int left, int right, int j, int dim){
		if (right > left) {
			int pivotIndex = partition(arr, left, right, dim);
			if (j < pivotIndex) {
				jSmallest(arr, left, pivotIndex-1, j, dim);
			}else if (j > pivotIndex) {
				jSmallest(arr, pivotIndex+1, right, j, dim);
			}
		}
	}
	
	// partition a subArray using its last element as a pivot
	// dim: indicate which dimension of the points is to be used to compare points
	public int partition(NDPoint280[] arr, int left, int right, int dim){
		NDPoint280 pivot = arr[right];
		int swapOffset = left;
		for (int i=left; i<right; i++){
			if (arr[i].compareByDim(dim, pivot) <= 0) {
				NDPoint280 temp = arr[i];
				arr[i] = arr[swapOffset];
				arr[swapOffset] = temp;
				swapOffset++;
			}
		}
		NDPoint280 temp = arr[right];
		arr[right] = arr[swapOffset];
		arr[swapOffset] = temp;
		return swapOffset;
	}
	
	/**
	 * 
	 * @param inRange an ArrayList to store nodes in range.
	 * @param node KDNode to check if it is in range
	 * @param low lower bound of search range
	 * @param high upper bound of search range
	 * @param depth the dimension of points to be used to check range, depth of tree mod dim
	 * @param dim the dim of the tree, constant for a specific tree
	 */
	public static void rangeSearch(ArrayList<NDPoint280> inRange, KDNode280 node, NDPoint280 low, NDPoint280 high, int depth, int dim){
		if (node == null) return;
		// in-range elements are in rightSubTree
		if (node.data.idx(depth) < low.idx(depth)){
			rangeSearch(inRange, node.rightNode, low, high, (depth+1) % dim, dim);
		}
		else if (node.data.idx(depth) > high.idx(depth)){	// in-range elements are in leftSubTree
			rangeSearch(inRange, node.leftNode, low, high, (depth+1) % dim, dim);
		}
		else {
			rangeSearch(inRange, node.leftNode, low, high, (depth+1) % dim, dim);
			rangeSearch(inRange, node.rightNode, low, high, (depth+1) % dim, dim);
			int j = 0;
			while (j < dim && node.data.idx(j) >= low.idx(j) && node.data.idx(j) <= high.idx(j)){	//check if in-range
				j++;
			}
			if (j == dim){
				inRange.add(node.data);
			}
			
		}
		
	}
	
	public String toString(){
		return rootNode.toString(0);
	}
	
	public static void main(String[] args) {
		NDPoint280[] arr0 = new NDPoint280[7];
		arr0[0] = new NDPoint280(new Double[]{ 5.0,  2.0});
		arr0[1] = new NDPoint280(new Double[]{ 9.0, 10.0});
		arr0[2] = new NDPoint280(new Double[]{11.0,  1.0});
		arr0[3] = new NDPoint280(new Double[]{ 4.0,  3.0});
		arr0[4] = new NDPoint280(new Double[]{ 2.0, 12.0});
		arr0[5] = new NDPoint280(new Double[]{ 3.0,  7.0});
		arr0[6] = new NDPoint280(new Double[]{ 1.0,  5.0});
		
		System.out.println("Input 2D points:");
		for (NDPoint280 p : arr0){
			System.out.println(p);
		}
		
		KDTree280 tree0 = new KDTree280(2);
		tree0.buildKDTree(arr0, 0, 6, 0);
		
		System.out.println("\nThe 2D tree built from these points is:");
		System.out.println(tree0);
		System.out.println();
		
		NDPoint280[] arr = new NDPoint280[8];
		arr[0] = new NDPoint280(new Double[]{ 1.0, 12.0,  1.0});
		arr[1] = new NDPoint280(new Double[]{18.0,  1.0,  2.0});
		arr[2] = new NDPoint280(new Double[]{ 2.0, 12.0, 16.0});
		arr[3] = new NDPoint280(new Double[]{ 7.0,  3.0,  3.0});
		arr[4] = new NDPoint280(new Double[]{ 3.0,  7.0,  5.0});
		arr[5] = new NDPoint280(new Double[]{16.0,  4.0,  4.0});
		arr[6] = new NDPoint280(new Double[]{ 4.0,  6.0,  1.0});
		arr[7] = new NDPoint280(new Double[]{ 5.0,  5.0, 17.0});
		
		System.out.println("Input 3D points:");
		for (NDPoint280 p : arr){
			System.out.println(p);
		}

		KDTree280 tree = new KDTree280(3);
		tree.buildKDTree(arr, 0, 7, 0);
		
		System.out.println("\nThe 3D tree built from these points is:");
		System.out.println(tree);
		System.out.println();
		
		ArrayList<NDPoint280> ret1 = new ArrayList<>();
		NDPoint280 low1 = new NDPoint280(new Double[]{0.0, 1.0, 0.0});
		NDPoint280 high1= new NDPoint280(new Double[]{4.0, 6.0, 3.0});
		KDTree280.rangeSearch(ret1, tree.rootNode, low1, high1, 0, 3);
		
		ArrayList<NDPoint280> ret2 = new ArrayList<>();
		NDPoint280 low2 = new NDPoint280(new Double[]{0.0, 1.0, 0.0});
		NDPoint280 high2= new NDPoint280(new Double[]{8.0, 7.0, 4.0});
		KDTree280.rangeSearch(ret2, tree.rootNode, low2, high2, 0, 3);
		
		ArrayList<NDPoint280> ret3 = new ArrayList<>();
		NDPoint280 low3 = new NDPoint280(new Double[]{ 0.0, 1.0,  0.0});
		NDPoint280 high3= new NDPoint280(new Double[]{17.0, 9.0, 10.0});
		KDTree280.rangeSearch(ret3, tree.rootNode, low3, high3, 0, 3);
		
		System.out.println("Looking for points between " + low1 + " and " + high1 + ". Found:");
		for (NDPoint280 p : ret1){
			System.out.println(p);
		}
		System.out.println();
		
		System.out.println("Looking for points between " + low2 + " and " + high2 + ". Found:");
		for (NDPoint280 p : ret2){
			System.out.println(p);
		}
		System.out.println();
		
		System.out.println("Looking for points between " + low3 + " and " + high3 + ". Found:");
		for (NDPoint280 p : ret3){
			System.out.println(p);
		}
		System.out.println();
		
	}
	
	
}
