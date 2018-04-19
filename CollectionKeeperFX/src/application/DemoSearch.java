package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DemoSearch {
	/*
	public static void main(String[] args) {
		ArrayList database = CreateDatabase("testData.txt");
		
		//Print all by unsorted name
		System.out.println("Unsorted Name List:\n");
		for(int i = 0; i < database.size(); i++) {
			System.out.println(((ArrayList) database.get(i)).get(0));
		}
		//Sort by Name
		SortAlpha(database, 0);
		//Print all by name
		System.out.println("\nSorted Name List:\n");
		for(int i = 0; i < database.size(); i++) {
			System.out.println(((ArrayList) database.get(i)).get(0));
		}
		
		//Print all by unsorted type (Pokemon/Trainer)
		System.out.println("\nUnsorted Type List:\n");
		for(int i = 0; i < database.size(); i++) {
			System.out.println(((ArrayList) database.get(i)).get(1));
		}
		//Sort by Name
		SortAlpha(database, 1);
		//Print all by name
		System.out.println("\nSorted Type List:\n");
		for(int i = 0; i < database.size(); i++) {
			System.out.println(((ArrayList) database.get(i)).get(1));
		}
	}
	*/

	private ArrayList CreateDatabase(String location) {
			// TODO Auto-generated method stub
		File file = new File(location);
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
	
	private void SortAlpha(ArrayList database, int mode) {
		//i'll use merge sort for here later. For illustration, i'll implement bubble sort
		for(int i = database.size()-1; i > 0; i--) {
			for(int j = 0; j < i; j++) {
				String item1 = (String) ((ArrayList) database.get(j)).get(mode);
				String item2 = (String) ((ArrayList) database.get(j+1)).get(mode);
				boolean check = true;
				int index = 0;
				while(check) {
					//Compares which is larger value, moves it further down list
					if(item1.charAt(index) > item2.charAt(index)) {
						ArrayList<String> temp = (ArrayList<String>) database.get(j);
						database.set(j, database.get(j+1));
						database.set(j+1, temp);
						check = false;
					}
					//Checks if equal
					else if(item1.charAt(index) == item2.charAt(index)) {
						//Safety if strings are completely identical, or one is longer than other
						if(index+1 == item1.length() || index+1 == item2.length()) {
							check = false;
						}
						else {
							index++;
						}
					}
					//Branch taken if first value is already smaller than next value
					else {
						check = false;
					}
				}
			}
		}
	}




}