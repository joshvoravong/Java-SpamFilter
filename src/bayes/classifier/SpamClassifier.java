//We originally did not export the Eclipse project and just submitted a text file.  So here it is.
package bayes.classifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


public class SpamClassifier implements IClassifier
{
	public int hamFiles=0;
	public int spamFiles=0;
	public double threshold;
	
	// Here are our hashmaps for Ham and Spam.
	public HashMap<String, Integer> hamMess= new HashMap<String, Integer>();
	public HashMap<String, Integer> spamMess= new HashMap<String, Integer>();
	
	

    @Override
    // This takes the spam message and compares it with our hashmap values.
    public int getNumSpamOccurrences(String word) {
        if(spamMess.containsKey(word)){
        	return spamMess.get(word);
        }
        return 0; // 0 denotes if word was not in the spam hashmap.
    }

    @Override
    public int getNumHamOccurrences(String word) {
    	if(hamMess.containsKey(word)){
        	return hamMess.get(word);
        }
        return 0; // 0 denotes if word was not in the ham hashmap.
    }

    @Override
    public int getNumSpamMessages() {
       return spamFiles;
       }//

    @Override
    public int getNumHamMessages() {
    	  return hamFiles;
    	  }

    @Override
    // this our setter method for threshold.
    public void setThreshold(double t) {
        threshold = t;
    }
    
    @Override
    // this our getter method for threshold.
    public double getThreshold() {
       return threshold;
       }
    
    @Override
    public double probSpamForMessage(InputStream in) {
      
    }

    @Override
    // This is the probability method derived from the equation given in IClassifier.
    public Double probSpamGivenWord(String word) {
    double spamGivenWord;
    spamGivenWord = (((double)getNumSpamOccurrences(word) / (double)getNumSpamMessages())
    		*((double)getNumSpamMessages() / ((double)getNumSpamMessages() + (double)getNumHamMessages())));
    
    double hamGivenWord;
    hamGivenWord = (((double)getNumHamOccurrences(word) / (double)getNumHamMessages())
    		*((double)getNumHamMessages() / ((double)getNumHamMessages() + (double)getNumSpamMessages())));
    
    double prob = (spamGivenWord/(spamGivenWord+hamGivenWord));
    return prob;
    }

    @Override
    // Here isSpam calls a helper method spamMessIdent 
    public boolean isSpam(InputStream in) {
        if(spamicity(in) > threshold) {
        	return true;
        }
        return false;
    }
    // This method helps determine the spamicity of input (the implementation was debatable for different ways)
    public double spamicity(InputStream in){
    	String str;
    	double sProb=0;
    	double sTotalProb=0;
    	double size=0;
        Scanner sc = new Scanner(in);
    	while(sc.hasNext()){
         	str = sc.next();
         	sProb = probSpamGivenWord(str);
         	sTotalProb = sTotalProb + sProb;
         	size++;
    	}
    	sTotalProb = sTotalProb/size;
    	return sTotalProb;
    }
    
    @Override
    // To read off the input stream we use a scanner to read in word by word.
    public void addSpamFile(InputStream in) throws IOException {
        String str;
        Scanner sc = new Scanner(in);
        HashSet<String> wordList = new HashSet<String>();
        while(sc.hasNext()){
        	str = sc.next();
        	// If the input word (str) is not in the spam word list, then we add the word.
        	if(!wordList.contains(str)){
        		spamMess.put(str, spamMess.get(str) + 1);
        	}
        	// Otherwise we add the input word (str) to the spam word list.
        	else{
        	spamMess.put(str, 1);
        	wordList.add(str);
        	}
        }
    }

    @Override
    public void addHamFile(InputStream in) throws IOException {
     String str;
     Scanner sc = new Scanner(in);
     HashSet<String> wordList = new HashSet<String>();
     while(sc.hasNext()){
     	str = sc.next();
     	// If the input word (str) is not in the spam word list, then we add the word.
     	if(!wordList.contains(str)){
     		hamMess.put(str, hamMess.get(str) + 1);
     	}
     	// Otherwise we add the input word (str) to the spam word list.
     	else{
     	hamMess.put(str, 1);
     	wordList.add(str);
     	}
     }
    }

    @Override
    // Adding all spam files to the directory
    public void addAllSpamFilesInDirectory(File dir) throws IOException {
    	for(File f : dir.listFiles()){
    		FileInputStream spam = new FileInputStream(f);
    		spamFiles++;
    		addSpamFile(spam);
    	}
    }

    @Override
    public void addAllHamFilesInDirectory(File dir) throws IOException {
    	for(File f : dir.listFiles()){
    		FileInputStream ham = new FileInputStream(f);
    		hamFiles++;
    		addHamFile(ham);
    	}
    }
    //We attempted this section after the lab was actually due, we didn't know about it until we came in for Lab03
   /** public String topTenSpam(){
    	Set spamWords = spamMess.keySet();
    	for(int i = 0, i < spamFiles; i++){
    	Iterator iter = set.iterator();
        while (spamWords.hasNext())
        {
            String x = iter.next();
            int index[] = new int[10];
            int value[] = new int[10];
            for(int j = 0, j < 10; j++){
            	
            }
        }
            
            
            System.out.println("keySet: " + o.toString());
        }
    }
    }*/
}
