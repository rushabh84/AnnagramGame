//Name: Rushabh Shah  andrew id-rushabhs
package hw3;


//import hw1.WordGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;


public class HangmanHandler extends GameHandler {

	Button[] alphaButtons;	
	GridPane alphaGrid;  //the gridPane that holds alphaRoot gridPane and Label display
	GridPane alphaRoot;  //the gridPane that holds all the alphaButtons

	public void handle(ActionEvent event) {
		//enter code here
		messageLabel=new Label("Let's Play Hangman!");
		messageLabel.setPrefSize(300,30);
		choice=1;
		game=new Hangman();
		super.handle(event);  
		setupUserGrid();
	}

	void setupUserGrid() {
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

	void setupInputGrid() {
		alphaRoot=new GridPane();  
		alphaButtons=new Button[26];
		for (int i = 0; i < alphaButtons.length; i++) {	
			alphaButtons[i] = new Button(((char)(i+'a'))+"");  // converts i into  character and stores in alphaButtons[i]
			alphaButtons[i].setStyle("-fx-font-size:10");
			alphaButtons[i].setPrefSize(30, 30);	
			if(game.clueWord.indexOf(alphaButtons[i].getText().charAt(0))!=-1)   
			{ 
				/*checks if the text of button pressed is already present in the clueWord     
				and if yes disables the button*/
				alphaButtons[i].setDisable(true);
			}
			else
			{
				/*since, we don't be clicking on disabled buttons,
				 we dont invoke handlers on these buttons*/
				alphaButtons[i].setOnAction(new AlphaButtonHandler());
			}
		}
		int col = 0, row = 0;	
		for (int i = 0; i < alphaButtons.length-2; i++) {  
			//arranging the buttons in alphaRoot gridPane, 6 buttons on each row
			alphaRoot.add(alphaButtons[i], col++, row);			
			if (col % 6 ==0) 
			{
				col = 0;  //if column%6=0, then move the pointer to next row and 1st column
				row+=1;
			}
		}
		alphaRoot.add(alphaButtons[24], 2, 4);  //add last two alphabets in the 5th row
		alphaRoot.add(alphaButtons[25], 3, 4);
		alphaGrid.add(alphaRoot, 0 , 1);    
		userGrid.add(alphaGrid, 0 , 0);  
	}

	private void disableAlphaButtons() {  
		//disables all the alphaButtons when the game is ended
		for(int i=0; i< alphaButtons.length; i++){
			alphaButtons[i].setDisable(true);
			alphaButtons[i].setStyle("fx-background-color: transparent;");
		}

	}

	private class AlphaButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			String choice=((Button)event.getSource()).getText();
			((Button)event.getSource()).setDisable(true);  //disables the button pressed
			int returned;
			returned=game.nextTry(choice);
			scoreValueLabels[0].setText((Integer.parseInt(scoreValueLabels[0].getText())-1)+"");  
			//sets the count of 'trials to go' by reducing it by one and converts it back to string
			clueWordLabels.setText(game.clueWord);  //resets the clueWordLables after each guess
			if(returned==1)
			{
				/*if the guess was correct, then message is reset and the
				 * pressed button's color is reset to green and miss count is increased by 1  
				 */
				messageLabel.setText(game.message);  
				((Button)event.getSource()).setStyle("-fx-background-color:green;");
				scoreValueLabels[1].setText(game.hit+"");

			}
			else
			{
				messageLabel.setText(game.message);
				((Button)event.getSource()).setStyle("-fx-background-color:red;");
				scoreValueLabels[2].setText(game.miss+"");

			}
			double score=Math.round(game.calcScore()*100)/100.0;  //to round off the score to 2 decimal places
			scoreValueLabels[3].setText(score+"");

			/*checks if after the button is pressed, if the trails get over or the entire 
			 * word is guessed correctly before running out of trials, it will disable
			 * all the buttons and and depending on either of condition set the messageLabel
			 */
			if(game.trialCount >= WordGame.MAX_TRIALS || game.won)
			{
				disableAlphaButtons();
				if(game.won==true)
				{
					messageLabel.setText("Congratulations! You won!");
				}
				else
				{
					messageLabel.setText("Sorry! You missed it. It's "+game.gameWord);
				}
			}	
		}
	}

}
