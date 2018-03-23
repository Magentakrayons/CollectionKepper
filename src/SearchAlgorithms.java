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
	 * @param index: target index to sort by.
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
			mergeHelperAlpha(leftArray, rightArray, database, index);
		}
		return database;
	}
	/***
	 * Helper method for combining left and right arrays together. Used in sortAlpha.
	 * @param leftArray: First arraylist to merge.
	 * @param rightArray: Second arraylist to merge.
	 * @param database: target sorted arraylist containing elements of both leftArray and rightArray.
	 * @param index: target index to sort by.
	 */
	private void mergeHelperAlpha(ArrayList<ArrayList<String>> leftArray, ArrayList<ArrayList<String>> rightArray, ArrayList<ArrayList<String>> database, int index) {
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
			//update k to next index
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
	 * Sorts the passed database by the specified index by MergeSort.
	 * @param database: target database to be sorted.
	 * @param index: target index to sort by.
	 */
	public ArrayList<ArrayList<String>> sortNumeric(ArrayList<ArrayList<String>> database, int index) {
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
			sortNumeric(leftArray, index);
			sortNumeric(rightArray, index);
			//combine left and right arrays
			mergeHelperNumeric(leftArray, rightArray, database, index);
		}
		return database;
	}
	
	/***
	 * Helper method for combining left and right arrays together. Used in SortAlpha. 
	 * @param leftArray: first arraylist to merge
	 * @param rightArray: second arraylist to merge
	 * @param database: target sorted arraylist containing elements of both leftArray and rightArray.
	 * @param index: target index to sort by
	 */
	private void mergeHelperNumeric(ArrayList<ArrayList<String>> leftArray, ArrayList<ArrayList<String>> rightArray, ArrayList<ArrayList<String>> database, int index) {
		//Iteration variables
		int i = 0;
		int j = 0;
		int k = 0;
		
		//sort while neither leftArray or rightArray are empty.
		while ((i < leftArray.size()) && (j < rightArray.size())) {
			//Capture numbers to compare for easy formatting
			int num1 = Integer.parseInt(leftArray.get(i).get(index));
			int num2 = Integer.parseInt(rightArray.get(i).get(index));
			
			if(num1 < num2) {
				database.set(k, leftArray.get(i));
				i++;
			} 
			else if (num1 > num2) {
				database.set(k, rightArray.get(j));
				j++;
			}
			//num1 and num2 are equal; adds num1 arbitrarily.
			else {
				database.set(k, leftArray.get(i));
				i++;
			}
			//update k to next index
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
	 * Searches the database for and returns instances of query.
	 * @param database: target database to search through.
	 * @param query: target input to search for instances of.
	 * @param index: target index to search through.
	 * @return ArrayList<ArrayList<String>> database containing only instances of query.
	 */
	public ArrayList<ArrayList<String>> searchEntry(ArrayList<ArrayList<String>> database, String query, int index) {
		//create new arrayList for viewing
		ArrayList<ArrayList<String>> newArrayList = new ArrayList<ArrayList<String>>();
		query = query.toLowerCase();
		for(ArrayList<String> s : database) {
			String entryText = s.get(index);
			entryText = entryText.toLowerCase();
			if(entryText.contains(query)) {
				newArrayList.add(s);
			}
		}
		return newArrayList;
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
