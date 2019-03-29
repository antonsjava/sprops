/*
 * 
 */
package sk.antons.sprops.alg;

import java.util.Properties;
import org.junit.Assert;
import org.junit.Test;
import sk.antons.sprops.PropertiesEncoder;

/**
 *
 * @author antons
 */
public class SimpleAesPropertiesEncoderTest {
    private static int ALG = SpropsAlgFactory.SIMPLE_AES;
    private static String PASS = "nejakypass";

    
    @Test
	public void decodeSimple() throws Exception {
        PropertiesEncoder encoder = PropertiesEncoder.instance(PASS);
        Properties p = new Properties();
        String text = "sprops:AAAAIB84xpG9Qmcfo2QB5sM284tJZQXWjf8NBG7tV5wfomOhs60XA5iyvvdHAE/Kna9+ew==";
        p.setProperty("encoded", text);
        p.setProperty("simple", "jablko");
        encoder.addProperties(p);
        String decoded = encoder.getProperty("encoded");
        Assert.assertEquals("same sprostosti", decoded);
        decoded = encoder.getProperty("simple");
        Assert.assertEquals("jablko", decoded);
    }
    
    @Test
	public void decodeProperties() throws Exception {
        PropertiesEncoder encoder = PropertiesEncoder.instance(PASS);
        Properties p = new Properties();
        String text = "sprops:AAAAIB84xpG9Qmcfo2QB5sM284tJZQXWjf8NBG7tV5wfomOhs60XA5iyvvdHAE/Kna9+ew==";
        p.setProperty("encoded", text);
        p.setProperty("simple", "jablko");
        encoder.addProperties(p);
        Properties decodedProps = encoder.decode();
        String decoded = decodedProps.getProperty("encoded");
        Assert.assertEquals("same sprostosti", decoded);
        decoded = decodedProps.getProperty("simple");
        Assert.assertEquals("jablko", decoded);
    }

}
