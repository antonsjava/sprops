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
public class AesTest {
    private static final String PASS = "nejakypass121212";

    @Test
	public void encodeSomething() throws Exception {
        String text = "same sprostosti";
        byte[] data = text.getBytes("UTF-8");
        byte[] bytes = Blowfish.encode(PASS.getBytes("UTF-8"), data);
        String encoded = Base64.encode(bytes);
        Assert.assertEquals("iwhDR/rmF1TDWukuB8Z1pQ==", encoded);
    }
    
    @Test
	public void decodeSomething() throws Exception {
        String text = "iwhDR/rmF1TDWukuB8Z1pQ==";
        byte[] data = Base64.decode(text);
        byte[] bytes = Blowfish.decode(PASS.getBytes("UTF-8"), data);
        String decoded = new String(bytes, "UTF-8");
        Assert.assertEquals("same sprostosti", decoded);
    }

    @Test
	public void encodeEmpty() throws Exception {
        String text = "";
        byte[] iv = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        byte[] data = text.getBytes("UTF-8");
        byte[] bytes = Aes.encode(PASS.getBytes("UTF-8"), iv, data);
        String encoded = Base64.encode(bytes);
        Assert.assertEquals("4cpvmEvJWhLQESi1ZxrtTA==", encoded);
    }
    
	public void decodeEmpty() throws Exception {
        String text = "qWBq06LGYQk=";
        byte[] iv = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        byte[] data = Base64.decode(text);
        byte[] bytes = Aes.decode(PASS.getBytes("UTF-8"), iv, data);
        String decoded = new String(bytes, "UTF-8");
        Assert.assertEquals("", decoded);
    }
}
