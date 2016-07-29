/**
 * @author Huang Jiaming
 * @NSID: jih211
 * @StuID: 11207964
 */

package assignment3;

import lib280.list.LinkedList280;
import lib280.tree.BasicMAryTree280;

public class SkillTree extends BasicMAryTree280<Skill> {

	/**	
	 * Create tree with the specified root node and 
	 * specified maximum arity of nodes.  
	 * @timing O(1) 
	 * @param x item to set as the root node
	 * @param m number of children allowed for future nodes 
	 */
	public SkillTree(Skill x, int m)
	{
		super(x,m);
	}

	/**
	 * A convenience method that avoids typecasts.
	 * Obtains a subtree of the root.
	 * 
	 * @param i Index of the desired subtree of the root.
	 * @return the i-th subtree of the root.
	 */
	public SkillTree rootSubTree(int i) {
		return (SkillTree)super.rootSubtree(i);
	}
	
	public LinkedList280<Skill> skillDependencies(Skill name) throws RuntimeException {
		LinkedList280<Skill> sList = new LinkedList280<>(); 
		if (!findSkill(name, sList))
			throw new RuntimeException(name.toString() + " not found");
		return sList;
	}
	
	public int skillTotalCost(Skill name) throws RuntimeException {
		int totalCost = 0;
		LinkedList280<Skill> sList = new LinkedList280<>(); 
		if (!findSkill(name, sList))
			throw new RuntimeException(name.toString() + " not found");
		sList.goFirst();
		while(!sList.after()){
			totalCost += sList.item().skillCost;
			sList.goForth();
		}
		return totalCost;
	}
	
	private boolean findSkill(Skill name, LinkedList280<Skill> list){
		if (this.rootItem().equals(name)){
			list.insert(this.rootItem());
			return true;
		}else if(this.rootLastNonEmptyChild() == 0){
			return false;
		}else{
			boolean ret = false;
			for(int i = 1; i <= this.rootLastNonEmptyChild(); i++){
				ret = ret || rootSubTree(i).findSkill(name, list);
			}
			if (ret)
				list.insert(this.rootItem());
			return ret;
		}
	}
	
	public static void main(String[] args) {
		Skill s1 = new Skill("level1_1st_skill_1", "", 1);
		Skill s2 = new Skill("level2_1st_skill_1_1", "", 1);
		Skill s3 = new Skill("level2_2nd_skill_1_2", "", 2);
		Skill s4 = new Skill("level2_3rd_skill_1_3", "", 2);
		Skill s5 = new Skill("level3_1st_skill_1_1_1", "", 3);
		Skill s6 = new Skill("level3_2nd_skill_1_2_1", "", 4);
		Skill s7 = new Skill("level3_3rd_skill_1_2_2", "", 3);
		Skill s8 = new Skill("level3_4th_skill_1_2_3", "", 3);
		Skill s9 = new Skill("level3_5th_skill_1_3_1", "", 2);
		Skill s10 = new Skill("level3_6th_skill_1_3_2", "", 3);
		Skill s11 = new Skill("level3_7th_skill_1_3_3", "", 1);
		Skill s12 = new Skill("level4_1st_skill_1_3_2_1", "", 4);
		Skill s13 = new Skill("level4_2nd_skill_1_3_2_2", "", 5);
		
		Skill serr = new Skill("extraSkill", "", 5);
		
		//number of default subtree: level_1 : 3, level_2 : 3, level_3 : 4, level_4 : 4
		SkillTree t1 = new SkillTree(s1, 3);
		SkillTree t2_1 = new SkillTree(s2, 3);
		SkillTree t2_2 = new SkillTree(s3, 3);
		SkillTree t2_3 = new SkillTree(s4, 3);
		SkillTree t3_1 = new SkillTree(s5, 4);
		SkillTree t3_2 = new SkillTree(s6, 4);
		SkillTree t3_3 = new SkillTree(s7, 4);
		SkillTree t3_4 = new SkillTree(s8, 4);
		SkillTree t3_5 = new SkillTree(s9, 4);
		SkillTree t3_6 = new SkillTree(s10, 4);
		SkillTree t3_7 = new SkillTree(s11, 4);
		SkillTree t4_1 = new SkillTree(s12, 4);
		SkillTree t4_2 = new SkillTree(s13, 4);
		
		//set subtrees
		t1.setRootSubtree(t2_1, 1);
		t1.setRootSubtree(t2_2, 2);
		t1.setRootSubtree(t2_3, 3);
		
		t2_1.setRootSubtree(t3_1, 1);
		t2_2.setRootSubtree(t3_2, 1);
		t2_2.setRootSubtree(t3_3, 2);
		t2_2.setRootSubtree(t3_4, 3);
		t2_3.setRootSubtree(t3_5, 1);
		t2_3.setRootSubtree(t3_6, 2);
		t2_3.setRootSubtree(t3_7, 3);
		
		t3_6.setRootSubtree(t4_1, 1);
		t3_6.setRootSubtree(t4_2, 2);
		
		System.out.println(t1.toStringByLevel());
		
		//test skillDependencies
		System.out.println("\n test skillDependencies");
		System.out.println("Dependencies for \"" + s5.getSkillName() + "\":\n" + t1.skillDependencies(s5).toString());
		System.out.println("Dependencies for \"" + s10.getSkillName() + "\":\n" + t1.skillDependencies(s10).toString());
		System.out.println("Dependencies for \"" + s13.getSkillName() + "\":\n" + t1.skillDependencies(s13).toString());
		try {
			System.out.println("Dependencies for \"" + serr.getSkillName() + "\":\n" + t1.skillDependencies(serr).toString());
		} catch (RuntimeException e) {
			System.out.println("Skill \"" + serr.getSkillName() + "\" not found");
		}
		System.out.println("\n test skillTotalCost");
		// test skillTotalCost
		System.out.println("To get skill \""+ s5.getSkillName() + "\" , you must invest " + t1.skillTotalCost(s5) + " points.");
		System.out.println("To get skill \""+ s10.getSkillName() + "\" , you must invest " + t1.skillTotalCost(s10) + " points.");
		System.out.println("To get skill \""+ s13.getSkillName() + "\" , you must invest " + t1.skillTotalCost(s13) + " points.");
		try {
			System.out.println("To get skill \""+ serr.getSkillName() + "\" , you must invest " + t1.skillTotalCost(serr) + " points.");
		} catch (RuntimeException e) {
			System.out.println("Skill \"" + serr.getSkillName() + "\" not found");
		}


	}
	

}
