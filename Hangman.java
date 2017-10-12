//Name: Rushabh Shah  andrew id-rushabhs
package hw3;

import java.util.Random;

public class Hangman extends WordGame{
	//
	@Override
	public void setupWords(){
		gameWord=pickGameWord();
		Random rn= new Random();
		clueWord=new String(gameWord);
		int l= gameWord.length();
		int pos;
		char atpos;  //stores the character to be replaced by '-'
		int dash= rn.nextInt(l-(l/2))+(l/2);  //randomly selects the number of dashes, ensures the number is >=0.5(gameWord.length())
		for(int i=0; i<dash;i++){  
			//loop to randomly replace dash number of characters with '-' in clueWord
			atpos='-';
			while(atpos=='-'){
				pos=rn.nextInt(l);
				atpos=clueWord.charAt(pos);
			}
			clueWord=clueWord.replaceAll(atpos+"","-");  //replaces all the atpos's value in clueWord with '-'
			i=countDashes(clueWord)-1;  //1 is subtracted as variable i will be auto-incremented for for loop's next iteration
		}
	}

	public int countDashes(String word) {
		//enter code here
		int no_dashes= 0;  //variable to store the number of dashes in a word
		for(int i=0;i<word.length();i++){
			if(word.charAt(i)=='-'){  //checks if the character at i position is '-' or not, if yes then increments no_dashes
				no_dashes++; 
			}
		}
		return no_dashes;
	}

	@Override
	public int nextTry(String input) {
		char entry=input.toLowerCase().charAt(0);  
		if(gameWord.indexOf(entry)==-1){  //checks if the guess made by user is present in the gameWord
			message="Sorry! Got it wrong!"; 
			miss++;  
			trialCount++; 
			return 0;
		}
		else{
			int []positions=new int[20];
			int i=0, count=0;
			int index = gameWord.indexOf(entry);  
			/** while loop finds indexes of all the occurrences in the guessWord 
			 * and stores these indexes in the positions array
			 */
			while (index>= 0) {
				positions[i]=index;  
				index= gameWord.indexOf(input, index + 1);  //searches the next occurrence of guess in gameWord
				i++;
				count++;
			}
			for(int p=0;p<count;p++){  
				//loop replaces the dashes in clueWord with the correct guess at the index stored in positions array
				clueWord=clueWord.substring(0, positions[p])+entry+clueWord.substring(positions[p]+1);
			}
			hit++;
			trialCount++;
			message="***You got that right!***";
			/**checks if the game is won by finding if there are any more dashes
			 * if yes then prints the message in the required format
			 */
			if(clueWord.indexOf('-')==-1){ 
				won=true;
				System.out.println(message);
				System.out.println(gameWord);
				message="";
			}
			return 1;
		}
	}

	@Override
	public double calcScore() {
		double score; 
		if(miss==0)  
			score=hit;
		else
		{
			score=(double)hit/miss;  //type-casting the value of hit/miss to a double value
		}
		return score;
	}
}

