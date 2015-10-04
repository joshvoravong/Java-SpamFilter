package bayes.classifier;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author jspacco
 * <p>
 * Bayesian spam filters based on this formulation of Bayes Thm:<p>

    Pr(S|W) =     &nbsp;&nbsp;
         Pr(W|S) * Pr(S)              <br>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

           --------------   <br>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Pr(W)   <br>

 * 
 */
public interface IClassifier
{
    /**
     * Get the number of times the given word occurs in 
     * the spam messages in the data set.
     * @param word
     * @return The number of occurrences of the given word
     * in the spam messages in the data set.
     */
    public int getNumSpamOccurrences(String word);
    /**
     * Get the number of times the given word occurs in
     * the ham messages in the data set.
     * @param word
     * @return The number of occurrences of the given word
     * in the ham messages in the data set.
     */
    public int getNumHamOccurrences(String word);
    
    /**
     * Get the number of spam messages in the data set.
     * @return The number of spam messages in the data set.
     */
    public int getNumSpamMessages();
    /**
     * Get the number of ham messages in the data set.
     * @return The number of ham messages in the data set.
     */
    public int getNumHamMessages();
    
    /**
     *  Set the threshold at which we classify a message as spam.
     * 
     * @param t the threshold
     */
    public void setThreshold(double t);
    
    /**
     * Get the threshold at which we classify a message as spam.
     */
    public double getThreshold();
    
    /**
     * Using the probabilities that we have trained into our dataset
     * determine the score for the given message.
     * @param in
     * @return The score for the given file.
     */
    public double probSpamForMessage(InputStream in);
    
    /**
     * Compute the probability that a message is spam given that
     * it contains the given word, using the corpus of messages
     * that has so far been used for training.
     * 
     * 
     * @param word The word to test.
     * @return The probability (between 0.0 and 1.0) that a message is spam 
     * given that it contains the given word, or null if the probability
     * for the given word cannot be computed.
     */
    public Double probSpamGivenWord(String word);
    
    /**
     * Read a message from the given InputStream.
     * Return true if the message is spam (i.e. its score
     * is below the current threshold) and false otherwise.
     * @param in
     * @return True if the given message is spam; false if it's ham.
     */
    public boolean isSpam(InputStream in);
    
    /**
     * Add the spam file to be read from the given input stream
     * to our dataset.
     * @param in The inputstream from which to read the spam file.
     */
    public void addSpamFile(InputStream in) throws IOException;
    /**
     * Add the ham file to be read from the given input stream
     * to our dataset.
     * @param in The inputstream from which to read the ham file.
     */
    public void addHamFile(InputStream in) throws IOException;
    
    /**
     * Add all the files in the given directory to
     * our dataset as spam files.
     * 
     * <b>NOTE</b> This method can call {@link #addSpamFile(InputStream)}.
     * @param dir The directory from which to read the files
     */
    public void addAllSpamFilesInDirectory(File dir) throws IOException;
    
    /**
     * Add all the files in the given directory to
     * our dataset as ham files.
     * 
     * <b>NOTE</b> This method can call {@link #addHamFile(InputStream)}.
     * @param dir The directory from which to read the files
     */
    public void addAllHamFilesInDirectory(File dir) throws IOException;
    
}
