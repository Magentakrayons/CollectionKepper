package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class CollectionKeeper extends Application {

	//Use for master list
	public static ObservableList<ObservableList<Object>> database = FXCollections.observableArrayList();
	//Use for column headers
	public static ObservableList<String> categoryList = FXCollections.observableArrayList();
	public static TableView<ObservableList<Object>> mainTable = new TableView();


	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,900,600);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Collection Keeper");

			/*
			 * Creation of menuBar and buttons begins here.
			 */

			//Create menuBar and controllers
			MenuBar menuBar = new MenuBar();
			EventHandler<ActionEvent> menuController = MenuBarController();
			EventHandler<ActionEvent> buttonController = ButtonController();
			menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

			//File Menu
			Menu menuFile = new Menu("File");
			MenuItem menuItemCreateDatabase = new MenuItem("Create");
			MenuItem menuItemLoadDatabase = new MenuItem("Load");
			MenuItem menuItemSaveDatabase = new MenuItem("Save");
			MenuItem menuItemExit = new MenuItem("Exit");
			//Bind EventHandler to menu items
			menuItemCreateDatabase.setOnAction(menuController);
			menuItemLoadDatabase.setOnAction(menuController);
			menuItemSaveDatabase.setOnAction(menuController);
			menuItemExit.setOnAction(menuController);
			//Bind MenuItems to Menu
			menuFile.getItems().addAll(menuItemCreateDatabase, menuItemLoadDatabase, menuItemSaveDatabase, menuItemExit);

			//Edit Menu
			Menu menuEdit = new Menu("Edit");
			MenuItem menuItemEditEntry = new MenuItem("Edit Entry");
			menuItemEditEntry.setOnAction(menuController);
			menuEdit.getItems().add(menuItemEditEntry);

			//Add New Item
			Button buttonAddNew = new Button("Add New");
			buttonAddNew.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
			buttonAddNew.setOnAction(buttonController);
			buttonAddNew.setAlignment(Pos.TOP_RIGHT);

			//Increase count
			Button buttonPlusOne = new Button("+1");
			buttonPlusOne.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
			buttonPlusOne.setOnAction(buttonController);
			buttonPlusOne.setAlignment(Pos.TOP_RIGHT);

			//Decrease count
			Button buttonMinusOne = new Button("-1");
			buttonMinusOne.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
			buttonMinusOne.setOnAction(buttonController);
			buttonPlusOne.setAlignment(Pos.TOP_RIGHT);

			//Add all items to Top of BorderPane
			HBox hBox = new HBox(menuBar, buttonAddNew, buttonPlusOne, buttonMinusOne);
			hBox.setHgrow(menuBar, Priority.ALWAYS);
			hBox.setHgrow(buttonPlusOne, Priority.NEVER);
			hBox.setHgrow(buttonMinusOne, Priority.NEVER);
			root.setTop(hBox);
			menuBar.getMenus().addAll(menuFile, menuEdit);

			root.setCenter(mainTable);

			//adds cute girl to the stage : ^)
			primaryStage.getIcons().add(new Image("mahira.jpg"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	//Println stubs need to be implemented
	//Handler for MenuBar
	private EventHandler<ActionEvent> MenuBarController()  {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				MenuItem item = (MenuItem) event.getSource(); // get name of item selected
				String text = item.getText();

				//Handle event
				if ("create".equalsIgnoreCase(text)) {
					System.out.println("create selected");
				} else if ("load".equalsIgnoreCase(text)) {
					//refreshes working database and categories to empty
					database.clear();
					categoryList.clear();
					mainTable.getItems().clear();
					mainTable.getColumns().clear();

					//test database method
					database = createDatabase();


					for (int i = 0; i < database.get(0).size(); i++) {
						final int colCount = i;
						final TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(String.valueOf(categoryList.get(i)));
						column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colCount)));
						mainTable.getColumns().add(column);

					}
					for (int i = 0; i < database.size(); i++) {
						mainTable.getItems().add(database.get(i));
					}
				} else if ("save".equalsIgnoreCase(text)) {
					System.out.println("save selected");
				} else if ("exit".equalsIgnoreCase(text)) {
					Platform.exit();
				} else if ("edit entry".equalsIgnoreCase(text)) {
					int index = database.indexOf(mainTable.getSelectionModel().getSelectedItem());
					editEntry(database.get(index));
				}
			}
		};
	}

	//Println stubs need to be implemented
	//Handler for Buttons
	private EventHandler<ActionEvent> ButtonController() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Button item = (Button) event.getSource(); // get name of item selected
				String text = item.getText();

				//Handle event
				if ("add new".equalsIgnoreCase(text)) {
					System.out.println("add new selected");
					//debugging lines
					System.out.println(database);
					System.out.println(mainTable.getSelectionModel().getSelectedItem());
					System.out.println(database.indexOf(mainTable.getSelectionModel().getSelectedItem()));
				}
				if ("+1".equalsIgnoreCase(text)) {
					int index = database.indexOf(mainTable.getSelectionModel().getSelectedItem());
					int count = (int) database.get(index).get(0) + 1;
					database.get(index).set(0, count);
					mainTable.refresh();
				} else if ("-1".equalsIgnoreCase(text)) {
					int index = database.indexOf(mainTable.getSelectionModel().getSelectedItem());
					int count = (int) database.get(index).get(0) - 1;
					database.get(index).set(0, count);
					mainTable.refresh();
				}
			}
		};
	}

	//Edits an entry in the table
	private void editEntry(ObservableList<Object> entry) {
		//build window
		Stage editStage = new Stage();
		editStage.setTitle("Edit Entry");
		editStage.getIcons().add(new Image("mahira.jpg"));
		GridPane editPane = new GridPane();
		editPane.setHgap(50);
		editPane.setVgap(5);
		editPane.setPadding(new Insets(5,5,5,5));
		Scene scene = new Scene(editPane, 250, (30*entry.size() + 70));


		//Populate Grid
		//Make categories
		for (int i = 0; i < database.get(0).size(); i++) {
			Label regex = new Label(categoryList.get(i));
			editPane.add(regex, 0, i);
		}
		//Make Textfields
		ObservableList<TextField> textBoxList = FXCollections.observableArrayList();
		for (int i = 0; i < entry.size(); i++) {
			String text = String.valueOf(entry.get(i));
			TextField textField = new TextField(text);
			editPane.add(textField, 1, i);
			textBoxList.add(textField);
		}

		//add confirm button and method for handling confirmed changes
		Button confirmButton = new Button("Confirm Changes");
		confirmButton.setMinSize(40, 25);
		confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < textBoxList.size(); i++) {
					try { //writes as int if all numbers
						int convertedInt = Integer.parseInt((String) textBoxList.get(i).getText());
						entry.set(i, convertedInt);
					} catch (NumberFormatException e) { // writes as string if has non-int characters
						entry.set(i, textBoxList.get(i).getText());
					}
				}
				editStage.hide();
				mainTable.refresh();
			}
		});


		//finalize window
		editPane.add(confirmButton, 1, editPane.getChildren().size());
		editPane.setAlignment(Pos.TOP_CENTER);

		editStage.setScene(scene);
		editStage.show();
	}


	//Add new Entry




	//This method exists for testing database with table. Please remove it later.
	public static ObservableList createDatabase() {
		// TODO Auto-generated method stub
	File file = new File("testData.txt");
	try {
		Scanner s = new Scanner(file);
		ObservableList<ObservableList<Object>> db = FXCollections.observableArrayList();
		boolean firstpass = true;
		while (s.hasNextLine()) {
			String text = s.nextLine();
			String[] tokens = text.split("/");
			ObservableList<Object> tempList = FXCollections.observableArrayList();
			for(String item: tokens) {
				tempList.add(item);
			}
			if (firstpass) {
				for (Object category : tempList) {
					categoryList.add(category.toString());
				}
				firstpass = false;
			} else {
				db.add(tempList);
			}
		}

		//fix numbers to ints
		for (int i = 0; i < db.size(); i++) {
			for (int j = 0; j < categoryList.size(); j++) {
				try {
					int convertedInt = Integer.parseInt((String) db.get(i).get(j));
					db.get(i).set(j, convertedInt);
				} catch (NumberFormatException e) {
					//pass
				}
			}
		}
		return db;
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	}

}
