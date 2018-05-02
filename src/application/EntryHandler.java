/* Contributors: Mark Olegario
 * File Creation Date: April 22nd, 2018
 * Finalization Date: May 2, 2018
 * File Name: EntryHandler.java
 *
 * Program Hierarchy: Entry Modification Module
 *
 * Purpose: This class contains all methods which interact with specific entries within the database.
 * Functions include adding a new entry, modifying existing entries, and deleting old entries.
 * Functions regarding the database as a whole are not contained within this class.
 */

package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EntryHandler {

	/*
	 * Function responsible for handling entry modification to the database.
	 * param: ObservableList<Object> entry - the selected entry which elements are to be modified.
	 */
	public void editEntry(ObservableList<Object> entry) {
		//build window
		Stage editStage = new Stage();
		editStage.setTitle("Edit Entry");
		editStage.getIcons().add(new Image("mahira.jpg"));
		GridPane editPane = new GridPane();
		editPane.setHgap(50);
		editPane.setVgap(5);
		editPane.setPadding(new Insets(10,10,10,10));
		Scene scene = new Scene(editPane);


		//Populate Grid
		//Make categories
		for (int i = 0; i < CollectionKeeper.categoryList.size(); i++) {
			Label category = new Label(CollectionKeeper.categoryList.get(i));
			category.setAlignment(Pos.CENTER);
			editPane.add(category, 0, i);
		}
		//Make Textfields
		ObservableList<TextField> textBoxList = FXCollections.observableArrayList();
		for (int i = 0; i < entry.size(); i++) {
			String text = String.valueOf(entry.get(i));
			TextField textField = new TextField(text);
			textField.setAlignment(Pos.CENTER);
			editPane.add(textField, 1, i);
			textBoxList.add(textField);
		}

		//add confirm button and method for handling confirmed changes
		Button confirmButton = new Button("Confirm Changes");
		confirmButton.setAlignment(Pos.CENTER);
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
				CollectionKeeper.mainTable.refresh();
			}
		});


		//finalize window
		editPane.add(confirmButton, 1, editPane.getChildren().size());
		editPane.setAlignment(Pos.TOP_CENTER);

		editStage.setScene(scene);
		editStage.sizeToScene();
		editStage.setResizable(false);
		editStage.show();
	}

	/*
	 * Function responsible for adding new entries to the main database in CollectionKeeper().
	 * Internally fetches user input and constructs new ObservableList<Object>.
	 */
	public void addEntry() {
		//build window
		Stage addStage = new Stage();
		addStage.setTitle("Add New Entry");
		addStage.getIcons().add(new Image("mahira.jpg"));
		GridPane addPane = new GridPane();
		addPane.setHgap(20);
		addPane.setVgap(5);
		addPane.setPadding(new Insets(10,10,10,10));
		Scene scene = new Scene(addPane);

		//Populate Grid
		//Make categories
		for (int i = 0; i < CollectionKeeper.categoryList.size(); i++) {
			Label category = new Label(CollectionKeeper.categoryList.get(i));
			category.setAlignment(Pos.CENTER);
			addPane.add(category, 0, i);
		}
		//Make Textfields
		ObservableList<TextField> textBoxList = FXCollections.observableArrayList();
		for (int i = 0; i < CollectionKeeper.categoryList.size(); i++) {
			TextField textField = new TextField("");
			textField.setAlignment(Pos.CENTER);
			addPane.add(textField, 1, i);
			textBoxList.add(textField);
		}
		//add confirm button and method for handling confirmed changes
		Button confirmButton = new Button("Confirm Changes");
		confirmButton.setMinSize(40, 25);
		confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				//empty check
				boolean emptyCheck = true;
				for (int i = 0; i < textBoxList.size(); i++) {
					if(!textBoxList.get(i).getText().equals("")) {
						emptyCheck = false;
						break;
					}
				}

				//display error window if all entries are blank
				if(emptyCheck) {
					Stage errorStage = new Stage();
					errorStage.setTitle("Error!");
					errorStage.getIcons().add(new Image("mahira.jpg"));
					BorderPane errorPane = new BorderPane();
					Scene errorScene = new Scene(errorPane);

					Label errorLabel = new Label("Cannot add an empty entry!");
					Button okButton = new Button("OK");
					okButton.setAlignment(Pos.CENTER);
					okButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
					okButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorStage.hide();
						}
					});
					VBox errorBox = new VBox(errorLabel, okButton);
					errorBox.setPadding(new Insets(10,10,10,10));
					errorBox.setSpacing(10);
					errorBox.setAlignment(Pos.CENTER);

					errorPane.setCenter(errorBox);
					errorStage.setScene(errorScene);
					errorStage.sizeToScene();
					errorStage.setResizable(false);
					errorStage.show();

				} else {
					ObservableList<Object> newEntry = FXCollections.observableArrayList();
					for (int i = 0; i < textBoxList.size(); i++) {
						try { //writes as int if all numbers
							int convertedInt = Integer.parseInt((String) textBoxList.get(i).getText());
							newEntry.add(convertedInt);
						} catch (NumberFormatException e) { // writes as string if has non-int characters
							if (textBoxList.get(i).getText().equals("")) {
								newEntry.add(null);
							} else {
								newEntry.add(textBoxList.get(i).getText());
							}
						}
					}
					//add new entry to table
					CollectionKeeper.database.add(newEntry);

					//refresh list based on whether search bar has text
					if (!CollectionKeeper.searchBar.getText().equals("") || CollectionKeeper.searchBar.textProperty() == null) {
						//add to current view
						CollectionKeeper.filteredList.add(newEntry);
						CollectionKeeper.mainTable.setItems(CollectionKeeper.filteredList);
					} else {
						CollectionKeeper.mainTable.setItems(CollectionKeeper.sortedDatabase);
					}
					CollectionKeeper.statusBar.setText("Added new item to collection!");
					addStage.hide();

				}
			}
		});

		//finalize window
		addPane.add(confirmButton, 1, addPane.getChildren().size());
		addPane.setAlignment(Pos.CENTER);
		addStage.setScene(scene);
		addStage.sizeToScene();
		addStage.setResizable(false);
		addStage.show();
	}

	/*
	 * Deletes selected entry from main database in CollectionKeeper().
	 */
	public void deleteEntry() {
		CollectionKeeper.database.remove(CollectionKeeper.mainTable.getSelectionModel().getSelectedItem());
		if (CollectionKeeper.searchBar.getText().equals("") || CollectionKeeper.searchBar.textProperty() == null) {
			CollectionKeeper.mainTable.setItems(CollectionKeeper.sortedDatabase);
			CollectionKeeper.statusBar.setText("Deleted item from collection...");
		} else {
			CollectionKeeper.filteredList.remove(CollectionKeeper.mainTable.getSelectionModel().getSelectedItem());
			CollectionKeeper.mainTable.setItems(CollectionKeeper.filteredList);
			CollectionKeeper.statusBar.setText("Deleted item from collection...");
		}
	}
}
