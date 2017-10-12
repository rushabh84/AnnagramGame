//Name: Rushabh Shah  andrew id-rushabhs
package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public abstract class WordGame {
	public String[] dictionaryWords;		//stores words read from the dictionary
	public String gameWord;  				//word picked up from the dictionary for the puzzle 
	public StringBuilder userInputs = new StringBuilder(); //stores all guesses entered by the user
	public String message;					//message to be printed on console after each user interaction
	public static final int MAX_TRIALS = 10; 
	public int trialCount=0;				
	public boolean won = false;  //set to true when user input matches the gameWord
	public String clueWord;	 //clue shown to the user on console
	public double score;  //updated by calcScore() 
	public int hit=0;  //number of correct guesses made by player
	public int miss=0;  //number of wrong guesses made by player
	public int size=0;
	WordGame() {
		dictionaryWords = readFile();
		//setupWords();
	}

	public String[] readFile() {
		//enter code here
		StringBuilder rdfile=new StringBuilder("");  //StringBuilder object rdfile created 
		Scanner sc=null;
		try 
		{
			sc= new Scanner(new File("dictionary.txt"));  //reads the file in a Scanner object sc
			while(sc.hasNextLine())
			{
				rdfile.append(sc.nextLine()+"\n");  //appends the data in rdfile from dictionary.txt file line-by-line
			}											
		} 											
		catch (FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
		return rdfile.toString().split("\n"); //returns the content of rdfile after converting to String
	}

	/*pickWord() picks a word randomly from within the dictionaryWords array
	It returns the word that has at least 4 or more characters in it.*/
	public String pickGameWord(){
		String pickWord;
		Random r= new Random();
		int choice;  //number(position) of word selected from the dicitonary.txt file
		do
		{
			choice = r.nextInt(dictionaryWords.length);  //random number is selected between  and length of dictionaryWords array
			pickWord= dictionaryWords[choice];
			pickWord=pickWord.toLowerCase();  //to avoid case-sensitive problem when guessing the word
		}while(pickWord.length()<=3);  //checks that the word selected is at least 4 characters long
		return pickWord;
	}

	public abstract int nextTry(String input);

	public abstract double calcScore();

	public abstract void setupWords();

}
