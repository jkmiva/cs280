//Name: Huang Jiaming
//NSID: jih211
//StuID:11207964
package assignment1;

import java.util.Random;

import lib280.list.LinkedList280;

/**
 * @author Huang Jiaming<br>
 *         NSID: jih211
 *
 */
public class A1Q1 {

	/**
	 * @param howMany
	 *            - number of sack objects
	 * @return array of sack objects
	 */
	public static Sack[] generatePlunder(int howMany) {
		Random generator = new Random();
		Sack[] grain = new Sack[howMany];
		for (int i = 0; i < howMany; i++) {
			grain[i] = new Sack(Grain.values()[generator.nextInt(Grain.values().length)], generator.nextDouble() * 100);
		}
		return grain;
	}

	public static void main(String[] args) {
		/**
		 * @param num
		 *            - number of sack objects,defined by user
		 */
		final int num = 20;
		Sack[] sacks = A1Q1.generatePlunder(num);
		/**
		 * @param arr
		 *            - array of LinkedList280<Stack>
		 */
		@SuppressWarnings("unchecked")
		LinkedList280<Sack>[] arr = (LinkedList280<Sack>[]) new LinkedList280[5];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new LinkedList280<Sack>();
		}
		// initialize the array
		arr[0].insert(new Sack(Grain.WHEAT, 0));
		arr[1].insert(new Sack(Grain.BARLEY, 0));
		arr[2].insert(new Sack(Grain.OATS, 0));
		arr[3].insert(new Sack(Grain.RYE, 0));
		arr[4].insert(new Sack(Grain.OTHER, 0));

		// insert sacks to corresponding lists
		for (int i = 0; i < num; i++) {
			switch (sacks[i].type) {
			case WHEAT:
				arr[0].insert(sacks[i]);
				break;
			case BARLEY:
				arr[1].insert(sacks[i]);
				break;
			case OATS:
				arr[2].insert(sacks[i]);
				break;
			case RYE:
				arr[3].insert(sacks[i]);
				break;
			case OTHER:
				arr[4].insert(sacks[i]);
				break;
			default:
				break;
			}
		}

		// calculate weights of different list
		float[] weight = new float[5];
		for (int i = 0; i < 5; i++) {
			arr[i].goFirst();
			Grain g = arr[i].item().type;
			while (arr[i].itemExists()) {
				weight[i] += arr[i].item().weight;
				arr[i].goForth();
			}
			System.out.println("Jack plundered " + weight[i] + " pounds of " + g);
		}

	}

}
