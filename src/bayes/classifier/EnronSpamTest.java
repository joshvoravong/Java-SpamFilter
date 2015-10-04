package bayes.classifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import org.junit.BeforeClass;
import org.junit.Test;

public class EnronSpamTest
{
    private static final double EPSILON = 0.0001;
    private static SpamClassifier f;
    
    @BeforeClass
    public static void setup() throws Exception {
        // This is like a constructor that is run once before
        // any test cases execute.
        f=new SpamClassifier();
        f.setThreshold(0.4);
        f.addAllSpamFilesInDirectory(new File("enron1/spam"));
        f.addAllHamFilesInDirectory(new File("enron1/ham"));
    }

    @Test
    public void testHam1() throws Exception {
        String filename="enron1/hamtest/0823.2000-04-03.farmer.ham.txt";
        double p=f.probSpamForMessage(new FileInputStream(filename));
        System.out.println("h1 "+p);
        // should be really small, around 4.4819801860405734E-36
        assertTrue(p < 0.01);
    }
    @Test
    public void testHam2() throws Exception {
        String filename="enron1/hamtest/1584.2000-07-06.farmer.ham.txt";
        double p=f.probSpamForMessage(new FileInputStream(filename));
        System.out.println("h2 "+p);
        // should be around 1.754984600391663E-14
        assertTrue(p<0.01);
    }
    @Test
    public void testSpam1() throws Exception {
        String filename="enron1/spamtest/0008.2003-12-18.GP.spam.txt";
        double p=f.probSpamForMessage(new FileInputStream(filename));
        System.out.println("s1 "+p);
        // p should be around 0.9997632241800091
        assertTrue(p > 0.99);
    }
    
    @Test
    public void testSpam2() throws Exception {
        String filename="enron1/spamtest/0529.2004-02-21.GP.spam.txt";
        double p=f.probSpamForMessage(new FileInputStream(filename));
        System.out.println("s2 "+p);
        // p shuld be around 0.9945485228055752
        assertTrue(p > 0.99);
        
    }
    
    @Test
    public void testFullSpam1() throws Exception {
        assertTrue(f.isSpam(new FileInputStream("enron1/spamtest/0008.2003-12-18.GP.spam.txt")));
    }

    @Test
    public void testFullHam1() throws Exception {
        assertFalse(f.isSpam(new FileInputStream("enron1/hamtest/0823.2000-04-03.farmer.ham.txt")));
    }
   
}
