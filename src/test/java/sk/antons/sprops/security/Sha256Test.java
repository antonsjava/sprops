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
public class Sha256Test {

    @Test
	public void hashSomething() throws Exception {
        String text = "same sprostosti";
        byte[] bytes = Sha256.hash(text);
        String encoded = Base64.encode(bytes);
        Assert.assertEquals("PBPy7YxSeQWNEQLAec54f4x/m+iPCU2Z9I43++5j9Gk=", encoded);
    }
    
    @Test
	public void hashEmpty() throws Exception {
        String text = "";
        byte[] bytes = Sha256.hash(text);
        String encoded = Base64.encode(bytes);
        Assert.assertEquals("47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=", encoded);
    }

}
