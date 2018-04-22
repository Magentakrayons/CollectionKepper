package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class DatabaseHandler {


	public void createNewDatabase() {
		//build window
		Stage createStage = new Stage();
		createStage.setTitle("Create Database");
		createStage.getIcons().add(new Image("mahira.jpg"));

		//Declare all Panes and Scenes used in Stage
		BorderPane getNumberPane = new BorderPane();
		Scene getNumberScene = new Scene(getNumberPane);

		BorderPane getNamePane = new BorderPane();
		Scene getNameScene = new Scene(getNamePane);

		/*
		 * Window Prompt One: Ask for how many categories user wishes to create.
		 */

		// create window components
		Label prompt = new Label("Enter number of columns.");
		prompt.setWrapText(true);
		prompt.setTextAlignment(TextAlignment.JUSTIFY);
		prompt.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

		TextField numberEntry = new TextField();
		numberEntry.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		numberEntry.setAlignment(Pos.CENTER);
		// force the field to be numeric only
		numberEntry.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue,
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            numberEntry.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});

		Button confirmButton = new Button("Confirm");
		confirmButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
		confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//proceed to "Name Categories" window
				createStage.hide();

				/*
				 * Window Prompt Two: Ask for names of each category.
				 */

				//create window components
				Label prompt2 = new Label("Enter column names.");
				ObservableList<TextField> textList = FXCollections.observableArrayList();
				for(int i = 0; i < Integer.parseInt(numberEntry.getText()); i++) {
					TextField newText = new TextField();
					newText.setAlignment(Pos.CENTER);
					textList.add(newText);
				}
				Button confirmButton2 = new Button("Confirm");
				confirmButton2.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
				confirmButton2.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						//create the category labels and return

						//re-initialize list, table and database
						CollectionKeeper.database.clear();
						CollectionKeeper.categoryList.clear();
						CollectionKeeper.mainTable.getItems().clear();
						CollectionKeeper.mainTable.getColumns().clear();

						//add category names to new table
						//Count is added by default
						CollectionKeeper.categoryList.add("Count");
						for (int i = 0; i < textList.size(); i++) {
							CollectionKeeper.categoryList.add(textList.get(i).getText());

						}
						//rebuild table
						for (int i = 0; i < CollectionKeeper.categoryList.size(); i++) {
							final int colCount = i;
							final TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(String.valueOf(CollectionKeeper.categoryList.get(i)));
							column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colCount)));
							CollectionKeeper.mainTable.getColumns().add(column);

						}
						CollectionKeeper.sortedDatabase.comparatorProperty().bind(CollectionKeeper.mainTable.comparatorProperty());
						CollectionKeeper.mainTable.setItems(CollectionKeeper.sortedDatabase);
						CollectionKeeper.statusBar.setText("New collection has been created!");
						createStage.hide();
					}
				});

				//organize components
				VBox getNameVbox = new VBox(prompt2);
				for(int i = 0; i < Integer.parseInt(numberEntry.getText()); i++) {
					getNameVbox.getChildren().add(textList.get(i));
				}
				getNameVbox.getChildren().add(getNameVbox.getChildren().size(), confirmButton2);
				getNameVbox.setPadding(new Insets(10,10,10,10));
				getNameVbox.setSpacing(10);
				getNameVbox.setAlignment(Pos.CENTER);
				getNamePane.setCenter(getNameVbox);
				getNamePane.setAlignment(getNameVbox, Pos.CENTER);

				createStage.setScene(getNameScene);
				createStage.sizeToScene();
				createStage.setResizable(false);
				createStage.show();
			}
		});

		//organize components
		VBox getNumberVbox = new VBox(prompt, numberEntry, confirmButton);
		getNumberVbox.setPadding(new Insets(10,10,10,10));
		getNumberVbox.setSpacing(10);
		getNumberVbox.setAlignment(Pos.CENTER);
		getNumberPane.setCenter(getNumberVbox);
		getNumberPane.setAlignment(getNumberVbox, Pos.CENTER);

		createStage.setScene(getNumberScene);
		createStage.sizeToScene();
		createStage.setResizable(false);
		createStage.show();
	}

	public static ObservableList loadDatabase() {
		//create filechooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load Database");
		fileChooser.getExtensionFilters().addAll(
			new ExtensionFilter("Database File", "*.dat"),
			new ExtensionFilter("All Files", "*.*")
		);
		fileChooser.setInitialDirectory(new File("Databases/"));
		Stage stage = new Stage();

		//get selected file
		CollectionKeeper.statusBar.setText("Loading...");
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			try {
				Scanner s = new Scanner(selectedFile);
				ObservableList<ObservableList<Object>> db = FXCollections.observableArrayList();
				boolean firstpass = true;
				while (s.hasNextLine()) {
					String text = s.nextLine();
					String[] tokens = text.split("/");
					ObservableList<Object> tempList = FXCollections.observableArrayList();
					for(String item: tokens) {
						if (item.equals("null")) {
							item = "";
						}
						tempList.add(item);
					}
					if (firstpass) {
						//clears CategoryList and writes new values
						CollectionKeeper.categoryList.clear();
						for (Object category : tempList) {
							CollectionKeeper.categoryList.add(category.toString());
						}
						firstpass = false;
					} else {
						db.add(tempList);
					}
				}

				//fix numbers to ints
				for (int i = 0; i < db.size(); i++) {
					for (int j = 0; j < CollectionKeeper.categoryList.size(); j++) {
						try {
							int convertedInt = Integer.parseInt((String) db.get(i).get(j));
							db.get(i).set(j, convertedInt);
						} catch (NumberFormatException e) {
							//pass
						}
					}
				}
				CollectionKeeper.statusBar.setText("Successfully loaded!");
				return db;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//returns the current database if nothing is loaded.
		return null;
	}

	public void saveDatabase() throws IOException {
		//create filechooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load Database");
		fileChooser.getExtensionFilters().addAll(
			new ExtensionFilter("Database File", "*.dat"),
			new ExtensionFilter("All Files", "*.*")
		);
		fileChooser.setInitialDirectory(new File("Databases/"));
		Stage stage = new Stage();

		//get selected file
		CollectionKeeper.statusBar.setText("Saving...");
		File selectedFile = fileChooser.showSaveDialog(stage);
		if (selectedFile != null) {
			try {
				FileWriter writer = new FileWriter(selectedFile);

				//save categories
				for (String category : CollectionKeeper.categoryList) {
					writer.write(category+"/");
				}
				writer.write(System.lineSeparator()); // newline
				//save all entries
				for (int i = 0; i < CollectionKeeper.database.size(); i++) {
					for (Object item : CollectionKeeper.database.get(i)) {
						writer.write(String.valueOf(item) + "/");
					}
					writer.write(System.lineSeparator()); // newline
				}
				writer.close();
				CollectionKeeper.statusBar.setText("Successfully saved!");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
