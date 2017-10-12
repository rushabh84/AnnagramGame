package hw3;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class AnagramHandler extends GameHandler{

	TextField inputTextField;
	Button submitButton;
	TextArea anagramsTextArea;
	GridPane alphaGrid;  //the gridPane that holds alphaRoot gridPane and Label display
	GridPane alphaRoot;  //the gridPane that holds all the alphaButtons


	@Override
	void setupUserGrid() {
		// TODO Auto-generated method stub
		alphaGrid=new GridPane();
		userGrid=new GridPane();
		ColumnConstraints columnConstraints=new ColumnConstraints();
		columnConstraints.setFillWidth(true);
		columnConstraints.setHgrow(Priority.ALWAYS);
		userGrid.getColumnConstraints().add(columnConstraints);
		Label display=new Label("Choose next letter");
		display.setAlignment(Pos.CENTER);
		setupInputGrid();
		setupScoreGrid();
		alphaGrid.add(display,0,0);  // adds the Label display to alphaGrid
		WordNerd.root.setBottom(userGrid);
		userGrid.add(messageLabel,3,2);  //adds the messageLabel to the 4th column of userGrid


	}

	public void handle(ActionEvent event) {
		//enter code here
		anagramsTextArea=new TextArea();
		inputTextField=new TextField();
		messageLabel=new Label("Let's Play Anagrams!");
		messageLabel.setPrefSize(300,30);
		choice=2;
		game=new Anagrammer();
		//super.handle(event);  
		setupUserGrid();
		System.out.println("Anagram handle");
		setupClueWordStack();
		
	}


	void setupClueWordStack() {
		//enter code here
		clueWordStack=new StackPane(); 
		clueWordStack.setAlignment(Pos.TOP_CENTER);
	//	game.setupWords();
		clueWordLabels=new Label(game.clueWord);
		clueWordLabels.setStyle("-fx-background-color: white;");  //sets clueWordLabels's background to white 
		clueWordLabels.setPrefSize(600,75);
		clueWordLabels.setMinWidth(100);
		clueWordLabels.setAlignment(Pos.CENTER);
		clueWordStack.getChildren().add(clueWordLabels);
		WordNerd.root.setCenter(clueWordStack);  //adds clueWordStack to the center section of root

	}


	@Override
	void setupInputGrid() {
		// TODO Auto-generated method stub
		GridPane inputGrid = new GridPane();
		//Label anagramsToFind = new Label();
		inputTextField.setPrefWidth(200);
		submitButton = new Button("Submit");
		submitButton.setPrefWidth(200);
		submitButton.setOnAction(new SubmitButtonHandler());
		inputGrid.add(new Label("Type your anagram"), 0, 0);
		inputGrid.add(inputTextField, 0, 1);
		inputGrid.add(submitButton, 0, 2);
		inputGrid.add(anagramsTextArea, 0, 4);
		inputGrid.add(new Label("Find 2 anagrams"), 0, 3);
		//inputGrid.add(anagramsToFind, 0, 7);
		anagramsTextArea.setPrefSize(200, 100);
		anagramsTextArea.setEditable(false);

		userGrid.add(inputGrid, 0, 1);
	}

	private class SubmitButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			String input=inputTextField.getText();
			int ret=game.nextTry(input);
			if(ret==1)
			{
				anagramsTextArea.setText(anagramsTextArea.getText()+"\n"+input);
				//game.hit++;
				inputTextField.clear();
				scoreValueLabels[1].setText(game.hit+"");
				if(game.won)
				{
					submitButton.setDisable(true);
					inputTextField.setDisable(true);
					anagramsTextArea.setDisable(true);
					//scoreValueLabels[3].setText(String(game.calcScore()));
					//messageLabel.setText(game.message);
				}
				messageLabel.setText(game.message);
				/*else
				{
					messageLabel.setText("You got that right. "+(--game.size)+"more to go");
				}*/
			}
			else
			{
				//game.miss++;
				inputTextField.clear();
				scoreValueLabels[2].setText(game.miss+"");
				if(game.trialCount >= WordGame.MAX_TRIALS)
				{
					submitButton.setDisable(true);
					inputTextField.setDisable(true);
					anagramsTextArea.setDisable(true);
					messageLabel.setText("Sorry you lost!");
				}
				else
				{
					messageLabel.setText(game.message);
					/*if(Arrays.asList(game.dictionaryWords).contains(input))
						messageLabel.setText("Sorry, "+input+" is not "+game.clueWord+" 's anagram");
					else
						messageLabel.setText("Sorry, "+input+" is not in the dictionary");*/
				}

			}
			double score=Math.round(game.calcScore()*100)/100.0;  //to round off the score to 2 decimal places
			scoreValueLabels[3].setText(score+"");
	//		scoreValueLabels[0].setText((Integer.parseInt(scoreValueLabels[0].getText())-game.trialCount)+""); 
			scoreValueLabels[0].setText((10-game.trialCount)+"");
		}
	}


}
