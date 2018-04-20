package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class CollectionKeeper extends Application {

	//Use for master list
	public static ObservableList<ObservableList<String>> database;
	//Use for modified search lists
	public static ObservableList<ObservableList<String>> tempDatabase;

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


			/*
			 * Creation of TableViews begins here
			 */

			//Create TableView

			//test database method
			database = createDatabase();

			/* Database is procedurally added to table. Creates columns, then adds items by row.
			 * "Col" + colcount + 1 will need to be changed so that it matches the attribute
			 * in question. Columns can be swapped around as well, so the problem gets more complicated.
			 * Maybe we can keep a category regex at the top of database, using @ to
			 * distinguish that regex as so:
			 *
			 * @ Count/Name/Category/Type/HP/Set Number/Set Name/Rarity
			 * 0/Sneasal/Pokemon/Dark/70/85/Shining Legends/Common
			 *
			 * We could just update the new column categories at save time.
			 * Additionally, we will have to add an additional column to implement the +1/-1 function.
			 */
			TableView<ObservableList<String>> mainTable = new TableView();
			for (int i = 0; i < database.get(0).size(); i++) {
				final int colCount = i;
				final TableColumn<ObservableList<String>, String> column = new TableColumn<>("Col " + (colCount + 1));
				column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colCount)));
				mainTable.getColumns().add(column);

			}
			for (ObservableList<String> row : database) {
				mainTable.getItems().add(row);
			}
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
					System.out.println("load selected");
				} else if ("save".equalsIgnoreCase(text)) {
					System.out.println("save selected");
				} else if ("exit".equalsIgnoreCase(text)) {
					Platform.exit();
				} else if ("edit entry".equalsIgnoreCase(text)) {
					System.out.println("edit item selected");
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
				}
				if ("+1".equalsIgnoreCase(text)) {
					System.out.println("+1");
				} else if ("-1".equalsIgnoreCase(text)) {
					System.out.println("-1");
				}
			}
		};
	}

	//This method exists for testing database with table. Please remove it later.
	public static ObservableList createDatabase() {
		// TODO Auto-generated method stub
	File file = new File("testData.txt");
	try {
		Scanner s = new Scanner(file);
		ObservableList<ObservableList<String>> db = FXCollections.observableArrayList();
		while (s.hasNextLine()) {
			String text = s.nextLine();
			String[] tokens = text.split("/");
			ObservableList<String> tempList = FXCollections.observableArrayList();
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
