import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SearchAlgorithms {
	
	/***
	 * Constructor for SearchAlgorithms.
	 */
	public SearchAlgorithms() {
	}
	

	/***
	 * Sorts the passed database by the specified index by MergeSort.
	 * @param database: target database to be sorted.
	 * @param index: index to sort by.
	 */
	public ArrayList<ArrayList<String>> sortAlpha(ArrayList<ArrayList<String>> database, int index) {
		//check for arraylist size of 1.
		if (database.size() > 1) {
			//divide database in half
			int middle = database.size() / 2;
			ArrayList<ArrayList<String>> leftArray = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> rightArray = new ArrayList<ArrayList<String>>();
			for (ArrayList<String> a : database) {
				if(database.indexOf(a) < middle) {
					leftArray.add(a);
				}
				else {
					rightArray.add(a);
				}
			}
			
			//recursive call
			sortAlpha(leftArray, index);
			sortAlpha(rightArray, index);
			
			//combine left and right arrays
			MergeHelper(leftArray, rightArray, database, index);
		}
		return database;
	}
	/***
	 * Helper method for combining left and right arrays together. Used in SortAlpha.
	 * @param leftArray: First array to merge.
	 * @param rightArray: Second array to merge.
	 * @param database: unsorted list containing elements of both leftArray and rightArray.
	 * @param index: the index to sort by.
	 */
	private void MergeHelper(ArrayList<ArrayList<String>> leftArray, ArrayList<ArrayList<String>> rightArray, ArrayList<ArrayList<String>> database, int index) {
		//Iteration variables
		int i = 0;
		int j = 0;
		int k = 0;
		
		//sort while neither leftArray or rightArray are empty.
		while ((i < leftArray.size()) && (j < rightArray.size())) {
			//Capture strings to compare for easy formatting
			String string1 = (String) leftArray.get(i).get(index);
			String string2 = (String) rightArray.get(i).get(index);
			
			//string1 comes before string2
			if(string1.compareTo(string2) < 0) {
				database.set(k, leftArray.get(i));
				i++;
			} 
			//string1 comes after string2
			else if (string1.compareTo(string2) > 0) {
				database.set(k, rightArray.get(j));
				j++;
			}
			//string1 and string2 are equal; adds string1 arbitrarily.
			else {
				database.set(k, leftArray.get(i));
				i++;
			}
			//update index
			k++;
		}
		
		//add remainder of non-empty array to database.
		if (i == leftArray.size()) {
			while (j < rightArray.size()) {
				database.set(k, rightArray.get(j));
				j++;
				k++;
			}
		}
		else {
			while (i < leftArray.size()) {
				database.set(k, leftArray.get(i));
				i++;
				k++;
			}
		}
	}
	
	/***
	 * Reverses the order of entries in the database.
	 * @param database: target database to reverse order.
	 * @return: reversed database
	 */
	public ArrayList<ArrayList<String>> reverseOrder(ArrayList<ArrayList<String>> database) {
		Collections.reverse(database);
		return database;
	}
	
}
