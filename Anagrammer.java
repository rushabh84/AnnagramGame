package hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Anagrammer extends WordGame {

	List<String> anagrams;
	Map<String, List<String>>anagramMap=new HashMap<>();
	String pickup;     // your trial
	List<String> dummy;    //your trial
	ArrayList<String> valuesEntered=new ArrayList<>();
	Anagrammer(){
		setupWords();
	}

	public void loadAnagramMap()
	{
		int i=0;
		while(i<dictionaryWords.length)
		{
			String w=dictionaryWords[i];
			String al=alphabetize(w);
			anagrams=anagramMap.get(al);
			if(anagrams==null)
			{
				anagramMap.put(al, anagrams=new ArrayList<String>());  //populates the anagramMap Map
			}
			anagrams.add(w); 
			i++;
		}
	}
	public String alphabetize(String dummy) {
		// TODO Auto-generated method stub
		char[]a=dummy.toCharArray();
		Arrays.sort(a);  //used to generate the keys of anagramMap
		return new String(a);
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

	@Override
	public void setupWords() {
		Random rn= new Random();
		loadAnagramMap();  //loads the anagramMap with all the anagram permutation and combination
		/* Since, using an integer randomly selected 
		 * 
		 */
		Set<String> key=anagramMap.keySet(); 
		Iterator<String> iter=key.iterator();
		ArrayList<String> temp = new ArrayList<>();
		while(iter.hasNext())
			temp.add(iter.next());
		do
		{
			int ch=rn.nextInt(anagramMap.size());
			pickup=temp.get(ch);
			dummy=anagramMap.get(pickup);
		}
		while(dummy.size()<=4);  //checks whether the randomly selected word has atleast 3 anagrams
		size=dummy.size();
		int random=rn.nextInt(size);  //to randomly select the word from dummy ArrayList
		clueWord=dummy.get(random);
		anagrams = dummy;

		//printing
		for(int i=0;i<dummy.size();i++) {
			System.out.println(dummy.get(i));
		}
		/*ArrayList<String> t2 = (ArrayList<String>) anagramMap.get("loop");
		for(int k =0 ; k<t2.size() ; k++){
		System.out.println(t2.get(k));}

		System.out.println(anagrams.size());*/
	}

	@Override
	public int nextTry(String input) {

		//trialCount++;

		if(valuesEntered.contains(input))  //checks if the string entered was already entered previously
		{
			message="You already entered that!";
			return -1;
		}
		else if(input.equals(clueWord))  //checks if the string entered is the clueWord itself
		{
			message="That's the clue";
			return -1;
		}
		else if(dummy.contains(input)) { 
			/*if the string entered in the inputTextField is an anagram of the clueWord, increase the hit, 
			 * increase the trialCount and reset the scoreValueLabels
			 */
			hit++;
			trialCount++;
			valuesEntered.add(input);  //add the input in the ArrayList storing all the inputs
			if(calcScore()>anagrams.size()-2|| hit>=dummy.size()-1)  //condition to check if game is won

			{
				won=true;
				message="Congratulations! You won!";
				//scoreValueLabels[3].setText(calcScore());
				return 1;
			}
			else{
				message="You got that right! "+(--size-1)+" more to go";  //print the message and set the size to size-2, since those many anagrams are left to guess
				return 1;
			}
		}
		else
		{   miss++;
		trialCount++;
		valuesEntered.add(input);
		if(Arrays.asList(dictionaryWords).contains(input)) //condition to check if the input is a valid word in the dictionary
		{
			message="Sorry, "+input+" is not "+clueWord+" 's anagram"; }
		else {
			message="Sorry, "+input+" is not in the dictionary";} 

		return 0;
		}

	}
}


