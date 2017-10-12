//Name: Rushabh Shah  andrew id-rushabhs
package hw3;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;

public class WordNerd extends Application{
	static BorderPane root = new BorderPane();
	Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//enter code here
		Scene scene=new Scene(root, 600, 300);
		primaryStage.setTitle("The Word Nerd");
		primaryStage.setScene(scene);
		primaryStage.show();
		setupMenus();
	}

	public void setupMenus() {
		/*this method creates two Menus and add them to the menuBar which is placed in the top 
		 * portion of the root, adds a submenu playGame to the Menu gameMenu
		 * and calls their respective event handler classes
		 */
		MenuBar menuBar=new MenuBar();
		Menu gameMenu=new Menu("Game");
		Menu helpMenu=new Menu("Help");
		Menu playGame=new Menu("Play");
		MenuItem hangman=new MenuItem("Hangman");
		MenuItem anagram=new MenuItem("Anagrams");
		playGame.getItems().add(hangman);
		playGame.getItems().add(anagram);
		hangman.setOnAction(new HangmanHandler());
		anagram.setOnAction(new AnagramHandler());
		MenuItem closeGame=new MenuItem("Close Game");
		closeGame.setOnAction(new EventHandler<ActionEvent>(){
			//Anonymous inner class to handle the event of closing the game
			@Override
			public void handle(ActionEvent event) {
				root.setBottom(null);
				root.setCenter(null);
			}
		});
		MenuItem exitGame=new MenuItem("Exit");
		exitGame.setOnAction(new EventHandler<ActionEvent>(){
			//Anonymous Inner class to handle the event of exiting the game
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
		MenuItem details=new MenuItem("About");
		details.setOnAction(new AboutEventHandler());
		gameMenu.getItems().addAll(playGame, closeGame, exitGame);
		helpMenu.getItems().add(details);
		menuBar.getMenus().addAll(gameMenu, helpMenu);
		root.setTop(menuBar);
	}

	private class AboutEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Alert alert=new Alert(AlertType.INFORMATION);  //AlertType.INFORMATION to have the code for OK
			alert.setTitle("About");
			alert.setHeaderText("The Word Nerd");
			alert.setContentText("Version 1.0\n"+"Release 1.0 \n"+"Copyright CMU \n"+"Developed by a minion!");
			Image image=new Image(this.getClass().getResource("minion.gif").toString());
			ImageView imageView=new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(100);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			alert.setGraphic(imageView);
			alert.showAndWait();


		}
	}
}
