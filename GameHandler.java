//Name: Rushabh Shah  andrew id-rushabhs
package hw3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public abstract class GameHandler implements EventHandler<ActionEvent> {

	WordGame game;	
	public int choice;

	Label messageLabel;  //used to display the message after very user-move
	Label clueWordLabels;	 	//displays clueWord 
	GridPane userGrid;			//holds the alphaGrid and the scoreGrid
	Label[] scoreValueLabels=new Label[4];	//scores used in scoreGrid
	StackPane clueWordStack;
	GridPane scoreGrid;
	GridPane content;

	abstract void setupUserGrid();
	abstract void setupInputGrid();

	@Override
	public void handle(ActionEvent event) {
		/*if(choice==1)
		{
			game=new Hangman();
			
		}
		else if(choice==2)
			game=new Anagrammer();
		*/
		setupClueWordStack();
	}	

	void setupClueWordStack() {
		//enter code here
		clueWordStack=new StackPane(); 
		clueWordStack.setAlignment(Pos.TOP_CENTER);
		game.setupWords();
		clueWordLabels=new Label(game.clueWord);
		clueWordLabels.setStyle("-fx-background-color: white;");  //sets clueWordLabels's background to white 
		clueWordLabels.setPrefSize(600,75);
		clueWordLabels.setMinWidth(100);
		clueWordLabels.setAlignment(Pos.CENTER);
		clueWordStack.getChildren().add(clueWordLabels);
		WordNerd.root.setCenter(clueWordStack);  //adds clueWordStack to the center section of root

	}

	void setupScoreGrid() {
		scoreGrid=new GridPane();
		scoreGrid.setAlignment(Pos.CENTER);
		scoreGrid.setPrefHeight(150);
		scoreGrid.setPrefWidth(300);
		Label[] description=new Label[3];
		description[0]=new Label("Trials to go");
		description[1]=new Label("Hit n Miss");
		description[2]=new Label("Game score");
		scoreValueLabels[0]=new Label("10");
		scoreValueLabels[1]=new Label("0");
		scoreValueLabels[2]=new Label("0");
		scoreValueLabels[3]=new Label("0");
		for(int j=0;j<scoreValueLabels.length; j++)
		{
			scoreValueLabels[j].setStyle("-fx- font- size: 15");  //sets the font size of label
			scoreValueLabels[j].setAlignment(Pos.CENTER);
		}
		scoreValueLabels[0].setPrefSize(50, 25);
		scoreValueLabels[1].setPrefSize(25, 25);  //set the dimensions of the scoreValueLabels[]
		scoreValueLabels[2].setPrefSize(25, 25);
		scoreValueLabels[3].setPrefSize(50, 25);
		scoreValueLabels[0].setStyle("-fx-background-color: white;");  //set the respective color for the elements of scoreValueLabels[]
		scoreValueLabels[1].setStyle("-fx-background-color: green");
		scoreValueLabels[2].setStyle("-fx-background-color: red");
		scoreValueLabels[3].setStyle("-fx-background-color: white;");
		HBox hitmiss=new HBox();  //adding the the label for counting hit and trial together in HBox 
		hitmiss.getChildren().addAll(scoreValueLabels[1],scoreValueLabels[2]);
		scoreGrid.add(description[0], 0, 0);
		scoreGrid.add(scoreValueLabels[0], 1, 0);
		scoreGrid.add(description[1], 0, 1);
		scoreGrid.add(hitmiss,1, 1);  //adds hitmiss HBox grid to the 2nd column and 2nd row of scoreGrid
		scoreGrid.add(description[2],0, 2);
		scoreGrid.add(scoreValueLabels[3], 1, 2);
		userGrid.add(scoreGrid, 3, 0);  // adds scoreGrid to userGrid
	}
}
