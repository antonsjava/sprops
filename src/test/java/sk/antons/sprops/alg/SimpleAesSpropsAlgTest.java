/*
 * 
 */
package sk.antons.sprops.alg;

import org.junit.Assert;
import org.junit.Test;
import sk.antons.sprops.SimpleEncoder;

/**
 *
 * @author antons
 */
public class SimpleAesSpropsAlgTest {
    private static int ALG = SpropsAlgFactory.SIMPLE_AES;
    private static String PASS = "nejakypass";

    @Test
	public void encodeSomething() throws Exception {
        SimpleEncoder enc = SimpleEncoder.instance(PASS);
        enc.algorithm(ALG);
        
        String text = "same sprostosti";
        String encoded = enc.encode(text);
        Assert.assertTrue(encoded.startsWith("sprops:"));
        String decoded = enc.decode(encoded);
        Assert.assertEquals("same sprostosti", decoded);

    }
    
    @Test
	public void decodeSomething() throws Exception {
        SimpleEncoder enc = SimpleEncoder.instance(PASS);
        
        String text = "sprops:AAAAIB84xpG9Qmcfo2QB5sM284tJZQXWjf8NBG7tV5wfomOhs60XA5iyvvdHAE/Kna9+ew==";
        String decoded = enc.decode(text);
        Assert.assertEquals("same sprostosti", decoded);
    }

}
