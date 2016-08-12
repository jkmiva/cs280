/**
 * Name : Huang Jiaming
 * NSID : jih211
 * StuID: 11207964
 */
package lib280.base;

public class KDNode280 implements Comparable<KDNode280>{
	
	NDPoint280 data;
	KDNode280 leftNode;
	KDNode280 rightNode;
	boolean detected;
	
	public KDNode280(){
		data = null;
		leftNode = null;
		rightNode = null;
	}

	@Override
	public int compareTo(KDNode280 o) {
		return this.data.compareTo(o.data);
	}
	
	public int compareByDim(int i, KDNode280 other){
		return this.data.compareByDim(i, other.data);
	}
	
	// helper function of toString
	public String drawBlanks(int n){
		String ret = "";
		for (int i = 0; i < n; i++){
			ret += "    ";
		}
		return ret;
	}
	public String toString(int depth){
		String ret = "";
		if (depth == 0){
			System.out.println("/* r: rootNode, L: leftSubTree, R: rightSubTree, leftmost number: level of nodes */");
			ret += "1r: " + this.data.toString();
		}
		else {
			ret += this.data.toString();
		}
		if (this.leftNode != null){
			ret += "\n" + drawBlanks(depth+1) + (depth+2) + "L: " + leftNode.toString(depth+1);
		}
		if (this.rightNode != null){
			ret += "\n" + drawBlanks(depth+1) + (depth+2) + "R: " + rightNode.toString(depth+1);
		}
		return ret;
	}
}
