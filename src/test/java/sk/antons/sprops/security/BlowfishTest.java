/*
 * 
 */
package sk.antons.sprops.security;

import org.junit.Assert;
import org.junit.Test;
import sk.antons.jaul.binary.Base64;

/**
 *
 * @author antons
 */
public class BlowfishTest {
    private static final String PASS = "nejakypass";

    @Test
	public void encodeSomething() throws Exception {
        String text = "same sprostosti";
        byte[] data = text.getBytes("UTF-8");
        byte[] bytes = Blowfish.encode(PASS.getBytes("UTF-8"), data);
        String encoded = Base64.encode(bytes);
        Assert.assertEquals("Tmoch1LsTTJbSnCL4KydZg==", encoded);
    }
    
    @Test
	public void decodeSomething() throws Exception {
        String text = "Tmoch1LsTTJbSnCL4KydZg==";
        byte[] data = Base64.decode(text);
        byte[] bytes = Blowfish.decode(PASS.getBytes("UTF-8"), data);
        String decoded = new String(bytes, "UTF-8");
        Assert.assertEquals("same sprostosti", decoded);
    }

    @Test
	public void encodeEmpty() throws Exception {
        String text = "";
        byte[] data = text.getBytes("UTF-8");
        byte[] bytes = Blowfish.encode(PASS.getBytes("UTF-8"), data);
        String encoded = Base64.encode(bytes);
        Assert.assertEquals("qWBq06LGYQk=", encoded);
    }
    
	public void decodeEmpty() throws Exception {
        String text = "qWBq06LGYQk=";
        byte[] data = Base64.decode(text);
        byte[] bytes = Blowfish.decode(PASS.getBytes("UTF-8"), data);
        String decoded = new String(bytes, "UTF-8");
        Assert.assertEquals("", decoded);
    }
}
