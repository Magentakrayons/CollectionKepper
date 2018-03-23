import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestDatabase {
	
	//testing bench
	public static void main(String[] args) {
		TestDatabase testdb = new TestDatabase();
		ArrayList<ArrayList<String>> database = testdb.CreateDatabase();
		SearchAlgorithms searchfuncs = new SearchAlgorithms();
		
		//Index 0, alphbetical
		System.out.println("Alphabetical Sort: Column 0");
		searchfuncs.sortAlpha(database, 0);
		for (ArrayList<String> s : database) {
			System.out.println(s);
		}
		System.out.println();

		//Reverse order
		System.out.println("Reverse Column 0");
		searchfuncs.reverseOrder(database);
		for (ArrayList<String> s : database) {
			System.out.println(s);
		}
		System.out.println();
		
		//Index 4, numeric
		System.out.println("Alphabetical Sort: Column 4");
		searchfuncs.sortNumeric(database, 4);
		for (ArrayList<String> s : database) {
			System.out.println(s);
		}
		System.out.println();
		
		//Search for any instances of 'd' in database column 2
		System.out.println("Searching column 3: 'd'");
		ArrayList<ArrayList<String>> newdb = searchfuncs.searchEntry(database, "d", 2);
		for (ArrayList<String> s : newdb) {
			System.out.println(s);
		}
		System.out.println();
		
		//Search for any instance of '70' in database column 3
		System.out.println("Searching column 4: '70'");
		newdb = searchfuncs.searchEntry(database, "70", 3);
		for (ArrayList<String> s : newdb) {
			System.out.println(s);
		}
	}
	
	public TestDatabase() {
	}
	
	private ArrayList CreateDatabase() {
		// TODO Auto-generated method stub
	File file = new File("testData.txt");
	try {
		Scanner s = new Scanner(file);
		ArrayList<ArrayList<String>> db = new ArrayList<>();
		while (s.hasNextLine()) {
			String text = s.nextLine();
			String[] tokens = text.split("/");
			ArrayList<String> tempList = new ArrayList<>();
			for(String item: tokens) {
				tempList.add(item);
			}
			db.add(tempList);
		}	
		return db;
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
}
