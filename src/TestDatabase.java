import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestDatabase {
	
	//testing bench
	public static void main(String[] args) {
		TestDatabase testdb = new TestDatabase();
		ArrayList<ArrayList<String>> database = testdb.CreateDatabase();
		
		//Index 0, alphbetical
		SearchAlgorithms searchfuncs = new SearchAlgorithms();
		searchfuncs.sortAlpha(database, 0);
		for (ArrayList<String> s : database) {
			System.out.println(s);
		}
		//Reverse order
		System.out.println();
		searchfuncs.reverseOrder(database);
		for (ArrayList<String> s : database) {
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
