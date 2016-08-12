/**
 * Name:  Huang Jiaming
 * NSID:  jih211
 * StuID: 11207964
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.opencsv.CSVReader;

import lib280.base.Pair280;
import lib280.hashtable.KeyedChainedHashTable280;
import lib280.tree.OrderedSimpleTree280;

// This project uses a JAR called opencsv which is a library for reading and
// writing CSV (comma-separated value) files.
// 
// You don't need to do this for this project, because it's already done, but
// if you want to use opencsv in other projects on your own, here's the process:
//
// 1. Download opencsv-3.1.jar from http://sourceforge.net/projects/opencsv/
// 2. Drag opencsv-3.1.jar into your project.
// 3. Right-click on the project in the package explorer, select "Properties" (at bottom of popup menu)
// 4. Choose the "Libraries" tab
// 5. Click "Add JARs"
// 6. Select the opencsv-3.1.jar from within your project from the list.
// 7. At the top of your .java file add the following imports:
//        import java.io.FileReader;
//        import com.opencsv.CSVReader;
//
// Reference documentation for opencsv is here:  
// http://opencsv.sourceforge.net/apidocs/overview-summary.html



public class QuestLog extends KeyedChainedHashTable280<String, QuestLogEntry> {

	public QuestLog() {
		super();
	}
	
	/**
	 * Obtain an array of the keys (quest names) from the quest log.  There is 
	 * no expectation of any particular ordering of the keys.
	 * 
	 * @return The array of keys (quest names) from the quest log.
	 */
	public String[] keys() {
		ArrayList<String> ret = new ArrayList<>(); 
		int num = this.count;
		int i = 0;
		while (num>0){
			this.findNextItem(i);
			i = this.hashPos(this.item().key()) + 1;
			while (this.itemListLocation.itemExists()){
				ret.add(this.item().getQuestName());
				num--;
				this.itemListLocation.goForth();
			}
		}
		String[] s = new String[ret.size()];
		return ret.toArray(s);  // Remove this line you're ready.  It's just to prevent compiler errors.
	}
	
	/**
	 * Format the quest log as a string which displays the quests in the log in 
	 * alphabetical order by name.
	 * 
	 * @return A nicely formatted quest log.
	 */
	public String toString() {
		ArrayList<String> ret = new ArrayList<>(); 
		int num = this.count;
		int i = 0;
		while (num>0){
			this.findNextItem(i);
			i = this.hashPos(this.item().key()) + 1;
			while (this.itemListLocation.itemExists()){
				ret.add(this.item().toString()+'\n');
//				ret.add(this.item().getQuestName() + ": " + this.item().getQuestArea() + ", Level Range: "
//						+ this.item().getRecommendedMinLevel() + "-" + this.item().getRecommendedMaxLevel() + '\n');
				num--;
				this.itemListLocation.goForth();
			}
		}
		Collections.sort(ret);
		StringBuilder s = new StringBuilder();
		for(int index = 0; index < ret.size(); index++){
			s.append(ret.get(index));
		}
		return s.toString();
		
	}
	
	/**
	 * Obtain the quest with name k, while simultaneously returning the number of
	 * items examined while searching for the quest.
	 * @param k Name of the quest to obtain.
	 * @return A pair in which the first item is the QuestLogEntry for the quest named k, and the
	 *         second item is the number of items examined during the search for the quest named k.
	 *         Note: if no quest named k is found, then the first item of the pair should be null.
	 */
	public Pair280<QuestLogEntry, Integer> obtainWithCount(String k) {
		
		// Write a method that returns a Pair280 which contains the quest log entry with name k, 
		// and the number QuestLogEntry objects that were examined in the process.  You need to write
		// this method from scratch without using any of the superclass methods (mostly because 
		// the superclass methods won't be terribly useful unless you can modify them, which you
		// aren't allowed to do!).

		int i = this.hashPos(k);
		this.findNextItem(i);
		int retNum = 0;
		QuestLogEntry retQLE = null;
		while (this.itemListLocation.itemExists()){
			if(this.item().getQuestName().equals(k)) {
				retNum++;
				retQLE = this.item();
				break;
			}
			retNum++;
			this.itemListLocation.goForth();
		}
		Pair280<QuestLogEntry, Integer> retPair = new Pair280<QuestLogEntry, Integer>(retQLE, retNum);
		return retPair;
	}
	
	
	public static void main(String args[])  {
		// Make a new Quest Log
		QuestLog hashQuestLog = new QuestLog();
		
		// Make a new ordered binary tree.
		OrderedSimpleTree280<QuestLogEntry> treeQuestLog =
				new OrderedSimpleTree280<QuestLogEntry>();
		
		
		// Read the quest data from a CSV (comma-separated value) file.
		// To change the file read in, edit the argument to the FileReader constructor.
		CSVReader inFile;
		try {
			inFile = new CSVReader(new FileReader("quests4.csv"));
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
			return;
		}
		
		String[] nextQuest;
		try {
			// Read a row of data from the CSV file
			while ((nextQuest = inFile.readNext()) != null) {
				// If the read succeeded, nextQuest is an array of strings containing the data from
				// each field in a row of the CSV file.  The first field is the quest name,
				// the second field is the quest region, and the next two are the recommended
				// minimum and maximum level, which we convert to integers before passing them to the
				// constructor of a QuestLogEntry object.
				QuestLogEntry newEntry = new QuestLogEntry(nextQuest[0], nextQuest[1], 
						Integer.parseInt(nextQuest[2]), Integer.parseInt(nextQuest[3]));
				// Insert the new quest log entry into the quest log.
				hashQuestLog.insert(newEntry);
				treeQuestLog.insert(newEntry);
			}
		} catch (IOException e) {
			System.out.println("Something bad happened while reading the quest information.");
			e.printStackTrace();
		}

		// Print out the hashed quest log's quests in alphabetical order.
		// COMMENT THIS OUT when you're testing the file with 100,000 quests.  It takes way too long.
		System.out.println(hashQuestLog);
		
		// Print out the tree quest log's quests in alphabetical order.
		// COMMENT THIS OUT when you're testing the file with 100,000 quests.  It takes way too long.
	    System.out.println(treeQuestLog.toStringInorder());
		

	    // (call hashQuestLog.obtainWithCount() for each quest in the log and find average # of access)
	    
	    String[] s4 = hashQuestLog.keys();
	    float f4 = 0;
	    for (String s : s4){
	    	f4 += hashQuestLog.obtainWithCount(s).secondItem();
	    }
	    System.out.println("Avg of items examined per query in the hashed quest log with 4 entries is: " + f4/4);
	    
		
	    // (call treeQuestLog.searchCount() for each quest in the log and find average # of access)
	    float f4_1 = 0;
	    for (String s : s4){
	    	f4_1 += treeQuestLog.searchCount(hashQuestLog.obtainWithCount(s).firstItem());
	    }
	    System.out.println("Avg of items examined per query in the tree quest log with 4 entries is: " + f4_1/4);
	    
/*	    
	    // test quests16.csv
	    String[] s16 = hashQuestLog.keys();
	    float f16 = 0;
	    for (String s : s16){
	    	f16 += hashQuestLog.obtainWithCount(s).secondItem();
	    }
	    System.out.println("Avg of items examined per query in the hashed quest log with 16 entries is: " + f16/16);
	    
	    float f16_1 = 0;
	    for (String s : s16){
	    	f16_1 += treeQuestLog.searchCount(hashQuestLog.obtainWithCount(s).firstItem());
	    }
	    System.out.println("Avg of items examined per query in the tree quest log with 16 entries is: " + f16_1/16);
	    
	    
	    // test quests250.csv
	    String[] s250 = hashQuestLog.keys();
	    float f250 = 0;
	    for (String s : s250){
	    	f250 += hashQuestLog.obtainWithCount(s).secondItem();
	    }
	    System.out.println("Avg of items examined per query in the hashed quest log with 250 entries is: " + f250/250);
	    
	    float f250_1 = 0;
	    for (String s : s250){
	    	f250_1 += treeQuestLog.searchCount(hashQuestLog.obtainWithCount(s).firstItem());
	    }
	    System.out.println("Avg of items examined per query in the tree quest log with 250 entries is: " + f250_1/250);
	    
	    
	    // test quests1000.csv
	    String[] s1000 = hashQuestLog.keys();
	    float f1000 = 0;
	    for (String s : s1000){
	    	f1000 += hashQuestLog.obtainWithCount(s).secondItem();
	    }
	    System.out.println("Avg of items examined per query in the hashed quest log with 1000 entries is: " + f1000/1000);
	    
	    float f1000_1 = 0;
	    for (String s : s1000){
	    	f1000_1 += treeQuestLog.searchCount(hashQuestLog.obtainWithCount(s).firstItem());
	    }
	    System.out.println("Avg of items examined per query in the tree quest log with 1000 entries is: " + f1000_1/1000);
	    
	    
	    // test quests100000.csv
	    String[] s100000 = hashQuestLog.keys();
	    float f100000 = 0;
	    for (String s : s100000){
	    	f100000 += hashQuestLog.obtainWithCount(s).secondItem();
	    }
	    System.out.println("Avg of items examined per query in the hashed quest log with 100000 entries is: " + f100000/100000);
	    
	    float f100000_1 = 0;
	    for (String s : s100000){
	    	f100000_1 += treeQuestLog.searchCount(hashQuestLog.obtainWithCount(s).firstItem());
	    }
	    System.out.println("Avg of items examined per query in the tree quest log with 100000 entries is: " + f100000_1/100000);
	    
*/	    
	}
	
}
