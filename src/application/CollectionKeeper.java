package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class CollectionKeeper extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,900,600);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Collection Keeper");

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

			//Make buttons for top bar
			HBox hBox = new HBox(menuBar, buttonPlusOne, buttonMinusOne);


			//Add all items to Top of BorderPane
			hBox.setHgrow(menuBar, Priority.ALWAYS);
			hBox.setHgrow(buttonPlusOne, Priority.NEVER);
			hBox.setHgrow(buttonMinusOne, Priority.NEVER);
			root.setTop(hBox);
			menuBar.getMenus().addAll(menuFile, menuEdit);


			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

		ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();

	}


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

	private EventHandler<ActionEvent> ButtonController() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Button item = (Button) event.getSource(); // get name of item selected
				String text = item.getText();

				//Handle event
				if ("+1".equalsIgnoreCase(text)) {
					System.out.println("+1");
				} else if ("-1".equalsIgnoreCase(text)) {
					System.out.println("-1");
				}
			}
		};
	}

}
