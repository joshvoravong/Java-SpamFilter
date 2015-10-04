package bayes.classifier;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import sun.font.StrikeCache;


public class BasicFilterTest
{
    SpamClassifier f;
    public static final double EPSILON=0.001;
    
    @Before
    public void setup() throws Exception {
        // This method is like a constructor
        // It is run before every test case.
        f=new SpamClassifier();
        f.addAllHamFilesInDirectory(new File("test1/ham"));
        f.addAllSpamFilesInDirectory(new File("test1/spam"));
    }
     
    @Test
    public void testThreshold() throws Exception
    {
        f.setThreshold(0.4);
        assertEquals(0.4, f.getThreshold(), EPSILON);
    }
    
    @Test
    public void testBasicStatsTest1() throws Exception
    {
        // Make sure that we are getting both messages
        assertEquals(2, f.getNumHamMessages());
        assertEquals(3, f.getNumSpamMessages());
        // Make sure we have the right numbers of files that contain
        // these words
        assertEquals(1, f.getNumSpamOccurrences("foo"));
        assertEquals(1, f.getNumHamOccurrences("foo"));
    }
    
    @Test
    public void testFoo() throws Exception
    {
        double w1=f.probSpamGivenWord("foo");
        assertEquals(0.50, w1, EPSILON);
    }
    
    @Test
    public void testPlease() throws Exception
    {
        double w1=f.probSpamGivenWord("please");
        assertEquals(0.66666666, w1, EPSILON);
    }
    
    @Test
    public void testBuy() throws Exception
    {
        double w1=f.probSpamGivenWord("buy");
        assertEquals(1.0, w1, EPSILON);
    }
    
    @Test
    public void testPleaseBuyFoo() throws Exception
    {
        double p=f.probSpamForMessage(stringToInputStream("please buy foo"));
        assertEquals(0.66666666, p, EPSILON);
    }
    
    @Test
    public void testSentence2() throws Exception
    {
        double p=f.probSpamForMessage(stringToInputStream("please buy great replica foo"));
        assertEquals(0.666666, p, EPSILON);
    }
    
    @Test
    public void testSentence3() throws Exception
    {
        // none of these words are in the training data
        double p=f.probSpamForMessage(stringToInputStream("sing song"));
        assertEquals(0.5, p, EPSILON);
    }
    
    @Test
    public void testSentence4() throws Exception
    {
        double p=f.probSpamForMessage(stringToInputStream("please ham your credit card"));
        assertEquals(0.66666666, p, EPSILON);
    }
    
    @Test
    public void testcheck() throws Exception
    {
        for (String s : new String[] {"please","buy","great","replica","foo"}) {
            double p1=f.probSpamGivenWord(s);
            System.out.println("P(S|"+s+")="+p1);
        }
    }
    
    private static InputStream stringToInputStream(String message) {
        return new ByteArrayInputStream(message.getBytes());
    }
}
